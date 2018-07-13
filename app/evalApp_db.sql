DROP DATABASE  IF EXISTS evalApp_db;

CREATE DATABASE  IF NOT EXISTS evalApp_db;
USE evalApp_db;

--
-- Table structure for table user
--

DROP TABLE IF EXISTS eval_user;
CREATE TABLE eval_user (
  user_id varchar(50) NOT NULL,
  u_password varchar(100) NOT NULL,
  u_role varchar(50) NOT NULL,
  u_enabled tinyint(1) NOT NULL,
  u_created_at datetime,
  u_updated_at datetime,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting default data for table user
-- mdp : amx, aze, aze.
--

INSERT INTO eval_user
VALUES 
('admin','$2a$10$HRQYKAbCLfPqZlFV4aYAr.Aj59..i2rZ5DJiH1.M3RhPPjnIeb4UC','ROLE_ADMIN',1,'2018-07-09 18:00','2018-07-09 18:00'),
('consultat.1','$2a$10$Kmfd1ah1.WS0OL/I/NLo1ePxZL.bpFE1jIxVFiH/H4xeYv.GtyG2C','ROLE_EMPLOYE',1,'2018-07-09 18:00','2018-07-09 18:00'),
('consultat.2','$2a$10$Kmfd1ah1.WS0OL/I/NLo1ePxZL.bpFE1jIxVFiH/H4xeYv.GtyG2C','ROLE_EMPLOYE',1,'2018-07-09 18:00','2018-07-09 18:00'),
('man','$2a$10$Kmfd1ah1.WS0OL/I/NLo1ePxZL.bpFE1jIxVFiH/H4xeYv.GtyG2C','ROLE_MANAGER',1,'2018-07-09 18:00','2018-07-09 18:00');

DROP TABLE IF EXISTS eval_manager;
CREATE TABLE eval_manager (
  manager_id varchar(50) NOT NULL,
  m_first_name varchar(50) NOT NULL,
  m_last_name varchar(50) NOT NULL,
  m_created_at datetime,
  m_updated_at datetime,
  PRIMARY KEY (manager_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO eval_manager VALUES 
('aymen.daknou','Aymen','Daknou',NULL,NULL),
('maxime.cariou','Maxime','Cariou',NULL,NULL);


--
-- user_manager association
--

DROP TABLE IF EXISTS eval_user_manager;
CREATE TABLE eval_user_manager (
  um_id int NOT NULL AUTO_INCREMENT,
  um_user_id varchar(50) NOT NULL,
  um_manager_id varchar(50) NOT NULL,
  um_begin_date date NOT NULL,
  um_end_date date NOT NULL,
  PRIMARY KEY (um_id),
  FOREIGN KEY (um_user_id) REFERENCES eval_user(user_id),
  FOREIGN KEY (um_manager_id) REFERENCES eval_manager(manager_id),
  CONSTRAINT UC_eval_user_manager UNIQUE (um_user_id, um_manager_id, um_begin_date)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO eval_user_manager VALUES 
(1,'consultat.1','aymen.daknou','2018-01-01','2018-04-30'),
(2,'consultat.1','maxime.cariou','2018-05-01','2018-07-31'),
(3,'consultat.2','maxime.cariou','2018-03-01','2018-06-30'),
(4,'consultat.2','aymen.daknou','2018-06-01','2018-07-31');


--
-- feedback association
--

DROP TABLE IF EXISTS eval_feedback;
CREATE TABLE eval_feedback (
  f_id int NOT NULL AUTO_INCREMENT,
  f_user_id varchar(50) NOT NULL,
  f_manager_id varchar(50) NOT NULL,
  f_period date NOT NULL,
  f_note int(10) NOT NULL,
  f_commentary varchar(250),
  PRIMARY KEY (f_id),
  FOREIGN KEY (f_user_id) REFERENCES eval_user(user_id),
  FOREIGN KEY (f_manager_id) REFERENCES eval_manager(manager_id),
  CONSTRAINT UC_eval_feedback UNIQUE (f_user_id, f_manager_id, f_period)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO eval_feedback VALUES 
(1,'consultat.1','aymen.daknou','2018-01-01',9,NULL),
(2,'consultat.1','aymen.daknou','2018-02-01',9,NULL),
(3,'consultat.1','aymen.daknou','2018-03-01',8,NULL),
(4,'consultat.1','maxime.cariou','2018-05-01',7,NULL),
(5,'consultat.1','maxime.cariou','2018-07-01',8,NULL),
(6,'consultat.2','maxime.cariou','2018-03-01',9,NULL),
(7,'consultat.2','maxime.cariou','2018-04-01',10,NULL),
(8,'consultat.2','aymen.daknou','2018-06-01',5,'Mauvaise gestion'),
(9,'consultat.2','aymen.daknou','2018-07-01',7,NULL);


--
-- DB users
--
CREATE USER IF NOT EXISTS 'admin1'@'localhost' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON evalApp_db.* TO 'admin1'@'localhost';

CREATE USER IF NOT EXISTS 'admin2'@'192.168.202.66' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON evalApp_db.* TO 'admin2'@'192.168.202.66';

CREATE USER IF NOT EXISTS 'admin2'@'192.168.202.69' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON evalApp_db.* TO 'admin2'@'192.168.202.69';

CREATE USER IF NOT EXISTS 'admin2'@'192.168.202.75' IDENTIFIED BY 'amx';
GRANT ALL PRIVILEGES ON evalApp_db.* TO 'admin2'@'192.168.202.75';
