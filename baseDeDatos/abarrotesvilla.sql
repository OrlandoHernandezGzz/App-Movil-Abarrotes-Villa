-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3307
-- Tiempo de generación: 21-04-2021 a las 04:43:38
-- Versión del servidor: 10.4.13-MariaDB
-- Versión de PHP: 7.4.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `abarrotesvilla`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `det_prod_usuario`
--

CREATE TABLE `det_prod_usuario` (
  `id_det` int(11) NOT NULL,
  `fk_producto` int(11) NOT NULL,
  `fk_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `codigo_prod` bigint(20) NOT NULL,
  `nombre_prod` varchar(30) NOT NULL,
  `descrip_prod` varchar(50) NOT NULL,
  `cantidad_prod` int(11) NOT NULL,
  `cantres_prod` int(11) NOT NULL,
  `preciocom_prod` float NOT NULL,
  `precioven_prod` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`codigo_prod`, `nombre_prod`, `descrip_prod`, `cantidad_prod`, `cantres_prod`, `preciocom_prod`, `precioven_prod`) VALUES
(75053895, 'catsup del monte 370g', 'imitación de salsa de tomate catsup', 10, 5, 15, 17);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `us_id` int(11) NOT NULL AUTO_INCREMENT,
  `us_nombre` varchar(30) NOT NULL,
  `us_apellidos` varchar(30) NOT NULL,
  `us_telefono` bigint(12) NOT NULL,
  `us_usuario` varchar(15) NOT NULL,
  `us_password` varchar(20) NOT NULL,
  `us_tipo` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`us_id`, `us_nombre`, `us_apellidos`, `us_telefono`, `us_usuario`, `us_password`, `us_tipo`) VALUES
(1, 'Eduarda Elizabeth', 'Saucedo Delgado', 8126617002, 'saucedo', '123456', 'Administrador');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `det_prod_usuario`
--
ALTER TABLE `det_prod_usuario`
  ADD PRIMARY KEY (`id_det`),
  ADD KEY `fk_producto` (`fk_producto`),
  ADD KEY `fk_usuario` (`fk_usuario`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`codigo_prod`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`us_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `det_prod_usuario`
--
ALTER TABLE `det_prod_usuario`
  MODIFY `id_det` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `us_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `det_prod_usuario`
--
ALTER TABLE `det_prod_usuario`
  ADD CONSTRAINT `det_prod_usuario_ibfk_1` FOREIGN KEY (`fk_producto`) REFERENCES `producto` (`codigo_prod`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `det_prod_usuario_ibfk_2` FOREIGN KEY (`fk_usuario`) REFERENCES `usuario` (`us_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
