-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 18-02-2019 a las 02:22:47
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
(16, '2019-02-18 00:00:00', '21.0', 1),
(17, '2019-02-18 00:00:00', '21.0', 24),
(18, '2019-02-18 00:00:00', '21.0', 24),
(26, '2019-02-18 00:00:00', '21.0', 24);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `favorito`
--

CREATE TABLE `favorito` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
(20, '1', 117, 16),
(21, '1', 110, 16),
(22, '1', 111, 16),
(23, '1', 83, 17),
(24, '1', 87, 17),
(25, '1', 87, 18),
(26, '1', 89, 18),
(27, '1', 93, 18),
(35, '1', 105, 26),
(36, '1', 76, 26);

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
(53, 'Pegamento para tela', '20', 15, 'A1', 'cd731f4f-02cf-76a9-9e2f-75bc61e64efaa1.jpg', 3, 1, '20190217 19:09:08'),
(54, 'Tijeras microserradas', '5', 25, 'A2', '1c743366-844d-d1df-c782-913956e71e21a2.jpg', 3, 1, '20190217 19:10:08'),
(55, 'Marcador de tiza', '3', 12, 'A3', '02f39782-b68f-f8e2-dbd7-3706b44d12f8a3.jpg', 3, 1, '20190217 19:16:03'),
(56, 'Set marcador de tiza', '10', 10, 'A4', '890d44a1-34c8-cee6-7402-5c4c786b008ba4.jpg', 3, 1, '20190217 19:15:38'),
(57, 'Cinta métrica retráctil', '5', 5, 'A5', '0e11f029-781b-d16b-f2fe-6b92394ea072a5.jpg', 3, 1, '20190217 19:17:26'),
(58, 'Tablero de corte A3', '2', 23, 'A6', '831001c9-a939-3268-6c46-48b8f976fab7a6.jpg', 3, 1, '20190217 19:18:33'),
(59, 'Regla Patchwork', '4', 20, 'A7', '27c9d462-143d-d2d5-718e-8bc1650f1ebca7.jpg', 3, 1, '20190217 19:19:56'),
(60, 'Cortador giratorio Olfa', '3', 11, 'A8', 'dee91300-38ed-d4ff-b98a-1d5348a2b55ca8.jpg', 3, 1, '20190217 19:20:37'),
(61, 'Varillas apliquick', '3', 26, 'A9', '0c751648-e182-0a42-3c27-f1a65f9d3230a9.jpg', 3, 1, '20190217 19:27:18'),
(62, 'Corbata Patchwork', '10', 35, 'K1', 'fd524456-5c42-3fb3-864a-4d4dfff175b9k1.jpg', 4, 1, '20190217 21:05:59'),
(63, 'Cubre cinta métrica', '10', 15, 'K2', '79b2696a-d0f3-c8a9-c1d0-c3b3152e3ba0k2.jpg', 4, 1, '20190217 20:57:56'),
(64, 'Kit Alfiletero librito', '3', 20, 'K3', '5e8470c5-1410-0e02-e707-d08cc92bb1b0k3.jpg', 4, 1, '20190217 20:45:31'),
(65, 'Sunbonet halloween', '2', 36, 'K4', '2ccdf449-5b62-aa0e-95af-639eb16a8454k4.jpg', 4, 1, '20190217 20:58:49'),
(66, 'Kit cuello cuadros', '3', 18, 'K5', 'b5903592-c32a-0e58-4db5-b1e75c923fd1k5.jpg', 4, 1, '20190217 20:49:14'),
(67, 'Kit mi casita de patch', '4', 12, 'K6', 'c1f02b4e-f8bf-cd3d-6e10-026d09d6e684k6.jpg', 4, 1, '20190217 20:48:26'),
(68, 'Mini quilt gatos', '10', 55, 'K7', '2178c005-3ffd-4ca9-cd8e-6621c63e3c79k7.jpg', 4, 1, '20190217 20:51:06'),
(69, 'Kit colcha country', '2', 240, 'K8', 'd8726232-c204-8624-c2f2-7c51734e353ck8.jpg', 4, 1, '20190217 20:52:40'),
(70, 'Mini quilt técnicas', '6', 26, 'K9', 'c5898093-eced-2aa4-4eb3-3747cb1f7bebk9.jpg', 4, 1, '20190217 20:53:35'),
(71, 'Patchwork japonés', '10', 22, 'L1', '5a45c9d1-d454-9224-c927-59a79c4ab44bl1.jpg', 5, 1, '20190217 21:17:04'),
(72, 'Patchwork y bordados', '2', 25, 'L2', '174c90e7-645c-2f5d-12e3-a4bfc72f3607l2.jpg', 5, 1, '20190217 21:18:02'),
(73, 'Puntos de bordado', '2', 15, 'L3', '4292b5c1-0bdc-6fbf-2307-44cb79869364l3.jpg', 5, 1, '20190217 21:19:21'),
(74, 'Patchwork en casa', '4', 6, 'L4', '479006d7-730d-1be6-3e23-96e9bb89a5a0l4.jpg', 5, 1, '20190217 21:20:03'),
(75, 'El Taller de Tilda', '2', 26, 'L5', '4062f1b1-e165-02a6-2a20-547ca8658db2l5.jpg', 5, 1, '20190217 21:20:43'),
(76, 'Libro Shabby home', '2', 28, 'L6', 'e9ba2534-3cf4-2e9c-d4f1-b579b8f443fcl6.jpg', 5, 1, '20190217 21:31:21'),
(82, 'Tela rojo oscuro flores', '20', 12, 'T1', '51f1ea67-9420-b312-78b4-692a4bcd893dt1.jpg', 1, 1, '20190217 22:11:21'),
(83, 'Tela flores y claveles', '19', 12, 'T2', '823d729b-f3fb-cc0a-e622-a5689719b46et2.jpg', 1, 1, '20190217 22:09:58'),
(84, 'Tela azul flores rojas', '20', 14, 'T3', '9515d203-7b46-18b2-8df2-75cc85f7dd75t3.jpg', 1, 1, '20190217 22:12:22'),
(85, 'Tela azul flores rojas2', '20', 14, 'T4', '00c118c0-c038-df99-ba56-cbdb00deb1det4.jpg', 1, 1, '20190217 22:20:05'),
(86, 'Tela azul flores azules', '20', 12, 'T5', 'a10a0b00-f757-7df0-1d48-b2e67f0ca705t5.jpg', 1, 1, '20190217 22:15:13'),
(87, 'Tela azul a cuadros', '18', 15, 'T6', 'dc250fa1-36af-76c2-e743-a75337b4e801t6.jpg', 1, 1, '20190217 22:16:32'),
(88, 'Tela azul flores azules2', '20', 12, 'T7', 'b0b28359-e04d-23a7-8881-b5dd6b99fce3t7.jpg', 1, 1, '20190217 22:19:38'),
(89, 'Tela azul flores rojas3', '19', 15, 'T8', '090f5a9f-feb7-6dfb-fd05-6f61e0577a9dt8.jpg', 1, 1, '20190217 23:26:26'),
(90, 'Tela roja flores blancas', '20', 12, 'T9', 'bc6c18f2-5c13-f01a-48cb-aba81ed1fad0t9.jpg', 1, 1, '20190217 23:27:45'),
(91, 'Tela marrón con flores', '20', 16, 'T10', 'bfdaac4c-94d2-de09-d25b-f5713a15eea3t10.jpg', 1, 1, '20190217 23:57:00'),
(92, 'Tela roja flores rojas', '20', 17, 'T11', 'e5acc1b2-e351-cb26-fc71-861c0b9598cbt11.jpg', 1, 1, '20190217 23:29:19'),
(93, 'Tela marrón con flores2', '19', 10, 'T12', 'd63ea11f-3b24-a1a6-babf-cb0f6c08901at12.jpg', 1, 1, '20190217 23:58:08'),
(94, 'Tela caqui hojas', '20', 12, 'T13', '8e993eda-9d39-69e0-9f6c-27e8cd8921f4t13.jpg', 1, 1, '20190217 23:31:25'),
(95, 'Tela marrón puntos', '20', 11, 'T14', '69ee597b-54a4-29c8-5985-1f9bdaab9b82t14.jpg', 1, 1, '20190217 23:32:05'),
(96, 'Tela marrón flores2', '20', 12, 'T15', '57597067-e84e-e8ee-7964-32aa865e7ac9t15.jpg', 1, 1, '20190217 23:36:02'),
(97, 'Tela con hojas marrón', '20', 9, 'T16', 'aed7e7e3-31ca-c186-803a-010c2665b3d9t16.jpg', 1, 1, '20190217 23:34:27'),
(98, 'Tela japonesa azul', '20', 20, 'T17', '241654d3-2702-5fde-e622-4efda7365598t16-.jpg', 1, 1, '20190217 23:37:29'),
(99, 'Tela japonesa marrón', '20', 20, 'T18', '89a63218-bb43-35f6-8984-8cbac94a8dbdt18.jpg', 1, 1, '20190217 23:41:36'),
(100, 'Tela blanca hojas', '20', 12, 'T19', '9576d81a-336a-5baf-6d69-82c07dd4bd0bt19.jpg', 1, 1, '20190217 23:43:16'),
(101, 'Tela blanca cachemir', '20', 12, 'T20', '699490a4-67cc-8870-3753-83805169048et20.jpg', 1, 1, '20190217 23:43:59'),
(102, 'Tela blanca flores', '20', 12, 'T21', 'b4670964-abef-4a0a-7bd5-a679292e4460t21.jpg', 1, 1, '20190217 23:46:17'),
(103, 'Tela blanca ramitas', '20', 8, 'O1', '6abe8737-9347-0ced-68ba-0860c73b3b91o1.jpg', 6, 1, '20190217 23:48:41'),
(104, 'Tela marrón corazón', '10', 6, 'O2', '0eda3e18-9899-a5de-c011-b4f722fae7afo2.jpg', 6, 1, '20190217 23:54:30'),
(105, 'Tela marrón navidad', '9', 6, 'O3', 'e8d3a169-dcf8-f7e7-3df8-b541724b2a29o3.jpg', 6, 1, '20190217 23:52:33'),
(106, 'Tela azul estrellas', '5', 5, 'O4', '8697cf6d-ffa0-7102-8c43-21fa06b39190o4.jpg', 6, 1, '20190217 23:53:12'),
(107, 'Tela roja estrellas', '5', 6, 'O5', '03e59918-f846-b3d7-ff97-0dd9016e8d66o5.jpg', 6, 1, '20190217 23:53:59'),
(108, 'Tela vintage flores', '6', 6, 'O6', '94f3b914-ed3c-b358-00d2-a9df874abbb1o6.jpg', 6, 1, '20190217 23:55:16'),
(109, 'Patrón Muñeca tilda', '2', 25, 'P1', 'cd603e62-b595-3bd8-7449-565a77bc39e2p1.jpg', 2, 1, '20190218 00:13:48'),
(110, 'Patrón maceta fieltro', '5', 6, 'P2', '023371d4-db12-000e-5dd0-74179f986c58p2.jpg', 2, 1, '20190218 00:14:41'),
(111, 'Pájaro Log cabin', '6', 12, 'P3', '40f17ada-7219-fa4c-7676-51e6bb4e9a6ap3.jpg', 2, 1, '20190218 00:21:59'),
(112, 'Patrón Cesta flores', '6', 25, 'P4', 'dc9e8bf1-b5d7-a06f-a1a3-9fb24e14279cp4.jpg', 2, 1, '20190218 00:16:31'),
(113, 'Patrón duende otoño', '5', 18, 'P5', '5f879a33-5a8e-7d2c-644a-007abd15a10fp5.jpg', 2, 1, '20190218 00:17:14'),
(114, 'Patrón cartera gato', '4', 18, 'P6', 'a57050b5-6a2b-6927-61d6-d2747decea88p6.jpg', 2, 1, '20190218 00:17:53'),
(115, 'Patrón bolso bandolera', '3', 22, 'P7', 'ab2d1516-37c2-874e-b4de-8f67530029edp7.jpg', 2, 1, '20190218 00:18:45'),
(116, 'Colcha plato Dresden', '7', 16, 'P8', '466730e7-6f78-bf42-ec58-06838e06ed81p8.jpg', 2, 1, '20190218 00:20:40'),
(117, 'Patrón colcha conejitos', '10', 22, 'P9', '4abae547-f2e2-25f6-2a2f-92281c7a87c4p9.jpg', 2, 1, '20190218 00:23:03');

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
(1, 'Admin', 'Admin', 'Admin', 'Admin', 'c1c224b03cd9bc7b6a86d77f5dace40191766c485cd55dc48caf9ac873335d6f', '47692839Q', 1, 'fotoUser.png'),
(24, 'Cliente', 'Cliente', 'Cliente', 'Cliente', 'f851d9a83ab0e0b98ec70876284df326df5b692656f1eb7982dc3f3656f1a17d', '12345678Y', 2, 'fotoUser.png');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `favorito`
--
ALTER TABLE `favorito`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `linea`
--
ALTER TABLE `linea`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=118;

--
-- AUTO_INCREMENT de la tabla `tipoproducto`
--
ALTER TABLE `tipoproducto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `tipousuario`
--
ALTER TABLE `tipousuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

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
