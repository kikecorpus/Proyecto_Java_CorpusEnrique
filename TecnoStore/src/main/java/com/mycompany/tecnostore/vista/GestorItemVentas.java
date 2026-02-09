
package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.Calculos;
import com.mycompany.tecnostore.controlador.ItemsVentaDAO;
import com.mycompany.tecnostore.controlador.Validador;
import com.mycompany.tecnostore.modelo.Celular;
import com.mycompany.tecnostore.modelo.ItemVenta;
import com.mycompany.tecnostore.modelo.Venta;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;


public class GestorItemVentas {
    
    private ItemsVentaDAO iv = new ItemsVentaDAO();
    private GestorVentas gv = new GestorVentas();
    private GestorCelulares gcel = new GestorCelulares();
    // crud
    public ItemVenta registrarItemVenta(Venta venta){
        
        // inicializar itemVenta a llenar
        ItemVenta itemVenta = new ItemVenta(); 

        // solicitar informacion de id_celular
        System.out.println("\nIngrese el ID del celular:");
        
        gcel.listarCelular();
        
        int idCelular = Validador.validatePositiveInt(new Scanner(System.in).nextInt());
        
        // Crear objeto Celular solo con el ID
        Celular celular = new Celular();
        celular.setId(idCelular);
        
        // solicitar info  cantidad
        System.out.println("\nIngrese la cantidad:");
        int cantidad = Validador.validatePositiveInt(new Scanner(System.in).nextInt());
        
        // llenar subtotal
        
        double subtotal = Calculos.calcularSubtotalIva(cantidad, celular.getId());
        
        // construir itemVenta
        itemVenta.setId_venta(venta);
        itemVenta.setId_celular(celular);
        itemVenta.setCantidad(cantidad);
        itemVenta.setSubtotal(subtotal);
        
        // registrar en base de datos
        Optional<ItemVenta> x = iv.RegistrarIv(itemVenta);
        
        return x.get();
    }
    
    public void listarItemVenta(){
        
        ArrayList<ItemVenta> itemVentas = iv.listarIV();
        
        imprimirTablaItemVentas(itemVentas);
    }
    
    // buscar como asistente de otra funcion 
    public Optional<ItemVenta> buscarItemVenta(){
        
        // valida que el id no sea negativo, ni que sea una letra
        int id = Validador.validateID("\nIngresa el ID del detalle de venta:");
        
        Optional<ItemVenta> optItemVenta = iv.buscar(id);

        return optItemVenta;
    }
       
    // buscar como funcion principal 
    public void buscarIV(){
 
        int id = Validador.validateID("\nIngresa el ID del detalle de venta:");
        Optional<ItemVenta> optItemVenta = iv.buscar(id);
        
        if (!Validador.validateResultSetItemVenta(optItemVenta)) {
            return;
        }
        
        ItemVenta itemVenta = optItemVenta.get();

        System.out.println(itemVenta);
    }
    
    // funciones para imprimir tablas 
    private void imprimirTablaItemVentas(ArrayList<ItemVenta> itemVentas) {
        if (itemVentas == null || itemVentas.isEmpty()) {
            System.out.println("No hay detalles de venta para mostrar.");
            return;
        }
        
        // Calcular anchos máximos para cada columna
        int anchoId = Math.max("ID".length(), 
            itemVentas.stream().mapToInt(iv -> String.valueOf(iv.getId()).length()).max().orElse(0));
        
        int anchoIdVenta = Math.max("ID VENTA".length(), 
            itemVentas.stream().mapToInt(iv -> String.valueOf(iv.getId_venta().getId()).length()).max().orElse(0));
        
        int anchoIdCelular = Math.max("ID CELULAR".length(), 
            itemVentas.stream().mapToInt(iv -> String.valueOf(iv.getId_celular().getId()).length()).max().orElse(0));
        
        int anchoCantidad = Math.max("CANTIDAD".length(), 
            itemVentas.stream().mapToInt(iv -> String.valueOf(iv.getCantidad()).length()).max().orElse(0));
        
        int anchoSubtotal = Math.max("SUBTOTAL".length(), 
            itemVentas.stream().mapToInt(iv -> String.format("%.2f", iv.getSubtotal()).length()).max().orElse(0));
        
        // formato para cada fila
        String formato = "| %-" + anchoId + "s | %-" + anchoIdVenta + "s | %-" + anchoIdCelular + "s | %-" + 
                        anchoCantidad + "s | %" + anchoSubtotal + "s |\n";
        
        // linea separadora
        String separador = "+" + "-".repeat(anchoId + 2) + "+" + "-".repeat(anchoIdVenta + 2) + "+" + 
                          "-".repeat(anchoIdCelular + 2) + "+" + "-".repeat(anchoCantidad + 2) + "+" + 
                          "-".repeat(anchoSubtotal + 2) + "+\n";
        
        // Imprimir tabla
        System.out.print(separador);
        System.out.printf(formato, "ID", "ID VENTA", "ID CELULAR", "CANTIDAD", "SUBTOTAL");
        System.out.print(separador);
        
        itemVentas.forEach(itemVenta -> System.out.printf(formato, 
                itemVenta.getId(),
                itemVenta.getId_venta().getId(),
                itemVenta.getId_celular().getId(),
                itemVenta.getCantidad(),
                String.format("%.2f", itemVenta.getSubtotal())
            )
        );
        
        System.out.print(separador);
    }
    

    
}
