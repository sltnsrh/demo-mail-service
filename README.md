# Mailing App
## Getting started:

Running from CLI:
* Clone the Git repository to your local and move into the directory
* Export variables with your Gmail credentials: 
  * `export EMAIL=<your_email>`
  * `export PASSWORD=<your_app_password_for_google>`
* Run the application: `mvn spring-boot:run`

Running from IDE:
* Clone the Git repository to your local
* Open project in IDE
* Add environment variables to a run configuration:
  * `EMAIL=<your_email>`
  * `PASSWORD=<your_app_password_for_google>`
* Run the app

If you don't use Gmail, you need to change other mail properties in [application.properties](src/main/resources/application.properties).


Postman collection file is in a project directory: [demo-mail-service.postman_collection.json](demo-mail-service.postman_collection.json) <br />

H2 Database url: http://localhost:8080/h2console <br />
* JDBC URL: jdbc:h2:mem:mailServiceDb
* User Name: sa
* Password: 

API endpoints:

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
