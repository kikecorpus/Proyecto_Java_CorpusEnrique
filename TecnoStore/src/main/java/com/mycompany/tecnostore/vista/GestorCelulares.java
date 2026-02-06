
package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.CelularDAO;
import com.mycompany.tecnostore.modelo.CategoriaGama;
import com.mycompany.tecnostore.modelo.Celular;
import java.util.Scanner;


public class GestorCelulares  {
    
    Celular cel = new Celular(); 
    
    public  void menuRegistrarCelular(){
        
        System.out.println("\nIngrese la marca:");
        String marca = new Scanner(System.in).nextLine();
        
        System.out.println("\nIngrese el modelo:");
        String modelo = new Scanner(System.in).nextLine();
        
        System.out.println("\nIngrese el sistema operativo:");
        String sistema= new Scanner(System.in).nextLine();
        
        System.out.println("\nSeleccione la gama:");
        System.out.println("1. BAJA");
        System.out.println("2. MEDIA");
        System.out.println("3. ALTA");
        
        int opG = new Scanner(System.in).nextInt();
        CategoriaGama gama;
        switch (opG) {
            case 1:
                gama = CategoriaGama.Baja;
                break;
            case 2:
                gama = CategoriaGama.Media;
                break;
            case 3:
                gama = CategoriaGama.Alta;
                break;
            default:
                System.out.println("Opción inválida");
                return;
        }

        System.out.println("Ingrese el precio");
        double precio = new Scanner(System.in).nextInt();
        
        System.out.println("Ingrese el stock");
        int stock = new Scanner(System.in).nextInt();
        
        cel.setMarca(marca);
        cel.setModelo(modelo);
        cel.setSistema_operativo(sistema);
        cel.setGama(gama);
        cel.setPrecio(precio);
        cel.setStock(stock);
        
        CelularDAO c = new CelularDAO();
        
        c.registrarC(cel);
    
    }
}
