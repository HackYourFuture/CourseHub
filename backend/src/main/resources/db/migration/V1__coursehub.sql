-- User account (used for user authentication and authorization)
CREATE TYPE role AS ENUM ('instructor', 'student');

CREATE TABLE user_account
(
    user_id       INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    password_hash VARCHAR(60)  NOT NULL,
    email_address VARCHAR(100) NOT NULL,
    role          role         NOT NULL
);

-- preserve original email case for display, but enforce case-insensitive uniqueness
CREATE UNIQUE INDEX idx_email_unique
    ON user_account (lower(email_address));

-- A student is a user who can enroll in courses.
CREATE TABLE student
(
    student_id      INTEGER PRIMARY KEY,
    first_name      VARCHAR NOT NULL,
    last_name       VARCHAR NOT NULL,
    enrollment_date DATE    NOT NULL DEFAULT current_date,
    FOREIGN KEY (student_id) REFERENCES user_account (user_id) ON DELETE CASCADE
);

-- An instructor is a user who can create and manage courses.
CREATE TABLE instructor
(
    instructor_id INTEGER PRIMARY KEY,
    first_name    VARCHAR NOT NULL,
    last_name     VARCHAR NOT NULL,
    hire_date     DATE    NOT NULL DEFAULT current_date,
    FOREIGN KEY (instructor_id) REFERENCES user_account (user_id) ON DELETE CASCADE
);

-- A course is taught by an instructor and can have many students enrolled.
CREATE TABLE course
(
    course_id       INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name            VARCHAR(255) NOT NULL,
    description     TEXT,
    instructor_id   INTEGER      REFERENCES instructor (instructor_id) ON DELETE SET NULL,
    start_date      DATE,
    end_date        DATE,
    max_enrollments INT CHECK (max_enrollments > 0)
);

-- An enrollment is a student taking a course.
CREATE TABLE enrollment
(
    enrollment_id   INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    student_id      INTEGER REFERENCES student (student_id) ON DELETE CASCADE,
    course_id       INTEGER REFERENCES course (course_id) ON DELETE CASCADE,
    enrollment_date DATE DEFAULT current_date
);
