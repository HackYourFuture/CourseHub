CREATE TABLE course
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  TEXT,
    instructor   BIGINT REFERENCES instructor (id) ON DELETE SET NULL,
    start_date   DATE,
    end_date     DATE,
    max_students INT CHECK (max_students > 0)
);