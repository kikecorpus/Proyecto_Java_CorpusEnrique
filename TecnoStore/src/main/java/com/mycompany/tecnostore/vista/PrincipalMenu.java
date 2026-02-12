package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.Validador;

public class PrincipalMenu {
        
    public static void menuPrincipal() {
        
        GestorMain g = new GestorMain();
        GestorAnalisis ga = new GestorAnalisis(); // AGREGAR
        
        int opM;
        
        do{
            System.out.println("""

                               =============================
                               ******** TecnoStore *********
                               =============================
                               1.   Gestionar Celulares.
                               2.   Gestionar Clientes.
                               3.   Gestionar Ventas.
                               4.   Analisis y Estadisticas.
                               5.   Salir.
                               =============================
                               """);
            opM = Validador.validateMenu(1, 5, "Ingrese la accion que desea:");

            switch(opM){
               case 1 -> g.menuCelular();
               case 2 -> g.menuCliente();
               case 3 -> g.menuVenta();
               case 4 -> ga.menuAnalisis(); 
               case 5 -> System.out.println("---- Las Excusas son para los debiles ---- \n~ By KikeCorpus\nSaliendo del sistema ...");
           }
        }while(opM != 5);
    }
}