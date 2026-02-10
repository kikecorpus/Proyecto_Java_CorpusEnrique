
package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.Validador;


public class GestorMain {
    
    private GestorCelulares gCel = new  GestorCelulares();
    private GestorClientes gClient = new  GestorClientes();
    private VentaCompleta vc = new VentaCompleta();
    private GestorVentas gVent = new GestorVentas();
        
    public  void menuCelular(){
        int opC;
        do{
            System.out.println("""

                               =============================
                               ********* Celulares *********
                               =============================
                               1.   Registrar Celular.
                               2.   Actualizar Celular.
                               3.   Eliminar Celular.
                               4.   Listar Celulares.
                               5.   Buscar Celular por Id.
                               6.   Regresar

                               =============================
                               """);
           opC = Validador.validateMenu(1, 6, "Ingrese la opcion que desea:");

           switch(opC){
               case 1 -> {gCel.registrarCelular();}
               case 2 -> {gCel.actualizarCelular();}
               case 3 -> {gCel.eliminarCelular(); }
               case 4 -> {gCel.listarCelular();}
               case 5 -> {gCel.buscarCelular();}
               case 6 -> {}
           }
        }while(opC != 6);
    }
    
    public  void menuCliente(){
        int opC;
        do{
            System.out.println("""

                               =============================
                               ********** Clientes *********
                               =============================
                               1.   Registrar Cliente.
                               2.   Actualizar Cliente.
                               3.   Eliminar Cliente.
                               4.   Listar Cliente.
                               5.   Buscar Cliente por Id.
                               6.   Regresar

                               =============================
                               """);
           opC = Validador.validateMenu(1, 6, "Ingrese la opcion que desea:");

           switch(opC){
               case 1 -> {gClient.registrarCliente();}
               case 2 -> {gClient.actualizarCliente();}
               case 3 -> {gClient.eliminarCliente(); }
               case 4 -> {gClient.listarCliente();}
               case 5 -> {gClient.buscarCliente();}
               case 6 -> {}
           }
        }while(opC != 6);
    }

    void menuVenta() {
        int opC;
        do{
            System.out.println("""
                               =============================
                               **********  Ventas  *********
                               =============================
                               1.   Registrar Venta.
                               2.   Actualizar Venta.
                               3.   Listar Ventas.
                               4.   Buscar Venta por Id.
                               5.   Regresar

                               =============================
                               """);
           opC = Validador.validateMenu(1, 6, "Ingrese la opcion que desea:");

           switch(opC){
               case 1 -> {vc.realizarVentaCompleta();}
               case 2 -> {vc.actualizarVentaCompleta();}
               case 3 -> {gVent.listarVenta();}
               case 4 -> {gVent.buscarVenta();}
               case 5 -> {}
           }
        }while(opC != 6);
    }
}
