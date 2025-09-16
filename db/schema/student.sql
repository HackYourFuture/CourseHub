CREATE TABLE student (
    id BIGSERIAL PRIMARY KEY,
    student_number INTEGER NOT NULL UNIQUE,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    email VARCHAR(255)
);