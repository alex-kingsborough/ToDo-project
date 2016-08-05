DROP DATABASE IF EXISTS TodoProject;
CREATE DATABASE TodoProject;

USE TodoProject;

CREATE TABLE users(
	userID int(11) primary key not null auto_increment,
    username varchar(30) not null,
    actualname varchar(40) not null,
    email varchar(40) not null,
    hashword varchar(50) not null,
    dob int(8) not null,
    aboutme varchar(140)
);

CREATE TABLE lists(
	listID int(12) primary key not null auto_increment,
    userID int(11) not null,
    listName varchar(30) not null,
    listPrivate bool not null,
    
    FOREIGN KEY fk1(userID) REFERENCES users(userID)
);

CREATE TABLE todos(
	todoID int(17) primary key not null auto_increment,
    userID int(11) not null,
    listID int(12) not null,
    todoPoints int(10),
    todoPriority int(20),
    todoDesc varchar(140),
    todoName varchar(25) not null,
    todoFinished bool,
    todoPrivate bool,
    
    
    FOREIGN KEY fk2(userID) REFERENCES users(userID),
    FOREIGN KEY fk3(listID) REFERENCES lists(listID)
);



INSERT INTO users(username, actualname, hashword, email, dob, aboutme) VALUES('testuser', 'Testy McTesterson', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'testemail@emailserver.net', 01301996, 'Im the best tester ever!!!!');