package com.proyecto.hellofx;

import Datos.BDA;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {

    @FXML
    private Label etiquetaOutput;
    @FXML
    private TextField idParqueInput;
    @FXML
    private TextField nombreInput;
    @FXML
    private TextField extensionInput;
    @FXML
    private TextField comunidadIDInput;
    @FXML
    private Button guardar_Button;
    @FXML
    private Button conectarBD;

    private boolean isBDConected;

    private int errorSQL;

    BDA bd = new BDA();
    @FXML
    private Button clear;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        guardar_Button.setDisable(true);
        bdConexionResult();
    }

    @FXML
    private void conectarBD(ActionEvent event) {
        String usuario = "root";
        String password = "san608921482";
        String url = "parquesnaturales";
        String imprime;

        imprime = "Conectando con la Base de Datos => " + bd.horaPeticion() + "\n";

        if (bd.conectar(url, usuario, password)) {
            imprime += "Conexion establecida con la Base de Datos => "
                    + bd.horaPeticion() + "\n";

            guardar_Button.setDisable(false);
            isBDConected = true;
        } else {

            isBDConected = false;

        }
        bdConexionResult();
    }

    private void recogerDatos() {

        String comunity = comunidadIDInput.getText();
        String parque = idParqueInput.getText();
        int idParque = Integer.parseInt(parque);
        int idComunidad = Integer.parseInt(comunity);
        String nombre = nombreInput.getText();
        double extension = Double.parseDouble(extensionInput.getText());
        bd.insertar(idParque, nombre, extension, idComunidad);
        System.out.println(bd.getCodigoError());
        if (bd.getCodigoError() == 0) {
            showConfirmation();

        } else {
            switch (bd.getCodigoError()) {
                case 1062:
                    alertaSQL(Alert.AlertType.WARNING, "Problemas", "Error al insertar el registro", "El id de parque ya existe, modífiquelo.");
                    break;
                case 1452:
                    //CLAVE AJENA
                    alertaSQL(Alert.AlertType.WARNING, "Problemas", "Error al insertar el registro", "El id de la comunidad no existe.");
                    break;
                default:
                    alertaSQL(Alert.AlertType.WARNING, "Problemas", "Error al insertar el registro", bd.getMensajeError() + " \n\n Codigo SQL:" + bd.getCodigoError());
                    break;
            }
        }

    }

    @FXML
    private void saveData(ActionEvent event) {
        if (idParqueInput.getText().isEmpty() || nombreInput.getText().isEmpty() || extensionInput.getText().isEmpty() || comunidadIDInput.getText().isEmpty()) {
            showAlert();
        } else {
            recogerDatos();
        }
    }

    private void bdConexionResult() {
        if (isBDConected) {
            etiquetaOutput.setStyle("-fx-text-fill:green");
            etiquetaOutput.setText("CONECTADO");
        } else {
            etiquetaOutput.setStyle("-fx-text-fill:red");
            etiquetaOutput.setText("NO CONECTADO");
        }
    }

    private void alertaSQL(Alert.AlertType tipo, String titulo, String header, String content) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(header);
        alerta.setContentText(content);
        alerta.showAndWait();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alerta de campos vacios");
        alert.setHeaderText("Cuidado, estas dejando campos vacios");
        alert.setContentText("Por favor, revisa que todos los campos esten cumplimentados");
        alert.showAndWait();
    }

    private void showConfirmation() {
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setTitle("Registro");
        confirmation.setHeaderText("Los datos se han introducido correctamente");
        confirmation.setContentText("Se han modificado " + bd.getLineasModificadas() + " lineas");
        confirmation.showAndWait();
    }

    @FXML
    private void clearAll(ActionEvent event) {

        idParqueInput.setText(null);
        nombreInput.setText(null);
        extensionInput.setText(null);
        comunidadIDInput.setText(null);
        bdConexionResult();

    }
}

//public class PrimaryController implements Initializable {
//
//    @FXML
//    private TextField userInput;
//    @FXML
//    private TextField pwdInput;
//    @FXML
//    private TextField bdInput;
//    @FXML
//    private Button closeConexion_Button;
//    @FXML
//    private Button startConexion_Button;
//    @FXML
//    private TextArea result;
//
//    BDA iniciarBD = new BDA();
//
//    static private boolean isBDConected;
//
////    SecondaryController segundoControlador = new SecondaryController();
////    private boolean isBDConected;
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        closeConexion_Button.setDisable(true);
//    }
//
//    public static boolean isBDConected() {
//        return isBDConected;
//    }
//
//    @FXML
//    private void finish(ActionEvent event) {
//        if (iniciarBD.desconectar()) {
//            result.setText("Conexion cerrada => " + iniciarBD.horaPeticion() + "\n");
//            clearTextFields();
//        } else {
//            result.setText("Error en el cierre de la conexion => " + iniciarBD.horaPeticion()
//                    + "\n" + iniciarBD.getMensaje());
//
//        }
//    }
//
////        private boolean isFill(String usuario, String password, String bd) {
//        String relleneCampo = "Este campo debe de estar completo";
//        boolean isTexted = false;
//        if (usuario.equals(null)) {
//            userInput.setText(relleneCampo);
//            isTexted = true;
//        } else if (password.equals(null)) {
//            pwdInput.setText(relleneCampo);
//            isTexted = true;
//        } else if (bd.equals(null)) {
//            bdInput.setText(relleneCampo);
//            isTexted = true;
//
//        }
//
//        return isTexted;
//    }
//    private void iniciar2Controlador() {
//        try {
//            // Crear una nueva ventana fxml que esta enlazada con el archivo SecondaryController.java
//            FXMLLoader secondaryScene = new FXMLLoader(getClass().getResource("secondary.fxml"));
//            Parent root = secondaryScene.load();
//            SecondaryController controlador2 = secondaryScene.getController();
//            Scene scene = new Scene(root);
//            Stage stage = new Stage();
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.setScene(scene);
//            stage.showAndWait();
//        } catch (IOException e) {
//        }
//    }
//
////    private boolean isFill(String usuario, String password, String bd) {
////        String relleneCampo = "Este campo debe de estar completo";
////        boolean isTexted = false;
////        if (usuario.equals(null)) {
////            userInput.setText(relleneCampo);
////            isTexted = true;
////        } else if (password.equals(null)) {
////            pwdInput.setText(relleneCampo);
////            isTexted = true;
////        } else if (bd.equals(null)) {
////            bdInput.setText(relleneCampo);
////            isTexted = true;
////
////        }
////
////        return isTexted;
////    }
//    
//}
