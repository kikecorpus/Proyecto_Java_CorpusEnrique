
package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.Calculos;
import com.mycompany.tecnostore.controlador.ItemsVentaDAO;
import com.mycompany.tecnostore.controlador.Validador;
import com.mycompany.tecnostore.controlador.VentaDAO;
import com.mycompany.tecnostore.modelo.Celular;
import com.mycompany.tecnostore.modelo.ItemVenta;
import com.mycompany.tecnostore.modelo.Venta;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;


public class GestorItemVentas {
    
    private ItemsVentaDAO iv = new ItemsVentaDAO();
    private VentaDAO vdao = new VentaDAO();
    private GestorCelulares gcel = new GestorCelulares();
    
// crud
    // registrar
    public ArrayList<ItemVenta> registrarItemVenta(Venta venta){
        
        // inicializar itemVenta a llenar
        ItemVenta itemVenta = new ItemVenta(); 
        ArrayList<ItemVenta> detalle = new ArrayList<>();
        int op = 1;
        while(op == 1){
        // solicitar informacion de id_celular
        System.out.println("\nIngrese el ID del celular:");
        
        gcel.listarCelular();
        
        int idCelular = Validador.validatePositiveInt(new Scanner(System.in).nextInt());
        
        // Crear objeto Celular solo con el ID
        Celular celular = Validador.validateResultSet(idCelular);
        
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
        
        // guardar en array
        detalle.add(x.get());
        op = Validador.validateMenu(1, 2, "Desea añadir otra compra? \n1. Si \n2. No.");
        
    }
        return detalle;
    }
    
    //actualizar
    public ArrayList<ItemVenta> actualizarItemVenta(Venta venta){
       //obtengo valores de itemventa
        ArrayList<ItemVenta> detalles = iv.listarIV();
        
        
        // filtro a los que pertenecen a la venta en curso
        ArrayList<ItemVenta> detallesObtenidos = detalles.stream()
                .filter(dv -> dv.getId_venta().getId() == venta.getId())
                .collect(Collectors.toCollection(ArrayList::new));

        if (detallesObtenidos.isEmpty()) {
             System.out.println("No hay items asociados a esta venta");}
      
        //actualizar cada item obtenido
        detallesObtenidos.stream().forEach(d -> {
            //solicitar informacion a actualizar 
        
             // solicitar informacion de id_celular

                gcel.listarCelular();
                System.out.println("\nIngrese el ID del celular:");
                int idCelular = Validador.validatePositiveInt(new Scanner(System.in).nextInt());

                System.out.println("\nIngrese la cantidad:");
                int cantidad = Validador.validatePositiveInt(new Scanner(System.in).nextInt());
                
                d.
                d.setCantidad(cantidad);
                
                iv.actualizarIV(d);
                
                
        });
        
        
        if(!detalles.isEmpty()) {
        System.out.println("\n***** Items actualizados exitosamente *****");
        detalles.forEach(d -> System.out.println(d));
         }
        return detallesObtenidos;
        
    }
    
    //listar
    public ArrayList<ItemVenta>  listarItemVenta(){
        
        ArrayList<ItemVenta> itemVentas = iv.listarIV();
        
        imprimirTablaItemVentas(itemVentas);
        
        return itemVentas;
    }
    
    
    // buscar 
    public ArrayList<ItemVenta> buscarIV(){
 
        int id = Validador.validateID("\nIngresa el ID del detalle de venta:");
        ArrayList<ItemVenta> detalles = Validador.validateResultSetItemVenta(id); 
       
        if(detalles == null ){
            System.out.println(" detalles Vacio, Regresando ...");
        }else {
         detalles.forEach(d -> System.out.println(d));    
        
        }
        return detalles;
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
