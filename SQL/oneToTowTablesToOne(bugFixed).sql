PRAGMA foreign_keys = off;
drop table if exists A;
drop table if exists B;
drop table if exists C;
drop table if exists D;
drop table if exists E;
drop table if exists F;
drop table if exists J;
PRAGMA foreign_keys = ON;
CREATE TABLE A
(    
	A1 int PRIMARY KEY
);
CREATE TABLE B
(   
	B1 int PRIMARY KEY,
	FOREIGN KEY (B1) REFERENCES A (A1),
	FOREIGN KEY (B1) REFERENCES C (C1)	   
);
CREATE TABLE C
(    
	C1 int PRIMARY KEY   ,
	FOREIGN KEY (C1) REFERENCES D (D1)
);
CREATE TABLE D
(
	D1 int PRIMARY KEY,
	FOREIGN KEY (D1) REFERENCES E (E1),
	FOREIGN KEY (D1) REFERENCES F (F1)
);
CREATE TABLE E
(
	E1 int PRIMARY KEY
);
CREATE TABLE F
(
	F1 int PRIMARY KEY
);
CREATE TABLE J
(
	J1 int PRIMARY KEY
);
