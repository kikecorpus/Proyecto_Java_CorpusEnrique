package com.mycompany.tecnostore.vista;


import com.mycompany.tecnostore.controlador.Validador;
import com.mycompany.tecnostore.modelo.ItemVenta;
import com.mycompany.tecnostore.modelo.Venta;
import java.util.ArrayList;



public class VentaCompleta {
    
  private  GestorVentas gv = new GestorVentas();
  private  GestorItemVentas giv = new GestorItemVentas();
  

  public void realizarVentaCompleta(){
      
      
         // paso 1: registrar venta
         Venta venta = gv.registrarVenta();
         
         //paso 2: registro de item venta
         ArrayList<ItemVenta> detalles = giv.registrarItemVenta(venta);
        
         // paso 3: informar al usuario
        
         System.out.println("***** Venta exitosa *****");
         
         System.out.print("Factura: \n"+ venta);
         
         detalles.forEach(d -> System.out.println(d));
         
         
    } 
  
  public void actualizarVentaCompleta(){
      //actualiza info de la clase venta y la retorna
       Venta venta = gv.actualizarVenta();
       //para referencia luego  detalles de la venta
       ArrayList<ItemVenta> detalles = giv.actualizarItemVenta(venta);
           
       detalles.forEach( d -> System.out.println(d));
      
      
  }

}
