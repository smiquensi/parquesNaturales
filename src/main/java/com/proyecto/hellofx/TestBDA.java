package com.proyecto.hellofx;

import java.sql.*;

public class TestBDA {

    public static void main(String[] args) throws Exception {
        String bd = "parquesnaturales";
        String login = "root";
        String password = "san608921482";
		
        String url = "jdbc:mysql://localhost:3306/" + bd ;
        
        try (Connection conn = DriverManager.getConnection(url, login, password)) {

            if (conn != null) {
		  //CONECTADOS CON LA BDA REFERENCIADA POR URL, CON EL USUARIO LOGIN Y SU PASSWORD
                System.out.println("CONEXION ESTABLECIDA con la base de datos " + url + " ... OK");
            }
			
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos " + bd);
			System.out.println("URL utilizada" + url);
            System.out.println("ERROR: " + ex.getMessage());
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
