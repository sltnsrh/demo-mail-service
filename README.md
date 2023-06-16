# Mailing App :mailbox_with_mail: 
## Introduction
The Mailing App is a Spring Boot application designed to send greeting emails to users. 
It provides a RESTful API for managing users, scheduling mail jobs, and controlling the mailing scheduler. 
This application aims to simplify the process of sending personalized greetings to users via email.
___
## Technologies:
* Java 17
* Spring Boot: 3.0.6
  * Spring Data JPA
  * Spring Boot Starter Mail
  * Spring Boot Starter Web
  * Spring Boot DevTools
  * Springdoc OpenAPI
  * Spring Boot Starter Validation
* H2 Database
* Project Lombok
* MapStruct
* Docker
* Junit and Mockito: for unit and integration tests
___
## Code Coverage
The project utilizes the JaCoCo Maven plugin to generate code coverage reports. During the build process, the plugin 
instruments the compiled code and tracks the code execution during test runs. After the tests are executed, it 
generates a detailed report indicating the code coverage metrics.

By running the command mvn clean install, the project's tests will be executed, and the code coverage report will 
be generated. The report can be accessed in the target directory of the project: `target/site/jacoco/index.html`
___
## Getting started:

### Running from CLI:
* Ensure you have Java and Maven installed on your system
* Clone the Git repository to your local and move into the directory
* In [application.properties](src/main/resources/application.properties) set your gmail credentials: 
  * `spring.mail.username=<your_gmail_address>`
  * `spring.mail.password=<your_app_password_for_google>`
* Execute the following command to build the project: `mvn clean install`
* Run the application: `mvn spring-boot:run`

### Running from Docker container:
* Clone repository to your local and move into
* Go into the [docker-compose.yml](docker-compose.yml) and set your gmail credentials variables:
  * `spring.mail.username=<your_gmail_address>`
  * `spring.mail.password=<your_app_password_for_google>`
* After changing run: `docker-compose up` command to start a Docker container on local port 8080

Also, if remote Docker image `srhsltn/mail-service` is unavailable, you can build it yourself:
* Run: `mvn package` to build .jar file
* Run: `docker build -t <your-image-name> .`
Then in docker-compose.yml change image name to provided during the build <your-image-name>.

If you don't use **Gmail**, you need to change other mail properties in [application.properties](src/main/resources/application.properties).
___
### After starting the App:
**Postman collection** file is in a project directory: [demo-mail-service.postman_collection.json](demo-mail-service.postman_collection.json) <br />

**H2 Database** url: http://localhost:8080/h2console <br />
* JDBC URL: jdbc:h2:mem:mailServiceDb
* User Name: sa
* Password: 

The API documentation is automatically generated using **Springdoc OpenAPI**. 
Once the application is running, you can access the API documentation at: http://localhost:8080/swagger-ui/index.html#/
___
## API endpoints list:

1. `POST: /users` - creating a new user
2. `PUT: /users` - updating username and email by user id
3. `DELETE: /users/{id}` - deleting user by id
4. `GET: /users` - getting all users with pageable result and find a user by username or email
5. `GET: /users/stats` - getting statistics by all users with pageable result

6. `POST: /crons` - creating a new cron expression in db 
7. `PUT: /crons` - updating a cron expression by id
8. `GET: /crons` - finding all existing crons with pageable result
9. `DELETE: /crons/{id}` - deleting cron entity expression by id

10. `POST: /mail/users/{id}/send` - sending greeting email to a user by user id
11. `POST: /mail/crons/{id}/schedule` - scheduling mailing job by cron id
12. `POST: /mail/stop-scheduler` - allows to stop a cron job started before
