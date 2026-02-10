
package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.Venta;
import java.util.ArrayList;
import java.util.Optional;


public interface IntGestionarVentas {
    
    Optional<Venta> registrarV(Venta venta);
    void actualizarV(Venta venta);
    //void eliminarV(int id);     // eliminar no es optimo deberia de crearse un log, o inactivar el producto 
    ArrayList<Venta> listarV();
    
}
