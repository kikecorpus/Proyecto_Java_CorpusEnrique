
package com.mycompany.tecnostore.controlador;

import java.util.Scanner;


public class Validador {
    
    public static int validateOptions(){
        
        return 0;
    };
    
    public static double validatePositiveDouble(double valor){
        
        while(valor< 0){
            
            System.out.println("\n Solo se admiten valores positivos. \nIngrese nuevamente el valor: ");
            valor = new Scanner(System.in).nextDouble();
            return valor ;
        }
        
        return valor;
    };
    
    public static int validatePositiveInt(int valor){
        
        while(valor< 0){
            
            System.out.println("\n Solo se admiten valores positivos. \nIngrese nuevamente el valor: ");
            valor = new Scanner(System.in).nextInt();
            
            return valor ;
        }
        
        return valor;
    }
    
}
