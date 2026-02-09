
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

    ConexionDb con = new ConexionDb();
    Connection conexion = con.conectar();
    
    
    @Override
    public Optional<ItemVenta> RegistrarIv(ItemVenta itemVenta) {
        
          
         String sql = "INSERT INTO Detalle_ventas(id_venta, id_celular, cantidad, subtotal) VALUES(?,?,?,?)";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
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
            
            Optional<ItemVenta> optItemVenta = buscar(itemVenta.getId());
            System.out.println("****** Registro exitoso ******");
            return optItemVenta;
            
        }catch(SQLException e){
            System.out.println("****** Registro no exitoso ******");
        }
        return Optional.empty();
     }
    
    @Override
    public Optional<ItemVenta> buscar(int id){

            String sql = "SELECT * FROM Detalle_ventas WHERE id=?";
            ItemVenta itemVenta = new ItemVenta();

            try(PreparedStatement stmt = conexion.prepareStatement(sql)){

                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {

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

                    // uso de optional por si el sql no trae resultados
                    Optional<ItemVenta> optItemVenta = Optional.ofNullable(itemVenta); 

                    return optItemVenta;
                } else {
                    System.out.println("\n****** Detalle de venta no encontrado en la base de datos ******");
                }

            }catch(SQLException e){
                e.printStackTrace();
            }
            return Optional.empty();
    }    
    
     @Override
    public ArrayList<ItemVenta> listarIV() {
        
        String sql = "SELECT * FROM detalle_ventas";
        
        ArrayList<ItemVenta> itemVentas = new ArrayList<>();
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            
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
