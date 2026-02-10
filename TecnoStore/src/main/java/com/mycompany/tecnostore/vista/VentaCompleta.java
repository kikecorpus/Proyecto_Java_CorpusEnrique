package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.VentaDAO;
import com.mycompany.tecnostore.modelo.ItemVenta;
import com.mycompany.tecnostore.modelo.Venta;
import java.util.Optional;


public class VentaCompleta {
    
  private  GestorVentas gv = new GestorVentas();
  private  GestorItemVentas giv = new GestorItemVentas();

  public Venta realizarVenta(){
        
         // paso 1: registrar venta
         Venta venta = gv.registrarVenta();
         
         //paso 2: registro de item venta
         ItemVenta itemVenta = giv.registrarItemVenta(venta);
        
         // paso 4: informar al usuario
         System.out.println("***** Venta exitosa *****");
         
         return venta;
    } 

}
