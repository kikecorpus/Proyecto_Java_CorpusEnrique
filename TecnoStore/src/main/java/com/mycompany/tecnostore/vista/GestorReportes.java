package com.mycompany.tecnostore.vista;

import com.mycompany.tecnostore.controlador.ClienteDAO;
import com.mycompany.tecnostore.controlador.ReporteUtils;
import com.mycompany.tecnostore.controlador.Validador;
import com.mycompany.tecnostore.controlador.VentaDAO;
import com.mycompany.tecnostore.modelo.Cliente;
import com.mycompany.tecnostore.modelo.Venta;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class GestorReportes {
    
    private VentaDAO ventaDAO = new VentaDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    
   
    public void generarReporteGeneral() {
        
        System.out.println("\n========== Generar Reporte de Ventas ==========");
        
        ArrayList<Venta> ventas = ventaDAO.listarV();
        
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas para generar reporte");
            
            JOptionPane.showMessageDialog(
                null,
                "No hay ventas registradas para generar reporte",
                "Sin datos",
                JOptionPane.WARNING_MESSAGE
            );
            
            return;
        }
        
        // Poblar información de clientes
        for (Venta venta : ventas) {
            Cliente cliente = clienteDAO.buscar(venta.getId_cliente().getId()).orElse(null);
            if (cliente != null) {
                venta.setId_cliente(cliente);
            }
        }
        
        System.out.println("\n?Total de ventas a incluir: " + ventas.size());
        
        // Mostrar confirmación
        int opcion = JOptionPane.showConfirmDialog(
            null,
            "Se generará un reporte con " + ventas.size() + " venta(s).\n¿Continuar?",
            "Confirmar generación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (opcion == JOptionPane.YES_OPTION) {
            ReporteUtils.generarReporteVentas(ventas);
        } else {
            System.out.println("Generación de reporte cancelada");
        }
    }
    
   
    public void generarReportePorFecha() {
        
        System.out.println("\n========== Generar Reporte por Fecha ==========");
        
        ArrayList<Venta> ventas = ventaDAO.listarV();
        
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            
            JOptionPane.showMessageDialog(
                null,
                "No hay ventas registradas.",
                "Sin datos",
                JOptionPane.WARNING_MESSAGE
            );
            
            return;
        }
        
        LocalDate fecha = Validador.validarFecha("Ingrese la fecha del reporte");
        String fechaStr = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        // Poblar información de clientes
        for (Venta venta : ventas) {
            Cliente cliente = clienteDAO.buscar(venta.getId_cliente().getId()).orElse(null);
            if (cliente != null) {
                venta.setId_cliente(cliente);
            }
        }
        
        ReporteUtils.generarReportePorFecha(ventas, fechaStr);
    }
    
    
    public void menuReportes() {
        int op;
        
        do {
            System.out.println("""
                               
                               =============================
                               ******* Reportes ***********
                               =============================
                               1.   Reporte General (Todas las ventas)
                               2.   Reporte por Fecha
                               3.   Regresar
                               =============================
                               """);
            
            op = Validador.validateMenu(1, 3, "Ingrese la opción:");
            
            switch (op) {
                case 1 -> generarReporteGeneral();
                case 2 -> generarReportePorFecha();
                case 3 -> System.out.println("Regresando...");
            }
            
        } while (op != 3);
    }
    
    
}
