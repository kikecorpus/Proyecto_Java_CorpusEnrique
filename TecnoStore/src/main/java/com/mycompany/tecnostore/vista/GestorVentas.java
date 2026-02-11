package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.ClienteDAO;
import com.mycompany.tecnostore.controlador.VentaDAO;
import com.mycompany.tecnostore.controlador.Validador;
import com.mycompany.tecnostore.modelo.Cliente;
import com.mycompany.tecnostore.modelo.Venta;
import static java.lang.String.valueOf;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;


public class GestorVentas {
    
    private VentaDAO v = new VentaDAO();
    
    private GestorClientes gc = new GestorClientes();
    private ClienteDAO cdao = new ClienteDAO();
    
    // crud
    public Venta registrarVenta(){
        Optional<Venta> x;
        
        do{
        // inicializar venta a llenar
        Venta venta = new Venta(); 
        
        // solicitar data necesaria
        System.out.println("\nIngrese el ID del cliente:");
        
        //listar clientes 
         gc.listarCliente();
        
        int idCliente = Validador.validatePositiveInt(new Scanner(System.in).nextInt());
        
        //poblar cliente dentro de Venta

        Cliente cliente = Validador.validateResultSetCliente(idCliente);
       

        // construir venta
        venta.setId_cliente(cliente);
        
        
        // registrar en base de datos y obtener venta 
         x = v.registrarV(venta);
         //actualizar fecha obtenida sql a java
         Venta VentaConFecha = Validador.validateResultSetVenta(x.get().getId());
         x.get().setFecha(VentaConFecha.getFecha());
         
        if(x.isEmpty()){
            System.out.println("Error en registrar la venta Intente nuevamente");
            
         }
        } while(x.isEmpty());
        
        return x.get();
    }
    
    public Venta actualizarVenta(){
        
        ArrayList<Venta> vacio = listarVenta();
        
        if(vacio.isEmpty()){
            System.out.println("Presione 0 para volver");
        }
        int id = Validador.validateID("\nIngresa el ID de la venta:");

        Venta venta = Validador.validateResultSetVenta(id); // valida que el cliente que existe no sea null o vacio
        if(venta == null){
             return null;
         }
        
          //buscar cliente para actualizar venta 
        Cliente cliente = Validador.validateResultSetCliente(venta.getId_cliente().getId());
        venta.setId_cliente(cliente);
        
        //clonar
        Venta ventaBefore = (Venta) venta.clone(); // toca castear porque el .clone devuelve un object
        
        System.out.println("\nInformacion de la Venta con id: #" + venta.getId());
        System.out.println("\n***** Usala de Referencia ******" + "\n" + venta);
       
        System.out.println("\nIngrese el ID del cliente:");
         //listar clientes 
         gc.listarCliente();
        int idCliente = Validador.validatePositiveInt(new Scanner(System.in).nextInt());
        
        // Actualiza a  nuevo Cliente  
        cliente = Validador.validateResultSetCliente(idCliente);
       
        String fecha = valueOf(Validador.validarFecha("Ingrese la fecha"));
 
        venta.setId_cliente(cliente);
        venta.setFecha(fecha);
        
        v.actualizarV(venta);
        
        imprimirTablaComparativa(ventaBefore, venta);
        
        return venta;
    }
    
    public ArrayList<Venta> listarVenta(){
        
        ArrayList<Venta> ventas = v.listarV();
        
        imprimirTablaVentas(ventas);
        
        return ventas;
        
    }
    
   
    // buscar 
    public void buscarVenta(){
 
        int id = Validador.validateID("\nIngresa el ID de la venta:");
        Venta venta = Validador.validateResultSetVenta(id); // valida que el cliente que existe no sea null o vacio
           if(venta == null ){
            System.out.println(" Celular Vacio Regresando ...");
        }else {
        System.out.println(venta);}

    }
    
    // funciones para imprimir tablas 
    private void imprimirTablaVentas(ArrayList<Venta> ventas) {
        if (ventas == null || ventas.isEmpty()) {
            System.out.println("No hay ventas para mostrar.");
            return;
        }
        
        // Calcular anchos máximos para cada columna
        int anchoId = Math.max("ID".length(), 
            ventas.stream().mapToInt(v -> String.valueOf(v.getId()).length()).max().orElse(0));
        
        int anchoIdCliente = Math.max("ID CLIENTE".length(), 
            ventas.stream().mapToInt(v -> String.valueOf(v.getId_cliente().getId()).length()).max().orElse(0));
        
        int anchoFecha = Math.max("FECHA".length(), 
            ventas.stream().mapToInt(v -> v.getFecha().length()).max().orElse(0));
        
        int anchoTotal = Math.max("TOTAL".length(), 
            ventas.stream().mapToInt(v -> String.format("%.2f", v.getTotal()).length()).max().orElse(0));
        
        // formato para cada fila
        String formato = "| %-" + anchoId + "s | %-" + anchoIdCliente + "s | %-" + anchoFecha + "s | %" + anchoTotal + "s |\n";
        
        // linea separadora
        String separador = "+" + "-".repeat(anchoId + 2) + "+" + "-".repeat(anchoIdCliente + 2) + "+" + 
                          "-".repeat(anchoFecha + 2) + "+" + "-".repeat(anchoTotal + 2) + "+\n";
        
        // Imprimir tabla
        System.out.print(separador);
        System.out.printf(formato, "ID", "ID CLIENTE", "FECHA", "TOTAL");
        System.out.print(separador);
        
        ventas.forEach(venta -> System.out.printf(formato, 
                venta.getId(),
                venta.getId_cliente().getId(),
                venta.getFecha(),
                String.format("%.2f", venta.getTotal())
            )
        );
        
        System.out.print(separador);
    }
    
    private void imprimirTablaComparativa(Venta before, Venta after) {
        System.out.println("\n----------------------  Resultados de Actualizazcion ---------------------- ");
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.printf("| %-30s | %-30s | %-20s | %-2s|\n", "Campo", "Antes", "Después", "M");
        System.out.println("----------------------------------------------------------------------------------------------");

        // ID Cliente
        System.out.printf("| %-30s | %-30d | %-20d |%-3s|\n",
                "ID Cliente",
                before.getId_cliente().getId(),
                after.getId_cliente().getId(),
                before.getId_cliente().getId() != after.getId_cliente().getId() ? "  *" : ""
        );

        // Fecha
        System.out.printf("| %-30s | %-30s | %-20s |%-3s|\n",
                "Fecha",
                before.getFecha(),
                after.getFecha(),
                !before.getFecha().equals(after.getFecha()) ? "  *" : ""
        );

        // Total
        System.out.printf("| %-30s | %-30.2f | %-20.2f |%-3s|\n",
                "Total",
                before.getTotal(),
                after.getTotal(),
                before.getTotal() != after.getTotal() ? "  *" : ""
        );

        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println(" ---- '*' indica Campo modificado ----  \n");
    }
    
    // eliminar no es optimo deberia de crearse un log, o inactivar el producto 
    
    /*public void eliminarVenta(){
        
        int id = Validador.validateID("\nIngresa el ID de la venta:");
        Venta venta = Validador.validateResultSetVenta(id); // valida que el cliente que existe no sea null o vacio
           
        System.out.println(venta);
        int op = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la venta?", null, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (op == 0) {
            v.eliminarV(venta.getId());
        } else if (op == JOptionPane.NO_OPTION) {
            System.out.println("***** No se elimino la venta *****");
        } else {
            System.out.println("***** Operación cancelada *****");
        }
    }   
    
    
    */
}