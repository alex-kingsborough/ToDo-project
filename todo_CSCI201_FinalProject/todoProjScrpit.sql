DROP DATABASE IF EXISTS TodoProject;
CREATE DATABASE TodoProject;

USE TodoProject;

CREATE TABLE users(
	userID int(11) primary key not null auto_increment,
    username varchar(50) not null,
    actualname varchar(40) not null,
    email varchar(40) not null,
    hashword varchar(50) not null,
    points int(22) not null DEFAULT 1,
    aboutme text
);

CREATE TABLE lists(
	listID int(12) primary key not null auto_increment,
    userID int(11) not null,
    listName varchar(30) not null,
    isActive bool not null DEFAULT true,
    
    FOREIGN KEY fk1(userID) REFERENCES users(userID)
);

CREATE TABLE todos(
	todoID int(17) primary key not null auto_increment,
    userID int(11) not null,
    listID int(12) not null,
    todoPoints int(10) not null DEFAULT 0,
    todoPriority int(20),
    todoDesc text,
    todoTitle varchar(40) not null,
    todoIsCompleted bool,
    todoPrivate bool,
    createdAt datetime default current_timestamp,
    
    FOREIGN KEY fk2(userID) REFERENCES users(userID),
    FOREIGN KEY fk3(listID) REFERENCES lists(listID)
);

CREATE TABLE friendship (
	friendshipID int(11) primary key auto_increment,
    fromID int(11) not null,
    toId int(11) not null,
    createdAt datetime default current_timestamp
);