-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: db.springbike.site    Database: springbike
-- ------------------------------------------------------
-- Server version	5.7.30-0ubuntu0.18.04.1

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
-- Table structure for table `ActivationCode`
--

DROP TABLE IF EXISTS `ActivationCode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ActivationCode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL,
  `id_user` int(11) NOT NULL,
  `date_start` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `minutes_available` int(11) NOT NULL DEFAULT '30',
  `claimed` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  KEY `fk_activationcode_user_idx` (`id_user`),
  CONSTRAINT `fk_activationcode_user` FOREIGN KEY (`id_user`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Address`
--

DROP TABLE IF EXISTS `Address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `zipcode` varchar(8) DEFAULT NULL,
  `street` varchar(128) NOT NULL,
  `city` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Bike`
--

DROP TABLE IF EXISTS `Bike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Bike` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `price` decimal(13,2) NOT NULL,
  `id_type` int(11) DEFAULT NULL,
  `description` varchar(4096) DEFAULT NULL,
  `avatar_url` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_type_idx` (`id_type`),
  CONSTRAINT `fk_bike_type` FOREIGN KEY (`id_type`) REFERENCES `BikeType` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BikeType`
--

DROP TABLE IF EXISTS `BikeType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BikeType` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_UNIQUE` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Blacklist`
--

DROP TABLE IF EXISTS `Blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Blacklist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_company` int(11) NOT NULL,
  `id_client` int(11) NOT NULL,
  `reason` varchar(256) DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_blacklist_company_idx` (`id_company`),
  KEY `fk_blacklist_client_idx` (`id_client`),
  CONSTRAINT `fk_blacklist_client` FOREIGN KEY (`id_client`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_blacklist_company` FOREIGN KEY (`id_company`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Coupon`
--

DROP TABLE IF EXISTS `Coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL,
  `date_start` datetime DEFAULT NULL,
  `date_finish` datetime DEFAULT NULL,
  `value` decimal(13,2) NOT NULL,
  `voucher` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CouponTransaction`
--

DROP TABLE IF EXISTS `CouponTransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CouponTransaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `id_coupon` int(11) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_coupon_transaction_user_idx` (`id_user`),
  KEY `fk_coupon_transaction_coupon_idx` (`id_coupon`),
  CONSTRAINT `fk_coupon_transaction_coupon` FOREIGN KEY (`id_coupon`) REFERENCES `Coupon` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_coupon_transaction_user` FOREIGN KEY (`id_user`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Inventory`
--

DROP TABLE IF EXISTS `Inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Inventory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_company` int(11) NOT NULL,
  `id_bike` int(11) NOT NULL,
  `id_location` int(11) DEFAULT NULL,
  `rent_price_hour` decimal(13,2) NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `visible` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_bike_UNIQUE` (`id_bike`),
  KEY `fk_company_idx` (`id_company`),
  KEY `fk_bike_idx` (`id_bike`),
  KEY `fk_location_idx` (`id_location`),
  KEY `fk_id_location_idx` (`id_location`),
  CONSTRAINT `fk_inventory_bike` FOREIGN KEY (`id_bike`) REFERENCES `Bike` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_inventory_company` FOREIGN KEY (`id_company`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_inventory_location` FOREIGN KEY (`id_location`) REFERENCES `Location` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Lease`
--

DROP TABLE IF EXISTS `Lease`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Lease` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_company` int(11) NOT NULL,
  `id_client` int(11) NOT NULL,
  `id_inventory` int(11) NOT NULL,
  `date_start` datetime NOT NULL,
  `date_finish` datetime NOT NULL,
  `price_total` decimal(13,2) NOT NULL,
  `random_code` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_lease_company_idx` (`id_company`),
  KEY `fk_lease_client_idx` (`id_client`),
  KEY `fk_lease_inventory_idx` (`id_inventory`),
  KEY `id_lease_company_idx` (`id_company`),
  CONSTRAINT `id_lease_client` FOREIGN KEY (`id_client`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_lease_company` FOREIGN KEY (`id_company`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_lease_inventory` FOREIGN KEY (`id_inventory`) REFERENCES `Inventory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Location`
--

DROP TABLE IF EXISTS `Location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_company` int(11) DEFAULT NULL,
  `id_address` int(11) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `latitude` decimal(10,8) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_company_idx` (`id_company`),
  KEY `fk_id_address_idx` (`id_address`),
  CONSTRAINT `fk_location_address` FOREIGN KEY (`id_address`) REFERENCES `Address` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_location_company` FOREIGN KEY (`id_company`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Message`
--

DROP TABLE IF EXISTS `Message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user_sender` int(11) NOT NULL,
  `id_user_receiver` int(11) DEFAULT NULL,
  `subject` varchar(128) NOT NULL,
  `body` varchar(4096) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `email` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_message_user_sender_idx` (`id_user_sender`),
  KEY `fk_message_user_receiver_idx` (`id_user_receiver`),
  CONSTRAINT `fk_message_user_receiver` FOREIGN KEY (`id_user_receiver`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_user_sender` FOREIGN KEY (`id_user_sender`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Transaction`
--

DROP TABLE IF EXISTS `Transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Transaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_lease` int(11) NOT NULL,
  `id_location_start` int(11) DEFAULT NULL,
  `id_location_finish` int(11) DEFAULT NULL,
  `date_finish` datetime DEFAULT NULL,
  `finished` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_transaction_lease_idx` (`id_lease`),
  KEY `id_transaction_location_start_idx` (`id_location_start`),
  KEY `id_transaction_location_finish_idx` (`id_location_finish`),
  CONSTRAINT `id_transaction_lease` FOREIGN KEY (`id_lease`) REFERENCES `Lease` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_transaction_location_finish` FOREIGN KEY (`id_location_finish`) REFERENCES `Location` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `id_transaction_location_start` FOREIGN KEY (`id_location_start`) REFERENCES `Location` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(48) NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_confirmed` tinyint(4) NOT NULL DEFAULT '0',
  `password` varchar(86) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `balance` decimal(13,2) NOT NULL DEFAULT '0.00',
  `type` varchar(7) NOT NULL,
  `date_created` datetime DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(256) DEFAULT NULL,
  `avatar_url` varchar(256) DEFAULT NULL,
  `first_name` varchar(64) DEFAULT NULL,
  `last_name` varchar(64) DEFAULT NULL,
  `company_name` varchar(64) DEFAULT NULL,
  `id_location` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_location_idx` (`id_location`),
  CONSTRAINT `fk_user_location` FOREIGN KEY (`id_location`) REFERENCES `Location` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-20 13:30:56
