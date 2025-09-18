# CourseHub

A modern reference application that resembles a school administration system to manage courses, enrollments, etc.

This application is used in HackYourFuture to showcase best practices in building a full-stack application and for
students to implement parts. For example, in the frontend course students create their own frontend and in the backend
course create their own backend in Java.

## Getting started

### Prerequisites

Make sure to have the following installed, for the frontend application:

* [Node.js 22 or later](https://nodejs.org/en/download)

Make sure to have the following installed, for backend application:

* [Java 21 or later](https://adoptium.net/temurin/releases)
* [Docker](https://www.docker.com/products/docker-desktop/)
* Any IDE: We recommend [VSCode](https://code.visualstudio.com/download)
  or [IntelliJ IDEA](https://www.jetbrains.com/idea/download/?section=mac)

### Running the frontend

To install the required dependencies (only once), from the `ui` directory, run:

```bash 
    npm install
```

To run the frontend application locally, from the `ui` directory, run:

```bash
    npm run dev
```

### Running the backend

Starting Postgres database container:

```bash
docker compose up -d
```

To run the backend application:

* From Gradle: `./gradlew bootRun`
* From IDE: Run the `CourseHubApplication` main class.

### Making requests

The application is accessible on `http://localhost:8080`.

### Building docker images

To build a Docker image of the course-hub backend, run the following command:
```bash
./gradlew bootBuildImage
```
To build a Docker image for the frontend, from the `ui` directory, run:
```bash
docker build -t ghcr.io/hackyourfuture/course-hub-frontend ui
```

#### Running docker image

After the image is built, you can run it using a special Docker Compose profile _(make sure you stop the application if you're running it from Gradle or IDE)_:
```bash
docker compose --profile include-course-hub up
```

### Cleanup

Keep in mind that containers will keep running in the background even after you stop the application. To stop and remove the containers, run:
```bash
docker compose --profile include-course-hub down -v
