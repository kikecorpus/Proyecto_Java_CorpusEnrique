
package com.mycompany.tecnostore.controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Patrón Singleton: Garantiza una única instancia de conexión a la BD
 
public class ConexionDb{
   
    // Instancia única (Singleton)
    // 1. volatile para evitar problemas de visibilidad en memoria
    private static volatile ConexionDb instancia;
    private Connection conexion;
    
    // Datos de conexión
    private static final String url =  "jdbc:mysql://localhost:3306/tecnostore";
    private static final String user  = "root";
    private static final String pass = "campus2023";
    
    private ConexionDb(){
        
         try {
           
            this.conexion = DriverManager.getConnection(url, user, pass );
            
         } catch (SQLException e) {
            System.err.println("***** Error al conectar con la base de datos *****");
            e.printStackTrace();
        }
    }
    
    
    public static ConexionDb getInstancia() {
        if (instancia == null) {
            // Se sincroniza con la clase porque el método es estático
            synchronized (ConexionDb.class) {
                if (instancia == null) {
                    instancia = new ConexionDb();
                }
            }
        }
        return instancia;
    }
    
    public Connection conectar() throws SQLException {
        
          if (conexion == null || conexion.isClosed()) {
        this.conexion = DriverManager.getConnection(url, user, pass);
        }
        return conexion;
    
    }
    
}
