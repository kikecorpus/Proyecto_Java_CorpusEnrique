
package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.ItemVenta;
import java.util.ArrayList;


public class Calculos {
    
    static CelularDAO cdao = new CelularDAO();
    
    // calulos de subtotal con StreamApi 
    
    public static double calcularSubtotal(ArrayList<ItemVenta> detalles){
        
          double subtotal = detalles.stream().mapToDouble(x -> x.getId_celular().getPrecio() * x.getCantidad()).sum();

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
        
        double subtotal  = calcularSubtotal();
        double iva = calularIva(subtotal);
        double subIva = subtotal + iva ;
        
        return subIva;
    }
    
    // calculos de total con StreamApi 
    
    public static double calcularTotal(ArrayList<ItemVenta> detalles){
        
        double total = detalles.stream().mapToDouble(ItemVenta::getSubtotal).sum();

        return total;
    
    }

    
    
}
