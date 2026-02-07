
package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.CelularDAO;
import com.mycompany.tecnostore.modelo.CategoriaGama;
import com.mycompany.tecnostore.modelo.Celular;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class GestorCelulares  {
    
    CelularDAO c = new CelularDAO();
    
    public  void menuRegistrarCelular(){
        Celular cel = new Celular(); 
        
        System.out.println("\nIngrese la marca:");
        String marca = new Scanner(System.in).nextLine();
        
        System.out.println("\nIngrese el modelo:");
        String modelo = new Scanner(System.in).nextLine();
        
        System.out.println("\nIngrese el sistema operativo:");
        String sistema= new Scanner(System.in).nextLine();
        
        System.out.println("\nSeleccione la gama:");
        System.out.println("1. Baja");
        System.out.println("2. Media");
        System.out.println("3. Alta");
        
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

        System.out.println("\nIngrese el precio");
        double precio = new Scanner(System.in).nextDouble();
        
        System.out.println("\nIngrese el stock");
        int stock = new Scanner(System.in).nextInt();
        
        cel.setMarca(marca);
        cel.setModelo(modelo);
        cel.setSistema_operativo(sistema);
        cel.setGama(gama);
        cel.setPrecio(precio);
        cel.setStock(stock);
        
        c.registrarC(cel);
    
    }
    
    public void menuActualizarCelular(){
       
        Celular cel =  menuBuscarCelular();
        
        Celular celBefore = (Celular) cel.clone();
        
        System.out.println( "\nInformacion del Celular con id: #" + cel.getId());
        System.out.println("\n***** Usala de Referencia ******"+ "\n" +cel);
       
        System.out.println("\nIngrese la marca:");
        String marca = new Scanner(System.in).nextLine();
        
        System.out.println("\nIngrese el modelo:");
        String modelo = new Scanner(System.in).nextLine();
        
        System.out.println("\nIngrese el sistema operativo:");
        String sistema= new Scanner(System.in).nextLine();
        
        System.out.println("\nSeleccione la gama:");
        System.out.println("1. Baja");
        System.out.println("2. Media");
        System.out.println("3. Alta");
        
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
                System.out.println("\nOpción inválida");
                return;
        }

        System.out.println("\nIngrese el precio");
        double precio = new Scanner(System.in).nextDouble();
        
        System.out.println("\nIngrese el stock");
        int stock = new Scanner(System.in).nextInt();
        
        cel.setMarca(marca);
        cel.setModelo(modelo);
        cel.setSistema_operativo(sistema);
        cel.setGama(gama);
        cel.setPrecio(precio);
        cel.setStock(stock);
        
        c.actualizarC(cel, cel.getId());
        
        
       imprimirTablaComparativa(celBefore, cel);
        
    }
    
    public Celular menuBuscarCelular(){
        System.out.println("\nIngrese Id del celular");
        int id = new Scanner(System.in).nextInt();
        Celular cel= c.buscar(id);
         return cel;
    }
    
    public void imprimirTablaComparativa(Celular before, Celular after) {

    System.out.println("------------------------------------------------------------------------------");
    System.out.printf("| %-18s | %-15s | %-15s | %-15s   |\n", "Campo", "Antes", "Después", "Modificado");
    System.out.println("------------------------------------------------------------------------------");

    // Marca
    System.out.printf("| %-18s | %-15s | %-15s |%s\n",
            "Marca",
            before.getMarca(),
            after.getMarca(),
            !before.getMarca().equals(after.getMarca()) ? "  *" : ""
    );

    // Modelo
    System.out.printf("| %-18s | %-15s | %-15s |%s\n",
            "Modelo",
            before.getModelo(),
            after.getModelo(),
            !before.getModelo().equals(after.getModelo()) ? "  *" : ""
    );

    // Sistema Operativo
    System.out.printf("| %-18s | %-15s | %-15s |%s\n",
            "Sistema Op.",
            before.getSistema_operativo(),
            after.getSistema_operativo(),
            !before.getSistema_operativo().equals(after.getSistema_operativo()) ? "  *" : ""
    );

    // Gama (enum)
    System.out.printf("| %-18s | %-15s | %-15s |%s\n",
            "Gama",
            before.getGama(),
            after.getGama(),
            before.getGama() != after.getGama() ? "  *" : ""
    );

    // Precio (double)
    System.out.printf("| %-18s | %-15.2f | %-15.2f |%s\n",
            "Precio",
            before.getPrecio(),
            after.getPrecio(),
            before.getPrecio() != after.getPrecio() ? "  *" : ""
    );

    // Stock (int)
    System.out.printf("| %-18s | %-15d | %-15d |%s\n",
            "Stock",
            before.getStock(),
            after.getStock(),
            before.getStock() != after.getStock() ? "  *" : ""
    );

    System.out.println("------------------------------------------------------------------------------");
    System.out.println(" ---- '*' indica Campo modificado ---- ");
}
    
    public void menuEliminar(){
        
        Celular cel = menuBuscarCelular();
        
          System.out.println(cel);
          int op = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el celular?", null, JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
          if (op == 0) {
                c.eliminarC(cel.getId());
                System.out.println("****** Eliminado con exito ******");
          } else {
                System.out.println("***** No se elimino el celular *****");}
    }
}
