/* Delete the tables if they already exist */
drop table if exists Movie;
drop table if exists Reviewer;
drop table if exists Rating;

/* Create the schema for our tables */
CREATE TABLE IF NOT EXISTS Movie(
	movieID int PRIMARY KEY NOT NULL, 
	title text, 
	year int CHECK (year>1900), 
	director text,
	UNIQUE(title,year));

CREATE TABLE IF NOT EXISTS Reviewer(
	rID int PRIMARY KEY 
	NOT NULL, 
	name text);

CREATE TABLE IF NOT EXISTS Rating(
    rID int ,
    mID int , 
    stars int CHECK(stars>0 and stars<6), 
    ratingDate date ,
    FOREIGN KEY (mID) REFERENCES Movie (movieID) ON DELETE RESTRICT,
    FOREIGN KEY (rID) REFERENCES Reviewer (rID));



