DROP DATABASE  IF EXISTS ratem_db;

CREATE DATABASE  IF NOT EXISTS ratem_db;
USE ratem_db;


--
-- Entité utilisateurs
--
DROP TABLE IF EXISTS ratem_user;
CREATE TABLE ratem_user (
	u_email varchar(50) NOT NULL,
	u_password varchar(100) NOT NULL,
	u_first_name varchar(20) NOT NULL,
	u_last_name varchar(20) NOT NULL,
	u_enabled tinyint(1) NOT NULL,
	u_created_at datetime,
	u_updated_at datetime,
	PRIMARY KEY (u_email)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO ratem_user
VALUES 
('admin@aymax.fr','$2a$10$HRQYKAbCLfPqZlFV4aYAr.Aj59..i2rZ5DJiH1.M3RhPPjnIeb4UC','Admin','AYMAX',1,NULL,NULL),
('ahmed.satour@aymax.tn','$2a$10$Kmfd1ah1.WS0OL/I/NLo1ePxZL.bpFE1jIxVFiH/H4xeYv.GtyG2C','Ahmed','Satour',1,NULL,NULL),
('victor.hugo@aymax.fr','$2a$10$Kmfd1ah1.WS0OL/I/NLo1ePxZL.bpFE1jIxVFiH/H4xeYv.GtyG2C','Victor','Hugo',1,NULL,NULL),
('aymen.daknou@aymax.fr','$2a$10$Kmfd1ah1.WS0OL/I/NLo1ePxZL.bpFE1jIxVFiH/H4xeYv.GtyG2C','Aymen','Daknou',1,NULL,NULL),
('maxime.cariou@aymax.fr','$2a$10$Kmfd1ah1.WS0OL/I/NLo1ePxZL.bpFE1jIxVFiH/H4xeYv.GtyG2C','Maxime','Cariou',1,NULL,NULL);


--
-- Entité profiles (roles)
--
DROP TABLE IF EXISTS ratem_profile;
CREATE TABLE ratem_profile (
	p_id int NOT NULL AUTO_INCREMENT,
	p_role varchar(20) NOT NULL,
	p_user varchar(50) NOT NULL,
	PRIMARY KEY (p_id),
	FOREIGN KEY (p_user) REFERENCES ratem_user(u_email),
	CONSTRAINT UC_ratem_profile UNIQUE (p_role, p_user)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO ratem_profile (p_user, p_role)
VALUES 
('admin@aymax.fr','ROLE_ADMIN'),
('ahmed.satour@aymax.tn','ROLE_CONSULTANT'),
('victor.hugo@aymax.fr','ROLE_CONSULTANT'),
('aymen.daknou@aymax.fr','ROLE_MANAGER'),
('maxime.cariou@aymax.fr','ROLE_MANAGER');


--
-- Association managed_by
--
DROP TABLE IF EXISTS ratem_managed_by;
CREATE TABLE ratem_managed_by (
	mb_id int NOT NULL AUTO_INCREMENT,
	mb_consultant varchar(50) NOT NULL,
	mb_manager varchar(50) NOT NULL,
	mb_begin_date date NOT NULL,
	mb_end_date date NOT NULL,
	PRIMARY KEY (mb_id),
	FOREIGN KEY (mb_consultant) REFERENCES ratem_user(u_email),
	FOREIGN KEY (mb_manager) REFERENCES ratem_user(u_email),
	CONSTRAINT UC_ratem_managed_by UNIQUE (mb_consultant, mb_manager, mb_begin_date)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO ratem_managed_by (mb_consultant, mb_manager, mb_begin_date, mb_end_date) 
VALUES
('ahmed.satour@aymax.tn','aymen.daknou@aymax.fr','2018-01-01','2018-04-30'),
('ahmed.satour@aymax.tn','maxime.cariou@aymax.fr','2018-05-01','2018-07-31'),
('victor.hugo@aymax.fr','maxime.cariou@aymax.fr','2018-03-01','2018-06-30'),
('victor.hugo@aymax.fr','aymen.daknou@aymax.fr','2018-06-01','2018-07-31');


--
-- Association feedback
--
DROP TABLE IF EXISTS ratem_feedback;
CREATE TABLE ratem_feedback (
	f_id int NOT NULL AUTO_INCREMENT,
	f_consultant varchar(50) NOT NULL,
	f_manager varchar(50) NOT NULL,
	f_month date NOT NULL,
	f_note int(10) NOT NULL,
	f_commentary varchar(250),
	PRIMARY KEY (f_id),
	FOREIGN KEY (f_consultant) REFERENCES ratem_user(u_email),
	FOREIGN KEY (f_manager) REFERENCES ratem_user(u_email),
	CONSTRAINT UC_ratem_feedback UNIQUE (f_consultant, f_manager, f_month)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO ratem_feedback (f_consultant, f_manager, f_month, f_note, f_commentary)
VALUES
('ahmed.satour@aymax.tn','aymen.daknou@aymax.fr','2018-01-01',9,NULL),
('ahmed.satour@aymax.tn','aymen.daknou@aymax.fr','2018-02-01',9,NULL),
('ahmed.satour@aymax.tn','aymen.daknou@aymax.fr','2018-03-01',8,NULL),
('ahmed.satour@aymax.tn','maxime.cariou@aymax.fr','2018-05-01',7,NULL),
('ahmed.satour@aymax.tn','maxime.cariou@aymax.fr','2018-07-01',8,NULL),
('victor.hugo@aymax.fr','maxime.cariou@aymax.fr','2018-03-01',9,NULL),
('victor.hugo@aymax.fr','maxime.cariou@aymax.fr','2018-04-01',10,'Très bon manager, merci !'),
('victor.hugo@aymax.fr','aymen.daknou@aymax.fr','2018-06-01',5,'Mauvaise gestion !!'),
('victor.hugo@aymax.fr','aymen.daknou@aymax.fr','2018-07-01',7,NULL);


--
-- DB users
--
CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.67' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.67';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.68' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.68';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.69' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.69';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.70' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.70';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.71' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.71';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.72' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.72';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.73' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.73';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.74' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.74';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.75' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.75';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.76' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.76';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.77' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.77';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.78' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.78';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.79' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.79';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.80' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.80';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.81' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.81';

CREATE USER IF NOT EXISTS 'AYMAXadmin'@'192.168.202.82' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON ratem_db.* TO 'AYMAXadmin'@'192.168.202.82';
