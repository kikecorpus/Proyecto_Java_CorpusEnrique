
package com.mycompany.tecnostore.modelo;

/**
ventas (id, id_cliente, fecha, total)
 */
public class Venta {
    
  private int id;
  private Cliente id_cliente;
  private String fecha;
  private double total;

    public Venta(int id, Cliente id_cliente, String fecha, double total) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.fecha = fecha;
        this.total = total;
    }
    
    //Contructor vacio para sql 
    public Venta() {
    }
    
// Getter and Setter 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Cliente id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
  
  // to string para imprimir
   @Override
    public String toString() {
         return """
               *****************************
               Id:          %s
               Id Cliente:      %s
               Fehca:   %s
               total: %s
             
               """.formatted(id,id_cliente, fecha,total);
    }
  
}
