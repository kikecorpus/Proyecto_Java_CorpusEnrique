package com.mycompany.tecnostore.vista;


import com.mycompany.tecnostore.controlador.Calculos;
import com.mycompany.tecnostore.controlador.ItemsVentaDAO;
import com.mycompany.tecnostore.controlador.Validador;
import com.mycompany.tecnostore.controlador.VentaDAO;
import com.mycompany.tecnostore.modelo.ItemVenta;
import com.mycompany.tecnostore.modelo.Venta;
import java.util.ArrayList;



public class VentaCompleta {
    
  private  GestorVentas gv = new GestorVentas();
  private  GestorItemVentas giv = new GestorItemVentas();
  
//patron facade
  public void realizarVentaCompleta(){
          VentaDAO vdao = new VentaDAO();
          ItemsVentaDAO ivdao = new ItemsVentaDAO();
         // paso 1: registrar venta
         Venta venta = gv.registrarVenta();
            // opcion regresar
          if(venta == null){
              System.out.println("Venta no se pudo registrar");
              System.out.println("Regresando ...");
             return;
         }
        
         //paso 2: registro de item venta
         ArrayList<ItemVenta> detalles = giv.registrarItemVenta(venta);
         
         // paso 3 Actualizar valores 
         double total = Calculos.calcularTotal(detalles);
         double IVA = Calculos.calularIva(total);
         double totalConIva = Calculos.calcularTotalConIva(detalles);

         //enviar nuevos valores a la base de datos
         venta.setTotal(totalConIva);
         vdao.actualizarV(venta);
       
         // paso 4: informar al usuario
        
         imprimirFactura(venta, detalles, IVA, totalConIva);
         
         
    } 
  
  public void actualizarVentaCompleta(){
      //actualiza info de la clase venta y la retorna
       Venta venta = gv.actualizarVenta();
       
       if(venta == null){
              System.out.println("Venta no se pudo actualizar");
              System.out.println("Regresando ...");
             return;
         }
       
       System.out.println("=============================");
       System.out.println("****  Detalle de Ventas  **** ");
       System.out.println("=============================");

       //para referencia luego  detalles de la venta
       ArrayList<ItemVenta> detalles = giv.actualizarItemVenta(venta);
           
       detalles.forEach( d -> System.out.println(d));
      
      
  }
  
  public void buscarVentaCompleta(){
      Venta venta = gv.buscarVenta();
      ArrayList<ItemVenta> detalles = giv.buscarIV(venta);
      
      if(detalles == null ){
        System.out.println(" detalles Vacio, Regresando ...");
        return;}
  }
          
  public void imprimirFactura(Venta venta, ArrayList<ItemVenta> detalles, double IVA, double total) {
      
        
        
        System.out.println("\n=========== FACTURA ===========");
        System.out.println("Venta N°: " + venta.getId());
        System.out.println("Fecha   : " + venta.getFecha());
        System.out.println("--------------------------------");
        System.out.println("Cliente : " + venta.getId_cliente().getNombre());
        System.out.println("Correo  : " + venta.getId_cliente().getCorreo());
        System.out.println("--------------------------------");

        detalles.forEach(d -> {
            System.out.printf(
                "%s %s x%d  $%.2f%n",
                d.getId_celular().getMarca(),
                d.getId_celular().getModelo(),
                d.getCantidad(),
                d.getSubtotal()
            );
        });

        System.out.println("--------------------------------");
        System.out.printf("Subtotal: $%.2f%n", total);
        System.out.printf("Iva: $%.2f%n", IVA);
        System.out.printf("TOTAL: $%.2f%n", venta.getTotal());
        System.out.println("================================\n");
    }
}
