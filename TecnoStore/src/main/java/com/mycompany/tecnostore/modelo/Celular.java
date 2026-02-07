
package com.mycompany.tecnostore.modelo;

/**
celulares (id, marca, modelo, sistema_operativo, gama, precio, stock)

 */
public class Celular implements Cloneable{
    private int id;
    private String marca;
    private String modelo;
    private String sistema_operativo;
    private CategoriaGama gama; // inyeccion de de enum 
    private double precio;
    private int stock;

    
    public Celular(int id, String marca, String modelo, String sistema_operativo, CategoriaGama  gama, double precio, int stock) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.sistema_operativo = sistema_operativo;
        this.gama = gama;
        this.precio = precio;
        this.stock = stock;
       
    }
    
    //constructor para sql 
    public Celular() {
    }

    // Patron de diseño Prototype
    @Override
    public Object clone(){
        
        Celular cloneCelular = new Celular();
        cloneCelular.setId(this.id);
        cloneCelular.setMarca(this.marca);
        cloneCelular.setModelo(this.modelo);
        cloneCelular.setSistema_operativo(this.sistema_operativo);
        cloneCelular.setGama(this.gama);
        cloneCelular.setPrecio(this.precio);
        cloneCelular.setStock(this.stock);
        
        return cloneCelular;
    }
    
    //getter and setter 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSistema_operativo() {
        return sistema_operativo;
    }

    public void setSistema_operativo(String sistema_operativo) {
        this.sistema_operativo = sistema_operativo;
    }

    public CategoriaGama  getGama() {
        return gama;
    }

    public void setGama(CategoriaGama  gama) {
        this.gama = gama;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // to string 
     @Override
    public String toString() {
         return """
               
               *****************************
               Id:      %s
               Marca:    %s
               Modelo:   %s
               Sistema op : %s
               Gama:    %s
               Precio:  %s
               Stock:   %s
               *****************************
               """.formatted(id,marca, modelo,sistema_operativo,gama,precio,stock);
    }
    
}
