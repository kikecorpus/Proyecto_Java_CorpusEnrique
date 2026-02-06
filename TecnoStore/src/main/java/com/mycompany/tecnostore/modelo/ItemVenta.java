
package com.mycompany.tecnostore.modelo;

/**
 detalle_ventas (id, id_venta, id_celular, cantidad, subtotal)
 */
public class ItemVenta {
    private int id;
    private Venta id_venta;
    private Celular id_celular;
    private int cantidad;
    private double subtotal;

    public ItemVenta(int id, Venta id_venta, Celular id_celular, int cantidad, double subtotal) {
        this.id = id;
        this.id_venta = id_venta;
        this.id_celular = id_celular;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public ItemVenta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Venta getId_venta() {
        return id_venta;
    }

    public void setId_venta(Venta id_venta) {
        this.id_venta = id_venta;
    }

    public Celular getId_celular() {
        return id_celular;
    }

    public void setId_celular(Celular id_celular) {
        this.id_celular = id_celular;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    @Override
    public String toString() {
         return """
               *****************************
               Id:          %s
               Id Venta:      %s
               Id Celular  %s
               Cantidad: %s
               Subtotal:    %s
               
               """.formatted(id,id_venta, id_celular, cantidad,subtotal);
    }
    
}
