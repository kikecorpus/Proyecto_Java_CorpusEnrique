DROP DATABASE tecnostore;

CREATE DATABASE tecnostore;

USE tecnostore;



-- -----------------------------------------------------
-- Table celulares
-- -----------------------------------------------------
CREATE TABLE Celulares (id INT AUTO_INCREMENT,
	marca VARCHAR(50),
	modelo VARCHAR(50),
	sistema_operativo VARCHAR(50),
	gama VARCHAR(50),
	precio DOUBLE(10,2),
	stock INT,
	PRIMARY KEY (id)
);

INSERT INTO Celulares (marca, modelo, sistema_operativo, gama, precio, stock) VALUES
('Samsung', 'Galaxy S23', 'Android', 'Alta', 4200000.00, 10),
('Apple', 'iPhone 15', 'iOS', 'Alta', 5200000.00, 8),
('Xiaomi', 'Redmi Note 13', 'Android', 'Media', 1200000.00, 15),
('Motorola', 'Moto G84', 'Android', 'Media', 950000.00, 20),
('Tecno', 'Spark 20', 'Android', 'Baja', 650000.00, 25);

-- -----------------------------------------------------
-- Table Cliente
-- -----------------------------------------------------

CREATE TABLE Cliente( id INT AUTO_INCREMENT, 
	nombre VARCHAR(50),
	identificacion VARCHAR(50),
	correo VARCHAR(50) UNIQUE,
	telefono VARCHAR(50),
	PRIMARY KEY  (id));

INSERT INTO Cliente (nombre, identificacion, correo, telefono) VALUES
('Carlos Perez', '1098765432', 'carlos@gmail.com', '3001234567'),
('Laura Gómez', '1122334455', 'laura@gmail.com', '3019876543'),
('Andrés Martínez', '9988776655', 'andres@gmail.com', '3024567890');



-- -----------------------------------------------------
-- Table Venta
-- -----------------------------------------------------

CREATE TABLE Venta(
	id INT AUTO_INCREMENT,
	id_cliente INT,
	fecha DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP, 
	total DOUBLE(10,2) DEFAULT 0,
	PRIMARY KEY (id),
	FOREIGN KEY (id_cliente) REFERENCES Cliente(id)
	);



INSERT INTO Venta (id_cliente, total) VALUES
(1, 0),
(2, 0),
(3, 0);

-- -----------------------------------------------------
-- Table detalle venta
-- -----------------------------------------------------
CREATE TABLE Detalle_ventas(
	id INT AUTO_INCREMENT,
	id_venta INT NOT NULL,
	id_celular INT NOT NULL,
	cantidad INT NOT NULL, 
	subtotal DOUBLE (10,2) NOT NULL, 
	PRIMARY KEY (id),
  	FOREIGN KEY (id_venta)
 	REFERENCES  Venta(id),
 	FOREIGN KEY (id_celular)
 	REFERENCES  Celulares(id)
);

INSERT INTO Detalle_ventas (id_venta, id_celular, cantidad, subtotal) VALUES
(1, 1, 1, 4200000.00),
(1, 3, 2, 2400000.00),
(2, 2, 1, 5200000.00),
(3, 4, 3, 2850000.00),
(3, 5, 2, 1300000.00);


UPDATE Venta SET total = 6600000.00 WHERE id = 1;
UPDATE Venta SET total = 5200000.00 WHERE id = 2;
UPDATE Venta SET total = 4150000.00 WHERE id = 3;



