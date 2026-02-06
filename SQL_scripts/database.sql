use tecnostore;


-- -----------------------------------------------------
-- Table celulares
-- -----------------------------------------------------
CREATE TABLE Celulares (
	
	id INT AUTO_INCREMENT,
	marca VARCHAR(50),
	modelo VARCHAR(50),
	sistema_operativo VARCHAR(50),
	precio DOUBLE(10,2),
	stock INT,
	PRIMARY KEY (id)
	
);

-- -----------------------------------------------------
-- Table Cliente
-- -----------------------------------------------------

CREATE TABLE Cliente(
	id INT AUTO_INCREMENT, 
	nombre VARCHAR(50),
	apellido VARCHAR(50),
	identificacion VARCHAR(50),
	correo VARCHAR(50),
	telefono VARCHAR(50),
	
	PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table Venta
-- -----------------------------------------------------

CREATE TABLE Venta(

	id INT AUTO_INCREMENT,
	id_cliente INT,
	fecha DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP, 
	total DOUBLE(10,2),
	
	PRIMARY KEY (id),
	FOREIGN KEY (id_cliente) REFERENCES Cliente(id)
	);

-- -----------------------------------------------------
-- Table detalle venta
-- -----------------------------------------------------
CREATE TABLE Detalle_ventas(
	
	id INT AUTO_INCREMENT,
	id_venta INT NOT NULL,
	id_celular INT NOT NULL,
	cantidad INT NOT NULL, 
	subtotal DOUBLE NOT NULL , 
	
	PRIMARY KEY (id),
  	FOREIGN KEY (id_venta)
 	REFERENCES  Venta(id),
 	FOREIGN KEY (id_celular)
 	REFERENCES  Celulares(id)
);


