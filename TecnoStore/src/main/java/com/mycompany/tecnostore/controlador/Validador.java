
package com.mycompany.tecnostore.controlador;

import java.util.Scanner;


public class Validador {
    
    // Validaciones Celular
    public static double validatePositiveDouble(double valor){
        
        while(valor< 0){
            
            System.out.println("\n***** Solo se admiten valores positivos. ***** \nIngrese nuevamente el valor: ");
            valor = new Scanner(System.in).nextDouble();
            return valor ;
        }
        
        return valor;
    };
    
    public static int validatePositiveInt(int valor){
        
        while(valor< 0){
            
            System.out.println("\n***** Solo se admiten valores positivos. ***** \nIngrese nuevamente el valor: ");
            valor = new Scanner(System.in).nextInt();
            
            return valor ;
        }
        
        return valor;
    }
    
    // Validaciones Menu
    
    public static int validateMenu(int minV,int maxV, String mensaje){
        
        int op = 0;
        try{
 
            System.out.println(mensaje);
        
           op = new Scanner(System.in).nextInt(); 
            
            while (op>maxV || op<minV){
            
            System.out.println("Ingresa una opcion valida");
            op = new Scanner(System.in).nextInt(); 
            }
            
             return op;
            
        }catch (Exception e) {
                 System.out.println("**** Solo se admiten numeros ****");
                }  
        return op;
        
        }
    
    
}
