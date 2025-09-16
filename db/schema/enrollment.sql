CREATE TABLE enrollment
(
    id              INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    student_id      INTEGER REFERENCES student (id) ON DELETE CASCADE,
    course_id       INTEGER REFERENCES course (id) ON DELETE CASCADE,
    enrollment_date TIMESTAMP DEFAULT now()
);