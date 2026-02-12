package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.ReporteUtils;
import com.mycompany.tecnostore.controlador.Validador;

public class GestorAnalisis {
    
   
    public void menuAnalisis() {
        int op;
        
        do {
            System.out.println("""
                               
                               =============================
                               ******* Analisis ***********
                               =============================
                               1.   Stock Bajo (< 5 unidades)
                               2.   Top 3 Celulares Mas Vendidos
                               3.   Ventas por Mes
                               4.   Ver Todos los Analisis
                               5.   Regresar
                               =============================
                               """);
            
            op = Validador.validateMenu(1, 5, "Ingrese la opcion:");
            
            switch (op) {
                case 1 -> ReporteUtils.analizarStockBajo();
                case 2 -> ReporteUtils.analizarTop3MasVendidos();
                case 3 -> ReporteUtils.analizarVentasPorMes();
                case 4 -> mostrarTodosLosAnalisis();
                case 5 -> System.out.println("Regresando...");
            }
            
        } while (op != 5);
    }
    //patron facade
    private void mostrarTodosLosAnalisis() {
        System.out.println("\n");
        System.out.println("***********************************************");
        System.out.println("       REPORTE COMPLETO DE ANALISIS");
        System.out.println("***********************************************");
        
        ReporteUtils.analizarStockBajo();
        
        System.out.println("\n--- Presione ENTER para continuar ---");
        new java.util.Scanner(System.in).nextLine();
        
        ReporteUtils.analizarTop3MasVendidos();
        
        System.out.println("\n--- Presione ENTER para continuar ---");
        new java.util.Scanner(System.in).nextLine();
        
        ReporteUtils.analizarVentasPorMes();
        
        System.out.println("\n***********************************************");
        System.out.println("       FIN DEL REPORTE DE ANALISIS");
        System.out.println("***********************************************\n");
    }
}