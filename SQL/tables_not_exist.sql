drop table InfoA;
drop table InfoB;
drop table InfoC;
CREATE TABLE InfoA
(    
	idA INT PRIMARY KEY,
	mText text
);
CREATE TABLE InfoB
(    
   	idB INT PRIMARY KEY,
	bInt int ,
	FOREIGN KEY (idB) REFERENCES InfoB (idA)
);
CREATE TABLE InfoC
(    
	idC INT PRIMARY KEY,
	cInt int ,
	FOREIGN KEY (idC) REFERENCES InfoD (idB) /*the table InfoD don't exist*/
);

