
package com.mycompany.tecnostore.controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionDb {
    static String url = "jdbc:mysql://localhost:3306/tecnostore";
    static String user= "root";
    static String pass = "campus2023";
    
    
    public Connection conectar(){
        
        Connection con = null; 
        
        try{
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("**** conexion extiosa ****");
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return con;
    }
    
}
