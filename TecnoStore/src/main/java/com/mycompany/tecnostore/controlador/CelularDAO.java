/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.Celular;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CelularDAO implements IntGestionarCelulares{
    
    ConexionDb con = new ConexionDb();
    Connection conexion = con.conectar();
      
    //celulares (id, marca, modelo, sistema_operativo, gama, precio, stock)

    @Override
    public void registrarC(Celular celular) {
        
        String sql = "insert into Celulares(marca, modelo, sistema_operativo, gama, precio, stock VALUES(?,?,?,?,?,?))";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql) ){
            
            stmt.setString(1, celular.getMarca());
            stmt.setString(2, celular.getModelo());
            stmt.setString(3, celular.getSistema_operativo());
            stmt.setObject(4, celular.getGama());
            stmt.setDouble(5, celular.getPrecio());
            stmt.setInt(6, celular.getStock());
            System.out.println(celular);
            System.out.println("****** Registro exitoso ******");
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }

    @Override
    public void actualizarC() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminarC() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void listarC() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
