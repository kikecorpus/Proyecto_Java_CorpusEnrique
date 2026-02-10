
package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.ClienteDAO;
import com.mycompany.tecnostore.controlador.Validador;
import com.mycompany.tecnostore.modelo.Cliente;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class GestorClientes {
        
    ClienteDAO c = new ClienteDAO();
    
    public  void registrarCliente(){
        
        // inicializar cliente a llenar
        Cliente cliente = new Cliente(); 
        
        // solicitar data necesaria
        System.out.println("\nIngrese nombre:");
        String nombre = new Scanner(System.in).nextLine();
        
        System.out.println("\nIngrese identificacion:");
        String identificacion = Validador.validarIdentificacion(new Scanner(System.in).nextLine());
        if(identificacion == null){
            return;
        }
        
        System.out.println("\nIngrese Correo:");
        String correo =  Validador.validateCorreo(new Scanner(System.in).nextLine());
        
        System.out.println("\nIngrese telefono:");
        String telefono= new Scanner(System.in).nextLine();
  
        
        // construir cliente
        cliente.setNombre(nombre);
        cliente.setIdentificacion(identificacion);
        cliente.setCorreo(correo);
        cliente.setTelefono(telefono);
        
        // registrar en base de dato
        c.registrarCl(cliente);
    }
        
    public void actualizarCliente(){
        
        // uso de la clase optional para validar si cel retorna vacio

        int id = Validador.validateID("Ingrese id: "); // valida que id n o sea negativo ni letra
        Cliente cliente = Validador.validateResultSetCliente(id); // valida que el cliente que existe no sea null o vacio

        
        Cliente clienteBefore = (Cliente) cliente.clone(); // toca castear porque el .clone devuelve un object
        
        System.out.println( "\nInformacion del Cliente con id: #" + cliente.getId());
        System.out.println("\n***** Usala de Referencia ******"+ "\n" + cliente);
       
        System.out.println("\nIngrese el nombre:");
        String nombre = new Scanner(System.in).nextLine();
        
        System.out.println("\nIngrese la identificacion:");
        String identificacion = new Scanner(System.in).nextLine();
        
        System.out.println("\nIngrese el Correo:");
        String correo= new Scanner(System.in).nextLine();
        
        System.out.println("\nIngrese el Telefono:");
        String telefono= new Scanner(System.in).nextLine();
        
        
        cliente.setNombre(nombre);
        cliente.setIdentificacion(identificacion);
        cliente.setCorreo(correo);
        cliente.setTelefono(telefono);

        c.actualizarCl(cliente);
        
        imprimirComparacion(clienteBefore, cliente);
    }
    
    public void eliminarCliente(){
           // uso de la clase optional para validar si cel retorna vacio
       int id = Validador.validateID("Ingrese id: "); // valida que id n o sea negativo ni letra
       Cliente cliente = Validador.validateResultSetCliente(id); // valida que el cliente que existe no sea null o vacio

       System.out.println(cliente);
       int op = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el cliente?", null, JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
       if (op == 0) {
             c.eliminarCl(cliente.getId()); 
       } else if (op == JOptionPane.NO_OPTION) {
            System.out.println("***** No se elimino la venta *****");
        } else {
            System.out.println("***** Operación cancelada *****");
        }
    }
    
    public void listarCliente(){
        
        ArrayList<Cliente> clientes =  c.listarCl();
        
        // clientes.forEach( cliente -> System.out.println(cliente)); // para impresion directa
        
        //clientes.stream().forEach(c -> System.out.println(c)); // para trabajar con un flujo 
        
        imprimirTablaCliente (clientes); // funcion para tabla que se ajusta a la casilla // reto en clase 
    }
    
    
    //Buscar
     // buscar como funcion principal 
        
    public void buscarCliente(){
        
        int id = Validador.validateID("\nIngresa el ID del cliente:");
        Cliente cliente= Validador.validateResultSetCliente(id);
        
          if(cliente == null ){
            System.out.println(" Cliente Vacio Regresando ...");
        }else {
            System.out.println(cliente);
        }
    }
    
    // funciones para imprimir tablas 
    private void imprimirTablaCliente(ArrayList<Cliente> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("No hay clientes para mostrar.");
            return;
        }
        
        // Calcular anchos máximos para cada columna
        int anchoId = Math.max("ID".length(), 
            // MapToInt aplica una funcion y devuele entero, en este caso el int max del stream int.
            // orElse es un metodo de la clase Optional, que en caso el valor sea null devuelve el parametro.
            clientes.stream().mapToInt(c -> String.valueOf(c.getId()).length()).max().orElse(0));
        
        int anchoNombre = Math.max("NOMBRE".length(), 
            clientes.stream().mapToInt(c -> c.getNombre().length()).max().orElse(0));
        
        int anchoIdentificacion = Math.max("IDENTIFICACION".length(), 
            clientes.stream().mapToInt(c -> c.getIdentificacion().length()).max().orElse(0));
        
        int anchoCorreo = Math.max("CORREO".length(), 
            clientes.stream().mapToInt(c -> c.getCorreo().length()).max().orElse(0));
        
        int anchoTelefono = Math.max("TELEFONO".length(), 
            clientes.stream().mapToInt(c -> c.getTelefono().length()).max().orElse(0));
        
        
        // formato para cada fila
        String formato = "| %-" + anchoId + "s | %-" + anchoNombre + "s | %-" + anchoIdentificacion + "s | %-" + 
                        anchoCorreo + "s | %-" + anchoTelefono + "s |\n";
        
        //  linea separadora
        String separador = "+" + "-".repeat(anchoId + 2) + "+" + "-".repeat(anchoNombre + 2) + "+" + 
                          "-".repeat(anchoIdentificacion + 2) + "+" + "-".repeat(anchoCorreo + 2) + "+" + 
                          "-".repeat(anchoTelefono + 2) + "+\n";
        
        // Imprimir tabla
        System.out.print(separador);
        System.out.printf(formato, "ID", "NOMBRE", "IDENTIFICACION", "CORREO", "TELEFONO");
        System.out.print(separador);
        
        clientes.forEach(cliente -> System.out.printf(formato, 
                cliente.getId(),
                cliente.getNombre(),
                cliente.getIdentificacion(),
                cliente.getCorreo(),
                cliente.getTelefono()
            )
        );
        
        System.out.print(separador);
    }
    private void imprimirComparacion(Cliente antes, Cliente despues){
        
        System.out.println("==========================");
        System.out.println("********* Antes **********");
        System.out.println("==========================");
        System.out.println(antes + "\n");
        
        System.out.println("==========================");
        System.out.println("********* Despues **********");
        System.out.println("==========================");
        System.out.println(despues );
    
    }
    
}
