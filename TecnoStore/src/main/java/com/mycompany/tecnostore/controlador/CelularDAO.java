/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.CategoriaGama;
import com.mycompany.tecnostore.modelo.Celular;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class CelularDAO implements IntGestionarCelulares{
    
    ConexionDb con = new ConexionDb();
    Connection conexion = con.conectar();
      
    //celulares (id, marca, modelo, sistema_operativo, gama, precio, stock)

    @Override
    public void registrarC(Celular celular) {
        
        String sql = "INSERT INTO Celulares(marca, modelo, sistema_operativo, gama, precio, stock) VALUES(?,?,?,?,?,?)";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ){
            
            stmt.setString(1, celular.getMarca());
            stmt.setString(2, celular.getModelo());
            stmt.setString(3, celular.getSistema_operativo());
            stmt.setString(4, celular.getGama().name()); // ENUM → STRING
            stmt.setDouble(5, celular.getPrecio());
            stmt.setInt(6, celular.getStock());
            stmt.executeUpdate();
            
            // obtener id
           ResultSet rs = stmt.getGeneratedKeys();
           if (rs.next()) {
               celular.setId(rs.getInt(1));
           }
            System.out.println("****** Registro exitoso ******");
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }

    @Override
    public void actualizarC(Celular celular, int id) {
    String sql = "UPDATE Celulares set marca=?, modelo=?, sistema_operativo=?, gama=?, precio= ?, stock= ?  where id=?";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            stmt.setString(1, celular.getMarca());
            stmt.setString(2, celular.getModelo());
            stmt.setString(3, celular.getSistema_operativo());
            stmt.setObject(4, celular.getGama().name());
            stmt.setDouble(5, celular.getPrecio());
            stmt.setInt(6, celular.getStock());
            stmt.setInt(7, id);
            stmt.executeUpdate();
            celular.setId(id);
 
            System.out.println(celular);
            System.out.println("****** Actualizacion exitosa ******");
        }catch(SQLException e){
            e.printStackTrace();
        }
            }

    @Override
    public void eliminarC(int id) {
        
        String sql = "DELETE FROM Celulares where id=?";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            stmt.setInt(1, id);
            stmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void listarC() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Celular buscar(int id){
    
        String sql = "SELECT * FROM Celulares where id=?";
        Celular cel = new Celular();
        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            stmt.setInt(1, id);
            ResultSet rs= stmt.executeQuery();
            
             if (rs.next()) {
            
            cel.setId(rs.getInt("id"));
            cel.setMarca(rs.getString("marca"));
            cel.setModelo(rs.getString("modelo"));
            cel.setSistema_operativo(rs.getString("sistema_operativo"));
            cel.setGama(CategoriaGama.valueOf(rs.getString("gama")));
            cel.setPrecio(rs.getDouble("precio"));
            cel.setStock(rs.getInt("stock"));
            
            return cel;
        }
        
        }catch(SQLException e){
            e.printStackTrace();
        }
        return cel;
    };
    
    
}
