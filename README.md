# Getting started:

Running from CLI:
* Clone the Git repository to your local and move into the directory
* Export variables with you Gmail credentials: 
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