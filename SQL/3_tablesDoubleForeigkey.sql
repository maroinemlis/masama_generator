drop table if exists InfoA;
drop table if exists InfoB;
drop table if exists InfoC;
drop table if exists InfoD;

CREATE TABLE InfoA
(    
	idA INT PRIMARY KEY,
	aInt int ,
	mText text
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
	FOREIGN KEY (idC) REFERENCES InfoA (idA),
	FOREIGN KEY (idC) REFERENCES InfoB (idB)
);



