drop table if exists A;
drop table if exists B;
drop table if exists D;
drop table if exists B;

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
    FOREIGN KEY (C1) REFERENCES A (A1)	
);
CREATE TABLE D
(    
	D1 int PRIMARY KEY,
    FOREIGN KEY (D1) REFERENCES A (A1)	
);

CREATE TABLE E
(    
	E1 int PRIMARY KEY,
    FOREIGN KEY (E1) REFERENCES D (D1)	
);