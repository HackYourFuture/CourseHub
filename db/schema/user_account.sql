-- User account (used for user authentication and authorization)
CREATE TYPE role AS ENUM ('instructor', 'student');

CREATE TABLE user_account
(
    user_id       BIGSERIAL PRIMARY KEY,
    password_hash VARCHAR(60)         NOT NULL,
    email_address VARCHAR(100) UNIQUE NOT NULL,
    role          role                NOT NULL
);