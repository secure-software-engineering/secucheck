# To-do list Application

This demo applications helps its users to create, edit and delete tasks. They can also have attachments with them. This application is used as an example for testing Secucheck to find vulnerabilities in applications.

### Dependencies:
 
* Spring Boot(2.0.4)
* Spring Security
* Spring MVC
* JPA
* Thymeleaf
* Lombok
* MySQL
* Bootstrap (UI Presentation)
* Maven
* Eclipse IntelliJ
* Java 8
* Packaging (JAR)

## How to run the application:

* Install the above mentioned dependencies in/with the IDE of your choice.

* Start the MySQL server on the system. 

* Create a database with the name of `todolist`:

```
mysql> CREATE DATABASE todolist;
```

* Create a database user with the default credentials and update them in the application.properties:

```
mysql> CREATE USER 'dbuser'@'localhost' IDENTIFIED BY '*******';
```

```
spring.datasource.url = jdbc:mysql://127.0.0.1:3306/todolist
spring.datasource.username = dbuser
spring.datasource.password = dbpassword

```

* Open the application in the IDE of your choice with the dependencies setup in it, e.g.: Eclipse.

* Compile and run the application.

* Open the link `localhost:8080/` in a browser, which will land you on the homepage of the application.



## Using the application: 

* After running the application, open the link <href>http://localhost:8080 in your browser.

* You will be taken to the home page from where you can go to the registeration or login page.
 
* Register a new user if you have to, and login.

* After the login, you will be take to the home page again, from where you can navigate to the tasks page and if the current user is in the role of admin it can also view all the registered users and activate and deactivate them.

* In the task page create, edit and delete the tasks of the current users, you can also add an attachment with a task there.
