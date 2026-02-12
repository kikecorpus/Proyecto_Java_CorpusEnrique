
package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.Celular;
import com.mycompany.tecnostore.modelo.Cliente;
import com.mycompany.tecnostore.modelo.ItemVenta;
import com.mycompany.tecnostore.modelo.Venta;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
    // valida que exista en la base de datos 
    // Mas adelant me di cuenta que puedo usar estras funciones para simular lefjoin de sql en java
    
    public static Celular validateResultSet(int id) {

        CelularDAO cdao = new CelularDAO();

        Optional<Celular> optC =  cdao.buscar(id);
        
        while (optC == null || optC.isEmpty()) {
            System.out.println("***** El id no coincide por favor ingrese otro  *****");
            System.out.println("Presione el numeo 0 para salir");
            id = validateID("Ingrese nuevo id para continuar");
            optC =  cdao.buscar(id);
            switch (id){
                case 0 -> {return null;}
            }
               
        }
        
        return optC.get();
    }
    
    public static  Cliente validateResultSetCliente(int id) {
        ClienteDAO cdao = new ClienteDAO();
        
        Optional<Cliente> optCl =  cdao.buscar(id);

        while (optCl == null || optCl.isEmpty()) {
            System.out.println("***** El id no coincide por favor ingrese otro  *****");
            System.out.println("Presione el numeo 0 para salir");
            id = validateID("Ingrese nuevo id para cliente");
            optCl =  cdao.buscar(id);
            
             switch (id){
                case 0 -> {return optCl.orElse(null);}
            }
            
        }

        return optCl.get();
    }
    
    public static Venta validateResultSetVenta(int id) {
        VentaDAO vdao = new VentaDAO();

        Optional<Venta> optV =  vdao.buscar(id);

        while (optV == null || optV.isEmpty()) {
            System.out.println("***** El id no coincide por favor ingrese otro  *****");
            System.out.println("Presione el numeo 0 para salir");
            id = validateID("Ingrese nuevo id para venta");
            optV =  vdao.buscar(id);
            switch (id){
                case 0 -> {return optV.orElse(null);}
            }
            
        }
        
        return optV.get();
           
    }
    
    public static ArrayList<ItemVenta> validateResultSetItemVenta(int id) {

        ItemsVentaDAO ivdao = new ItemsVentaDAO();

        ArrayList<ItemVenta> detalles =  ivdao.buscar(id);

        do {
        detalles = ivdao.buscar(id);

        if (detalles.isEmpty()) {
            System.out.println("***** El id no coincide, por favor ingrese otro *****");
            System.out.println("Presione el numeo 0 para salir");
            id = validateID("Ingrese nuevo id para Item venta");
            switch (id){
                case 0 -> {return null;}
            }
        }

        } while (detalles.isEmpty());
        
        return detalles;
    }

    //validacion CLiente
    
    public static String validateFormatoCorreo(String correo){

        while(!correo.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")){

           System.out.println("****** correo no valido *****");
           System.out.println("Ingrese nuevamente el correo");
           correo = new Scanner(System.in).nextLine();   
        }
        return correo;
    }
            
    public static boolean validateCorreoExistente(String correo){
        ClienteDAO c = new ClienteDAO();
        ArrayList<Cliente> clientes = c.listarCl();
        
        boolean bool = clientes.stream().anyMatch(x -> x.getCorreo().equalsIgnoreCase(correo));
            
        return bool;
    }
    
    public static String validarIdentificacion(String identificacion) {
        ClienteDAO c = new ClienteDAO();
        ArrayList<Cliente> cliente = c.listarCl();
        Scanner scanner = new Scanner(System.in);

        // Buscar si ya existe un cliente con esaidentificación
         Optional<Cliente> filtro = cliente.stream()
            .filter(z -> z.getIdentificacion().equals(identificacion))
            .findFirst();
        if (filtro.isPresent()) {
            System.out.println("***** Identificaicion ya registrada, intente nuevamente *****");
            return null;
          }
          
         return identificacion;
         
        /*
        if (filtro.isPresent()) {
            System.out.println("***** La identificación ya se encuentra registrada *****");
            System.out.println("¿Desea ingresar una identificación diferente?");
            System.out.println("1. Sí");
            System.out.println("2. No");
            
            int op = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            switch(op) {
                case 1:
                    System.out.print("Ingrese nueva identificación: ");
                     String identificacionNueva = scanner.nextLine();
                     
                    // El bucle continuará y volverá a validar
                    break;
                    
                case 2:
                    return null; // Usuario no quiere continuar
                    
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } else {
            // Identificación disponible
            return identificacion;
            */
        }
    // celular 
    
    public static boolean validateCelularListarStock(ArrayList<Celular> celulares, int idCelular){
    
        boolean bool =  celulares.stream().anyMatch(c -> c.getId() == idCelular);
        if(bool == false){
            System.out.println("Celular no esta en la lista ");
            return false;
        }
        
        return true;
    }
    // validar fecha 
    
    public static boolean validateStockSuficiente(Celular celular, int cantidad){
        
        if(celular.getStock() < cantidad){
            System.out.println("****** No hay suficientes stock ******");
            return false;
        }
    return true;
    }
    public static LocalDate validarFecha(String mensaje) {

    Scanner sc = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    while (true) {
        System.out.println(mensaje + " (formato: YYYY-MM-DD): ");
        String input = sc.nextLine();

        try {
            return LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("***** Fecha inválida. Ejemplo válido: 2025-02-10 *****");
        }
    }
}
    

}
