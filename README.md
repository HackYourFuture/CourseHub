# CourseHub

A modern reference application that resembles a school administration system to manage courses, enrollments, etc.

This application is used in HackYourFuture to showcase best practices in building a full-stack application and for
students to implement parts. For example, in the frontend course students create their own frontend and in the backend
course create their own backend in Java.

## Live demo

You can access a live demo of CourseHub at: [https://coursehub.hyf.dev](https://coursehub.hyf.dev) and explore the OpenAPI specification at: [https://coursehub.hyf.dev/api/swagger-ui/index.html](https://coursehub.hyf.dev/api/swagger-ui/index.html).

## Development

### Prerequisites

Make sure to have the following installed, for backend application:

* [Java 21 or later](https://adoptium.net/temurin/releases)
* [Docker](https://www.docker.com/products/docker-desktop/)
* Any IDE: We recommend [VSCode](https://code.visualstudio.com/download)
  or [IntelliJ IDEA](https://www.jetbrains.com/idea/download/?section=mac)

Make sure to have the following installed, for the frontend application:

* [Node.js 22 or later](https://nodejs.org/en/download)

### Running the backend

Starting all required dependencies for the CourseHub backend:

```bash
docker compose up -d
```

To run the backend application:

* From Gradle: `./gradlew bootRun`
* From IDE: Run the `CourseHubApplication` main class.

Now you can access the CourseHub UI on `http://localhost:80` and the backend API on `http://localhost:8080`.

### Making requests

Without authentication, you can only access the public endpoints, for example:
```bash
curl http://localhost:8080/courses
```

You can also see all available endpoints in the [OpenAPI documentation](http://localhost:8080/swagger-ui/index.html).

### Running the frontend

By default you get the the frontend running on `http://localhost:80` from a docker compose, if you want to run it locally, follow the steps below.

To install the required dependencies (only once), from the `ui` directory, run:

```bash 
npm install
```

To run the frontend application locally, from the `ui` directory, run:

```bash
npm run dev
```

With this you can now access the CourseHub UI on `http://localhost:5173` in development mode.

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
```
