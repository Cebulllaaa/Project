-- MySQL dump 10.17  Distrib 10.3.25-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: games_data
-- ------------------------------------------------------
-- Server version	10.3.25-MariaDB-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `games_table`
--

DROP TABLE IF EXISTS `games_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games_table` (
  `id` int(11) NOT NULL,
  `number_of_players` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games_table`
--

LOCK TABLES `games_table` WRITE;
/*!40000 ALTER TABLE `games_table` DISABLE KEYS */;
INSERT INTO `games_table` VALUES (1,2),(2,2);
/*!40000 ALTER TABLE `games_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_not_cached_value` bigint(21) NOT NULL,
  `minimum_value` bigint(21) NOT NULL,
  `maximum_value` bigint(21) NOT NULL,
  `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
  `increment` bigint(21) NOT NULL COMMENT 'increment value',
  `cache_size` bigint(21) unsigned NOT NULL,
  `cycle_option` tinyint(1) unsigned NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB SEQUENCE=1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1001,1,9223372036854775806,1,1,1000,0,0);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moves_table`
--

DROP TABLE IF EXISTS `moves_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `moves_table` (
  `move_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `next_position` varchar(255) DEFAULT NULL,
  `previous_position` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`move_id`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moves_table`
--

LOCK TABLES `moves_table` WRITE;
/*!40000 ALTER TABLE `moves_table` DISABLE KEYS */;
INSERT INTO `moves_table` VALUES (1,1,'52','100'),(2,1,'41','66'),(3,1,'31','99'),(4,1,'42','70'),(5,1,'32','52'),(6,1,'38','65'),(7,1,'50','95'),(8,1,'39','68'),(9,1,'29','97'),(10,1,'20','69'),(11,1,'28','98'),(12,1,'8','20'),(13,1,'98','94'),(14,1,'2','67'),(15,1,'14','50'),(16,1,'67','62'),(17,1,'50','98'),(18,1,'3','8'),(19,1,'4','14'),(20,1,'37','64'),(21,1,'14','50'),(22,1,'66','61'),(23,1,'100','93'),(24,1,'23','42'),(25,1,'0','4'),(26,1,'30','63'),(27,1,'94','91'),(28,1,'20','67'),(29,1,'97','92'),(30,1,'70','66'),(31,1,'49','94'),(32,1,'12','3'),(33,1,'33','31'),(34,1,'50','38'),(35,1,'5','29'),(36,1,'38','37'),(37,1,'53','96'),(38,1,'98','50'),(39,1,'6','5'),(40,1,'95','38'),(41,1,'1','100'),(42,1,'38','39'),(43,1,'48','97'),(44,1,'0','0'),(45,1,'47','49'),(46,1,'92','95'),(47,1,'39','0'),(48,1,'95','38'),(49,1,'8','6'),(50,1,'10','70'),(51,1,'27','47'),(52,1,'52','30'),(53,1,'38','8'),(54,1,'22','41'),(55,1,'68','38'),(56,1,'9','22'),(57,1,'38','27'),(58,1,'0','9'),(59,1,'64','39'),(60,1,'100','52'),(61,1,'27','28'),(62,1,'5','0'),(63,1,'67','38'),(64,1,'15','5'),(65,1,'38','27'),(66,1,'8','20'),(67,1,'27','48'),(68,1,'6','8'),(69,1,'8','27'),(70,1,'3','23'),(71,1,'5','14'),(72,1,'14','6'),(73,1,'54','53'),(74,1,'27','3'),(75,1,'6','5'),(76,1,'3','10'),(77,1,'21','1'),(78,1,'13','12'),(79,1,'40','21'),(80,1,'48','27'),(81,1,'70','54'),(82,1,'29','2'),(83,1,'65','38'),(84,1,'31','15'),(85,1,'66','70'),(86,1,'52','31'),(87,1,'16','33'),(88,1,'99','52'),(89,1,'70','16'),(90,1,'96','13'),(91,1,'69','40'),(92,1,'50','14'),(93,1,'62','65'),(94,1,'97','50'),(95,1,'16','32'),(96,1,'50','29'),(97,1,'21','16'),(98,1,'93','95'),(99,1,'65','8'),(100,1,'91','96'),(101,1,'1','6'),(102,1,'95','50'),(103,1,'63','65'),(104,1,'49','48'),(105,1,'8','1'),(106,1,'4','3'),(107,1,'40','8'),(108,1,'14','4'),(109,1,'65','40'),(110,1,'30','14'),(111,1,'61','63'),(112,1,'51','30'),(113,1,'40','21'),(114,1,'96','51'),(115,1,'63','65'),(116,1,'94','49'),(117,2,'38','67'),(118,2,'51','99'),(119,2,'20','62'),(120,2,'29','92'),(121,2,'7','64'),(122,2,'14','95'),(123,2,'1','68'),(124,2,'4','98'),(125,2,'6','38'),(126,2,'0','51'),(127,2,'5','20'),(128,2,'3','0'),(129,2,'15','7'),(130,2,'13','14'),(131,2,'30','1'),(132,2,'2','29'),(133,2,'52','6'),(134,2,'9','13'),(135,2,'99','5'),(136,2,'21','4'),(137,2,'8','65'),(138,2,'51','96'),(139,2,'1','8'),(140,2,'29','51'),(141,2,'68','70'),(142,2,'13','91'),(143,2,'39','66'),(144,2,'14','13'),(145,2,'96','15'),(146,2,'53','100'),(147,2,'20','69'),(148,2,'54','93'),(149,2,'93','30'),(150,2,'15','14'),(151,2,'0','39'),(152,2,'40','3'),(153,2,'8','20'),(154,2,'30','29'),(155,2,'100','52'),(156,2,'69','2'),(157,2,'39','61'),(158,2,'16','30'),(159,2,'65','63'),(160,2,'31','54'),(161,2,'20','39'),(162,2,'52','53'),(163,2,'38','65'),(164,2,'66','9'),(165,2,'3','0'),(166,2,'63','21'),(167,2,'2','1'),(168,2,'70','40'),(169,2,'4','68'),(170,2,'6','52'),(171,2,'19','38'),(172,2,'18','31'),(173,2,'10','19'),(174,2,'7','15'),(175,2,'24','2'),(176,2,'19','16'),(177,2,'12','20'),(178,2,'38','6'),(179,2,'13','4'),(180,2,'67','18'),(181,2,'28','10'),(182,2,'49','94'),(183,2,'50','3'),(184,2,'48','97'),(185,2,'97','12'),(186,2,'47','49'),(187,2,'94','13'),(188,2,'27','47'),(189,2,'9','8'),(190,2,'12','48'),(191,2,'10','24'),(192,2,'64','7'),(193,2,'98','28'),(194,2,'8','27'),(195,2,'11','9'),(196,2,'2','12'),(197,2,'26','10'),(198,2,'68','19'),(199,2,'95','50'),(200,2,'62','67'),(201,2,'92','97'),(202,2,'61','64'),(203,2,'91','94'),(204,2,'65','38'),(205,2,'47','11'),(206,2,'20','2'),(207,2,'46','26'),(208,2,'38','8'),(209,2,'48','46'),(210,2,'67','20'),(211,2,'49','47'),(212,2,'37','38'),(213,2,'97','48');
/*!40000 ALTER TABLE `moves_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-23  3:01:24
