# Merchant Spring Boot Service
This is a Spring Boot application for creating and managing a merchant with MongoDb.
## Getting Started
To get started with this application, you'll need to have Java 8 and Maven installed on your machine. You can check if you have them installed by running the following commands:

> - java -version 
> - mvn -version

If you don't have Java or Maven installed, you can download them from the following links:

* [Java](https://www.java.com/en/download/)
* [Maven](https://maven.apache.org/download.cgi)

Once you have Java and Maven installed, you can clone this repository and navigate to the root directory of the project:

> * git clone https://github.com/abdullahtariq007/java-assessment.git
> * cd java.assessment

## Running the Application
To run the application, you can use the following command:
> mvn spring-boot:run

This will start the application and make it available at http://localhost:8080

Don't forget to update mongodb credentials in **application.properties** file

See the swagger api documentation here: http://localhost:8080/swagger-ui/index.html