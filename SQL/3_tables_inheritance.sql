drop table if exists A;
drop table if exists B;
drop table if exists C;

CREATE TABLE A
(    
	A1 INT  PRIMARY KEY
);
CREATE TABLE B
(    
   	B1 INT PRIMARY KEY,
	FOREIGN KEY (B1) REFERENCES A (A1)
);
CREATE TABLE C
(    	
	C1 INT PRIMARY KEY,
	FOREIGN KEY (C1) REFERENCES B (B1) 
);


