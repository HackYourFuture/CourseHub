CREATE TABLE course
(
    course_id     INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name          VARCHAR(255) NOT NULL,
    description   TEXT,
    instructor_id BIGINT       REFERENCES instructor (instructor_id) ON DELETE SET NULL,
    start_date    DATE,
    end_date      DATE,
    max_enrollments  INT CHECK (max_enrollments > 0)
);
