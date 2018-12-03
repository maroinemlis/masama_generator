
drop table if exists InfoC;

CREATE TABLE InfoC
(    
	idC INT PRIMARY KEY,
	cInt int ,
	FOREIGN KEY (idC) REFERENCES InfoD (idB)
);

