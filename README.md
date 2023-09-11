# simple-bank-account

## Background
A sample Spring Boot application of a simple bank account system with a REST interface and in memory relational database.

## Prerequisites
- Java 17

## Running Application
Run the command in the root of the project.
### Windows
```bash
gradlew.bat bootRun
 ```
### Linux
```bash
./gradlew bootRun
```

## OpenAPI/Swagger UI
The OpenAPI/Swagger UI can be found at the following link:
http://localhost:9080/actuator/swagger-ui/index.html

All documentation about testing the application is found here.

## Database
The system uses an H2 in memory database that can be accessed at the following URL:  
http://localhost:8080/h2-console
The following details can be used to access the database:

**Driver Class:** org.h2.Driver  
**JDBC URL:** jdbc:h2:mem:testdb  
**User Name:** sa  
**Password:** 

## Unit Tests
Run the command in the root of the project.
### Windows
```bash
gradlew.bat test
 ```
### Linux
```bash
./gradlew test
```
Then open the test report file in the web browser
```
/build/reports/tests/test/index.html
```

## Assumtions
- An account is allowed to have multiple cards
- Fees are only charged for credit card payments
- Transfer is made from one account to another
- No security is required to be implemented
- Audit of transactions will thus not include user details of logged in user.
- No external dependencies such as message queues will be used to make application easier to start up.