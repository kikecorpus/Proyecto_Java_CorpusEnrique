
package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.CelularDAO;
import com.mycompany.tecnostore.modelo.CategoriaGama;
import com.mycompany.tecnostore.modelo.Celular;

public class TecnoStore {

    /* gestionar el catálogo de : 
        -celulares, 
    clientes
    ventas
    reportes
    
    */
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        Celular cel = new Celular();
        
        cel.setMarca("motorola");
        cel.setModelo("motog35");
        cel.setSistema_operativo("android");
        cel.setGama(CategoriaGama.Media);
        cel.setPrecio(2000000);
        cel.setStock(20);
                
        CelularDAO c = new CelularDAO();
        
        c.registrarC(cel);
        
        
//celulares (id, marca, modelo, sistema_operativo, gama, precio, stock)

        
        
    }
}
