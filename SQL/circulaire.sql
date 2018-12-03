PRAGMA foreign_keys = OFF;
DROP TABLE IF EXISTS B;
DROP TABLE IF EXISTS A;
DROP TABLE IF EXISTS C;
DROP TABLE IF EXISTS D;

	PRAGMA foreign_keys = ON;
	

	CREATE TABLE A(
		a INT PRIMARY KEY,
		FOREIGN KEY(a) REFERENCES B(b) DEFERRABLE INITIALLY DEFERRED
	);

	CREATE TABLE B(
		b INT PRIMARY KEY , 
		FOREIGN KEY(b) REFERENCES C(c)  DEFERRABLE INITIALLY DEFERRED
	);
	CREATE TABLE C(
		c INT PRIMARY KEY , 
		FOREIGN KEY(c) REFERENCES D(d)  DEFERRABLE INITIALLY DEFERRED
	);
	CREATE TABLE D(
		d INT PRIMARY KEY
	);
	

	BEGIN ;
	INSERT INTO D VALUES (1);
	INSERT INTO C VALUES (1);
	INSERT INTO B VALUES (1);	
	INSERT INTO A VALUES (1);	
	
	COMMIT;


	
	SELECT * FROM A;
	SELECT * FROM B;
	SELECT * FROM C;
	SELECT * FROM D;
	
	
	
	

