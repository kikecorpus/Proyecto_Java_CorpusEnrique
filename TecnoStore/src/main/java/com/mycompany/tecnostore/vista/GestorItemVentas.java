
package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.Calculos;
import com.mycompany.tecnostore.controlador.CelularDAO;
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
    private CelularDAO celdao = new CelularDAO();
    
// crud
    // registrar
    public ArrayList<ItemVenta> registrarItemVenta(Venta venta){
        
        // inicializar itemVenta a llenar
        ArrayList<ItemVenta> detalle = new ArrayList<>();
        int op = 1;
        // para registrar varios detalles de venta en una misma venta
        while(op == 1){
        ItemVenta itemVenta = new ItemVenta();  
        
        // solicitar informacion de id_celular
      
        System.out.println("\nIngrese el ID del celular:");
        
        ArrayList<Celular> celulares = gcel.listarCelular(); // retorna celulares con stock
        
        int idCelular = Validador.validatePositiveInt(new Scanner(System.in).nextInt());
        // validar que no seleciones un id sin stock 
        boolean bool = Validador.validateCelularListarStock(celulares, idCelular);
        if(bool = false){
            return detalle;
        }
        
        // poblar celular completo en itemventa
        Celular celular = Validador.validateResultSet(idCelular);
        

        // solicitar info  cantidad
        System.out.println("\nIngrese la cantidad:");
        int cantidad = Validador.validatePositiveInt(new Scanner(System.in).nextInt());
         
        // validar que la cantidad no supere al stock 
        
        boolean bool2 = Validador.validateStockSuficiente(celular, cantidad);
        if(bool = false){
            return detalle;
        }
        
        
        
        
        // calcular subtotal 
        
        double subtotal= cantidad * celular.getPrecio();
        
        // construir itemVenta
        itemVenta.setId_venta(venta);
        celular.setStock(celular.getStock()-cantidad); // actualizar stock en java
        itemVenta.setId_celular(celular);
        itemVenta.setCantidad(cantidad);
        itemVenta.setSubtotal(subtotal);
        // guardar en array
        detalle.add(itemVenta);
        
        // registrar en base de datos
        Optional<ItemVenta> x = iv.RegistrarIv(itemVenta);
        //Disminuir Stock 
        celdao.actualizarC(celular); // actualizar stock base de datos
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
        // mostrar detalles de venta
        detallesObtenidos.forEach(d -> {
                                        d.setId_celular(Validador.validateResultSet(d.getId_celular().getId()));
                                        
                                        System.out.println(d);            
                                        Celular celularViejo = d.getId_celular(); // obtener Celular viejo
                                        celularViejo.setStock(celularViejo.getStock() + d.getCantidad()); //sumar devolucion en java 
                                        d.setId_celular(celularViejo);
                                        celdao.actualizarC(celularViejo);//actualizacion en base de datos
                                        });
        //le mando el stock ya asumiendo que hara un cambio,
        //y en caso ponga el mismo ps vuelve y los suma
        
        //valida que existan
        if (detallesObtenidos.isEmpty()) {
             System.out.println("No hay items asociados a esta venta");}
        
        //actualizar cada item obtenido
        detallesObtenidos.stream().forEach(d -> {
            
             
            //solicitar informacion a actualizar 
            
             // solicitar informacion de id_celular
                System.out.println(" ------------ Actualizar Detalle de venta id: #" + d.getId_celular().getId() + " ------------");
               
                ArrayList<Celular> celulares = gcel.listarCelular(); // celulares con stock 
      
                System.out.println("\nIngrese el ID del celular:"); // solicita id del celuldar
                int idCelular = Validador.validatePositiveInt(new Scanner(System.in).nextInt());

                System.out.println("\nIngrese la cantidad:"); // solicita la cantidad nuevaque se vendio
                int cantidad = Validador.validatePositiveInt(new Scanner(System.in).nextInt());
                
                // actualiza celular nuevo 
                Celular celularNuevo = Validador.validateResultSet(idCelular);
                
                celularNuevo.setStock(celularNuevo.getStock() - cantidad);//actuliza stock nuevo
                d.setId_celular(celularNuevo);
                d.setCantidad(cantidad);

                //actualizar sub total de cada detalle 
                double subtotal = cantidad * d.getId_celular().getPrecio();
                d.setSubtotal(subtotal);
      
            
                iv.actualizarIV(d);  
                celdao.actualizarC(celularNuevo);//actulizar celular nuevo en base de datos
                
        });
       
        //actualizar venta con nuevos subtotales
        venta.setTotal(Calculos.calcularTotalConIva(detallesObtenidos));
        vdao.actualizarV(venta);
        System.out.println("********** total actualizado a: $" + venta.getTotal());
        
        if(!detallesObtenidos.isEmpty()) {
        System.out.println("\n***** Items actualizados exitosamente *****");
        detallesObtenidos.forEach(d -> System.out.println(d));
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
    public ArrayList<ItemVenta> buscarIV(Venta venta){
        
        ArrayList<ItemVenta> detalles = iv.listarIV();
        
        detalles.stream().filter(d -> d.getId_venta().getId() == venta.getId()).collect(Collectors.toCollection(ArrayList:: new));
 
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
