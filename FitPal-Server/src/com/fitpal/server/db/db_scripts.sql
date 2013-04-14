create database fitpal;

create table fitpal.user_profile( 

  username varchar(100) primary key,
  password varchar(50),
  email varchar(100) unique,
  display_name varchar(100),
  dob date,
  height float,
  weight float,
  created_timestamp timestamp default CURRENT_TIMESTAMP
);
