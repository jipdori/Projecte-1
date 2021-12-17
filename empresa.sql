-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Temps de generació: 17-12-2021 a les 18:42:34
-- Versió del servidor: 10.4.21-MariaDB
-- Versió de PHP: 7.4.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de dades: `empresa`
--

-- --------------------------------------------------------

--
-- Estructura de la taula `categories`
--

CREATE TABLE `categories` (
  `id_categoria` int(11) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `descripcio` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Bolcament de dades per a la taula `categories`
--

INSERT INTO `categories` (`id_categoria`, `nom`, `descripcio`) VALUES
(1, 'deportiva', 'Roba per entrenar'),
(2, 'pijames', 'Roba per dormir.'),
(3, 'roba_interior', 'Roba interior.'),
(4, 'jaquetes', 'Jaquetes.'),
(5, 'camisetes', 'Camisetes.'),
(6, 'jerseis', 'Jerseis'),
(7, 'cosplay', 'Cosplay.'),
(8, 'banyadors', 'Banyadors.');

-- --------------------------------------------------------

--
-- Estructura de la taula `equivalencia`
--

CREATE TABLE `equivalencia` (
  `codi1` int(11) NOT NULL,
  `codi2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de la taula `pertany`
--

CREATE TABLE `pertany` (
  `id_categoria` int(11) NOT NULL,
  `codiProducte` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Bolcament de dades per a la taula `pertany`
--

INSERT INTO `pertany` (`id_categoria`, `codiProducte`) VALUES
(5, 1234),
(6, 4321);

-- --------------------------------------------------------

--
-- Estructura de la taula `productes`
--

CREATE TABLE `productes` (
  `codi` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `material` varchar(30) NOT NULL,
  `preu` decimal(5,2) NOT NULL,
  `dni_proveidor` char(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Bolcament de dades per a la taula `productes`
--

INSERT INTO `productes` (`codi`, `stock`, `nom`, `material`, `preu`, `dni_proveidor`) VALUES
(1234, 11, 'chaqueta', 'cuero', '44.00', '123456789'),
(4321, 12, 'jersey', 'coton', '55.00', '123456789'),
(5324, 15, 'pijama', 'coton', '21.00', '223456789'),
(5423, 18, 'zapatos', 'cuero', '63.00', '223456789'),
(6666, 8, 'camiseta', 'coton', '15.00', '123456789'),
(6741, 10, 'bufanda', 'poliester', '5.00', '123456789');

-- --------------------------------------------------------

--
-- Estructura de la taula `proveidors`
--

CREATE TABLE `proveidors` (
  `dni` char(9) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `cognom` varchar(30) NOT NULL,
  `telefon` varchar(9) NOT NULL,
  `direccio` varchar(30) NOT NULL,
  `correu` varchar(30) NOT NULL,
  `contrassenya` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Bolcament de dades per a la taula `proveidors`
--

INSERT INTO `proveidors` (`dni`, `nom`, `cognom`, `telefon`, `direccio`, `correu`, `contrassenya`) VALUES
('112345678', 'Shein', 'shein', '900911912', 'Guangzhou', 'shein@gmail.com', 1123),
('123456789', 'Alibaba', 'alibaba', '654546524', 'China', 'alibaba@gmail.com', 2234),
('223456789', 'Amazon', 'amazon', '922012345', 'Washington', 'amazon@gmail.com', 3345),
('334567891', 'Aliexpress', 'aliexpress', '932471653', 'Hangzhou', 'aliexpress@gmail.com', 4456),
('445678912', 'Bigbuy', 'bigbuy', '961150422', 'Valencia', 'bigbuy@gmail.com', 5567);

--
-- Índexs per a les taules bolcades
--

--
-- Índexs per a la taula `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Índexs per a la taula `equivalencia`
--
ALTER TABLE `equivalencia`
  ADD PRIMARY KEY (`codi1`,`codi2`),
  ADD KEY `codi2` (`codi2`),
  ADD KEY `codi1` (`codi1`);

--
-- Índexs per a la taula `pertany`
--
ALTER TABLE `pertany`
  ADD PRIMARY KEY (`id_categoria`,`codiProducte`),
  ADD KEY `codiProducte` (`codiProducte`);

--
-- Índexs per a la taula `productes`
--
ALTER TABLE `productes`
  ADD PRIMARY KEY (`codi`),
  ADD KEY `FK_dniProveidor` (`dni_proveidor`);

--
-- Índexs per a la taula `proveidors`
--
ALTER TABLE `proveidors`
  ADD PRIMARY KEY (`dni`);

--
-- Restriccions per a les taules bolcades
--

--
-- Restriccions per a la taula `equivalencia`
--
ALTER TABLE `equivalencia`
  ADD CONSTRAINT `equivalencia_ibfk_1` FOREIGN KEY (`codi1`) REFERENCES `productes` (`codi`),
  ADD CONSTRAINT `equivalencia_ibfk_2` FOREIGN KEY (`codi2`) REFERENCES `productes` (`codi`);

--
-- Restriccions per a la taula `pertany`
--
ALTER TABLE `pertany`
  ADD CONSTRAINT `pertany_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categories` (`id_categoria`),
  ADD CONSTRAINT `pertany_ibfk_2` FOREIGN KEY (`codiProducte`) REFERENCES `productes` (`codi`);

--
-- Restriccions per a la taula `productes`
--
ALTER TABLE `productes`
  ADD CONSTRAINT `FK_dniProveidor` FOREIGN KEY (`dni_proveidor`) REFERENCES `proveidors` (`dni`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
