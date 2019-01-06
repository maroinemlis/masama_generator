PRAGMA foreign_keys = off;
drop table if exists A;
drop table if exists B;
drop table if exists C;
PRAGMA foreign_keys = ON;
CREATE TABLE A
(    
	A1 text PRIMARY KEY,
	A2 int    
);
CREATE TABLE B
(    
	B1 text PRIMARY KEY,
	B2 int
);
CREATE TABLE C
(    
	C1 text PRIMARY KEY,
	C2 int,
    FOREIGN KEY (C1) REFERENCES A (A1),
    FOREIGN KEY (C1) REFERENCES B (B1)	
);

insert into A values(1,2);
insert into A values(2,2);

insert into B values(1,2);
insert into B values(3,2);

insert into C values(1,2);

select * from C;