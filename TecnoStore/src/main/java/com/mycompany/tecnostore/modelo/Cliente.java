
package com.mycompany.tecnostore.modelo;

/**
clientes (id, nombre, identificacion, correo, telefono)
 */


public class Cliente implements Cloneable{
    private int id;
    private String nombre;
    private String identificacion;
    private String correo;
    private String telefono;

      
    // Constructor completo (para SELECT)
    public Cliente(int id, String nombre, String identificacion, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.correo = correo;
        this.telefono = telefono;
    }
    
    // constructor vacio 
    public Cliente() {
    }
    
    // clone 

    @Override
    public Object clone()  {
        Cliente cloneCliente = new Cliente();
        
        cloneCliente.setId(this.id);
        cloneCliente.setNombre(this.nombre);
        cloneCliente.setIdentificacion(this.identificacion);
        cloneCliente.setCorreo(this.correo);
        cloneCliente.setTelefono(this.telefono);
        
        return cloneCliente;
        
    }
    
    
    // getter and setter 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
         return """
               *****************************
               Id:          %s
               Nombre:      %s
               Identificacion:   %s
               Correo: %s
               Telefono:    %s
               
               """.formatted(id,nombre, identificacion, correo,telefono);
    }
    
    
    
    
    
    
}
