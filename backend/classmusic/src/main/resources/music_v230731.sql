-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: music
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `article_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `created_at` int DEFAULT (unix_timestamp()),
  `hit` int DEFAULT NULL,
  PRIMARY KEY (`article_id`),
  KEY `user_to_article_FK_user_id` (`user_id`),
  CONSTRAINT `user_to_article_FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (3,1,'test title','test content',1689905051,0),(6,1,'test title1','test content1',0,0),(7,1,'test title2','test content2',0,0),(8,1,'test title3','test content3',0,0),(9,1,'test title4','test content4',0,0),(10,1,'update title11','update content11',0,12),(11,1,'test title5','test content5',0,0),(12,1,'test ttt','test ttt',1689912777,0),(13,1,'test ttttt','test ttttt',0,0),(14,1,'test tttt12t','test tt12ttt',1689913038,0),(15,1,'test tttt12t','test tt12ttt',1689913127,0),(16,1,'new one title','new one content',1689915442,0),(17,1,'update new title11','update new content11',1689918135,0),(20,1,'update new title11','update new content11',1690243167,5),(21,14,'update new title123123','update new content123123',1690773100,8),(24,16,'안녕하세요 자유게시판에 쓰는 두번째 글입니다','글내용입니다 두번째 감사합니다',1690784177,0),(25,16,'안녕하세요 자유게시판에 쓰는 두번째 글입니다','글내용입니다 두번째 감사합니다',1690784908,0);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_file`
--

DROP TABLE IF EXISTS `article_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_file` (
  `article_file_id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NOT NULL,
  `file_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`article_file_id`),
  KEY `article_to_article_file_FK_article_id` (`article_id`),
  CONSTRAINT `article_to_article_file_FK_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_file`
--

LOCK TABLES `article_file` WRITE;
/*!40000 ALTER TABLE `article_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `chat_id` int NOT NULL AUTO_INCREMENT,
  `contact_id` int NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_at` int DEFAULT (unix_timestamp()),
  `sender` tinyint DEFAULT NULL,
  PRIMARY KEY (`chat_id`),
  KEY `contact_to_chat_FK_contact_id` (`contact_id`),
  CONSTRAINT `contact_to_chat_FK_contact_id` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NOT NULL,
  `user_id` int NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_at` int DEFAULT (unix_timestamp()),
  PRIMARY KEY (`comment_id`),
  KEY `article_to_comment_FK_article_id` (`article_id`),
  KEY `user_to_comment_FK_user_id` (`user_id`),
  CONSTRAINT `article_to_comment_FK_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`),
  CONSTRAINT `user_to_comment_FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (3,10,1,'댓글 수정111',1690257235),(4,10,1,'test 댓글입니다2',1690257238),(5,10,1,'test 댓글입니다2',1690257239),(6,10,1,'test 댓글입니다2',1690257239),(7,21,14,'댓글 수정111',1690777237),(8,21,14,'test 댓글입니다3',1690777251);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact` (
  `contact_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `teacher_id` int NOT NULL,
  `state` tinyint DEFAULT NULL,
  `student_memo` varchar(255) DEFAULT NULL,
  `teacher_memo` varchar(255) DEFAULT NULL,
  `student_order` int DEFAULT NULL,
  `teacher_order` int DEFAULT NULL,
  `start_time` int DEFAULT NULL,
  `end_time` int DEFAULT NULL,
  `room_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`contact_id`),
  KEY `user_to_contact_FK_user_id` (`student_id`),
  KEY `teacher_to_contact_FK_teacher_id` (`teacher_id`),
  CONSTRAINT `teacher_to_contact_FK_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`),
  CONSTRAINT `user_to_contact_FK_user_id` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (4,1,1,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite`
--

DROP TABLE IF EXISTS `favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite` (
  `favorite_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `teacher_id` int NOT NULL,
  PRIMARY KEY (`favorite_id`),
  KEY `user_to_like_FK_user_id` (`student_id`),
  KEY `teacher_to_like_FK_teacher_id` (`teacher_id`),
  CONSTRAINT `teacher_to_like_FK_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`),
  CONSTRAINT `user_to_like_FK_user_id` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite`
--

LOCK TABLES `favorite` WRITE;
/*!40000 ALTER TABLE `favorite` DISABLE KEYS */;
INSERT INTO `favorite` VALUES (1,14,1),(2,14,3),(3,14,4),(5,14,5),(8,14,6);
/*!40000 ALTER TABLE `favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instrument`
--

DROP TABLE IF EXISTS `instrument`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instrument` (
  `instrument_id` int NOT NULL AUTO_INCREMENT,
  `instrument_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`instrument_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instrument`
--

LOCK TABLES `instrument` WRITE;
/*!40000 ALTER TABLE `instrument` DISABLE KEYS */;
INSERT INTO `instrument` VALUES (7,'피아노'),(8,'기타'),(9,'베이스'),(10,'드럼'),(11,'키보드'),(12,'오보에');
/*!40000 ALTER TABLE `instrument` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `hit` int DEFAULT NULL,
  `created_at` int DEFAULT (unix_timestamp()),
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (4,'두근두근 하네요','가보자고',0,1690778699),(5,'두근두근 하네요','가보자고',0,1690778700),(6,'두근두근 하네요','가보자고',0,1690778701),(7,'두근두근 하네요','가보자고',0,1690778702),(8,'두근두근 하네요','가보자고',0,1690778702),(9,'두근두근 하네요','가보자고',0,1690778703);
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice_file`
--

DROP TABLE IF EXISTS `notice_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice_file` (
  `notice_file_id` int NOT NULL AUTO_INCREMENT,
  `notice_id` int NOT NULL,
  `file_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`notice_file_id`),
  KEY `notice_to_notice_file_FK_notice_id` (`notice_id`),
  CONSTRAINT `notice_to_notice_file_FK_notice_id` FOREIGN KEY (`notice_id`) REFERENCES `notice` (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice_file`
--

LOCK TABLES `notice_file` WRITE;
/*!40000 ALTER TABLE `notice_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `notice_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `contact_id` int NOT NULL,
  `rating` float DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `created_at` int DEFAULT (unix_timestamp()),
  PRIMARY KEY (`review_id`),
  KEY `contact_to_review_FK_contact_id` (`contact_id`),
  CONSTRAINT `contact_to_review_FK_contact_id` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `teacher_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `career` int DEFAULT NULL,
  `cost` int DEFAULT NULL,
  `introduce` varchar(4000) DEFAULT NULL,
  `start_time` int DEFAULT NULL,
  `end_time` int DEFAULT NULL,
  `class_day` int DEFAULT NULL,
  `avg_rating` float DEFAULT NULL,
  `contact_cnt` int DEFAULT NULL,
  PRIMARY KEY (`teacher_id`),
  KEY `user_to_teacher_FK_user_id` (`user_id`),
  CONSTRAINT `user_to_teacher_FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,9,1,30000,'자기소개입니다',12,18,23,NULL,NULL),(4,10,1,30000,'자기소개입니다',12,18,23,NULL,NULL),(5,11,1,30000,'자기소개입니다',12,18,23,NULL,NULL),(6,12,1,30000,'자기소개입니다',12,18,116,NULL,NULL),(7,12,1,30000,'자기소개입니다',12,18,116,NULL,NULL),(14,16,1,30000,'자기소개입니다',12,18,116,NULL,NULL);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher_file`
--

DROP TABLE IF EXISTS `teacher_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher_file` (
  `teacher_file_id` int NOT NULL AUTO_INCREMENT,
  `teacher_id` int NOT NULL,
  `file_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`teacher_file_id`),
  KEY `teacher_to_teacher_file_FK_teacher_id` (`teacher_id`),
  CONSTRAINT `teacher_to_teacher_file_FK_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_file`
--

LOCK TABLES `teacher_file` WRITE;
/*!40000 ALTER TABLE `teacher_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacher_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treat`
--

DROP TABLE IF EXISTS `treat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treat` (
  `treatment_id` int NOT NULL AUTO_INCREMENT,
  `teacher_id` int NOT NULL,
  `instrument_id` int NOT NULL,
  PRIMARY KEY (`treatment_id`),
  KEY `teacher_to_treat_FK_teacher_id` (`teacher_id`),
  KEY `instrument_to_treat_FK_instrument_id` (`instrument_id`),
  CONSTRAINT `instrument_to_treat_FK_instrument_id` FOREIGN KEY (`instrument_id`) REFERENCES `instrument` (`instrument_id`),
  CONSTRAINT `teacher_to_treat_FK_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treat`
--

LOCK TABLES `treat` WRITE;
/*!40000 ALTER TABLE `treat` DISABLE KEYS */;
INSERT INTO `treat` VALUES (1,3,8),(2,3,7),(3,4,8),(4,4,7),(5,5,8),(6,5,7),(7,6,8),(8,6,7),(9,7,8),(10,7,7),(11,7,10),(12,14,8),(13,14,7),(14,14,10);
/*!40000 ALTER TABLE `treat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `id` varchar(20) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(10) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `user_profile_image` varchar(255) DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `created_at` int DEFAULT (unix_timestamp()),
  `gender` tinyint DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ssafy','ssafy','ssafy','ssafy','ssafy',1,NULL,1),(4,'user','$2a$10$Jzydx7R7x0/ynMm2IpuCIeGrkABAoq0NoW.GvWPeIxPL//TCpVGmu','new ssafy','JJJ@email.com','new ssafy',1,1690162607,1),(7,'user11','$2a$10$RQkchNc7Uylb7xvL0llqG.5vqrvEMpzoVc7mDrixHdXbiimLzPj8q','JJJ1111','JJJ@email.com',NULL,NULL,1690262559,1),(9,'ssafy1','$2a$10$gguJ1usfA1F2ZwkLjlwUnewnL5NbuWX/X0v0gh3H1rXW9LZMY6yza','ssafy1','ssafy@email.com',NULL,0,1690263901,1),(10,'ssafy2','$2a$10$vBTP6yJ0G5y6GN/FWtZHvuomhvplEG5M0yqTWw/tmZIytxWosHiK6','ssafy2','ssafy2@email.com',NULL,0,1690330728,1),(11,'ssafy3','$2a$10$gDMTNfCHE4U7GRlqVtCsO.Tfagwgl9Syeu99gjg7xhlnc6/xESGgW','ssafy3','ssafy3@email.com','new ssafy new 33',0,1690330741,1),(12,'ssafy4','$2a$10$7iLVFaxMNszOPsvhFDjxW.I1jEHF1tBGmgODEPnlX6Q7UrFLTBEnG','ssafy4','ssafy4@email.com','new ssafy new 4',0,1690330753,1),(13,'ssafy5','$2a$10$98ExuVASoeXKxiUTUdVr.ecKcfF0DYLE8e.mRavgD2OYmrXXUSTWm','new ssafy1','ssafy5@email.com','new ssafy new one',0,1690330764,1),(14,'ssafy6','$2a$10$stZDXBBfhULomZXAUplGiOcytQIY0zu2cP.eX0m2sfURjjAZW2flu','ssafy6','ssafy6@email.com','new ssafy new 6',0,1690330772,1),(15,'ssafy7','$2a$10$5uh3l3C9Y0QIrZdXFKE2I.PB/kr4uG8hQCrr1G125l09UqQQC3vPa','ssafy7','ssafy7@email.com',NULL,0,1690429409,1),(16,'ssafy8','$2a$10$QKYorew7J62qi7Bcswz1yu0/tcscR.DtIsGmtZuahxoAhkA/mWHCq','ssafy8','ssafy8@email.com',NULL,1,1690429414,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-31 15:31:10
