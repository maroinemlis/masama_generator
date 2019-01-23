/* Delete the tables if they already exist */
drop table if exists Movie;
drop table if exists Reviewer;
drop table if exists Rating;

/* Create the schema for our tables */
create table Movie(mID int, title text, year int, director text);
create table Reviewer(rID int, name text);
create table Rating(rID int, mID int, stars int, ratingDate date);

/* Populate the tables with our data */
insert into Movie values(101, 'Gone with the Wind', 1939, 'Victor Fleming');
insert into Movie values(102, 'Star Wars', 1977, 'George Lucas');
insert into Movie values(103, 'The Sound of Music', 1965, 'Robert Wise');
insert into Movie values(104, 'E.T.', 1982, 'Steven Spielberg');
insert into Movie values(105, 'Titanic', 1997, 'James Cameron');
insert into Movie values(106, 'Snow White', 1937, 'David Hand');
insert into Movie values(107, 'Avatar', 2009, 'James Cameron');
insert into Movie values(108, 'Raiders of the Lost Ark', 1981, 'Steven Spielberg');

insert into Reviewer values(201, 'Sarah Martinez');
insert into Reviewer values(202, 'Daniel Lewis');
insert into Reviewer values(203, 'Brittany Harris');
insert into Reviewer values(204, 'Mike Anderson');
insert into Reviewer values(205, 'Chris Jackson');
insert into Reviewer values(206, 'Elizabeth Thomas');
insert into Reviewer values(207, 'James Cameron');
insert into Reviewer values(208, 'Ashley White');

insert into Rating values(201, 101, 2, '2011-01-22');
insert into Rating values(201, 101, 4, '2011-01-27');
insert into Rating values(202, 106, 4, '2011-01-26');
insert into Rating values(203, 103, 2, '2011-01-20');
insert into Rating values(203, 108, 4, '2011-01-12');
insert into Rating values(203, 108, 2, '2011-01-30');
insert into Rating values(204, 101, 3, '2011-01-09');
insert into Rating values(205, 103, 3, '2011-01-27');
insert into Rating values(205, 104, 2, '2011-01-22');
insert into Rating values(205, 108, 4, '2011-01-28');
insert into Rating values(206, 107, 3, '2011-01-15');
insert into Rating values(206, 106, 5, '2011-01-19');
insert into Rating values(207, 107, 5, '2011-01-20');
insert into Rating values(208, 104, 3, '2011-01-02');

INSERT INTO Movie VALUES (68, 'LDHqOZYhfXkayTDlgLJEycUdqblidLu', 75, 'rrdhBaJZsCWKgTtAVZaSQjXHOLScoQQrXJqQPvhBGnHlkUYJG');
INSERT INTO Movie VALUES (41, 'MxDKgmmigIHuzjaobeObzOYwxgbvfelcpnw', 17, 'eZsPoeqTgLRTbLIuoTThtwlmnBdvCZUzlizlUWXNlOSnBREDFtDogVEtCzqAjWEiEDAUevpEJpJiuDQRLDwDcfRFZbwVeRuAZaPt');
INSERT INTO Movie VALUES (13, 'znZmNZALMWOKtKACwwRNAZTnv', 94, 'ZUCnbZSzvLjzCLiIdrLcrIztfLZiijZmmfkRuFvLgmWfaYOFAgXvaedaWBOIatonRAoRLKlbJCeMwKUB');
INSERT INTO Movie VALUES (18, 'FiourVegbEizyFhSeISjpmsfbrhDDZAWjTGFvkZGPveZJFTYVFLLxiSEKOvaDxn', 5, 'ndg');
INSERT INTO Movie VALUES (9, 'BiAKNdxMzeeqzCANHqPwpwmssNGLGUSdCoTCx', 77, 'hLwEtzJnSqPNkXgUMrNJyMLWxlxIEPTsH');
INSERT INTO Movie VALUES (33, 'HIQlxUySQreJFQrXBCMZgWgwMrpzkn', 53, 'hSXiUebQUFBoXyBWvvbgMDleLmbnrlzeHQZKKwZVsERYt');
INSERT INTO Movie VALUES (76, 'ZWxauxqPYcrWRdqLIQOIjfMyYYNCWNhztfeNOZWCqFejcNTQjOZBVAjenyBbXayiOSFOyeHcLirEsZsISyPCSCmMpcoayzdZKbZ', 98, 'zGibFHMYdsYjPtwYICZLKRREsrwfQHMxBcqdZGFsYaSgtRIEDieaMk');
INSERT INTO Movie VALUES (23, 'cridPcPIuABMZYPGyHrVbDKwvRGcDVHVwcInTCNNYUjh', 43, 'OUDqDmQiIowsjrPVRqFgLHfgGGcKolVSeGoXYLZPVaPTrdbXSygTTeZesbKWdDDmnGmTcEEPaYLWXPEmpIaOqrAJWOmBHdNJtqI');
INSERT INTO Movie VALUES (66, 'aGuZlKIoiCVwmFBIIVeiYTKEjOdddjCKLdSOWTiJhPwtbayDULKGaAtLnRwCAKrVZSIVWTeQBOo', 46, 'mRXaEKsotSaqViaszHhLvGdWujWapKTM');
INSERT INTO Movie VALUES (21, 'OoxsQpCTGNdxEYXmRPycXjxbinPIeVzYMTLExaIDYtNFiMYXvdxzmdO', 68, 'BnfkcxmyuKesdimXEmnSAgVJKRFOOlISUFFSDrjxSuo');
INSERT INTO Rating VALUES (20, 52, 40, '2016-10-07');
INSERT INTO Rating VALUES (85, 90, 14, '2017-03-24');
INSERT INTO Rating VALUES (32, 33, 33, '2016-41-22');
INSERT INTO Rating VALUES (72, 89, 1, '2015-57-04');
INSERT INTO Rating VALUES (40, 61, 52, '2017-58-20');
INSERT INTO Rating VALUES (38, 84, 81, '2015-53-07');
INSERT INTO Rating VALUES (26, 13, 57, '2016-47-06');
INSERT INTO Rating VALUES (2, 99, 20, '2016-34-09');
INSERT INTO Rating VALUES (96, 58, 81, '2017-20-02');
INSERT INTO Rating VALUES (69, 86, 59, '2017-10-15');
INSERT INTO Reviewer VALUES (54, 'RhJVIEuQQnfJjWPLZFKLUArCSoLVYgYAaxXdPkzXWdndECmETIj');
INSERT INTO Reviewer VALUES (68, 'aqSZrKofKgvDrPMdmnokXaTxzUC');
INSERT INTO Reviewer VALUES (17, 'SRsdryxDxZBZdtCAZlDzthXySgYzHpKYtEdQMvtyLbvkLHcQqH');
INSERT INTO Reviewer VALUES (62, 'AIRzsTatocRQxPhZeYOLywcyWRWXGxWextliICasVVIqTkdeGfZhkJHwXCzCPzxUVGXcIUfLGmFHq');
INSERT INTO Reviewer VALUES (10, 'fZoXsEdvfNzhVsRdFfzrVrOWBYhCmEuGkprALVLdZMTcsuZIRslQ');
INSERT INTO Reviewer VALUES (74, 'omGMnpmpPcnapMJQFZLRBRlxYOqYlMAHzxYuNIwGVrUoUmBKCVgwrLJfabHrDTTsszINCYCEK');
INSERT INTO Reviewer VALUES (67, 'wsyFLrwMolVCcEgLlpvgjAeasLbMGyysFTRNbFaSiWwWJCRrmUSwpWQnXi');
INSERT INTO Reviewer VALUES (78, 'gfrwRR');
INSERT INTO Reviewer VALUES (81, 'FOldtqToefdmypvnGlzhvwutqkgPtMfVOuCTcHhT');
INSERT INTO Reviewer VALUES (33, 'hwxMulggYSOQpRfqefjquw');
