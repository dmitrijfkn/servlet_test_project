/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 8.0.25 : Database - servletsdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`servletsdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `servletsdb`;

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8mb3;

/*Data for the table `department` */

insert  into `department`(`id`,`name`) values 
(162,'Department of AI'),
(168,'Департамент русского языка'),
(177,'Департамент української мови');

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `surname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `salary` int DEFAULT NULL,
  `date_of_birth` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=382 DEFAULT CHARSET=utf8mb3;

/*Data for the table `employee` */

insert  into `employee`(`id`,`name`,`surname`,`email`,`salary`,`date_of_birth`) values 
(347,'Cristina','Petrova','dima.kostenk@gmail.com',17890,'2002-04-23 00:00:00'),
(348,'Янина','Самойлова','samoylova@gmail.com',1000,'2021-07-07 00:00:00'),
(350,'Тимофей','Карпов','karpov@gmail.com',1000,'2021-06-06 00:00:00'),
(351,'Їжак','Їжаков','Izakow@gmail.com',100,'2021-06-06 00:00:00'),
(352,'Александр','Пушкин','pushkin@gmail.com',10000,'1799-06-06 00:00:00'),
(369,'Лев','Толстой','Lev.tolstoy1828@gmail.com',9000,'1828-08-28 00:00:00'),
(370,'Іван','Франко','Franko@mail.ru',20,'1856-08-27 00:00:00'),
(371,'Леся','Українка','lesya@gmail.com',200,'1871-02-25 00:00:00'),
(372,'Alan','Turing','Turing@gmail.com',8500,'1912-06-23 00:00:00'),
(373,'Li','Fei-Fei','Fei.Fei@sina.cn',4500,'1977-05-27 00:00:00');

/*Table structure for table `employees_department` */

DROP TABLE IF EXISTS `employees_department`;

CREATE TABLE `employees_department` (
  `id_employee` int DEFAULT NULL,
  `id_department` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `employees_department` */

insert  into `employees_department`(`id_employee`,`id_department`) values 
(347,162),
(350,168),
(351,177),
(352,168),
(369,168),
(370,177),
(371,177),
(348,162),
(372,162),
(373,162);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
