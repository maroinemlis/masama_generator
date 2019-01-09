drop table if exists A;
drop table if exists B;
drop table if exists C;
drop table if exists D;

CREATE TABLE A
(    
	A1 INT PRIMARY KEY,
	A2 INT,
	FOREIGN KEY (A1) REFERENCES B (B1),	
	FOREIGN KEY (A2) REFERENCES C (C1)
);
CREATE TABLE B
(    
   	B1 INT PRIMARY KEY,	
	B2 INT ,	
	FOREIGN KEY (B1) REFERENCES C (C1)
);
CREATE TABLE C
(    
	C1 INT PRIMARY KEY
);

CREATE TABLE D
(    
	D1 INT	PRIMARY KEY,
	D2 INT 	,
	D3 INT 	,
	FOREIGN KEY (D1) REFERENCES C (C1),
	FOREIGN KEY (D2) REFERENCES C (C1),
	FOREIGN KEY (D3) REFERENCES A (A1)
);

--insert into A values(1);
--insert into B values(1);
--insert into C values(2);

select * from A;
select * from B;
select * from C;

