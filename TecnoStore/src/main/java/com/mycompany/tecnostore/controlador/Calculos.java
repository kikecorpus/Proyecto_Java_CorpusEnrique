
package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.ItemVenta;
import java.util.ArrayList;


public class Calculos {
    private static final double IVA = 0.19;
   
    
    // calulos de subtotal con StreamApi 
    //evita llamados a la base de datos 
    
    public static double calularIva(double subtotal){
    
        double iva = subtotal * IVA;
        
        return iva;
    }
    
    public static double calcularTotalConIva(ArrayList<ItemVenta> detalles){
        
        double total  = calcularTotal(detalles);
        double iva = calularIva(total);
        double totalIva = total + iva ;
        
        return totalIva;
    }
    
    // calculos de total con StreamApi 
    
    public static double calcularTotal(ArrayList<ItemVenta> detalles){
        
        double total = detalles.stream().mapToDouble(ItemVenta::getSubtotal).sum();

        return total;
    
    }

    
    
}
