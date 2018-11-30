drop table if exists InfoA;
drop table if exists InfoB;
drop table if exists InfoC;
drop table if exists InfoD;
drop table if exists InfoE;
drop table if exists InfoF;
drop table if exists InfoG;
drop table if exists InfoH;
drop table if exists InfoI;
drop table if exists InfoJ;

CREATE TABLE InfoD
(
    idD INT PRIMARY KEY,
	dInt int 
);

CREATE TABLE InfoA
(    
	idA INT PRIMARY KEY,
	aInt int ,
	FOREIGN KEY (idA) REFERENCES InfoD (idD)

);
CREATE TABLE InfoB
(    
   	idB INT PRIMARY KEY,
	bInt int ,
	FOREIGN KEY (idB) REFERENCES InfoA (idA)
);
CREATE TABLE InfoC
(    
	idC INT PRIMARY KEY,
	cInt int ,
	FOREIGN KEY (idC) REFERENCES InfoB (idB)
);
CREATE TABLE InfoE
(
    idD INT PRIMARY KEY,
	dInt int 
);


CREATE TABLE InfoF
(
    idD INT PRIMARY KEY,
	dInt int 
);

CREATE TABLE InfoG
(
    idD INT PRIMARY KEY,
	dInt int 
);


CREATE TABLE InfoH
(
    idH INT PRIMARY KEY,
	dInt int 
);


CREATE TABLE InfoI
(
    idI INT PRIMARY KEY,
	dInt int ,
	FOREIGN KEY (idI) REFERENCES InfoH (idH)

);


CREATE TABLE InfoJ
(
    idJ INT PRIMARY KEY,
	dInt int ,
	FOREIGN KEY (idJ) REFERENCES InfoC (idC)
);


