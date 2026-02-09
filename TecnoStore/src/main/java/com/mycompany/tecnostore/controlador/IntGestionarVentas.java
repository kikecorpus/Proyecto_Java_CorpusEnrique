
package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.Venta;
import java.util.ArrayList;
import java.util.Optional;


public interface IntGestionarVentas {
    
    Optional<Venta> registrarV(Venta venta);
    void actualizarV(Venta venta, int id);
    void eliminarV(int id);
    ArrayList<Venta> listarV();
    
}
