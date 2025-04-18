# Digital Library Management System

This project implements a Digital Library Management System that provides RESTful APIs for managing books in a library. The system is built in Java using Spark Java, integrated with MySQL via JDBC, and is deployed on an AWS EC2 instance (Ubuntu).
# Deployment Link: [Click Here](http://13.203.77.189/)

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Installation and Setup](#installation-and-setup)
  - [Prerequisites](#prerequisites)
  - [Cloning the Repository](#cloning-the-repository)
  - [Compilation](#compilation)
  - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Deployment](#deployment)
- [Challenges faced](#challenges-faced)
- [Future Improvements](#future-improvements)


## Overview

The Digital Library Management System allows users (e.g., librarians) to manage books by adding, updating, deleting, and searching for them. This system uses a Java backend with Spark Java to expose RESTful endpoints that connect to a MySQL database, and it provides an API that can be used by a frontend application.

## Features

- **Add a Book:** Create new book records.
- **View All Books:** Retrieve a list of all books.
- **Search Books:** Search by book ID or by title.
- **Update a Book:** Modify details of an existing book.
- **Delete a Book:** Remove a book record.
- **CORS Enabled:** Allows cross-origin requests for frontend integration.

## Tech Stack

- **Backend:** Java 21, Spark Java, JDBC
- **Database:** MySQL
- **Testing:** JUnit, REST-assured
- **Build Tool:** Gradle (Groovy)
- **Deployment:** AWS EC2 (Ubuntu)
- **Dependencies:** 
  - spark-core-2.9.3.jar
  - gson-2.10.1.jar
  - slf4j-api-1.7.36.jar, slf4j-simple-1.7.36.jar
  - javax.servlet-api-4.0.1.jar
  - jetty-server-9.4.43.v20210629.jar, jetty-util-9.4.43.v20210629.jar, jetty-io-9.4.43.v20210629.jar, jetty-http-9.4.43.v20210629.jar
  - MySQL Connector/J (e.g., `/usr/share/java/mysql-connector-java-9.2.0.jar`)

## Installation and Setup

### Prerequisites

- Java 21 installed on the system.
- MySQL Server installed and running.
- AWS EC2 instance running Ubuntu.
- Gradle installed (or use the Gradle wrapper).

### Cloning the Repository

Clone the repository using Git:

```sh
git clone https://github.com/Siddhant4742/Ajackus-Java-Assignment.git
cd Ajackus-Java-Assignment/Digital Library Book Management System/src/

```
### Compilation
Compile the source files located under src/ with the necessary dependencies. For example:
```sh
javac -cp ".:spark-core-2.9.3.jar:gson-2.10.1.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar:javax.servlet-api-4.0.1.jar:jetty-server-9.4.43.v20210629.jar:jetty-util-9.4.43.v20210629.jar:jetty-io-9.4.43.v20210629.jar:jetty-http-9.4.43.v20210629.jar:/usr/share/java/mysql-connector-java-9.2.0.jar" FinalVersion.java
```
### Running the Application
Run the main class FinalVersion with the following command:
```sh
java -cp ".:spark-core-2.9.3.jar:gson-2.10.1.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar:javax.servlet-api-4.0.1.jar:jetty-server-9.4.43.v20210629.jar:jetty-util-9.4.43.v20210629.jar:jetty-io-9.4.43.v20210629.jar:jetty-http-9.4.43.v20210629.jar:/usr/share/java/mysql-connector-java-9.2.0.jar" FinalVersion
```
Make sure that your MySQL database is up and running and that any necessary configuration (such as database credentials) is set up in your application.

## API Endpoints
| Method | Endpoint       | Description |
|--------|--------------|-------------|
| `POST` | `/books` | Add a new book |
| `GET` | `/books` | Get all books |
| `GET` | `/books/{id}` | Get book by ID |
| `GET` | `/books/search?title={title}` | Search book by title |
| `PUT` | `/books/{id}` | Update book details |
| `DELETE` | `/books/{id}` | Delete a book |


## Testing
The project uses pytest to run test cases on a coverage rate of 98%
```sh
pytest -v --cov=. --cov-report=html
```
## Deployment
After cloning the repository and navigating to the project directory, you can use the following Bash script to compile and run the project in the background using nohup.
```sh
#!/bin/bash

# Navigate to the source directory
cd "$(dirname "$0")/Digital Library Book Management System/src/"

# Compile the Java file with necessary dependencies
javac -cp ".:spark-core-2.9.3.jar:gson-2.10.1.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar:javax.servlet-api-4.0.1.jar:jetty-server-9.4.43.v20210629.jar:jetty-util-9.4.43.v20210629.jar:jetty-io-9.4.43.v20210629.jar:jetty-http-9.4.43.v20210629.jar:/usr/share/java/mysql-connector-java-9.2.0.jar" FinalVersion.java

# Run the Java application in the background using nohup
nohup java -cp ".:spark-core-2.9.3.jar:gson-2.10.1.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar:javax.servlet-api-4.0.1.jar:jetty-server-9.4.43.v20210629.jar:jetty-util-9.4.43.v20210629.jar:jetty-io-9.4.43.v20210629.jar:jetty-http-9.4.43.v20210629.jar:/usr/share/java/mysql-connector-java-9.2.0.jar" FinalVersion > server.log 2>&1 &

# Print process ID
echo "Server started in the background. PID: $!"
```
Make the Script Executable
```sh
chmod +x startup.sh
```

Run the Deployment Script
```sh
./startup.sh
```

🌍 Frontend Deployment (Website)

1️⃣ Install Apache HTTPD
```sh
yum update
yum install apache2 -y
```
2️⃣ Start HTTPD
```sh
sudo systemctl start httpd
sudo systemctl enable httpd 
```
## Challenges Faced

During the development of the **Digital Library Management System**, several challenges were encountered:

1. **Spark Framework Dependencies & Jetty Server Issues**  
   - Setting up **Spark Java** with **Jetty Server** required managing multiple dependencies manually. Compatibility issues arose while integrating different versions of `spark-core`, `jetty-server`, and `javax.servlet-api`. Resolving these dependency conflicts and ensuring the correct execution order was time-consuming.  

2. **Switching from JUnit to PyTest for Testing**  
   - Initially, I intended to write test cases using **JUnit** in Java. However, due to my limited experience with JUnit and time constraints, I switched to **PyTest** for faster and more efficient test case writing. While this improved test coverage in the short term, integrating Python tests with a Java project was not ideal.  

3. **Database Management**  
   - A local MySQL database was used, which made scalability difficult. Managing connections and ensuring data consistency required additional effort. A cloud-based solution would have simplified database handling.  

4. **Deployment Issues**  
   - Setting up the AWS EC2 server and configuring **httpd** for hosting the web frontend required troubleshooting various network and permission-related issues. Ensuring the backend API was correctly accessible also required adjusting firewall settings.  

5. **Limited Time for Additional Features**  
   - Features like user authentication, cloud-based database support, and mobile app integration had to be postponed due to time constraints.  

## Future Improvements

If I had more time, I would have implemented the following improvements to enhance the system:

### 1. Cloud Database Integration  
Currently, the system uses a local MySQL database. Moving to a cloud-based database service like **AWS RDS**, **Google Cloud SQL**, or **Firebase Firestore** would offer better scalability, reliability, and automated backups. This would ensure high availability and reduce maintenance efforts.

### 2. Java-Based Test Cases (JUnit)  
Right now, I have written test cases using **Python (pytest)**, but since the project is in Java, it would be more appropriate to use **JUnit** for unit testing and **RestAssured** for API testing. This would ensure better integration with the Java ecosystem and allow for automated testing within a CI/CD pipeline.

### 3. User Authentication & Authorization  
Currently, there is no user authentication mechanism. I would implement **JWT-based authentication** to manage user sessions securely. Additionally, **role-based access control (RBAC)** could be introduced to restrict certain actions based on user roles (e.g., Admin, Librarian, Member).

### 4. Mobile App Support  
To enhance accessibility, I would develop a **Flutter-based mobile app** that allows users to search for books, check availability, and manage their library interactions on the go. This would improve the overall user experience and make the system more versatile.

These enhancements would significantly improve the project by making it more secure, scalable, and user-friendly.


