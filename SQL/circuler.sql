drop table if exists InfoA;
drop table if exists InfoB;
drop table if exists InfoC;

CREATE TABLE InfoA
(    
	A1 text PRIMARY KEY,
	A2 int,
    FOREIGN KEY (A1) REFERENCES InfoB (B1)
);
CREATE TABLE InfoB
(    
	B1 text PRIMARY KEY,
	B2 int,
    FOREIGN KEY (B1) REFERENCES InfoC (C1)	
);
CREATE TABLE InfoC
(    
	C1 text PRIMARY KEY,
	C2 int,
    FOREIGN KEY (C1) REFERENCES InfoA (A1)	
);