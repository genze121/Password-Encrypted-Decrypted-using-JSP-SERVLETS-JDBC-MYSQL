create database pass;
use pass;

create table passtable
(id int primary key not null auto_increment,
username varchar(50),
password varchar(1000));

drop table passtable;
select * from passtable;
