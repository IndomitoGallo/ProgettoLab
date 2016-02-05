CREATE DATABASE IF NOT EXISTS surveys;

USE surveys;

CREATE TABLE user (
	id int auto_increment PRIMARY KEY,
	username varchar(30) not null,
	name varchar(20) not null,
	surname varchar(20) not null,
	password varchar(10) not null, 
	email varchar(30) not null
) Engine=InnoDB;

CREATE TABLE survey (
	id int auto_increment PRIMARY KEY,
	question varchar(150) not null,
	answer1 varchar(20) not null,
	answer2 varchar(20) not null,
	answer3 varchar(20),
	answer4 varchar(20)
) Engine=InnoDB;

CREATE TABLE category (
	id int auto_increment PRIMARY KEY,
	name varchar(30) not null 
) Engine=InnoDB;

CREATE TABLE categoriesSurvey (
	idSurvey int,
	idCategory int,
	PRIMARY KEY (idSurvey, idCategory),
	FOREIGN KEY (idSurvey) REFERENCES survey(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE,
    FOREIGN KEY (idCategory) REFERENCES category(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE
) Engine=InnoDB;

CREATE TABLE categoriesUser (
	idUser int,
	idCategory int,
	PRIMARY KEY (idUser, idCategory),
	FOREIGN KEY (idUser) REFERENCES user(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE,
	FOREIGN KEY (idCategory) REFERENCES category(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE
) Engine=InnoDB;

CREATE TABLE answer (
	idUser int,
	idSurvey int,
	answer varchar(20),
	PRIMARY KEY (idUser, idSurvey),
	FOREIGN KEY (idUser) REFERENCES user(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE,
    FOREIGN KEY (idSurvey) REFERENCES survey(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE
) Engine=InnoDB;