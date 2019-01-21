drop table if exists A;
drop table if exists B;
drop table if exists C;
drop table if exists E;
drop table if exists F;
drop table if exists G;


CREATE TABLE A
(    
	A1 int PRIMARY KEY,
    FOREIGN KEY (A1) REFERENCES B (B1)
);
CREATE TABLE B
(    
	B1 int PRIMARY KEY,
    FOREIGN KEY (B1) REFERENCES C (C1)	
);
CREATE TABLE C
(    
	C1 int PRIMARY KEY,
    FOREIGN KEY (C1) REFERENCES A (A1),
    FOREIGN KEY (C1) REFERENCES E (E1)	
);


CREATE TABLE E
(    
	E1 int PRIMARY KEY,
    FOREIGN KEY (E1) REFERENCES F (F1)
);
CREATE TABLE F
(    
	F1 int PRIMARY KEY,
    FOREIGN KEY (F1) REFERENCES G (G1)	
);
CREATE TABLE G
(    
	G1 int PRIMARY KEY,
    FOREIGN KEY (G1) REFERENCES E (E1)	
);
