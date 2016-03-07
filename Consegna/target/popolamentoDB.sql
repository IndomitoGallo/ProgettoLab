REPLACE INTO `surveys`.`user`
VALUES
(1,
'admin',
'Marco',
'Bernacchi',
'admin',
'ingsoftwarelab@gmail.com'),
(2,
'IndomitoGallo',
'Luca',
'Talocci',
'labjava',
'lucatalocci@gmail.com');

REPLACE INTO `surveys`.`category`
(`id`,
`name`)
VALUES
(1,'Informatica'),
(2,'Elettronica'),
(3,'Giardinaggio');

REPLACE INTO `surveys`.`categoriesuser`
(`idUser`,
`idCategory`)
VALUES
(2,1),
(2,2);


REPLACE INTO `surveys`.`survey`
(`id`,
`question`,
`answer1`,
`answer2`,
`answer3`,
`answer4`)
VALUES 
(1, 
'Qual é la marca di Computer che preferisci?',
'Apple',
'Lenovo',
'Asus',
'Msi'),
(2,
'Secondo te sarebbe utile avere uno smartwatch?',
'Si',
'No',
NULL,
NULL),
(3,
'Quale tipo di tagliaerba preferisci?',
'Elettrico',
'A Benzina',
'A Gas',
NULL);

REPLACE INTO `surveys`.`categoriessurvey`
(`idSurvey`,
`idCategory`)
VALUES
(1,1),
(2,2),
(3,3);

