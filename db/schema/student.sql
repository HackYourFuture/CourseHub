CREATE TABLE student
(
    student_id      INTEGER PRIMARY KEY,
    first_name      VARCHAR NOT NULL,
    last_name       VARCHAR NOT NULL,
    enrollment_date DATE    NOT NULL DEFAULT current_date,
    FOREIGN KEY (student_id) REFERENCES user_account (user_id) ON DELETE CASCADE
);
