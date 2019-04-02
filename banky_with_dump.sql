CREATE DATABASE  IF NOT EXISTS `banky` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_sv_0900_ai_ci */;
USE `banky`;
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: banky
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `accounts` (
  `account_number` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci NOT NULL,
  `owner_id` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci DEFAULT NULL,
  `balance` double NOT NULL,
  `account_type` enum('saving','sallary','card','PG','BG') COLLATE utf8mb4_sv_0900_ai_ci NOT NULL,
  `account_name` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci DEFAULT NULL,
  `company_id` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci DEFAULT NULL,
  `saldotak` double DEFAULT '0',
  PRIMARY KEY (`account_number`),
  KEY `owner_id` (`owner_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `company` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_sv_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES ('101010101','banken',99849999,'saving','bank',NULL,0),('111222333','8802122492',40652.5,'card','Ted Card',NULL,30000),('123123123','8802122492',9520,'sallary','Ted sallary',NULL,30000),('1234323','banken',875765,'saving','cfgh',NULL,0),('12345678','',405332,'BG','bankgiro','Halebop',0),('321321321','8802122492',160272,'saving','Ted Saving',NULL,50000),('33334444','',403006,'PG','Telia_PG','Telia',0),('55554444','',207224.5,'PG','postgiro','Eon',0),('666666666','9312026754',40000,'saving','agge saving',NULL,0);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `companies` (
  `company_id` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci NOT NULL,
  PRIMARY KEY (`company_id`),
  UNIQUE KEY `company_id_UNIQUE` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_sv_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companies`
--

LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
INSERT INTO `companies` VALUES ('Eon'),('Halebop'),('Telia');
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons`
--

DROP TABLE IF EXISTS `persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `persons` (
  `person_id` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci NOT NULL,
  `first_name` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci NOT NULL,
  `last_name` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci NOT NULL,
  `email` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci NOT NULL,
  `password` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `pers_nr` (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_sv_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons`
--

LOCK TABLES `persons` WRITE;
/*!40000 ALTER TABLE `persons` DISABLE KEYS */;
INSERT INTO `persons` VALUES ('8802122492','Ted','Agnér','ted@ted.com','123123',1),('9312026754','Agge','Bagge','bagge@agge.com','111111',2),('banken','swed','bank','bank@bank.se','banken',99999);
/*!40000 ALTER TABLE `persons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personxgiro`
--

DROP TABLE IF EXISTS `personxgiro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `personxgiro` (
  `person_id` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci DEFAULT NULL,
  `account` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `owner` (`person_id`) /*!80000 INVISIBLE */,
  KEY `account` (`account`),
  CONSTRAINT `account` FOREIGN KEY (`account`) REFERENCES `accounts` (`account_number`),
  CONSTRAINT `owner` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_sv_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personxgiro`
--

LOCK TABLES `personxgiro` WRITE;
/*!40000 ALTER TABLE `personxgiro` DISABLE KEYS */;
INSERT INTO `personxgiro` VALUES ('8802122492','33334444',3),('8802122492','55554444',4);
/*!40000 ALTER TABLE `personxgiro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `transactions` (
  `transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci NOT NULL,
  `receiver_id` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci NOT NULL,
  `transaction_type` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci NOT NULL,
  `transaction_amount` float NOT NULL,
  `message` varchar(45) COLLATE utf8mb4_sv_0900_ai_ci NOT NULL,
  `transaction_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`transaction_id`),
  KEY `reveiver_id` (`receiver_id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `account_id` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_number`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_sv_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,'111222333','33334444','transaction',200,'Card Payment','2019-03-29 09:44:35'),(2,'123123123','55554444','sdf',123,'hej','2019-03-29 09:45:39'),(3,'321321321','55554444','ert',234,'he','2019-03-29 09:55:04'),(4,'123123123','321321321','transaction',30,'test','2019-03-29 09:57:32'),(5,'111222333','33334444','transaction',200,'Card Payment','2019-03-29 10:22:08'),(6,'111222333','33334444','transaction',200,'Card Payment','2019-03-29 10:22:08'),(7,'111222333','33334444','transaction',200,'Card Payment','2019-03-29 10:22:08'),(8,'111222333','33334444','transaction',200,'Card Payment','2019-03-29 10:22:08'),(9,'111222333','33334444','transaction',200,'Card Payment','2019-03-29 10:22:08'),(10,'111222333','33334444','transaction',200,'Card Payment','2019-03-29 10:22:08'),(11,'111222333','33334444','transaction',200,'Card Payment','2019-03-29 10:22:08'),(12,'111222333','33334444','transaction',200,'Card Payment','2019-03-29 10:22:09'),(13,'111222333','12345678','TX1553856054954',213,'telefon','2019-03-29 10:40:54'),(14,'111222333','33334444','transaction',200,'Card Payment','2019-03-29 12:21:09'),(15,'123123123','12345678','TX1553863461107',234,'telefon','2019-03-29 12:44:21'),(16,'111222333','321321321','TX1553868300695',2,'we','2019-03-29 14:05:00'),(17,'321321321','33334444','AG1553868318221',3,'hej','2019-03-31 15:36:04'),(18,'111222333','55554444','AG1554047617905',1000,'Elräkning','2019-03-31 15:53:37'),(19,'111222333','33334444','AG1554049209166',3,'test','2019-03-31 16:20:09'),(20,'111222333','33334444','Card payment',200,'Card Payment','2019-04-02 13:30:15'),(21,'111222333','33334444','Card payment',200,'Card Payment','2019-04-02 13:30:55'),(22,'111222333','123123123','TX1554212844184',45,'r','2019-04-02 13:47:24');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'banky'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `AG1554046564590` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`localhost`*/ /*!50106 EVENT `AG1554046564590` ON SCHEDULE EVERY 1 MONTH STARTS '2019-04-25 17:36:04' ON COMPLETION NOT PRESERVE ENABLE DO CALL transaction('AG1554046564590',10000.0,'101010101','123123123','Lön') */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
/*!50106 DROP EVENT IF EXISTS `AG1554047617905` */;;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`localhost`*/ /*!50106 EVENT `AG1554047617905` ON SCHEDULE EVERY 1 MONTH STARTS '2019-03-31 17:53:37' ON COMPLETION NOT PRESERVE ENABLE DO CALL transaction('AG1554047617905',1000.0,'111222333','55554444','Elräkning') */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
/*!50106 DROP EVENT IF EXISTS `BG1553546601285` */;;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`localhost`*/ /*!50106 EVENT `BG1553546601285` ON SCHEDULE EVERY 1 MONTH STARTS '2019-03-25 21:43:21' ON COMPLETION NOT PRESERVE ENABLE DO CALL transaction('BG1553546601285',3234.0,'111222333','12345678','Hej') */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'banky'
--
/*!50003 DROP FUNCTION IF EXISTS `saldotakValidation` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `saldotakValidation`(acc_nr VARCHAR(45), saldotak INT, days INT, new_transaction INT) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
	DECLARE summa INT;
    
	SELECT SUM(transaction_amount) INTO summa FROM transactions
	WHERE account_id = acc_nr AND transaction_date_time >= now() - interval days day;
    
    IF (summa + new_transaction > saldotak)
		THEN RETURN false;
        ELSE RETURN true;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `account_balance` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `account_balance`(own_id VARCHAR(50))
BEGIN
	SELECT SUM(balance) AS balance, owner_id FROM accounts
	WHERE accounts.owner_id = own_id
    UNION
	SELECT balance, account_number as summa FROM accounts
	WHERE accounts.owner_id = own_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `all_transactions` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `all_transactions`(persId VARCHAR(50), limit_amount INT)
BEGIN
	SELECT * FROM transactions
	WHERE receiver_id IN (SELECT account_number FROM accounts WHERE owner_id = persId)
    OR account_id IN (SELECT account_number FROM accounts WHERE owner_id = persId)
    ORDER BY transaction_date_time DESC
    LIMIT limit_amount;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `create_account` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_account`(acc_nr VARCHAR(45), owner_id VARCHAR(45), balance DOUBLE, account_type VARCHAR(50), acc_name VARCHAR(50))
BEGIN
	INSERT INTO accounts (account_number, owner_id, balance, account_type, account_name)
    VALUES (acc_nr, owner_id, balance, account_type, acc_name);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `create_giro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_giro`(person_id VARCHAR(45), acc_nr VARCHAR(45))
BEGIN
    INSERT INTO personxgiro (person_id, `account`)
    VALUES (person_id, acc_nr);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `create_person` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_person`(person_id VARCHAR(50), first_name VARCHAR(50), last_name VARCHAR(50), email VARCHAR(50), `password` VARCHAR(50))
BEGIN
	INSERT INTO persons (person_id, first_name, last_name, email, `password`)
    VALUES (person_id, first_name, last_name, email, `password`);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_account` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_account`(acc_nr VARCHAR(45))
BEGIN
	SET foreign_key_checks = 0;
	DELETE FROM accounts WHERE account_number = acc_nr;
    SET foreign_key_checks = 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_giro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_giro`(pers_id VARCHAR(45), acc_nr VARCHAR(45))
BEGIN
	SET foreign_key_checks = 0;
	DELETE FROM personxgiro WHERE person_id = pers_id AND `account` = acc_nr;
    SET foreign_key_checks = 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_company_name` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_company_name`(acc_nr VARCHAR(45))
BEGIN
	SELECT companies.company_id FROM companies
	LEFT JOIN accounts
	ON companies.company_id = accounts.company_id
	WHERE accounts.account_number = acc_nr;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_giro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_giro`(pers_id VARCHAR(45))
BEGIN
	SELECT * FROM banky.accounts
	WHERE account_number IN (SELECT `account` FROM personxgiro WHERE person_id = pers_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_saldotak_current` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_saldotak_current`(acc_nr VARCHAR(45), days INT)
BEGIN
	SELECT SUM(transaction_amount) as summa FROM transactions
	WHERE account_id = acc_nr AND transaction_date_time >= now() - interval days day;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `transaction` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `transaction`(t_type VARCHAR(20), t_amount DOUBLE, t_sender VARCHAR(45), t_receiver VARCHAR(45), message VARCHAR(50))
BEGIN
    IF EXISTS (SELECT owner_id FROM accounts WHERE t_receiver = account_number) THEN
		INSERT INTO transactions (account_id, receiver_id, transaction_type, transaction_amount, message)
		VALUES (t_sender,t_receiver,t_type,t_amount,message);
    
		UPDATE accounts SET balance = balance + t_amount
		WHERE account_number = t_receiver;
        UPDATE accounts set balance = balance - t_amount
        WHERE account_number = t_sender;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_account` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_account`(acc_number VARCHAR(45), acc_name VARCHAR(50), acc_type VARCHAR(50))
BEGIN
	UPDATE accounts
    SET account_name = acc_name, account_type = acc_type
    WHERE account_number = acc_number;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-02 15:53:09
