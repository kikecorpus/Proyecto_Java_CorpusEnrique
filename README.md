# 📱 TecnoStore

Sistema de gestión para una tienda de celulares desarrollado en Java con conexión a base de datos MySQL. Permite administrar el inventario de productos, clientes, ventas y generar reportes y análisis de negocio.

---

## 🗂️ Tabla de Contenidos

- [Descripción](#descripción)
- [Tecnologías](#tecnologías)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Base de Datos](#base-de-datos)
- [Instalación y Configuración](#instalación-y-configuración)
- [Funcionalidades](#funcionalidades)
- [Patrones de Diseño](#patrones-de-diseño)
- [Autor](#autor)

---

## 📋 Descripción

TecnoStore es una aplicación de consola que permite gestionar de forma integral una tienda de celulares. El sistema cubre el ciclo completo de ventas: desde la administración del inventario hasta la generación de reportes y análisis estadísticos del negocio.

---

## 🛠️ Tecnologías

| Herramienta | Versión | Uso |
|---|---|---|
| Java | 25 | Lenguaje principal |
| Maven | 3.x | Gestión de dependencias y build |
| MySQL | 8.x | Base de datos relacional |
| MySQL Connector/J | 9.6.0 | Driver JDBC para conectar Java con MySQL |

---

## 📁 Estructura del Proyecto

```
TecnoStore/
├── src/
│   └── main/
│       └── java/
│           └── com/mycompany/tecnostore/
│               ├── controlador/          # Lógica de negocio y acceso a datos
│               │   ├── ConexionDb.java       # Singleton de conexión a BD
│               │   ├── CelularDAO.java       # CRUD de celulares
│               │   ├── ClienteDAO.java       # CRUD de clientes
│               │   ├── VentaDAO.java         # CRUD de ventas
│               │   ├── ItemsVentaDAO.java    # CRUD de detalles de venta
│               │   ├── Calculos.java         # Cálculos de IVA y totales
│               │   ├── ReporteUtils.java     # Generación de reportes y análisis
│               │   ├── Validador.java        # Validaciones de entrada
│               │   └── ArchivoUtils.java     # Utilidades de archivos
│               ├── modelo/               # Entidades del dominio
│               │   ├── Celular.java
│               │   ├── Cliente.java
│               │   ├── Venta.java
│               │   ├── ItemVenta.java
│               │   └── CategoriaGama.java    # Enum: Alta, Media, Baja
│               └── vista/                # Interfaz de usuario (consola)
│                   ├── TecnoStore.java       # Punto de entrada (main)
│                   ├── PrincipalMenu.java    # Menú principal
│                   ├── GestorMain.java       # Coordinador de menús
│                   ├── GestorCelulares.java  # Gestión de celulares
│                   ├── GestorClientes.java   # Gestión de clientes
│                   ├── GestorVentas.java     # Gestión de ventas
│                   ├── GestorItemVentas.java # Gestión de detalles de venta
│                   ├── VentaCompleta.java    # Flujo de venta completa (Facade)
│                   ├── GestorReportes.java   # Generación de reportes
│                   └── GestorAnalisis.java   # Análisis y estadísticas
├── reportes/                             # Reportes generados (.txt)
├── SQL_scripts/
│   └── database.sql                      # Script de creación e inserción de datos
└── pom.xml
```

---

## 🗄️ Base de Datos

El sistema utiliza la base de datos `tecnostore` con el siguiente esquema relacional:

```
Celulares (id, marca, modelo, sistema_operativo, gama, precio, stock)
    |
    └──< Detalle_ventas (id, id_venta, id_celular, cantidad, subtotal)
                              |
Cliente (id, nombre, identificacion, correo, telefono)
    |
    └──< Venta (id, id_cliente, fecha, total)
```

Para crear e inicializar la base de datos, ejecuta el script SQL incluido:

```sql
-- En tu cliente MySQL:
source SQL_scripts/database.sql;
```

### Credenciales por defecto

> ⚠️ Modifica las credenciales en `ConexionDb.java` antes de usar en producción.

```java
URL:      jdbc:mysql://localhost:3306/tecnostore
Usuario:  root
Password: campus2023
```

---

## ⚙️ Instalación y Configuración

### Prerrequisitos

- Java 25 o superior
- Maven 3.x
- MySQL 8.x corriendo localmente

### Pasos

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/kikecorpus/Proyecto_Java_CorpusEnrique.git
   cd Proyecto_Java_CorpusEnrique
   ```

2. **Crea la base de datos:**
   ```bash
   mysql -u root -p < SQL_scripts/database.sql
   ```

3. **Configura las credenciales** en `ConexionDb.java` si son diferentes a las por defecto.

4. **Compila y ejecuta con Maven:**
   ```bash
   cd TecnoStore
   mvn compile
   mvn exec:java -Dexec.mainClass="com.mycompany.tecnostore.vista.TecnoStore"
   ```

   O desde NetBeans/IntelliJ: ejecuta directamente la clase `TecnoStore.java`.

---

## ✅ Funcionalidades

### 📦 Gestión de Celulares
- Registrar nuevo celular (marca, modelo, SO, gama, precio, stock)
- Actualizar información con tabla comparativa de cambios
- Eliminar celular (con validación de integridad referencial)
- Listar celulares con stock disponible en tabla formateada
- Buscar celular por ID

### 👤 Gestión de Clientes
- Registrar cliente con validación de correo único e identificación no duplicada
- Actualizar datos del cliente
- Eliminar cliente (con validación de ventas asociadas)
- Listar y buscar clientes

### 🛒 Gestión de Ventas
- **Venta completa paso a paso:**
  1. Selección del cliente
  2. Agregado de productos al carrito (múltiples ítems)
  3. Validación de stock en tiempo real
  4. Cálculo automático de subtotales, IVA (19%) y total
  5. Actualización automática del stock
  6. Impresión de factura en consola
- Actualizar venta y sus detalles
- Listar y buscar ventas

### 📊 Reportes
- **Reporte general:** exporta todas las ventas a un archivo `.txt` con resumen
- **Reporte por fecha:** filtra ventas por fecha específica (formato `YYYY-MM-DD`)
- Apertura automática del archivo generado (si el sistema lo soporta)

### 📈 Análisis y Estadísticas
- **Stock bajo:** detecta productos con menos de 5 unidades disponibles
- **Top 3 más vendidos:** ranking de celulares por unidades vendidas con monto total
- **Ventas por mes:** tabla mensual con cantidad de ventas, monto total y promedio
- **Reporte completo:** ejecuta todos los análisis en secuencia

---

## 🧩 Patrones de Diseño

| Patrón | Clase | Descripción |
|---|---|---|
| **Singleton** | `ConexionDb` | Garantiza una única instancia de conexión a la BD con doble verificación (`double-checked locking`) |
| **Prototype** | `Celular`, `Cliente`, `Venta` | Implementan `Cloneable` para crear copias antes de editar y mostrar comparativa de cambios |
| **Facade** | `VentaCompleta`, `GestorAnalisis` | Simplifica flujos complejos multi-paso en una sola operación |
| **DAO** | `CelularDAO`, `ClienteDAO`, `VentaDAO`, `ItemsVentaDAO` | Separación de la lógica de acceso a datos del resto de la aplicación |

---

## 📌 Detalles Técnicos Destacados

- **Stream API:** uso de `.stream()`, `.filter()`, `.mapToDouble()`, `.anyMatch()` para operaciones sobre colecciones sin bucles explícitos.
- **Optional:** manejo seguro de resultados nulos desde la base de datos.
- **PreparedStatement con `RETURN_GENERATED_KEYS`:** recuperación del ID generado tras cada inserción.
- **Tablas dinámicas en consola:** anchos de columna calculados automáticamente según el contenido.
- **IVA integrado:** el 19% se calcula y aplica en cada venta a través de la clase `Calculos`.
- **Validaciones robustas:** el `Validador` cubre entradas numéricas, formatos de correo, existencia en BD, rango de menús y fechas.

---

## 👨‍💻 Autor

**Enrique Corpus**

> *"Las Excusas son para los débiles"*

---