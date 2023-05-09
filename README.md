# Getting started:

* Clone the repository
* Run application with environment variables EMAIL and PASSWORD where pass your Gmail credentials. 
If you use not Gmail, you need to change mail properties in [application.properties](src/main/resources/application.properties).
* Postman collection file: [demo-mail-service.postman_collection.json](demo-mail-service.postman_collection.json) <br />
* H2 Database url: http://localhost:8080/h2console
    * JDBC URL: jdbc:h2:mem:mailServiceDb
    * User Name: sa
    * Password: 