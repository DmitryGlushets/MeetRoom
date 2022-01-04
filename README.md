# MeetRoom

---
### About application


This application is developed in Intellij IDEA 2021.2.3 (Ultimate Edition) environment 
in Java 8 language (Maven builder).    
The following technologies were used:
* Spring Framework *(Spring Boot, Spring Data, Spring WEB, Spring Security, Spring MVC)*.
* Hibernate, PostgreSQL.
* Thymeleaf.
* Bootstrap.

---
### System environment

* Java 8+.
* PostgreSQL-14.1-windows-x64..
* Intellij IDEA 2021.2.3 Ultimate Edition *(to compile the application)*.

---
### Application deployment order

* In PostgreSQL create a user named `user_meetroom_db` with password `qwerty`.
* Create a database called `meetroom_db`. SQL script:
  >CREATE DATABASE meetroom_db
  WITH
  OWNER = user_meetroom_db
  ENCODING = 'UTF8'
  LC_COLLATE = 'Russian_Russia.1251'
  LC_CTYPE = 'Russian_Russia.1251'
  TABLESPACE = pg_default
  CONNECTION LIMIT = -1;
* The base is loaded by means `Liquibase`, table templates are in `schema.sql`, data to fill in `data.sql`.
* Create a project in Intellij IDEA from the `meetroom` folder.
* Build and run the application from *Application class*.
* You can change the database access settings in the *application.yml* file:
  >spring:  
  datasource:  
  url: jdbc:postgresql://localhost:5432/meetroom_db  
  username: user_meetroom_db  
  password: qwerty  
  driver-class-name: org.postgresql.Driver

---
### User's manual
Only registered users have access to the system.
Users have two roles `ADMIN` and `USER`.
Users with the `ADMIN` role can add new users, delete and update them. And can delete and correct all 
meeting records in calendar.
Users with the `USER` role can add meeting in calendar. Update and delete only meeting add by yourself.
If in the database no user with the role `ADMIN`, it is created automatically (login `admin`, password: `admin`)
The database contains the following users:
* `admin`, password: `admin`.
* `Ivanov`, password: `qwerty`.
* `Sergeeva`, password: `12345`.
* `Eremin`, password: `12345`.

The database already contains some scheduled meetings.    
To view the details of the meeting, click on it.    
You can flip weeks into the past and into the future.    
To schedule a meeting, you need to click on the desired date.
You can create meetings from 30 minutes in length.

---
### Author
Author: Glushets Dmitry
dglushets@gmail.com
+7-917-060-44-52
