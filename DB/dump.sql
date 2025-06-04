-- MySQL dump 10.13  Distrib 8.4.5, for Linux (aarch64)
--
-- Host: localhost    Database: awesomepizza
-- ------------------------------------------------------
-- Server version	8.4.5

CREATE DATABASE awesomepizza;

--
-- Table structure for table `addition`
--

DROP TABLE IF EXISTS `addition`;


CREATE TABLE `addition` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_item_id` int NOT NULL,
  `ingredient_id` int NOT NULL,
  `is_addition` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_addition_ingredient` (`ingredient_id`),
  KEY `FKaorasaf8t6hfofm9h18hlk1ud` (`order_item_id`),
  CONSTRAINT `fk_addition_ingredient` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`id`),
  CONSTRAINT `fk_addition_orderitem` FOREIGN KEY (`order_item_id`) REFERENCES `orderitem` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


--
-- Dumping data for table `addition`
--

LOCK TABLES `addition` WRITE;

INSERT INTO `addition` VALUES (1,2,4,0),(2,3,14,1),(3,3,21,1);

UNLOCK TABLES;

--
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;


CREATE TABLE `ingredient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;

INSERT INTO `ingredient` VALUES (2,'Pomodoro','Salsa di pomodoro fresco per la base della pizza',0.00),(3,'Mozzarella','Formaggio fresco, morbido e filante',1.00),(4,'Basilico','Foglie fresche di basilico aromatico',0.00),(5,'Olio extravergine di oliva','Olio di oliva di alta qualità per condire',0.50),(6,'Origano','Spezie aromatiche essiccate',0.00),(7,'Salame piccante','Salame con un gusto deciso e piccante',1.00),(8,'Prosciutto cotto','Prosciutto cotto a fette sottili',1.00),(9,'Funghi','Funghi freschi affettati',0.50),(10,'Peperoni','Peperoni rossi tagliati a listarelle',0.50),(11,'Carciofi','Cuori di carciofo tagliati',0.50),(12,'Acciughe','Filetti di acciughe salate',1.00),(13,'Capperi','Capperi sotto sale per un gusto intenso',0.50),(14,'Olive nere','Olive nere denocciolate',0.50),(15,'Salsiccia','Salsiccia fresca sbriciolata',1.00),(16,'Cipolla','Cipolla affettata finemente',0.50),(17,'Aglio','Spicchi di aglio tritati',0.50),(18,'Ricotta','Formaggio fresco cremoso',1.00),(19,'Zucchine','Zucchine tagliate a rondelle',0.50),(20,'Melanzane','Melanzane grigliate',0.50),(21,'Gorgonzola','Formaggio erborinato, saporito e cremoso',1.00),(22,'Taleggio','Formaggio morbido, cremoso e aromatico',1.00),(23,'Parmigiano','Formaggio stagionato a pasta dura, dal sapore deciso',1.00),(24,'Speck','Speck affumicato dell\'Alto Adige',1.50);

UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;


CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `status_id` (`status_id`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`status_id`) REFERENCES `orderstatus` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;

INSERT INTO `order` VALUES (1,1,'2025-06-01 21:16:47'),(2,2,'2025-06-04 17:52:40');

UNLOCK TABLES;

--
-- Table structure for table `orderitem`
--

DROP TABLE IF EXISTS `orderitem`;


CREATE TABLE `orderitem` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `pizza_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orderitem_order` (`order_id`),
  KEY `fk_orderitem_pizza` (`pizza_id`),
  CONSTRAINT `fk_orderitem_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `fk_orderitem_pizza` FOREIGN KEY (`pizza_id`) REFERENCES `pizza` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


--
-- Dumping data for table `orderitem`
--

LOCK TABLES `orderitem` WRITE;

INSERT INTO `orderitem` VALUES (1,1,1),(2,1,1),(3,1,2),(4,2,1);

UNLOCK TABLES;

--
-- Table structure for table `orderstatus`
--

DROP TABLE IF EXISTS `orderstatus`;


CREATE TABLE `orderstatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `orderstatus`
--

LOCK TABLES `orderstatus` WRITE;

INSERT INTO `orderstatus` VALUES (1,'In attesa'),(2,'In preparazione'),(3,'Pronto'),(4,'Consegnato'),(5,'Annullato');

UNLOCK TABLES;

--
-- Table structure for table `pizza`
--

DROP TABLE IF EXISTS `pizza`;


CREATE TABLE `pizza` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `price` decimal(4,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `pizza`
--

LOCK TABLES `pizza` WRITE;

INSERT INTO `pizza` VALUES (1,'Margherita',6.50),(2,'Diavola',8.00),(3,'Quattro Formaggi',10.00);

UNLOCK TABLES;

--
-- Table structure for table `pizzaingredient`
--

DROP TABLE IF EXISTS `pizzaingredient`;


CREATE TABLE `pizzaingredient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pizza_id` int NOT NULL,
  `ingredient_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_pizza_ingredient` (`pizza_id`,`ingredient_id`),
  KEY `fk_pizzaingredient_ingredient` (`ingredient_id`),
  CONSTRAINT `fk_pizzaingredient_ingredient` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`id`),
  CONSTRAINT `fk_pizzaingredient_pizza` FOREIGN KEY (`pizza_id`) REFERENCES `pizza` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


--
-- Dumping data for table `pizzaingredient`
--

LOCK TABLES `pizzaingredient` WRITE;

INSERT INTO `pizzaingredient` VALUES (1,1,2),(2,1,3),(3,1,4),(4,1,5),(5,2,2),(6,2,3),(7,2,7),(8,3,3),(9,3,21),(10,3,22),(11,3,23);

UNLOCK TABLES;










-- Dump completed on 2025-06-04 17:20:09
