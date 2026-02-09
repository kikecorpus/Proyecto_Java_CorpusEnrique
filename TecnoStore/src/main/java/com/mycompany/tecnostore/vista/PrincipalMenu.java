
package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.Validador;

public class PrincipalMenu {
        
    
    public static void menuPrincipal() {
        
        GestorMain g = new GestorMain();
        int opM;
        
        do{
            System.out.println("""

                               =============================
                               ******** TecnoStore *********
                               =============================
                               1.   Gestionar Celulares.
                               2.   Gestionar Clientes.
                               3.   Gestionar Ventas.
                               4.   Salir.
                               =============================
                               """);
            opM = Validador.validateMenu(1, 4, "Ingrese la accion que desea:");

            switch(opM){
               case 1 -> g.menuCelular();
               case 2 -> g.menuCliente();
               case 3 -> g.menuVenta();
           }
        }while(opM != 4);
    }
}
