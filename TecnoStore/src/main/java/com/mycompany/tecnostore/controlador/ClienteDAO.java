
package com.mycompany.tecnostore.controlador;



import com.mycompany.tecnostore.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;


public class ClienteDAO implements IntGestionarClientes{
    
    
    @Override
    public void registrarCl(Cliente cliente) {
        String sql = "INSERT INTO cliente(nombre, identificacion, correo, telefono) VALUES(?,?,?,?)";
        
        try(Connection conexion = ConexionDb.getInstancia().conectar();
            PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS ) ){//este ultimo parametro me retorna el id de la  statement
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getIdentificacion());
            stmt.setString(3, cliente.getCorreo());
            stmt.setString(4, cliente.getTelefono());
            stmt.executeUpdate();
            
            // obtener id
            // con getGeneratedKeys puedo obtener el id generado en el statement
            // solo funciona con inserciones
           ResultSet rs = stmt.getGeneratedKeys(); 
           if (rs.next()) {
               cliente.setId(rs.getInt(1));
           }
            System.out.println("****** Registro exitoso ******");
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }

    @Override
    public void actualizarCl(Cliente cliente, int id) {
        String sql = "UPDATE Cliente SET nombre=?, identificacion=?, correo=?, telefono=?  WHERE id=?";
        
        try(Connection conexion = ConexionDb.getInstancia().conectar();
            PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            cliente.setId(id);
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getIdentificacion());
            stmt.setString(3, cliente.getCorreo());
            stmt.setObject(4, cliente.getTelefono());  
            stmt.setInt(5, id); 
            
            stmt.executeUpdate();// activa el codigo sql

            System.out.println(cliente);
            System.out.println("****** Actualizacion exitosa ******");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarCl(int id) {
        String sql = "DELETE FROM Cliente where id=?";
        
        try(Connection conexion = ConexionDb.getInstancia().conectar();
            PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            stmt.setInt(1, id);
            
            stmt.executeUpdate();

        }catch(SQLIntegrityConstraintViolationException e2){
             System.out.println("**** No se puede eliminar porque tiene ventas registradas ****");
        
        }catch(SQLException e){
            System.out.println("***** Error en eliminar de la base de datos *****");
        }
    }

    @Override
    public ArrayList<Cliente> listarCl() {
         String sql = "SELECT * FROM Cliente";
        
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try(Connection conexion = ConexionDb.getInstancia().conectar();
            PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
               
                Cliente cliente = new Cliente(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5) );
                
                clientes.add(cliente);
            
            }
            return clientes;
        
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return clientes;
    }
    
     public Optional<Cliente> buscar(int id){
    
        String sql = "SELECT * FROM Cliente WHERE id=?";
        Cliente cliente = new  Cliente();
        try(Connection conexion = ConexionDb.getInstancia().conectar();
            PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            stmt.setInt(1, id);
            ResultSet rs= stmt.executeQuery();
            
             if (rs.next()) {
            
            cliente.setId(rs.getInt(1));
            cliente.setNombre(rs.getString(2));
            cliente.setIdentificacion(rs.getString(3));
            cliente.setCorreo(rs.getString(4));
            cliente.setTelefono(rs.getString(5));
            
            // uso de optional por si el sql no trae resultados
            Optional<Cliente> optCl = Optional.ofNullable(cliente);
            
            return optCl;
        } else {
                 System.out.println("\n****** Cliente no encontrado en la base de datos ******");
             }
        
        }catch(SQLException e){
                System.out.println("\n****** La tabla Cliente no tiene ningun registro ******");
        }
        return Optional.empty();
    };
    
}
