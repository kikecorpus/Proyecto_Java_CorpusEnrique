package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.Cliente;
import com.mycompany.tecnostore.modelo.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

public class VentaDAO implements IntGestionarVentas {
    
   
    //ventas (id, id_cliente, fecha, total)

    //CRUD
    @Override
    public Optional<Venta> registrarV(Venta venta) {
        
        String sql = "INSERT INTO Venta(id_cliente) VALUES(?)";
        
        try(Connection conexion = ConexionDb.getInstancia().conectar();
            PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            stmt.setInt(1, venta.getId_cliente().getId()); // Solo se guarda el id del cliente
            stmt.executeUpdate();
            
            // obtener id generado
            ResultSet rs = stmt.getGeneratedKeys(); 
            if (rs.next()) {
                venta.setId(rs.getInt(1));
            }
            
            Optional<Venta> optVenta = Optional.of(venta);
            
            System.out.println("****** Registro exitoso ******");
            return optVenta;
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return Optional.empty();
    }

    @Override
    public void actualizarV(Venta venta, int id) {
        String sql = "UPDATE Ventas SET id_cliente=?, fecha=?, total=? WHERE id=?";
        
        try(Connection conexion = ConexionDb.getInstancia().conectar();
            PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            stmt.setInt(1, venta.getId_cliente().getId()); // Solo guardamos el ID del cliente
            stmt.setString(2, venta.getFecha());
            stmt.setDouble(3, venta.getTotal());
            stmt.setInt(4, id);
            stmt.executeUpdate();
            venta.setId(id);
 
            System.out.println(venta);
            System.out.println("****** Actualizacion exitosa ******");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarV(int id) {
        
        String sql = "DELETE FROM Ventas WHERE id=?";
        
        try(Connection conexion = ConexionDb.getInstancia().conectar();
            PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            
            if (filas > 0) {
                System.out.println("Registro eliminado correctamente");
            } else {
                System.out.println("No se encontró una venta con ese ID");
            }
        }
        catch(SQLException e){
            System.out.println("***** Error en eliminar de la base de datos *****");
        }
    }

    @Override
    public ArrayList<Venta> listarV() {
        
        String sql = "SELECT * FROM Venta";
        
        ArrayList<Venta> ventas = new ArrayList<>();
        
        try(Connection conexion = ConexionDb.getInstancia().conectar();
            PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                
                // Crear objeto Cliente solo con el ID
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                
                Venta venta = new Venta(
                    rs.getInt("id"),
                    cliente,
                    rs.getString("fecha"),
                    rs.getDouble("total")
                );
                
                ventas.add(venta);
            }
            return ventas;
        
        } catch (SQLException ex) {
            System.getLogger(VentaDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return ventas;
    }
    
    // buscar 
    public Optional<Venta> buscar(int id){
    
        String sql = "SELECT * FROM Ventas WHERE id=?";
        Venta venta = new Venta();
        
        try(Connection conexion = ConexionDb.getInstancia().conectar();
            PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                
                // Crear objeto Cliente solo con el ID
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                
                venta.setId(rs.getInt("id"));
                venta.setId_cliente(cliente);
                venta.setFecha(rs.getString("fecha"));
                venta.setTotal(rs.getDouble("total"));
                
                // uso de optional por si el sql no trae resultados
                Optional<Venta> optVenta = Optional.ofNullable(venta); 
                
                return optVenta;
            } else {
                System.out.println("\n****** Venta no encontrada en la base de datos ******");
            }
        
        }catch(SQLException e){
                System.out.println("\n****** La tabla Venta no tiene ningun registro ******");
        }
        return Optional.empty();
    }
    
}

