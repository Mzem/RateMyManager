DROP DATABASE  IF EXISTS `evalApp_db`;

CREATE DATABASE  IF NOT EXISTS `evalApp_db`;
USE `evalApp_db`;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting default data for table `users`
-- mdp : amx, aze, aze.
--

INSERT INTO `users` 
VALUES 
('admin','$2a$10$HRQYKAbCLfPqZlFV4aYAr.Aj59..i2rZ5DJiH1.M3RhPPjnIeb4UC','ROLE_ADMIN',1),
('emp','$2a$10$Kmfd1ah1.WS0OL/I/NLo1ePxZL.bpFE1jIxVFiH/H4xeYv.GtyG2C','ROLE_EMPLOYE',1),
('man','$2a$10$Kmfd1ah1.WS0OL/I/NLo1ePxZL.bpFE1jIxVFiH/H4xeYv.GtyG2C','ROLE_MANAGER',1);

DROP TABLE IF EXISTS `managers`;
CREATE TABLE `managers` (
  `username` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `description` varchar(250) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting default data for table `managers`
--

INSERT INTO `managers` 
VALUES 
('aymen.daknou','Aymen','Daknou','Co-fondateur'),
('maxime.cariou','Maxime','Cariou','Co-fondateur');

--
-- DB users
--
CREATE USER IF NOT EXISTS 'admin1'@'localhost' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON evalApp_db.* TO 'admin1'@'localhost';

CREATE USER IF NOT EXISTS 'admin2'@'192.168.202.68' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON evalApp_db.* TO 'admin2'@'192.168.202.68';
