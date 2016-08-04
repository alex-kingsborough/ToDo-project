DROP DATABASE IF EXISTS FinalProjectTodo;
CREATE DATABASE FinalProjectTodo;
USE FinalProjectTodo;

CREATE TABLE User (
	userID int(11) primary key auto_increment,
    username varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    aboutme text default "",
    createdAt datetime default current_timestamp
);

CREATE TABLE TodoList (
	todolistID int(11) primary key auto_increment,
    userID int(11) not null,
    name varchar(255) not null,
    createdAt datetime default current_timestamp,
    FOREIGN KEY fk1(userID) REFERENCES User(userID)
);

CREATE TABLE Todo (
	todoID int(11) primary key auto_increment,
    userID int(11) not null,
    todolistID int(11) not null,
    name varchar(255) not null,
    description text not null,
    isComplete tinyint(1) not null,
    priority int(2) not null,
    isPrivate tinyint(1) not null,
    points int(22) not null,
    createdAt datetime default current_timestamp,
    FOREIGN KEY fk1(userID) REFERENCES User(userID),
    FOREIGN KEY fk2(todolistID) REFERENCES TodoList(todolistID)
);

CREATE TABLE Friendship (
	friendshipID int(11) primary key auto_increment,
    fromID int(11) not null,
    toId int(11) not null,
    createdAt datetime default current_timestamp
);