
package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.Celular;
import com.mycompany.tecnostore.modelo.Cliente;
import com.mycompany.tecnostore.modelo.Venta;
import com.mycompany.tecnostore.vista.GestorCelulares;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;


public class Validador {
    
    // Validaciones Celular
        // que stock y precio sean positivos
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
        // dentro de rango de opciones y  que sea numero  
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
    
    // validacion id Scanner
        // que sean numeros y que sea positivo en el Scanner
    public static int validateID(String mensaje){

        Scanner sc = new Scanner(System.in);
        int id;

        while (true) {
            System.out.println(mensaje);

            if (sc.hasNextInt()) {
                id = sc.nextInt();

                if (id >= 0) {
                    return id;
                } else {
                    System.out.println("****** El ID no puede ser negativo ******");
                }

            } else {
                System.out.println("****** Solo se aceptan números ******");
                sc.next(); 
            }
        }
    }

    //Validacion ResultSet 
    
    public static boolean validateResultSet(Optional<Celular> optCel) {

    if (optCel == null || optCel.isEmpty()) {
        System.out.println("***** No existe el celular *****");
        return false;
    }

    return true;
    }
    
    public static boolean validateResultSetCliente(Optional<Cliente> optCl) {

    if (optCl == null || optCl.isEmpty()) {
        System.out.println("***** No existe el cliente *****");
        return false;
    }

    return true;
    }
    
    public static boolean validateResultSetVenta(Optional<Venta> optV) {

    if (optV == null || optV.isEmpty()) {
        System.out.println("***** No existe el celular *****");
        return false;
    }

    return true;
    }
}
