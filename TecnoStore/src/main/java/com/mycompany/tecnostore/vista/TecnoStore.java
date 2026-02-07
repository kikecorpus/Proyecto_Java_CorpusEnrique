
package com.mycompany.tecnostore.vista;



public class TecnoStore {

    /* gestionar el catálogo de : 
        -celulares, 
    clientes
    ventas
    reportes
    
    */
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        GestorCelulares gc = new  GestorCelulares();
        
 //       gc.menuRegistrarCelular();
        
 //       gc.menuActualizarCelular();
        
          gc.menuEliminar();
//celulares (id, marca, modelo, sistema_operativo, gama, precio, stock)

        
        
    }
}
