# Fitness Tracker

Dev: ![](https://github.com/djenkins1/FitnessTracker/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=dev)
Master:![](https://github.com/djenkins1/FitnessTracker/workflows/Java%20CI%20with%20Gradle/badge.svg)

This is a RESTful web application using Spring Boot. It is used to track physical exercise by week.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

1. Clone the repository from https://github.com/djenkins1/FitnessTracker.git
2. See Prerequisites for necessary software.
3. Import the project into your IDE of choice.
4. Import the database by running the fitness_db.sql file in PostgreSQL.
5. Run 'gradle bootRun' from the folder that the project is in.
6. Navigate to the url in browser: http://localhost:8080/
At this point you should see all the Fitness data from the database.

See the following to explore documentation on other endpoints: http://localhost:8080/swagger-ui.html

### Prerequisites

* Gradle
* Java
* PostgreSQL
* Node and npm
* IDE of your choice

## Running the tests

Run 'gradle test' from the folder that the project is in.

## Deployment

TBD

## Built With

* [Spring](https://spring.io/projects/spring-framework)
* [Spring-Boot](https://spring.io/projects/spring-boot)
* [Spring-Data](https://spring.io/projects/spring-data)
* [Gradle](https://docs.gradle.org/current/userguide/userguide.html) - Dependency Management
* [SpringFox](https://springfox.github.io/springfox/docs/current/) - Swagger Documentation
* [PostgreSQL](https://www.postgresql.org/docs/) - Database
* [React](https://reactjs.org/docs/getting-started.html) - UI

## Authors

* **Dilan Jenkins** - *Initial work* - [djenkins1](https://github.com/djenkins1)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details

## Acknowledgments

* Thanks to Billie Thompson [PurpleBooth](https://github.com/PurpleBooth) for a wonderful template for this Readme.


