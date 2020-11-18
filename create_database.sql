CREATE DATABASE webtodolist;
USE webtodolist;

CREATE TABLE `webtodolist`.`todo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(500) NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `webtodolist`.`user` (
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `role` ENUM('instructor','student'),
  PRIMARY KEY (`username`));
  
INSERT INTO `webtodolist`.`todo` (`id`,`description`) VALUES (1,'Read the Course 1 and try to start the TP 1');
INSERT INTO `webtodolist`.`todo` (`id`,`description`) VALUES (2,'Finish the TP1 before next session');
INSERT INTO `webtodolist`.`todo` (`id`,`description`) VALUES (3,'Read the Course 2 and try to implemant the methods presented');
INSERT INTO `webtodolist`.`todo` (`id`,`description`) VALUES (4,'Be prepared for a Quizz next week on Course 1');

INSERT INTO `webtodolist`.`user` (`username`,`password`,`role`) VALUES ('NadaNahle','passwordNada','instructor');
INSERT INTO `webtodolist`.`user` (`username`,`password`,`role`) VALUES ('HugoL','passwordHugo','student');
INSERT INTO `webtodolist`.`user` (`username`,`password`,`role`) VALUES ('MarineWeisser','passwordMarine','student');
INSERT INTO `webtodolist`.`user` (`username`,`password`,`role`) VALUES ('AntoineBr','passwordAntoine','student');

SELECT * FROM todo;
SELECT * FROM user;



SET @@global.time_zone = '+00:00';
SET @@session.time_zone = '+00:00';
SELECT @@global.time_zone, @@session.time_zone;



  
  
