CREATE TABLE instructor
(
    instructor_id INTEGER PRIMARY KEY,
    first_name    VARCHAR NOT NULL,
    last_name     VARCHAR NOT NULL,
    hire_date     DATE    NOT NULL DEFAULT current_date,
    FOREIGN KEY (instructor_id) REFERENCES user_account (user_id) ON DELETE CASCADE
);
