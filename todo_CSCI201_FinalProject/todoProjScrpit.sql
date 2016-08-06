DROP DATABASE IF EXISTS TodoProject;
CREATE DATABASE TodoProject;

USE TodoProject;

CREATE TABLE users(
	userID int(11) primary key not null auto_increment,
    username varchar(30) not null,
    actualname varchar(40) not null,
    email varchar(40) not null,
    hashword varchar(50) not null,
    points int(22) not null DEFAULT 0,
    aboutme text
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
    todoDesc text,
    todoName varchar(25) not null,
    todoFinished bool,
    todoPrivate bool,
    
    
    FOREIGN KEY fk2(userID) REFERENCES users(userID),
    FOREIGN KEY fk3(listID) REFERENCES lists(listID)
);

CREATE TABLE friendship (
	friendshipID int(11) primary key auto_increment,
    fromID int(11) not null,
    toId int(11) not null,
    createdAt datetime default current_timestamp
);

INSERT INTO users(username, actualname, hashword, email, aboutme) VALUES('testuser', 'Testy McTesterson', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'testemail@emailserver.net', 'Im the best tester ever!!!!');