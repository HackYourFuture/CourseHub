# CourseHub

A modern Java reference app for courses, enrollments, and school workflows.

## Getting started

### Requirements

* Java 21 or later
* Docker
* VSCode or Intellij IDEA

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
