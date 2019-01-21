drop table if exists A;
drop table if exists B;
drop table if exists C;

drop table if exists D;
drop table if exists E;

drop table if exists F;
drop table if exists J;
drop table if exists K;

drop table if exists L;
drop table if exists M;
drop table if exists N;

drop table if exists O;
drop table if exists P;

DROP TABLE IF EXISTS R;
DROP TABLE IF EXISTS S;
---------references
CREATE TABLE A
(    
	A1 INT  PRIMARY KEY ,
	A2 date  unique,
	FOREIGN KEY (A1) REFERENCES B (B1)	
);
CREATE TABLE B
(    
   	B1 INT PRIMARY KEY,	
	B2 real ,	
	FOREIGN KEY (B1) REFERENCES C (C1)
);
CREATE TABLE C
(    	
	C1 INT PRIMARY KEY	,
	C2 boolean ,
	C3 BLOB  
);
---------tow to one
CREATE TABLE D
(
	D1 int PRIMARY KEY,
	D2 float ,
	FOREIGN KEY (D1) REFERENCES E (E1),	
	FOREIGN KEY (D2) REFERENCES E (E1)
);
CREATE TABLE E
(
	E1 int PRIMARY KEY ,
	C2 text 
);
--------------one to tow

CREATE TABLE F
(    
	F1 INT PRIMARY KEY
);
CREATE TABLE J
(    
   	J1 INT PRIMARY KEY
);
CREATE TABLE K
(    
	K1 INT PRIMARY KEY,	
	FOREIGN KEY (K1) REFERENCES F (F1),
	FOREIGN KEY (K1) REFERENCES J (J1)
);

---------------circuler

CREATE TABLE O
(    
	O1 int PRIMARY KEY,
    FOREIGN KEY (O1) REFERENCES P (P1)
);
CREATE TABLE P
(    
	P1 int PRIMARY KEY,
    FOREIGN KEY (P1) REFERENCES O (O1)	
);
---------------circuler withe inheritance

CREATE TABLE L
(    
	L1 int PRIMARY KEY,
    FOREIGN KEY (L1) REFERENCES M (M1)
);
CREATE TABLE M
(    
	M1 int PRIMARY KEY,
    FOREIGN KEY (M1) REFERENCES L (L1)	
);
CREATE TABLE N
(    
	N1 int PRIMARY KEY,
    FOREIGN KEY (N1) REFERENCES L (L1)	
);

---------------- table with pre data
drop table if exists Q;

CREATE TABLE Q
(    
	Q1 INT PRIMARY KEY	
);

insert into Q values(1);
insert into Q values(3);
insert into Q values(5);

-------------------- tow tuple foreign key not inverse

	CREATE TABLE R(
		a1 INT ,
		a2 INT ,
		primary KEY (a1,a2)
	);

	CREATE TABLE S(
		b1 INT , 
		b2 INT , 
		b3 INT , 
		FOREIGN KEY (b1,b2) REFERENCES R (a1,a2),
		FOREIGN KEY (b2,b3) REFERENCES R (a1,a2)	
	);

