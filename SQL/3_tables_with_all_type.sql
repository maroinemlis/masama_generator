drop table if exists InfoA;
drop table if exists InfoB;
drop table if exists InfoC;
drop table if exists InfoD;

CREATE TABLE InfoA
(    
	idA INT PRIMARY KEY,
	aInt float ,
	aInt2 double ,
	mText text,
	aInt3 real ,
	aInt4 NUMERIC,
	aInt5 BLOB
);
CREATE TABLE InfoB
(    
   	idB INT PRIMARY KEY,
	bInt int ,
	bInt2 Boolean ,
	FOREIGN KEY (idB) REFERENCES InfoA (idA)
);
CREATE TABLE InfoC
(    
	idC INT PRIMARY KEY,
	cInt int ,
	FOREIGN KEY (idC) REFERENCES InfoB (idB)
);

insert into InfoA values(2,2,4,"Amir",4,1,'d');
insert into InfoB values(2,2,10);
select * from InfoA;
select * from InfoB;

