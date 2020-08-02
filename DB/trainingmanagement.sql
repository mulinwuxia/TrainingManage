/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 8.0.20 : Database - trainingmanagement
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`trainingmanagement` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `trainingmanagement`;

/*Table structure for table `administrator` */

DROP TABLE IF EXISTS `administrator`;

CREATE TABLE `administrator` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '培训公司管理者编号',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '培训公司管理者名称',
  `companyId` int DEFAULT NULL COMMENT '培训公司编号',
  `password` varchar(40) DEFAULT NULL COMMENT '培训公司管理者密码',
  PRIMARY KEY (`id`),
  KEY `companyId` (`companyId`),
  CONSTRAINT `administrator_ibfk_1` FOREIGN KEY (`companyId`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `administrator` */

insert  into `administrator`(`id`,`name`,`companyId`,`password`) values (1,'露娜',1,'123'),(2,'孙悟空',2,'123'),(3,'武则天',3,'123');

/*Table structure for table `apply` */

DROP TABLE IF EXISTS `apply`;

CREATE TABLE `apply` (
  `participantId` int NOT NULL COMMENT '培训参与者编号',
  `participantName` varchar(40) DEFAULT NULL COMMENT '培训参与者姓名',
  `trainingId` int NOT NULL COMMENT '培训课程编号',
  PRIMARY KEY (`participantId`,`trainingId`),
  KEY `trainingId` (`trainingId`),
  CONSTRAINT `apply_ibfk_1` FOREIGN KEY (`participantId`) REFERENCES `participant` (`id`),
  CONSTRAINT `apply_ibfk_2` FOREIGN KEY (`trainingId`) REFERENCES `training` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `apply` */

insert  into `apply`(`participantId`,`participantName`,`trainingId`) values (1,'钟馗',2);

/*Table structure for table `company` */

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '培训公司编号',
  `name` varchar(40) DEFAULT NULL COMMENT '培训公司名称',
  `money` double DEFAULT NULL COMMENT '培训公司资金',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `company` */

insert  into `company`(`id`,`name`,`money`) values (1,'A公司',1000000),(2,'B公司',1000000),(3,'C公司',1000000);

/*Table structure for table `grade` */

DROP TABLE IF EXISTS `grade`;

CREATE TABLE `grade` (
  `participantId` int NOT NULL COMMENT '培训参与者编号',
  `participantName` varchar(40) DEFAULT NULL COMMENT '培训参与者姓名',
  `trainingId` int NOT NULL COMMENT '培训课程编号',
  `score` double DEFAULT NULL COMMENT '培训成绩',
  PRIMARY KEY (`participantId`,`trainingId`),
  KEY `trainingId` (`trainingId`),
  CONSTRAINT `grade_ibfk_1` FOREIGN KEY (`participantId`) REFERENCES `participant` (`id`),
  CONSTRAINT `grade_ibfk_2` FOREIGN KEY (`trainingId`) REFERENCES `training` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `grade` */

insert  into `grade`(`participantId`,`participantName`,`trainingId`,`score`) values (1,'钟馗',1,0),(1,'钟馗',2,0),(2,'安琪拉',1,0),(3,'妲己',1,0),(4,'夏侯惇',1,0),(5,'上官婉儿',1,0);

/*Table structure for table `participant` */

DROP TABLE IF EXISTS `participant`;

CREATE TABLE `participant` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '培训参与者编号',
  `name` varchar(40) DEFAULT NULL COMMENT '培训参与者姓名',
  `password` varchar(40) DEFAULT NULL COMMENT '培训参与者密码',
  `money` double DEFAULT NULL COMMENT '培训参与者资金',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `participant` */

insert  into `participant`(`id`,`name`,`password`,`money`) values (1,'钟馗','123',100),(2,'安琪拉','234',100),(3,'妲己','345',100),(4,'夏侯惇','123',100),(5,'上官婉儿','234',100),(6,'公孙离','345',100),(7,'孙尚香','123',100),(8,'东方耀','234',100),(9,'张飞','345',100),(10,'牛魔','123',100);

/*Table structure for table `sign` */

DROP TABLE IF EXISTS `sign`;

CREATE TABLE `sign` (
  `participantId` int NOT NULL COMMENT '培训参与者编号',
  `participantName` varchar(40) DEFAULT NULL COMMENT '培训参与者姓名',
  `trainingId` int NOT NULL COMMENT '培训课程编号',
  `signFlag` int DEFAULT NULL COMMENT '培训签到，	1：已签到；0：未签到',
  PRIMARY KEY (`participantId`,`trainingId`),
  KEY `trainingId` (`trainingId`),
  CONSTRAINT `sign_ibfk_1` FOREIGN KEY (`participantId`) REFERENCES `participant` (`id`),
  CONSTRAINT `sign_ibfk_2` FOREIGN KEY (`trainingId`) REFERENCES `training` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sign` */

insert  into `sign`(`participantId`,`participantName`,`trainingId`,`signFlag`) values (1,'钟馗',1,1),(1,'钟馗',2,0),(2,'安琪拉',1,1),(3,'妲己',1,1),(4,'夏侯惇',1,1),(5,'上官婉儿',1,1);

/*Table structure for table `training` */

DROP TABLE IF EXISTS `training`;

CREATE TABLE `training` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '培训课程编号',
  `name` varchar(40) DEFAULT NULL COMMENT '培训课程名称',
  `companyId` int DEFAULT NULL COMMENT '培训公司编号',
  `time` date DEFAULT NULL COMMENT '培训课程时间',
  `price` double DEFAULT NULL COMMENT '培训课程价格',
  `capacity` int DEFAULT NULL COMMENT '培训课程课容量',
  PRIMARY KEY (`id`),
  KEY `companyId` (`companyId`),
  CONSTRAINT `training_ibfk_1` FOREIGN KEY (`companyId`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `training` */

insert  into `training`(`id`,`name`,`companyId`,`time`,`price`,`capacity`) values (1,'Java入门培训',1,'2020-07-25',60,0),(2,'Java入门培训',2,'2020-08-01',60,4),(3,'Java入门培训',3,'2020-08-02',60,5),(5,'python入门培训',2,'2020-08-03',60,5),(6,'python入门培训',3,'2020-09-04',60,5),(7,'python入门培训',1,'2020-09-06',65,5),(8,'ps小白入门培训',2,'2020-09-06',45,5),(9,'ps小白入门培训',3,'2020-09-07',45,5),(10,'PS小白入门培训',1,'2020-08-01',65,5);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
