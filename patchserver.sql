-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 17-02-2019 a las 02:25:28
-- Versión del servidor: 5.7.23
-- Versión de PHP: 7.1.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `patchserver`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `id` int(11) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `iva` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`id`, `fecha`, `iva`, `id_usuario`) VALUES
(2, '2019-01-09 00:00:00', '3', 1),
(4, '2019-02-05 00:00:00', '21.0', 1),
(5, '2019-02-07 00:00:00', '6.0', 1),
(6, '2019-02-09 00:00:00', '21.0', 1),
(7, '2019-02-10 00:00:00', '21.0', 1),
(8, '2019-02-10 00:00:00', '21.0', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `favorito`
--

CREATE TABLE `favorito` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `favorito`
--

INSERT INTO `favorito` (`id`, `id_usuario`, `id_producto`) VALUES
(2, 1, 9),
(8, 1, 1),
(9, 1, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `linea`
--

CREATE TABLE `linea` (
  `id` int(11) NOT NULL,
  `cantidad` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `id_factura` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `linea`
--

INSERT INTO `linea` (`id`, `cantidad`, `id_producto`, `id_factura`) VALUES
(1, '2', 9, NULL),
(2, '3', 1, 2),
(3, '2', 2, NULL),
(4, '2', 4, 4),
(5, '4', 1, 6),
(6, '1', 2, 6),
(7, '1', 2, 7),
(8, '1', 3, 8),
(9, '1', 1, NULL),
(10, '1', 2, 6),
(11, '3', 8, 6),
(12, '3', 9, 6),
(13, '1', 13, 6),
(14, '1', 5, 6),
(15, '3', 1, 6),
(16, '1', 6, 6),
(17, '3', 8, 6),
(18, '1', 9, 6),
(19, '1', 6, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` int(11) NOT NULL,
  `desc` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `existencias` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `codigo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `foto` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_tipoproducto` int(11) DEFAULT NULL,
  `novedad` tinyint(1) DEFAULT NULL,
  `fecha` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `desc`, `existencias`, `precio`, `codigo`, `foto`, `id_tipoproducto`, `novedad`, `fecha`) VALUES
(1, 'Tela de flores', '5', 10.95, '12345A', 'foto.jpg', 1, 1, '20190105 15:30:45'),
(2, 'Tela 01', '3', 10.59, '12345C', 'foto.jpg', 1, 0, NULL),
(3, 'Tela 02', '4', 10.59, '12345D', 'foto.jpg', 1, 0, ''),
(4, 'Tela 03', '5', 10.59, '12345E', 'foto.jpg', 1, 1, '20190120 10:45:34'),
(5, 'Tela 05', '5', 10.59, '12345F', 'foto.jpg', 1, 0, NULL),
(6, 'Tela 06', '10', 9.95, '12345G', 'foto.jpg', 1, 0, NULL),
(7, 'Tela 07', '8', 8.95, '12345H', 'foto.jpg', 1, 1, '20170830 22:20:05'),
(8, 'Tela 08', '6', 6.95, '12345I', 'foto.jpg', 1, 0, NULL),
(9, 'Libro patch', '10', 12.95, '123A', 'foto.jpg', 5, 1, '20160505 13:50:51'),
(13, 'Tela09', '5', 3.95, '123C', 'foto.jpg', 1, 1, '20190216 14:09:36'),
(18, 'Tela sin foto', '145', 12, 'A8934D', 'foto.png', 1, 1, '20190120 10:45:34'),
(27, 'Tablero corte', '2', 20, 'A8934Y', '51eec8cf-fe13-509f-8adf-b23a7a5d0b4ea6.jpg', 3, 1, '20190120 10:45:34'),
(41, 'Tela 2 con foto', '145', 156, 'A8934E', 'foto.png', 1, 1, '20190216 13:50:42'),
(42, 'Tela 01 con foto', '10', 11, 'dadfadf', '8b9de427-66fd-ddc2-f19a-3d0ddc0c349at17-.jpg', 1, 1, '20190216 13:57:43'),
(43, 'libro nuevo patch', '2', 16, 'A8934Y', 'foto.png', 1, 1, '20190216 14:01:09'),
(45, 'Tela 2 con foto', '2', 2, 'A8934E', 'dd23b31c-d23c-5a36-d3cf-d1e815351485k1.jpg', 1, 1, '20190216 14:53:06'),
(46, 'Librito01', '2', 6, 'a001', 'foto.png', 5, 1, '20190217 11:00:44'),
(47, 'Librito02', '3', 7, 'a002', 'foto.png', 5, 1, '20190217 11:04:10'),
(48, 'librito03', '1', 15, 'a003', 'foto.png', 5, 1, '20190217 11:05:57'),
(49, 'librito004', '1', 1, 'a004', 'foto.png', 5, 1, '20190217 11:06:39');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoproducto`
--

CREATE TABLE `tipoproducto` (
  `id` int(11) NOT NULL,
  `desc` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `tipoproducto`
--

INSERT INTO `tipoproducto` (`id`, `desc`) VALUES
(1, 'Telas'),
(2, 'Patrones'),
(3, 'Accesorios'),
(4, 'Kits'),
(5, 'Libros'),
(6, 'Outlet');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipousuario`
--

CREATE TABLE `tipousuario` (
  `id` int(11) NOT NULL,
  `desc` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `tipousuario`
--

INSERT INTO `tipousuario` (`id`, `desc`) VALUES
(1, 'Admin'),
(2, 'Cliente'),
(3, 'Proveedor');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ape1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ape2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `login` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pass` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dni` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_tipousuario` int(11) DEFAULT NULL,
  `foto` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `nombre`, `ape1`, `ape2`, `login`, `pass`, `dni`, `id_tipousuario`, `foto`) VALUES
(1, 'Admin', 'Admin', 'Admin', 'Admin', 'c1c224b03cd9bc7b6a86d77f5dace40191766c485cd55dc48caf9ac873335d6f', '47692839Q', 2, 'fotoUser.png'),
(15, 'Alex', 'Barrantes', 'Cano', 'alex', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '44877215Y', 2, '82551863-0773-7577-4b50-9aa4ab61737ba5.jpg');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_factura_usuario1_idx` (`id_usuario`);

--
-- Indices de la tabla `favorito`
--
ALTER TABLE `favorito`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_favorito_usuario` (`id_usuario`),
  ADD KEY `fk_favorito_producto` (`id_producto`);

--
-- Indices de la tabla `linea`
--
ALTER TABLE `linea`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_linea_producto1_idx` (`id_producto`),
  ADD KEY `fk_linea_factura1_idx` (`id_factura`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_producto_tipoproducto1_idx` (`id_tipoproducto`);

--
-- Indices de la tabla `tipoproducto`
--
ALTER TABLE `tipoproducto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipousuario`
--
ALTER TABLE `tipousuario`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_usuario_tipousuario_idx` (`id_tipousuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `favorito`
--
ALTER TABLE `favorito`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `linea`
--
ALTER TABLE `linea`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT de la tabla `tipoproducto`
--
ALTER TABLE `tipoproducto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `tipousuario`
--
ALTER TABLE `tipousuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `fk_factura_usuario1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Filtros para la tabla `favorito`
--
ALTER TABLE `favorito`
  ADD CONSTRAINT `fk_favorito_producto` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_favorito_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Filtros para la tabla `linea`
--
ALTER TABLE `linea`
  ADD CONSTRAINT `fk_linea_factura1` FOREIGN KEY (`id_factura`) REFERENCES `factura` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_linea_producto1` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `fk_producto_tipoproducto1` FOREIGN KEY (`id_tipoproducto`) REFERENCES `tipoproducto` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_usuario_tipousuario` FOREIGN KEY (`id_tipousuario`) REFERENCES `tipousuario` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
