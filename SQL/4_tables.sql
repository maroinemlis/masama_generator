drop table if exists InfoA;
drop table if exists InfoB;
drop table if exists InfoC;
drop table if exists InfoD;
drop table if exists InfoE;

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


