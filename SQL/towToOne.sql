PRAGMA foreign_keys = off;
drop table if exists A;
drop table if exists B;
drop table if exists C;
PRAGMA foreign_keys = ON;
CREATE TABLE A
(
	A1 int PRIMARY KEY,
	A2 int ,
	FOREIGN KEY (A1) REFERENCES B (B1),	
	FOREIGN KEY (A2) REFERENCES B (B1)
);
CREATE TABLE B
(
	B1 int PRIMARY KEY 
);
