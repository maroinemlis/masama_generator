PRAGMA foreign_keys = OFF;
DROP TABLE IF EXISTS BB;
DROP TABLE IF EXISTS AA;

	PRAGMA foreign_keys = ON;
	

	CREATE TABLE AA(
		a1 INT ,
		a2 INT ,
		primary KEY (a1,a2)
	);

	CREATE TABLE BB(
		b1 INT , 
		b2 INT , 
		b3 INT ,
		FOREIGN KEY (b1,b2) REFERENCES AA (a1,a2)
		FOREIGN KEY (b2,b3) REFERENCES AA (a1,a2)	
	);

	
	
	

