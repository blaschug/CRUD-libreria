DROP TABLE IF EXISTS autor;

CREATE TABLE autor
(
    id     BIGINT IDENTITY PRIMARY KEY,
    nombre VARCHAR(50)
);

CREATE TABLE editorial
(
    id     BIGINT IDENTITY PRIMARY KEY,
    nombre VARCHAR(50)
);

CREATE TABLE libro
(
    id                   BIGINT IDENTITY PRIMARY KEY,
    alta                 BIT(1),
    anio                 INT,
    ejemplares           INT,
    ejemplares_prestados INT,
    ejemplares_restantes INT,
    isbn                 BIGINT,
    nombre               VARCHAR(200),
    autor_id             BIGINT,
    editorial_id         BIGINT

);

INSERT INTO autor
VALUES (1, 'Blas');

INSERT INTO autor
VALUES (2, 'Pedor');

INSERT INTO editorial
VALUES (1, 'Anagrama');

INSERT INTO libro
values (1, 1, 1995, 10, 5, 5, 125, 'El Silmarillion', 1, 1);
INSERT INTO libro
values (2, 1, 1980, 50, 25, 5, 130, 'La bestia', 1, 1);


