CREATE DATABASE IF NOT EXISTS sondaggi;

USE sondaggi;

CREATE TABLE user (
	username varchar(30) PRIMARY KEY,
	nome varchar(20) not null,
	cognome varchar(20) not null,
	password varchar(10) not null, 
	email varchar(30) not null
) Engine=InnoDB;

CREATE TABLE sondaggio (
	idSondaggio int auto_increment PRIMARY KEY,
	domanda varchar(100) not null,
	risposta1 varchar(20) not null,
	risposta2 varchar(20) not null,
	risposta3 varchar(20),
	risposta4 varchar(20)
) Engine = InnoDB;

CREATE TABLE categoria (
	nomeCategoria varchar(45) PRIMARY KEY
) Engine=InnoDB;

CREATE TABLE categorieSondaggio (
	idSondaggio int,
	nomeCategoria varchar(45),
	PRIMARY KEY (idSondaggio, nomeCategoria),
	FOREIGN KEY (idSondaggio) REFERENCES sondaggio(idSondaggio)
		ON DELETE CASCADE
    	ON UPDATE CASCADE,
    FOREIGN KEY (nomeCategoria) REFERENCES categoria(nomeCategoria)
		ON DELETE CASCADE
    	ON UPDATE CASCADE
)Engine=InnoDB;

CREATE TABLE categorieUser (
	username varchar(30),
	nomeCategoria varchar(45),
	PRIMARY KEY (username, nomeCategoria),
	FOREIGN KEY (username) REFERENCES user(username)
		ON DELETE CASCADE
    	ON UPDATE CASCADE
	FOREIGN KEY (nomeCategoria) REFERENCES categoria(nomeCategoria)
		ON DELETE CASCADE
    	ON UPDATE CASCADE
) Engine=InnoDB;