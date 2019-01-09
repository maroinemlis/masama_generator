drop table if exists L;
drop table if exists M;
drop table if exists N;

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