PRAGMA foreign_keys = OFF;
DROP TABLE IF EXISTS B;
DROP TABLE IF EXISTS A;
PRAGMA foreign_keys = ON;

	CREATE TABLE A(
		a1 INT ,
		a2 INT ,
		primary KEY (a1,a2)
	);

	CREATE TABLE B(
		b1 INT , 
		b2 INT , 
		b3 INT , 
		FOREIGN KEY (b1,b2) REFERENCES A (a1,a2),
		FOREIGN KEY (b2,b3) REFERENCES A (a1,a2)	
	);

--insert into A values(1,1);
--insert into B values(1,2,1);
	
select * from A;
select * from B;

