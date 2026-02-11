
package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.Celular;
import com.mycompany.tecnostore.modelo.ItemVenta;
import com.mycompany.tecnostore.modelo.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;


public class ItemsVentaDAO implements IntGestionarItemVentas{

    @Override
    public Optional<ItemVenta> RegistrarIv(ItemVenta itemVenta) {
        
        ArrayList<ItemVenta> detalles = new ArrayList<>();

         String sql = "INSERT INTO Detalle_ventas(id_venta, id_celular, cantidad, subtotal) VALUES(?,?,?,?)";
        
        try(Connection conexion = ConexionDb.getInstancia().conectar();
            PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            stmt.setInt(1, itemVenta.getId_venta().getId()); // Solo guardamos el ID de la venta
            stmt.setInt(2, itemVenta.getId_celular().getId()); // Solo guardamos el ID del celular
            stmt.setInt(3, itemVenta.getCantidad());
            stmt.setDouble(4, itemVenta.getSubtotal());
            stmt.executeUpdate();
            
            // obtener id generado
            ResultSet rs = stmt.getGeneratedKeys(); 
            if (rs.next()) {
                itemVenta.setId(rs.getInt(1));
            }
            detalles = buscar(itemVenta.getId());
            
            Optional<ItemVenta> optItemVenta = Optional.of(detalles.getFirst());
            
            return optItemVenta;
            
        }catch(SQLException e){
            System.out.println("****** Registro no exitoso ******");
        }
        return Optional.empty();
     }
    
    @Override
    public void actualizarIV(ItemVenta itemVenta) {
        String sql = "UPDATE Detalle_ventas SET id_celular=?, cantidad=? WHERE id=?";
        
        try(Connection conexion = ConexionDb.getInstancia().conectar();
            PreparedStatement stmt = conexion.prepareStatement(sql)){

            stmt.setInt(1, itemVenta.getId_celular().getId()); // Solo guardamos el ID del celular
            stmt.setInt(2, itemVenta.getCantidad());
            stmt.setDouble(3, itemVenta.getId());

            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public ArrayList<ItemVenta> buscar(int id){

            String sql = "SELECT * FROM Detalle_ventas WHERE id=?";
            ArrayList<ItemVenta> detalles = new ArrayList<>();
            ItemVenta itemVenta = new ItemVenta();

            try(Connection conexion = ConexionDb.getInstancia().conectar();
                PreparedStatement stmt = conexion.prepareStatement(sql)){

                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {

                    // Crear objeto Venta solo con el ID
                    Venta venta = new Venta();
                    venta.setId(rs.getInt("id_venta"));

                    // Crear objeto Celular solo con el ID
                    Celular celular = new Celular();
                    celular.setId(rs.getInt("id_celular"));
                    
                    itemVenta.setId(rs.getInt("id"));
                    itemVenta.setId_venta(venta);
                    itemVenta.setId_celular(celular);
                    itemVenta.setCantidad(rs.getInt("cantidad"));
                    itemVenta.setSubtotal(rs.getDouble("subtotal"));

                    detalles.add(itemVenta);
                    
                    if(detalles.isEmpty()){
                        System.out.println("\n****** La venta no tiene ítems asociados ******");
                    }
                    
                    return detalles;
                }

            }catch(SQLException e){
                System.out.println("Erro en encontral al usuario");
            }
            return detalles;
    }    
    
     @Override
    public ArrayList<ItemVenta> listarIV() {
        
        String sql = "SELECT * FROM Detalle_ventas";
        
        ArrayList<ItemVenta> itemVentas = new ArrayList<>();
        
        try(Connection conexion = ConexionDb.getInstancia().conectar();
            PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                
                // Crear objeto Venta solo con el ID
                Venta venta = new Venta();
                venta.setId(rs.getInt("id_venta"));
                
                // Crear objeto Celular solo con el ID
                Celular celular = new Celular();
                celular.setId(rs.getInt("id_celular"));
                
                ItemVenta itemVenta = new ItemVenta(
                    rs.getInt("id"),
                    venta,
                    celular,
                    rs.getInt("cantidad"),
                    rs.getDouble("subtotal")
                );
                
                itemVentas.add(itemVenta);
            }
            return itemVentas;
        
        } catch (SQLException ex) {
            System.getLogger(ItemsVentaDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return itemVentas;
    }
}
