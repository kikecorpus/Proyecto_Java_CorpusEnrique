package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.VentaDAO;
import com.mycompany.tecnostore.modelo.ItemVenta;
import com.mycompany.tecnostore.modelo.Venta;
import java.util.Optional;


public class VentaCompleta {
    
  private  GestorVentas gv = new GestorVentas();
  private  GestorItemVentas giv = new GestorItemVentas();
  private  VentaDAO vdao = new VentaDAO();
    
   
  
  public Venta flujoDeVenta(){
        
         // paso 1: registrar venta
         Venta venta = gv.registrarVenta();
         
         //paso 2: registro de item venta
         ItemVenta itemVenta = giv.registrarItemVenta(venta);
         
         //paso 3: acutalizar total de venta
         venta = vdao.buscar(venta.getId()).get();
         
         // paso 4: informar al usuario
         System.out.println("***** Venta exitosa *****");
         
         return venta;
    } 
    
  public venta actulizarTota
        
    
}
