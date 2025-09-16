# CourseHub

A modern reference application that resembles a school administration system to manage courses, enrollments, etc.
This application is used in HackYourFuture to showcase best practices in building a full-stack application and for
students to implement parts. For example, in the frontend course create their own frontend and in the backend course
create their own backend in Java.

## Getting started

### Requirements

* Java 21 or later
* Docker
* Any IDE: we recommend VSCode or Intellij IDEA

### Running the application

Starting Postgres database container:

```bash
docker compose up -d
```

Starting the application:

* From Gradle: `./gradlew bootRun`
* From IDE: Run the `CourseHubApplication` main class.

### Making requests

The application runs on `http://localhost:8080`.
