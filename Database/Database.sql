-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Wersja serwera:               10.5.6-MariaDB - mariadb.org binary distribution
-- Serwer OS:                    Win64
-- HeidiSQL Wersja:              11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Zrzut struktury bazy danych games_data
DROP DATABASE IF EXISTS `games_data`;
CREATE DATABASE IF NOT EXISTS `games_data` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `games_data`;

-- Zrzut struktury tabela games_data.games_table
DROP TABLE IF EXISTS `games_table`;
CREATE TABLE IF NOT EXISTS `games_table` (
  `id` int(11) NOT NULL,
  `number_of_players` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Zrzucanie danych dla tabeli games_data.games_table: ~1 rows (około)
DELETE FROM `games_table`;
/*!40000 ALTER TABLE `games_table` DISABLE KEYS */;
INSERT INTO `games_table` (`id`, `number_of_players`) VALUES
	(1, 2);
/*!40000 ALTER TABLE `games_table` ENABLE KEYS */;

-- Zrzut struktury tabela games_data.moves_table
DROP TABLE IF EXISTS `moves_table`;
CREATE TABLE IF NOT EXISTS `moves_table` (
  `move_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `next_position` varchar(255) DEFAULT NULL,
  `previous_position` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`move_id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=latin1;

-- Zrzucanie danych dla tabeli games_data.moves_table: ~15 rows (około)
DELETE FROM `moves_table`;
/*!40000 ALTER TABLE `moves_table` DISABLE KEYS */;
INSERT INTO `moves_table` (`move_id`, `game_id`, `next_position`, `previous_position`) VALUES
	(1, 1, '52', '100'),
	(2, 1, '41', '66'),
	(3, 1, '31', '99'),
	(4, 1, '42', '70'),
	(5, 1, '32', '52'),
	(6, 1, '38', '65'),
	(7, 1, '50', '95'),
	(8, 1, '39', '68'),
	(9, 1, '29', '97'),
	(10, 1, '20', '69'),
	(11, 1, '28', '98'),
	(12, 1, '8', '20'),
	(13, 1, '98', '94'),
	(14, 1, '2', '67'),
	(15, 1, '14', '50'),
	(16, 1, '67', '62'),
	(17, 1, '50', '98'),
	(18, 1, '3', '8'),
	(19, 1, '4', '14'),
	(20, 1, '37', '64'),
	(21, 1, '14', '50'),
	(22, 1, '66', '61'),
	(23, 1, '100', '93'),
	(24, 1, '23', '42'),
	(25, 1, '0', '4'),
	(26, 1, '30', '63'),
	(27, 1, '94', '91'),
	(28, 1, '20', '67'),
	(29, 1, '97', '92'),
	(30, 1, '70', '66'),
	(31, 1, '49', '94'),
	(32, 1, '12', '3'),
	(33, 1, '33', '31'),
	(34, 1, '50', '38'),
	(35, 1, '5', '29'),
	(36, 1, '38', '37'),
	(37, 1, '53', '96'),
	(38, 1, '98', '50'),
	(39, 1, '6', '5'),
	(40, 1, '95', '38'),
	(41, 1, '1', '100'),
	(42, 1, '38', '39'),
	(43, 1, '48', '97'),
	(44, 1, '0', '0'),
	(45, 1, '47', '49'),
	(46, 1, '92', '95'),
	(47, 1, '39', '0'),
	(48, 1, '95', '38'),
	(49, 1, '8', '6'),
	(50, 1, '10', '70'),
	(51, 1, '27', '47'),
	(52, 1, '52', '30'),
	(53, 1, '38', '8'),
	(54, 1, '22', '41'),
	(55, 1, '68', '38'),
	(56, 1, '9', '22'),
	(57, 1, '38', '27'),
	(58, 1, '0', '9'),
	(59, 1, '64', '39'),
	(60, 1, '100', '52'),
	(61, 1, '27', '28'),
	(62, 1, '5', '0'),
	(63, 1, '67', '38'),
	(64, 1, '15', '5'),
	(65, 1, '38', '27'),
	(66, 1, '8', '20'),
	(67, 1, '27', '48'),
	(68, 1, '6', '8'),
	(69, 1, '8', '27'),
	(70, 1, '3', '23'),
	(71, 1, '5', '14'),
	(72, 1, '14', '6'),
	(73, 1, '54', '53'),
	(74, 1, '27', '3'),
	(75, 1, '6', '5'),
	(76, 1, '3', '10'),
	(77, 1, '21', '1'),
	(78, 1, '13', '12'),
	(79, 1, '40', '21'),
	(80, 1, '48', '27'),
	(81, 1, '70', '54'),
	(82, 1, '29', '2'),
	(83, 1, '65', '38'),
	(84, 1, '31', '15'),
	(85, 1, '66', '70'),
	(86, 1, '52', '31'),
	(87, 1, '16', '33'),
	(88, 1, '99', '52'),
	(89, 1, '70', '16'),
	(90, 1, '96', '13'),
	(91, 1, '69', '40'),
	(92, 1, '50', '14'),
	(93, 1, '62', '65'),
	(94, 1, '97', '50'),
	(95, 1, '16', '32'),
	(96, 1, '50', '29'),
	(97, 1, '21', '16'),
	(98, 1, '93', '95'),
	(99, 1, '65', '8'),
	(100, 1, '91', '96'),
	(101, 1, '1', '6'),
	(102, 1, '95', '50'),
	(103, 1, '63', '65'),
	(104, 1, '49', '48'),
	(105, 1, '8', '1'),
	(106, 1, '4', '3'),
	(107, 1, '40', '8'),
	(108, 1, '14', '4'),
	(109, 1, '65', '40'),
	(110, 1, '30', '14'),
	(111, 1, '61', '63'),
	(112, 1, '51', '30'),
	(113, 1, '40', '21'),
	(114, 1, '96', '51'),
	(115, 1, '63', '65'),
	(116, 1, '94', '49');
/*!40000 ALTER TABLE `moves_table` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
