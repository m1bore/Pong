drop database pong;
Create database pong;
use pong;
create table users (
user_code varchar(10),
	user varchar(50) not null,
	password varchar (50),
    games int,
    points int,
	primary key (user_code));
#insert into users (user_code,user,password,games,points) 
#values (1 , "Marco","caracola",5,47);
insert into users (user_code,user,password,games,points) 
values (2 , "Toni","caracola",7,39);