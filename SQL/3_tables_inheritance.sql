drop table if exists A;
drop table if exists B;
drop table if exists C;

CREATE TABLE A
(    
	A1 INT PRIMARY KEY,
	FOREIGN KEY (A1) REFERENCES B (B1)	
);
CREATE TABLE B
(    
   	B1 INT PRIMARY KEY,	
	FOREIGN KEY (B1) REFERENCES C (C1)
);
CREATE TABLE C
(    
	C1 INT PRIMARY KEY	
);

--insert into A values(1);
--insert into B values(1);
--insert into C values(2);

select * from A;
select * from B;
select * from C;

