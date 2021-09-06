-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: i5b105.p.ssafy.io    Database: ssafy_web_db
-- ------------------------------------------------------
-- Server version	8.0.26-0ubuntu0.20.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user_room`
--

DROP TABLE IF EXISTS `user_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `join_time` time DEFAULT NULL,
  `state` bit(1) DEFAULT NULL,
  `sum_time` bigint NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `roompk` bigint DEFAULT NULL,
  `userpk` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgt54e5c6xo562ckj4eato6mft` (`roompk`),
  KEY `FKt1tt1i70b2xhaoed74o3vhafh` (`userpk`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_room`
--

LOCK TABLES `user_room` WRITE;
/*!40000 ALTER TABLE `user_room` DISABLE KEYS */;
INSERT INTO `user_room` VALUES (27,'01:30:15',_binary '',0,'please',17,1),(26,'01:30:08',_binary '',0,'please',16,1),(25,'01:04:56',_binary '',0,'apple',14,3),(24,'01:02:07',_binary '',0,'ssafy',15,10),(21,'01:29:32',_binary '',705,'nahyun',14,8),(22,'00:50:29',_binary '',217,'kangjun',14,1),(23,'00:46:16',_binary '',0,'kido',14,2);
/*!40000 ALTER TABLE `user_room` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-20 10:35:06
