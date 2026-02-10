
package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.ItemVenta;
import java.util.ArrayList;
import java.util.Optional;


public interface IntGestionarItemVentas {
    
    Optional<ItemVenta> RegistrarIv(ItemVenta itemVenta);
    public void actualizarIV(ItemVenta itemVenta);
    ArrayList<ItemVenta> buscar(int id);
    ArrayList<ItemVenta> listarIV();
}
