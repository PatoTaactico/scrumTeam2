-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-04-2025 a las 19:05:24
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ferreteria`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id_cliente` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `direccion` varchar(50) DEFAULT NULL,
  `correo` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `nombre`, `telefono`, `direccion`, `correo`) VALUES
(1, 'José Martínez', '3216549870', 'Cra 45 #12-30', 'jose.m@gmail.com'),
(2, 'María López', '3109988776', 'Cl 60 #15-24', 'm.lopez@hotmail.com'),
(3, 'David Herrera', '3004567890', 'Av 68 #72-90', 'dherrera@outlook.com'),
(4, 'Tatiana Ruiz', '3205554443', 'Cl 85 #24-10', 'tatiruiz@gmail.com'),
(5, 'Kevin Morales', '3123456789', 'Cra 50 #44-60', 'kevinmorales@mail.com'),
(6, 'Daniela Romero', '3119876543', 'Cl 100 #55-12', 'daniromero@gmail.com'),
(7, 'Luis Salazar', '3134567890', 'Cl 30 #22-78', 'luissala@outlook.com'),
(8, 'Natalia Peña', '3105678934', 'Av 1 #10-11', 'npena@gmail.com'),
(9, 'Esteban Lara', '3012345678', 'Cl 92 #23-20', 'estelara@gmail.com'),
(10, 'Melisa Franco', '3156781234', 'Cra 21 #33-40', 'melifranco@hotmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `id_empleado` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `cargo` enum('administrador','vendedor') NOT NULL,
  `salario` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`id_empleado`, `nombre`, `cargo`, `salario`) VALUES
(1, 'Carlos Mendoza', 'administrador', 2500000),
(2, 'Laura Torres', 'vendedor', 1800000),
(3, 'Santiago Ruiz', 'vendedor', 1900000),
(4, 'Ana Gómez', 'administrador', 2400000),
(5, 'Felipe Díaz', 'vendedor', 1850000),
(6, 'Camila Duarte', 'vendedor', 1750000),
(7, 'Julián Pérez', 'administrador', 2600000),
(8, 'Marcela Ríos', 'vendedor', 1700000),
(9, 'Andrés Vargas', 'vendedor', 1950000),
(10, 'Valentina Silva', 'vendedor', 1800000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario_productos`
--

CREATE TABLE `inventario_productos` (
  `id_producto` int(11) NOT NULL,
  `nombre_producto` varchar(50) NOT NULL,
  `categoria` varchar(50) DEFAULT NULL,
  `cantidad_stock` int(11) DEFAULT 0,
  `precio_producto` int(10) NOT NULL,
  `id_proveedor_asociado` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `inventario_productos`
--

INSERT INTO `inventario_productos` (`id_producto`, `nombre_producto`, `categoria`, `cantidad_stock`, `precio_producto`, `id_proveedor_asociado`) VALUES
(1, 'Taladro eléctrico Bosch', 'herramientas', 12, 250000, 1),
(2, 'Cemento gris x50kg', 'materiales', 100, 32000, 3),
(3, 'Cable eléctrico 10m', 'electricidad', 60, 15000, 2),
(4, 'Martillo acero inoxidable', 'herramientas', 25, 40000, 5),
(5, 'Destornillador estrella', 'herramientas', 80, 15000, 10),
(6, 'Lámpara LED industrial', 'electricidad', 30, 120000, 6),
(7, 'Pintura vinilo blanco', 'materiales', 45, 35000, 8),
(8, 'Carretilla de obra', 'construccion', 20, 110000, 4),
(9, 'Bloque de concreto', 'construccion', 200, 2500, 7),
(10, 'Cinta aislante negra', 'electricidad', 100, 3000, 2),
(11, 'Llave inglesa', 'herramientas', 33, 37000, 1),
(12, 'Panel de yeso 1.2m x 2.4m', 'materiales', 50, 45000, 3),
(13, 'Tubería PVC 2 pulgadas', 'materiales', 40, 10000, 8),
(14, 'Broca para concreto', 'herramientas', 100, 7000, 5),
(15, 'Multímetro digital', 'electricidad', 22, 95000, 6),
(16, 'Nivel de burbuja 30cm', 'herramientas', 75, 20000, 10),
(17, 'Guantes de seguridad', 'construccion', 60, 8000, 9),
(18, 'Mascarilla N95', 'construccion', 100, 5000, 9),
(19, 'Alicate universal', 'herramientas', 40, 18000, 1),
(20, 'Extensión eléctrica 5m', 'electricidad', 35, 25000, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenes_compra`
--

CREATE TABLE `ordenes_compra` (
  `id_orden_compra` int(11) NOT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `id_empleado` int(11) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `fecha_compra` timestamp NOT NULL DEFAULT current_timestamp(),
  `estado_orden` enum('pendiente','pagada','enviada','') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ordenes_compra`
--

INSERT INTO `ordenes_compra` (`id_orden_compra`, `id_cliente`, `id_empleado`, `id_producto`, `total`, `fecha_compra`, `estado_orden`) VALUES
(1, 1, 2, 1, 500000, '2023-07-20 05:00:00', 'pendiente'),
(2, 3, 5, 4, 40000, '2023-09-28 05:00:00', 'pagada'),
(3, 7, 6, 7, 105000, '2023-12-08 05:00:00', 'enviada'),
(4, 2, 8, 10, 6000, '2025-01-24 05:00:00', 'pagada'),
(5, 6, 1, 16, 20000, '2023-03-10 05:00:00', 'pendiente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `id_proveedor` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `categoria_producto` enum('herramientas','electricidad','materiales','construccion') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`id_proveedor`, `nombre`, `telefono`, `categoria_producto`) VALUES
(1, 'Herramientas S.A.', '3101234567', 'herramientas'),
(2, 'ElectroDistribuciones', '3112345678', 'electricidad'),
(3, 'Materiales del Norte', '3123456789', 'materiales'),
(4, 'Constructora Andina', '3134567890', 'construccion'),
(5, 'FullPower Tools', '3145678901', 'herramientas'),
(6, 'Suministros Electric', '3156789012', 'electricidad'),
(7, 'Grupo Construc', '3167890123', 'construccion'),
(8, 'MaxiMateriales', '3178901234', 'materiales'),
(9, 'BuildPro Colombia', '3189012345', 'construccion'),
(10, 'TecnoHerramientas', '3190123456', 'herramientas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro_ventas`
--

CREATE TABLE `registro_ventas` (
  `id_venta` int(11) NOT NULL,
  `id_orden_compra` int(11) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `id_cliente` int(11) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `sub_total` int(10) NOT NULL,
  `precio_producto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `registro_ventas`
--

INSERT INTO `registro_ventas` (`id_venta`, `id_orden_compra`, `id_producto`, `id_cliente`, `id_empleado`, `cantidad`, `sub_total`, `precio_producto`) VALUES
(1, 2, 4, 3, 5, 1, 40000, 40000),
(2, 3, 7, 7, 6, 3, 105000, 35000),
(3, 4, 10, 2, 8, 2, 6000, 3000),
(4, 5, 16, 6, 1, 1, 20000, 20000),
(5, 1, 1, 1, 2, 2, 500000, 250000);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`);

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`id_empleado`);

--
-- Indices de la tabla `inventario_productos`
--
ALTER TABLE `inventario_productos`
  ADD PRIMARY KEY (`id_producto`),
  ADD KEY `id_proveedor_asociado` (`id_proveedor_asociado`);

--
-- Indices de la tabla `ordenes_compra`
--
ALTER TABLE `ordenes_compra`
  ADD PRIMARY KEY (`id_orden_compra`),
  ADD KEY `id_cliente` (`id_cliente`),
  ADD KEY `id_empleado` (`id_empleado`),
  ADD KEY `id_producto` (`id_producto`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`id_proveedor`);

--
-- Indices de la tabla `registro_ventas`
--
ALTER TABLE `registro_ventas`
  ADD PRIMARY KEY (`id_venta`),
  ADD KEY `id_orden_compra` (`id_orden_compra`),
  ADD KEY `id_producto` (`id_producto`),
  ADD KEY `id_cliente` (`id_cliente`),
  ADD KEY `id_empleado` (`id_empleado`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `empleados`
--
ALTER TABLE `empleados`
  MODIFY `id_empleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `inventario_productos`
--
ALTER TABLE `inventario_productos`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `ordenes_compra`
--
ALTER TABLE `ordenes_compra`
  MODIFY `id_orden_compra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `id_proveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `registro_ventas`
--
ALTER TABLE `registro_ventas`
  MODIFY `id_venta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `inventario_productos`
--
ALTER TABLE `inventario_productos`
  ADD CONSTRAINT `inventario_productos_ibfk_1` FOREIGN KEY (`id_proveedor_asociado`) REFERENCES `proveedores` (`id_proveedor`);

--
-- Filtros para la tabla `ordenes_compra`
--
ALTER TABLE `ordenes_compra`
  ADD CONSTRAINT `ordenes_compra_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`),
  ADD CONSTRAINT `ordenes_compra_ibfk_2` FOREIGN KEY (`id_empleado`) REFERENCES `empleados` (`id_empleado`);

--
-- Filtros para la tabla `registro_ventas`
--
ALTER TABLE `registro_ventas`
  ADD CONSTRAINT `registro_ventas_ibfk_1` FOREIGN KEY (`id_orden_compra`) REFERENCES `ordenes_compra` (`id_orden_compra`),
  ADD CONSTRAINT `registro_ventas_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `inventario_productos` (`id_producto`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
