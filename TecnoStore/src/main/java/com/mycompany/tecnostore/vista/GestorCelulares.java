
package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.CelularDAO;
import com.mycompany.tecnostore.controlador.Validador;
import com.mycompany.tecnostore.modelo.CategoriaGama;
import com.mycompany.tecnostore.modelo.Celular;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;



public class GestorCelulares  {
    
    private CelularDAO c = new CelularDAO();
    
    
    // crud
    public  void registrarCelular(){
        
        // inicializar celular a llenar
        Celular cel = new Celular(); 
        
        // solicitar data necesaria
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
        
        int opG = Validador.validateMenu(1, 3, "");
        CategoriaGama gama = null;
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
        }

        System.out.println("\nIngrese el precio");
        
        // validacion de que precio  y stock no sean negativos 
        double precio = Validador.validatePositiveDouble(new Scanner(System.in).nextDouble());
        
        System.out.println("\nIngrese el stock");
        int stock = Validador.validatePositiveInt(new Scanner(System.in).nextInt());
        
        // construir celular
        cel.setMarca(marca);
        cel.setModelo(modelo);
        cel.setSistema_operativo(sistema);
        cel.setGama(gama);
        cel.setPrecio(precio);
        cel.setStock(stock);
        
        // registrar en base de dato
        c.registrarC(cel);
    }
    
    public void actualizarCelular(){
        
        // uso de la clase optional para validar si cel retorna vacio
         int id = Validador.validateID("Ingrese id: "); // valida que id n o sea negativo ni letra
         Celular cel = Validador.validateResultSet(id); // valida que el cliente que existe no sea null o vacio
         if(cel == null){
             return;
         }
        
        Celular celBefore = (Celular) cel.clone(); // toca castear porque el .clone devuelve un object
        
        System.out.println( "\nInformacion del Celular con id: #" + cel.getId());
        System.out.println("\n***** Usala de Referencia ******"+ "\n" + cel);
       
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
        
        int opG = Validador.validateMenu(1, 3, "");
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
        
        c.actualizarC(cel);
        
        
       imprimirTablaComparativa(celBefore, cel);
        
    }
    
    public void eliminarCelular(){
        
         int id = Validador.validateID("Ingrese id: "); // valida que id n o sea negativo ni letra
         Celular cel = Validador.validateResultSet(id); // valida que el cliente que existe no sea null o vacio
         if(venta == null){
             return null;
         }
          System.out.println(cel);
          int op = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el celular?", null, JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
          // valida decicion del usuario pero no lo que ocurrio en database
          if (op == 0) {
                c.eliminarC(cel.getId());
          }  else if (op == JOptionPane.NO_OPTION) {
          System.out.println("***** No se elimino el celular *****");
          } else {
          System.out.println("***** Operación cancelada *****");
          }
    }   
    
    public void listarCelular(){
        
        ArrayList<Celular> celulares =  c.listarC();
        
        // celulares.forEach( cel -> System.out.println(cel)); // para impresion directa
        
        //celulares.stream().forEach(c -> System.out.println(c)); // para trabajar con un flujo 
        
        imprimirTablaCelulares(celulares); // funcion para tabla que se ajusta a la casilla // reto en clase 
    }
    
    
    // buscar 
    public void buscarCelular(){
 
        int id = Validador.validateID("Ingrese id: "); // valida que id n o sea negativo ni letra
        Celular cel = Validador.validateResultSet(id); // valida que el cliente que existe no sea null o vacio
        
        if(cel == null ){
            System.out.println(" Celular Vacio Regresando ...");
        }else {
        System.out.println(cel);}
        
        }
    
    
    // funciones para imprimir tablas 
    private void imprimirTablaCelulares(ArrayList<Celular> celulares) {
        if (celulares == null || celulares.isEmpty()) {
            System.out.println("No hay celulares para mostrar.");
            return;
        }
        
        // Calcular anchos máximos para cada columna
        int anchoId = Math.max("ID".length(), 
            // MapToInt aplica una funcion y devuele entero, en este caso el int max del stream int.
            // orElse es un metodo de la clase Optional, que en caso el valor sea null devuelve el parametro.
            celulares.stream().mapToInt(c -> String.valueOf(c.getId()).length()).max().orElse(0));
        
        int anchoMarca = Math.max("MARCA".length(), 
            celulares.stream().mapToInt(c -> c.getMarca().length()).max().orElse(0));
        
        int anchoModelo = Math.max("MODELO".length(), 
            celulares.stream().mapToInt(c -> c.getModelo().length()).max().orElse(0));
        
        int anchoSO = Math.max("SISTEMA OPERATIVO".length(), 
            celulares.stream().mapToInt(c -> c.getSistema_operativo().length()).max().orElse(0));
        
        int anchoGama = Math.max("GAMA".length(), 
            celulares.stream().mapToInt(c -> c.getGama().toString().length()).max().orElse(0));
        
        int anchoPrecio = Math.max("PRECIO".length(), 
            celulares.stream().mapToInt(c -> String.format("%.2f", c.getPrecio()).length()).max().orElse(0));
        
        int anchoStock = Math.max("STOCK".length(), 
            celulares.stream().mapToInt(c -> String.valueOf(c.getStock()).length()).max().orElse(0));
        
        // formato para cada fila
        String formato = "| %-" + anchoId + "s | %-" + anchoMarca + "s | %-" + anchoModelo + "s | %-" + 
                        anchoSO + "s | %-" + anchoGama + "s | %" + anchoPrecio + "s | %" + anchoStock + "s |\n";
        
        //  linea separadora
        String separador = "+" + "-".repeat(anchoId + 2) + "+" + "-".repeat(anchoMarca + 2) + "+" + 
                          "-".repeat(anchoModelo + 2) + "+" + "-".repeat(anchoSO + 2) + "+" + 
                          "-".repeat(anchoGama + 2) + "+" + "-".repeat(anchoPrecio + 2) + "+" + 
                          "-".repeat(anchoStock + 2) + "+\n";
        
        // Imprimir tabla
        System.out.print(separador);
        System.out.printf(formato, "ID", "MARCA", "MODELO", "SISTEMA OPERATIVO", "GAMA", "PRECIO", "STOCK");
        System.out.print(separador);
        
        celulares.forEach(celular -> System.out.printf(formato, 
                celular.getId(),
                celular.getMarca(),
                celular.getModelo(),
                celular.getSistema_operativo(),
                celular.getGama(),
                celular.getPrecio(),
                celular.getStock()
            )
        );
        
        System.out.print(separador);
    }
    
    private void imprimirTablaComparativa(Celular before, Celular after) {

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
 
}
