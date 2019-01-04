drop table if exists InfoA;
drop table if exists InfoB;

CREATE TABLE InfoA
(    
	idA1 INT PRIMARY KEY,
	idA2 int 
);
CREATE TABLE InfoB
(    
	idB1 INT PRIMARY KEY,
	idB2 int
	
);

insert into InfoA values(1,2);
insert into InfoA values(2,2);
insert into InfoA values(3,2);

insert into InfoB values(1,2);
insert into InfoB values(2,2);
insert into InfoB values(3,2);
insert into InfoB values(4,4);



