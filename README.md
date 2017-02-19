# struts-spring-exercise
First exercise with spring and struts 1

This is my first exercise with JEE. I have used for this spring 4.3.5, struts 1 to begin with. It is a work in progress
and it is just intended to learn. 

It is a simple web application with a h2 database for testing, that holds a database of users and people.
Every person can have several users. Users and their passwords are used to login.
Web design is almost unexistent. Once you have logged in you can edit persons and users without restrictions
(there should always be at least a person and a user in order to log in. Other case is not implemented).

It follows a MVC paradigm. Model is implemented with JDBC templates provided with Spring.
Later on, this implementation will be substitued by Hibernate. Once this is working correctly,
I will use JPA.

Provided that I have designed Dao's as interfaces, I can implement this interfaces with the technology of my choice, and so I will
introduce these changes (Hibernate, JPA) and test the application with each of these technologies.

After that I will change the database to a 'production' one, Mysql.

If you find this useful at all, feel free to use it.
