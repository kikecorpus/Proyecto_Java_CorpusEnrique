package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.ClienteDAO;
import com.mycompany.tecnostore.controlador.VentaDAO;
import com.mycompany.tecnostore.controlador.Validador;
import com.mycompany.tecnostore.modelo.Cliente;
import com.mycompany.tecnostore.modelo.Venta;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class GestorVentas {
    
    private VentaDAO v = new VentaDAO();
    
    private GestorClientes gc = new GestorClientes();
    
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
        
        // guardo Cliente solo con el id
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);

        // construir venta
        venta.setId_cliente(cliente);
        
        // registrar en base de datos y obtener venta 
         x = v.registrarV(venta);
        
        if(x.isEmpty()){
            System.out.println("Error en registrar la venta Intente nuevamente");
            
         }
        } while(x.isEmpty());
        
        return x.get();
    }
    
    public void actualizarVenta(){
        
        // uso de la clase optional para validar si venta retorna vacio
        Optional<Venta> optVenta = buscarVenta(); 
        
        if (!Validador.validateResultSetVenta(optVenta)) {
            return;
        }
        
        Venta venta = optVenta.get();
        
        Venta ventaBefore = (Venta) venta.clone(); // toca castear porque el .clone devuelve un object
        
        System.out.println("\nInformacion de la Venta con id: #" + venta.getId());
        System.out.println("\n***** Usala de Referencia ******" + "\n" + venta);
       
        System.out.println("\nIngrese el ID del cliente:");
        int idCliente = Validador.validatePositiveInt(new Scanner(System.in).nextInt());
        
        // Crear objeto Cliente solo con el ID
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        
        System.out.println("\nIngrese la fecha (formato: YYYY-MM-DD):");
        String fecha = new Scanner(System.in).nextLine();
        
        System.out.println("\nIngrese el total:");
        double total = Validador.validatePositiveDouble(new Scanner(System.in).nextDouble());
        
        venta.setId_cliente(cliente);
        venta.setFecha(fecha);
        venta.setTotal(total);
        
        v.actualizarV(venta, venta.getId());
        
        imprimirTablaComparativa(ventaBefore, venta);
    }
    
    public void eliminarVenta(){
        
        Optional<Venta> optVenta = buscarVenta(); 

        if (!Validador.validateResultSetVenta(optVenta)) {
            return;
        }
          
        Venta venta = optVenta.get();
           
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
    
    public void listarVenta(){
        
        ArrayList<Venta> ventas = v.listarV();
        
        imprimirTablaVentas(ventas);
    }
    
    // buscar como asistente de otra funcion 
    public Optional<Venta> buscarVenta(){
        
        // valida que el id no sea negativo, ni que sea una letra
        int id = Validador.validateID("\nIngresa el ID de la venta:");
        
        Optional<Venta> optVenta = v.buscar(id);

        return optVenta;
    }
       
    // buscar como funcion principal 
    public void buscarVen(){
 
        int id = Validador.validateID("\nIngresa el ID de la venta:");
        Optional<Venta> optVenta = v.buscar(id);
        
        if (!Validador.validateResultSetVenta(optVenta)) {
            return;
        }
        
        Venta venta = optVenta.get();

        System.out.println(venta);
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

        System.out.println("------------------------------------------------------------------------------");
        System.out.printf("| %-18s | %-15s | %-15s | %-15s   |\n", "Campo", "Antes", "Después", "Modificado");
        System.out.println("------------------------------------------------------------------------------");

        // ID Cliente
        System.out.printf("| %-18s | %-15d | %-15d |%s\n",
                "ID Cliente",
                before.getId_cliente().getId(),
                after.getId_cliente().getId(),
                before.getId_cliente().getId() != after.getId_cliente().getId() ? "  *" : ""
        );

        // Fecha
        System.out.printf("| %-18s | %-15s | %-15s |%s\n",
                "Fecha",
                before.getFecha(),
                after.getFecha(),
                !before.getFecha().equals(after.getFecha()) ? "  *" : ""
        );

        // Total
        System.out.printf("| %-18s | %-15.2f | %-15.2f |%s\n",
                "Total",
                before.getTotal(),
                after.getTotal(),
                before.getTotal() != after.getTotal() ? "  *" : ""
        );

        System.out.println("------------------------------------------------------------------------------");
        System.out.println(" ---- '*' indica Campo modificado ---- ");
    }
}