CREATE DATABASE IF NOT EXISTS sondaggi;

USE sondaggi;

CREATE TABLE user (
	id int auto_increment PRIMARY KEY,
	username varchar(30) not null,
	nome varchar(20) not null,
	cognome varchar(20) not null,
	password varchar(10) not null, 
	email varchar(30) not null
) Engine=InnoDB;

CREATE TABLE sondaggio (
	id int auto_increment PRIMARY KEY,
	domanda varchar(150) not null,
	risposta1 varchar(20) not null,
	risposta2 varchar(20) not null,
	risposta3 varchar(20),
	risposta4 varchar(20)
) Engine=InnoDB;

CREATE TABLE categoria (
	id int auto_increment PRIMARY KEY,
	nome varchar(30) not null 
) Engine=InnoDB;

CREATE TABLE categorieSondaggio (
	idSondaggio int,
	idCategoria int,
	PRIMARY KEY (idSondaggio, idCategoria),
	FOREIGN KEY (idSondaggio) REFERENCES sondaggio(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE,
    FOREIGN KEY (idCategoria) REFERENCES categoria(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE
) Engine=InnoDB;

CREATE TABLE categorieUser (
	idUser int,
	idCategoria int,
	PRIMARY KEY (idUser, idCategoria),
	FOREIGN KEY (idUser) REFERENCES user(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE,
	FOREIGN KEY (idCategoria) REFERENCES categoria(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE
) Engine=InnoDB;

CREATE TABLE risposta (
	idUser int,
	idSondaggio int,
	risposta varchar(20),
	PRIMARY KEY (idUser, idSondaggio),
	FOREIGN KEY (idUser) REFERENCES user(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE,
    FOREIGN KEY (idSondaggio) REFERENCES sondaggio(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE
) Engine=InnoDB;