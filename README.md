# CourseHub

A modern reference application that resembles a school administration system to manage courses, enrollments, etc.

This application is used in HackYourFuture to showcase best practices in building a full-stack application and for
students to implement parts. For example, in the frontend course students create their own frontend and in the backend
course create their own backend in Java.

## Getting started

### Requirements

Make sure to have the following installed:

* [Java 21 or later](https://adoptium.net/temurin/releases)
* [Docker](https://www.docker.com/products/docker-desktop/)
* Any IDE: We recommend [VSCode](https://code.visualstudio.com/download)
  or [Intellij IDEA](https://www.jetbrains.com/idea/download/?section=mac)

### Running the application

Starting Postgres database container:

```bash
docker compose up -d
```

Starting the application:

* From Gradle: `./gradlew bootRun`
* From IDE: Run the `CourseHubApplication` main class.

### Making requests

The application is accessible on `http://localhost:8080`.

### Building docker image

```bash
./gradlew bootBuildImage
```

#### Running docker image
```bash
docker compose --profile include-course-hub up
```
