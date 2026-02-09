package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.VentaDAO;
import com.mycompany.tecnostore.modelo.ItemVenta;
import com.mycompany.tecnostore.modelo.Venta;
import java.util.Optional;


public class VentaCompleta {
    
  private  GestorVentas gv = new GestorVentas();
  private  GestorItemVentas giv = new GestorItemVentas();
    
    
   public void flujoDeVenta(){
        
         Venta venta = gv.registrarVenta();
         ItemVenta itemVenta = giv.registrarItemVenta(venta);
         System.out.println("***** Venta exitosa *****");
         
    } 
    
        
    
}
