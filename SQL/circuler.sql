drop table if exists InfoA;
drop table if exists InfoB;

CREATE TABLE InfoA
(    
	A1 int PRIMARY KEY,
	A2 int,
    FOREIGN KEY (A1) REFERENCES InfoB (B1)
);
CREATE TABLE InfoB
(    
	B1 int PRIMARY KEY,
	B2 int,
    FOREIGN KEY (B1) REFERENCES InfoA (A1)	
);