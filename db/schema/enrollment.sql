CREATE TABLE enrollment
(
    enrollment_id   INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    student_id      INTEGER REFERENCES student (student_id) ON DELETE CASCADE,
    course_id       INTEGER REFERENCES course (course_id) ON DELETE CASCADE,
    enrollment_date TIMESTAMP DEFAULT now()
);