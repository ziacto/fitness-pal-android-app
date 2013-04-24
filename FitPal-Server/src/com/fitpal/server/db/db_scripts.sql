create database fitpal;

create table fitpal.user_profile( 

  username varchar(100) primary key,
  password varchar(50),
  email varchar(100) unique,
  display_name varchar(100),
  dob date,
  height float,
  weight float,
  created_timestamp timestamp default CURRENT_TIMESTAMP
);

CREATE TABLE `plan` (
  `plan_id` int(11) NOT NULL,
  `plan` longtext,
  PRIMARY KEY (`plan_id`)
)

CREATE TABLE `userplan` (
  `username` int(11) NOT NULL,
  `planId` varchar(45) NOT NULL,
  `date` varchar(45) NOT NULL,
  PRIMARY KEY (`username`,`planId`,`date`),
  KEY `username` (`username`),
  CONSTRAINT `username` FOREIGN KEY (`username`) REFERENCES `userprofile` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
)

CREATE TABLE `userprofile` (
  `username` int(11) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `display_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `dob` varchar(45) DEFAULT NULL,
  `height` varchar(45) DEFAULT NULL,
  `weight` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`)
)






