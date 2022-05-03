/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import com.proyecto.hellofx.PrimaryController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author santimiquel
 */
public class BDA {

    private Connection conexion;
    private String mensaje;
    private boolean isConected;
    private int lineasModificadas;
    private int codigoError = 0;
    private String mensajeError;


    public BDA() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean conectar(String bd, String login, String password) {

        String url = "jdbc:mysql://localhost:3306/" + bd;

        try {
            conexion = DriverManager.getConnection(url, login, password);
            isConected = true;
            
        } catch (SQLException e) {
            isConected = false;
            mensaje = e.getMessage();
        }
        return isConected;
    }

    public boolean desconectar() {
        boolean isClosed;
        try {
            conexion.close();
            isClosed = true;
            

        } catch (SQLException e) {
            isClosed = false;
            mensaje = e.getMessage();

        }
        return isClosed;
    }

    public String horaPeticion() {
        LocalTime ahora = LocalTime.now();
        DateTimeFormatter patron = DateTimeFormatter.ofPattern("HH:mm");
        String hora = ahora.format(patron);
        return hora;

    }

    public boolean isConected() { // revisar
        return isConected;
    }

    public int insertar(int idParque, String nombre, double extension, int idComunidad) {
        
        String sql = "INSERT INTO PARQUES(id, nombre, extension, idcomunidad) VALUES"
                + "(?,?,?,?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idParque);
            ps.setString(2, nombre);
            ps.setDouble(3, extension);
            ps.setInt(4, idComunidad);
            
            lineasModificadas = ps.executeUpdate();

        } catch (SQLException e) {
            codigoError = e.getErrorCode();
            mensajeError = e.getMessage();
             
        }
        return codigoError;
    }

    public int getLineasModificadas() {
        return lineasModificadas;
    }

    public int getCodigoError() {
        return codigoError;
    }

    public String getMensajeError() {
        return mensajeError;
    }
    

}
