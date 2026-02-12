package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.Celular;
import com.mycompany.tecnostore.modelo.Cliente;
import com.mycompany.tecnostore.modelo.ItemVenta;
import com.mycompany.tecnostore.modelo.Venta;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ReporteUtils {

    //reportes
    public static boolean generarReporteVentas(ArrayList<Venta> ventas) {
        
        // Crear nombre sugerido para el archivo
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String nombreSugerido = "reporte_ventas_" + ahora.format(formatter) + ".txt";
        
        // Abrir dialogo para seleccionar ubicación
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte de Ventas");
        fileChooser.setSelectedFile(new File(nombreSugerido));
        
        // Filtro para solo mostrar archivos .txt
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");
        fileChooser.setFileFilter(filtro);
        
        // Mostrar diálogo
        int resultado = fileChooser.showSaveDialog(null);
        
        // Si el usuario canceló
        if (resultado != JFileChooser.APPROVE_OPTION) {
            System.out.println("Operación cancelada por el usuario");
            return false;
        }
        
        // Obtener archivo seleccionado
        File archivoSeleccionado = fileChooser.getSelectedFile();
        
        // Asegurar que tenga extensión .txt
        String rutaArchivo = archivoSeleccionado.getAbsolutePath();
        if (!rutaArchivo.toLowerCase().endsWith(".txt")) {
            rutaArchivo += ".txt";
            archivoSeleccionado = new File(rutaArchivo);
        }
        
        // Verificar si el archivo ya existe
        if (archivoSeleccionado.exists()) {
            int opcion = JOptionPane.showConfirmDialog(
                null,
                "El archivo ya existe. ¿Desea reemplazarlo?",
                "Confirmar sobrescritura",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (opcion != JOptionPane.YES_OPTION) {
                System.out.println("Operación cancelada");
                return false;
            }
        }
        
        // Generar el reporte
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSeleccionado))) {
            
            // Encabezado
            writer.write("===============================\n");
            writer.write("    REPORTE DE VENTAS\n");
            writer.write("    Fecha: " + ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n");
            writer.write("===============================\n\n");
            
            // Variables para resumen
            double montoTotal = 0;
            int totalVentas = ventas.size();
            
            // Recorrer cada venta
            for (Venta venta : ventas) {
                
                // Obtener cliente completo
                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente cliente = clienteDAO.buscar(venta.getId_cliente().getId()).orElse(null);
                
                String nombreCliente = (cliente != null) ? cliente.getNombre() : "Cliente desconocido";
                
                // Escribir información de la venta
                writer.write("Venta #" + venta.getId() + "\n");
                writer.write("Cliente: " + nombreCliente + "\n");
                writer.write("Fecha: " + venta.getFecha() + "\n");
                writer.write("Total: $" + String.format("%,.2f", venta.getTotal()) + "\n");
                writer.write("\n");
                
                // Acumular monto
                montoTotal += venta.getTotal();
            }
            
            // Resumen
            writer.write("-------------------------------\n");
            writer.write("RESUMEN:\n");
            writer.write("Total Ventas: " + totalVentas + "\n");
            writer.write("Monto Total: $" + String.format("%,.2f", montoTotal) + "\n");
            
            if (totalVentas > 0) {
                double promedio = montoTotal / totalVentas;
                writer.write("Venta Promedio: $" + String.format("%,.2f", promedio) + "\n");
            }
            
            writer.write("===============================\n");
            
            // Mostrar mensaje de éxito
            System.out.println("\nReporte generado exitosamente:");
            System.out.println(archivoSeleccionado.getAbsolutePath());
            
            // Mostrar diálogo de éxito
            JOptionPane.showMessageDialog(
                null,
                "Reporte generado exitosamente:\n" + archivoSeleccionado.getAbsolutePath(),
                "Reporte Generado",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            // Preguntar si desea abrir el archivo
            int opcionAbrir = JOptionPane.showConfirmDialog(
                null,
                "¿Desea abrir el archivo ahora?",
                "Abrir archivo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (opcionAbrir == JOptionPane.YES_OPTION) {
                abrirArchivo(archivoSeleccionado);
            }
            
            return true;
            
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
            
            JOptionPane.showMessageDialog(
                null,
                "Error al generar el reporte:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            
            return false;
        }
    }

    public static boolean generarReportePorFecha(ArrayList<Venta> ventas, String fecha) {
        
        // Filtrar ventas por fecha
        ArrayList<Venta> ventasFiltradas = new ArrayList<>();
        
        for (Venta venta : ventas) {
            if (venta.getFecha().startsWith(fecha)) {
                ventasFiltradas.add(venta);
            }
        }
        
        if (ventasFiltradas.isEmpty()) {
            System.out.println("No se encontraron ventas para la fecha: " + fecha);
            
            JOptionPane.showMessageDialog(
                null,
                "No se encontraron ventas para la fecha: " + fecha,
                "Sin resultados",
                JOptionPane.WARNING_MESSAGE
            );
            
            return false;
        }
        
        System.out.println("Se encontraron " + ventasFiltradas.size() + " venta(s) para la fecha " + fecha);
        
        return generarReporteVentas(ventasFiltradas);
    }

    private static void abrirArchivo(File archivo) {
        try {
            // Verificar si Desktop es soportado
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                
                if (archivo.exists()) {
                    desktop.open(archivo);
                    System.out.println("Abriendo archivo...");
                }
            } else {
                System.out.println("No se puede abrir el archivo automáticamente en este sistema");
            }
        } catch (IOException e) {
            System.out.println("Error al abrir el archivo: " + e.getMessage());
            
            JOptionPane.showMessageDialog(
                null,
                "No se pudo abrir el archivo automáticamente.\n" +
                "Puede encontrarlo en:\n" + archivo.getAbsolutePath(),
                "Información",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    //analisis
    public static void analizarStockBajo() {
        CelularDAO celularDAO = new CelularDAO();
        ArrayList<Celular> todosCelulares = celularDAO.listarC();
        
        // Filtrar celulares con stock bajo
        ArrayList<Celular> stockBajo = new ArrayList<>();
        for (Celular cel : todosCelulares) {
            if (cel.getStock() < 5) {
                stockBajo.add(cel);
            }
        }
        
        System.out.println("\n===============================================");
        System.out.println("    ANALISIS: CELULARES CON STOCK BAJO");
        System.out.println("    (Menos de 5 unidades)");
        System.out.println("===============================================\n");
        
        if (stockBajo.isEmpty()) {
            System.out.println("*** Todos los productos tienen stock suficiente ***\n");
            return;
        }
        
        // Calcular anchos de columna
        int anchoMarca = "MARCA".length();
        int anchoModelo = "MODELO".length();
        int anchoStock = "STOCK".length();
        int anchoEstado = "ESTADO".length();
        
        for (Celular cel : stockBajo) {
            anchoMarca = Math.max(anchoMarca, cel.getMarca().length());
            anchoModelo = Math.max(anchoModelo, cel.getModelo().length());
        }
        
        // Formato de tabla
        String formato = "| %-4s | %-" + anchoMarca + "s | %-" + anchoModelo + 
                        "s | %-" + anchoStock + "s | %-" + anchoEstado + "s |\n";
        
        String separador = "+" + "-".repeat(6) + "+" + "-".repeat(anchoMarca + 2) + 
                          "+" + "-".repeat(anchoModelo + 2) + "+" + "-".repeat(anchoStock + 2) + 
                          "+" + "-".repeat(anchoEstado + 2) + "+\n";
        
        // Imprimir tabla
        System.out.print(separador);
        System.out.printf(formato, "ID", "MARCA", "MODELO", "STOCK", "ESTADO");
        System.out.print(separador);
        
        for (Celular cel : stockBajo) {
            String estado = cel.getStock() == 0 ? "AGOTADO" : "CRITICO";
            System.out.printf(formato, 
                cel.getId(),
                cel.getMarca(),
                cel.getModelo(),
                cel.getStock(),
                estado
            );
        }
        
        System.out.print(separador);
        System.out.println("\nTotal productos con stock bajo: " + stockBajo.size());
        System.out.println("*** Recomendacion: Reabastecer inventario ***\n");
    }

    public static void analizarTop3MasVendidos() {
        ItemsVentaDAO itemsDAO = new ItemsVentaDAO();
        CelularDAO celularDAO = new CelularDAO();
        
        ArrayList<ItemVenta> todosItems = itemsDAO.listarIV();
        
        System.out.println("\n===============================================");
        System.out.println("    ANALISIS: TOP 3 CELULARES MAS VENDIDOS");
        System.out.println("===============================================\n");
        
        if (todosItems.isEmpty()) {
            System.out.println("*** No hay ventas registradas ***\n");
            return;
        }
        
        // Crear mapa para contar ventas por celular
        // Clave: ID del celular, Valor: cantidad total vendida
        java.util.Map<Integer, Integer> ventasPorCelular = new java.util.HashMap<>();
        
        for (ItemVenta item : todosItems) {
            int idCelular = item.getId_celular().getId();
            int cantidad = item.getCantidad();
            
            ventasPorCelular.put(idCelular, 
                ventasPorCelular.getOrDefault(idCelular, 0) + cantidad);
        }
        
        // Convertir a lista para ordenar
        ArrayList<java.util.Map.Entry<Integer, Integer>> listaVentas = 
            new ArrayList<>(ventasPorCelular.entrySet());
        
        // Ordenar de mayor a menor
        listaVentas.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        // Tomar solo top 3
        int top = Math.min(3, listaVentas.size());
        
        // Calcular anchos
        int anchoPos = "POS".length();
        int anchoMarca = "MARCA".length();
        int anchoModelo = "MODELO".length();
        int anchoCantidad = "UNIDADES".length();
        int anchoPrecio = "PRECIO UNIT.".length();
        int anchoTotal = "TOTAL VENDIDO".length();
        
        for (int i = 0; i < top; i++) {
            int idCelular = listaVentas.get(i).getKey();
            Celular cel = celularDAO.buscar(idCelular).orElse(null);
            
            if (cel != null) {
                anchoMarca = Math.max(anchoMarca, cel.getMarca().length());
                anchoModelo = Math.max(anchoModelo, cel.getModelo().length());
            }
        }
        
        String formato = "| %-" + anchoPos + "s | %-" + anchoMarca + "s | %-" + anchoModelo + 
                        "s | %" + anchoCantidad + "s | %" + anchoPrecio + "s | %" + anchoTotal + "s |\n";
        
        String separador = "+" + "-".repeat(anchoPos + 2) + "+" + "-".repeat(anchoMarca + 2) + 
                          "+" + "-".repeat(anchoModelo + 2) + "+" + "-".repeat(anchoCantidad + 2) + 
                          "+" + "-".repeat(anchoPrecio + 2) + "+" + "-".repeat(anchoTotal + 2) + "+\n";
        
        // Imprimir tabla
        System.out.print(separador);
        System.out.printf(formato, "POS", "MARCA", "MODELO", "UNIDADES", "PRECIO UNIT.", "TOTAL VENDIDO");
        System.out.print(separador);
        
        double totalGeneral = 0;
        int unidadesGeneral = 0;
        
        for (int i = 0; i < top; i++) {
            int idCelular = listaVentas.get(i).getKey();
            int cantidadVendida = listaVentas.get(i).getValue();
            
            Celular cel = celularDAO.buscar(idCelular).orElse(null);
            
            if (cel != null) {
                double totalVendido = cantidadVendida * cel.getPrecio();
                totalGeneral += totalVendido;
                unidadesGeneral += cantidadVendida;
                
                System.out.printf(formato,
                    (i + 1),
                    cel.getMarca(),
                    cel.getModelo(),
                    cantidadVendida,
                    String.format("$%,.2f", cel.getPrecio()),
                    String.format("$%,.2f", totalVendido)
                );
            }
        }
        
        System.out.print(separador);
        System.out.println("\nTotal unidades vendidas (Top 3): " + unidadesGeneral);
        System.out.println("Total ventas (Top 3): $" + String.format("%,.2f", totalGeneral));
        System.out.println();
    }

    public static void analizarVentasPorMes() {
    VentaDAO ventaDAO = new VentaDAO();
    ArrayList<Venta> todasVentas = ventaDAO.listarV();
    
    System.out.println("\n===============================================");
    System.out.println("    ANALISIS: VENTAS TOTALES POR MES");
    System.out.println("===============================================\n");
    
    if (todasVentas.isEmpty()) {
        System.out.println("*** No hay ventas registradas ***\n");
        return;
    }
    
    // Mapa para agrupar: Clave = "YYYY-MM", Valor = [cantidad ventas, monto total]
    java.util.Map<String, double[]> ventasPorMes = new java.util.TreeMap<>();
    
    for (Venta venta : todasVentas) {
        // Extraer año-mes de la fecha (formato: 2025-02-10 14:30:00)
        String fecha = venta.getFecha();
        String anoMes = fecha.substring(0, 7); // "2025-02"
        
        if (!ventasPorMes.containsKey(anoMes)) {
            ventasPorMes.put(anoMes, new double[]{0, 0});
        }
        
        double[] datos = ventasPorMes.get(anoMes);
        datos[0]++; // contador de ventas
        datos[1] += venta.getTotal(); // suma de montos
    }
    
    // Calcular anchos dinámicamente basándose en el contenido
    int anchoMes = "MES".length();
    int anchoVentas = "CANT. VENTAS".length();
    int anchoMonto = "MONTO TOTAL".length();
    int anchoPromedio = "VENTA PROMEDIO".length();
    
    // Revisar cada mes para calcular anchos necesarios
    for (java.util.Map.Entry<String, double[]> entry : ventasPorMes.entrySet()) {
        String mes = entry.getKey();
        int cantidadVentas = (int) entry.getValue()[0];
        double montoTotal = entry.getValue()[1];
        double promedio = montoTotal / cantidadVentas;
        
        String mesFormateado = formatearMes(mes);
        String ventasStr = String.valueOf(cantidadVentas);
        String montoStr = String.format("$%,.2f", montoTotal);
        String promedioStr = String.format("$%,.2f", promedio);
        
        anchoMes = Math.max(anchoMes, mesFormateado.length());
        anchoVentas = Math.max(anchoVentas, ventasStr.length());
        anchoMonto = Math.max(anchoMonto, montoStr.length());
        anchoPromedio = Math.max(anchoPromedio, promedioStr.length());
    }
    
    // Formato de tabla con anchos calculados
    String formato = "| %-" + anchoMes + "s | %" + anchoVentas + "s | %" + 
                    anchoMonto + "s | %" + anchoPromedio + "s |\n";
    
    String separador = "+" + "-".repeat(anchoMes + 2) + "+" + 
                      "-".repeat(anchoVentas + 2) + "+" + 
                      "-".repeat(anchoMonto + 2) + "+" + 
                      "-".repeat(anchoPromedio + 2) + "+\n";
    
    // Imprimir tabla
    System.out.print(separador);
    System.out.printf(formato, "MES", "CANT. VENTAS", "MONTO TOTAL", "VENTA PROMEDIO");
    System.out.print(separador);
    
    double montoTotalGeneral = 0;
    int ventasTotalesGeneral = 0;
    
    for (java.util.Map.Entry<String, double[]> entry : ventasPorMes.entrySet()) {
        String mes = entry.getKey();
        int cantidadVentas = (int) entry.getValue()[0];
        double montoTotal = entry.getValue()[1];
        double promedio = montoTotal / cantidadVentas;
        
        // Convertir "2025-02" a "Febrero 2025"
        String mesFormateado = formatearMes(mes);
        
        System.out.printf(formato,
            mesFormateado,
            cantidadVentas,
            String.format("$%,.2f", montoTotal),
            String.format("$%,.2f", promedio)
        );
        
        montoTotalGeneral += montoTotal;
        ventasTotalesGeneral += cantidadVentas;
    }
    
    System.out.print(separador);
    System.out.println("\nResumen General:");
    System.out.println("Total meses con ventas: " + ventasPorMes.size());
    System.out.println("Total ventas: " + ventasTotalesGeneral);
    System.out.println("Monto total: $" + String.format("%,.2f", montoTotalGeneral));
    
    if (ventasTotalesGeneral > 0) {
        System.out.println("Promedio por venta: $" + 
            String.format("%,.2f", montoTotalGeneral / ventasTotalesGeneral));
    }
    System.out.println();
}

    private static String formatearMes(String anoMes) {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                         "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        
        String[] partes = anoMes.split("-");
        int ano = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        
        return meses[mes - 1] + " " + ano;
    }
}
    
