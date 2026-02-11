
package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.ItemVenta;
import java.util.ArrayList;


public class Calculos {
    
    static CelularDAO cdao = new CelularDAO();
    
    // calulos de subtotal
    public static double calcularSubtotal(int cantidad,int id){
        
        double subtotal = cdao.buscar(id).get().getPrecio() * cantidad;

        return subtotal;
    
    }
    
    public static double calularIva(double subtotal){
    
        double iva = subtotal * 0.19;
        
        return iva;
    }
       
    public static  double calcularSubtotalIva(double subtotal, double iva){
    
        double subIva = subtotal + iva;
        
        return subIva;
    }
    
    public static double calcularSubtotalIva(int cantidad, int id){
        
        double subtotal  = calcularSubtotal(cantidad, id);
        double iva = calularIva(subtotal);
        double subIva = subtotal + iva ;
        
        return subIva;
    }
    
    // calculos de totales 
    
    public static double calcularTotal(ArrayList<ItemVenta> detalles){
        
        double total = detalles.stream().mapToDouble(ItemVenta::getSubtotal).sum();

        return total;
    
    }

    
    
}
