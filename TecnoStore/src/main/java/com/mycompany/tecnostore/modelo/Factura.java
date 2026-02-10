
package com.mycompany.tecnostore.modelo;

import java.util.List;

public class Factura {
    //aplicando composicion 
    private Venta venta;
    private Cliente cliente;
    private List<ItemVenta> detalles;
    private double total;

    public Factura(Venta venta, Cliente cliente, List<ItemVenta> detalles, double total) {
        this.venta = venta;
        this.cliente = cliente;
        this.detalles = detalles;
        this.total = total;
    }

    public void calcularTotal(List<ItemVenta> detalles) {
        total = detalles.stream()
                        .mapToDouble(ItemVenta::getSubtotal)
                        .sum();
    }
    
    public void imprimir() {
        System.out.println("=========== FACTURA ===========");
        System.out.println("Venta N°: " + venta.getId());
        System.out.println("Fecha   : " + venta.getFecha());
        System.out.println("--------------------------------");
        System.out.println("Cliente : " + cliente.getNombre());
        System.out.println("Correo  : " + cliente.getCorreo());
        System.out.println("--------------------------------");

        detalles.forEach(d -> {
            System.out.printf(
                "%s %s x%d  $%.2f%n",
                d.getId_celular().getMarca(),
                d.getId_celular().getModelo(),
                d.getCantidad(),
                d.getSubtotal()
            );
        });

        System.out.println("--------------------------------");
        System.out.printf("TOTAL: $%.2f%n", total);
        System.out.println("================================");
    }
    
}