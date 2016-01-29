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
	id int auto_increment PRIMARY KEY,
	domanda varchar(150) not null,
	risposta1 varchar(20) not null,
	risposta2 varchar(20) not null,
	risposta3 varchar(20),
	risposta4 varchar(20)
) Engine=InnoDB;

CREATE TABLE categoria (
	nome varchar(30) PRIMARY KEY
) Engine=InnoDB;

CREATE TABLE categorieSondaggio (
	id int,
	nome varchar(30),
	PRIMARY KEY (id, nome),
	FOREIGN KEY (id) REFERENCES sondaggio(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE,
    FOREIGN KEY (nome) REFERENCES categoria(nome)
		ON DELETE CASCADE
    	ON UPDATE CASCADE
)Engine=InnoDB;

CREATE TABLE categorieUser (
	username varchar(30),
	nome varchar(30),
	PRIMARY KEY (username, nome),
	FOREIGN KEY (username) REFERENCES user(username)
		ON DELETE CASCADE
    	ON UPDATE CASCADE,
	FOREIGN KEY (nome) REFERENCES categoria(nome)
		ON DELETE CASCADE
    	ON UPDATE CASCADE
) Engine=InnoDB;

CREATE TABLE risposta (
	username varchar(30),
	id int,
	risposta varchar(20),
	PRIMARY KEY (username, id),
	FOREIGN KEY (username) REFERENCES user(username)
		ON DELETE CASCADE
    	ON UPDATE CASCADE,
    FOREIGN KEY (id) REFERENCES sondaggio(id)
		ON DELETE CASCADE
    	ON UPDATE CASCADE
) Engine=InnoDB;