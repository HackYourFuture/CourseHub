-- User accounts for students. All passwords are the same: 'password123' hashed
INSERT INTO user_account (password_hash, email_address, role)
VALUES ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'alice1@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'bob2@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'carol3@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'david4@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'eva5@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'frank6@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'grace7@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'henry8@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'ivy9@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'jack10@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'kara11@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'liam12@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'mia13@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'noah14@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'olivia15@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'paul16@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'quinn17@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'ruby18@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'sam19@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'tina20@example.com', 'student'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'alan@example.com', 'instructor'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'brenda@example.com', 'instructor'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'charles@example.com', 'instructor'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'diana@example.com', 'instructor'),
       ('$2a$10$JPQ/pnZqC8efUOi3M9ZqReeNDR7IkA1Ry973r.IK020zHSuP4P.KC', 'edward@example.com', 'instructor');


-- Students (with user_id references)
INSERT INTO student (student_id, first_name, last_name, enrollment_date)
VALUES (1, 'Alice', 'Smith', '2023-09-01'),
       (2, 'Bob', 'Johnson', '2023-09-01'),
       (3, 'Carol', 'Lee', '2023-09-01'),
       (4, 'David', 'Kim', '2023-09-01'),
       (5, 'Eva', 'Brown', '2023-09-01'),
       (6, 'Frank', 'White', '2023-09-01'),
       (7, 'Grace', 'Green', '2023-09-01'),
       (8, 'Henry', 'Black', '2023-09-01'),
       (9, 'Ivy', 'Young', '2023-09-01'),
       (10, 'Jack', 'Hall', '2023-09-01'),
       (11, 'Kara', 'King', '2023-09-01'),
       (12, 'Liam', 'Scott', '2023-09-01'),
       (13, 'Mia', 'Adams', '2023-09-01'),
       (14, 'Noah', 'Baker', '2023-09-01'),
       (15, 'Olivia', 'Clark', '2023-09-01'),
       (16, 'Paul', 'Davis', '2023-09-01'),
       (17, 'Quinn', 'Evans', '2023-09-01'),
       (18, 'Ruby', 'Foster', '2023-09-01'),
       (19, 'Sam', 'Gray', '2023-09-01'),
       (20, 'Tina', 'Harris', '2023-09-01');


-- Instructors
INSERT INTO instructor (instructor_id, first_name, last_name, hire_date)
VALUES (21, 'Alan', 'Murray', '2021-04-01'),
       (22, 'Brenda', 'Stone', '2023-09-01'),
       (23, 'Charles', 'Ford', '2020-01-01'),
       (24, 'Diana', 'Wells', '2019-02-01'),
       (25, 'Edward', 'Lane', '2018-03-01');

-- Courses
INSERT INTO course (name, description, instructor_id, start_date, end_date, max_enrollments)
VALUES ('Introduction to Calculus', 'Fundamental concepts of calculus including limits, derivatives, and integrals.',
        21,
        '2024-09-01', '2024-12-15', 30),
       ('General Physics I', 'Mechanics, motion, energy, and basic physical laws.', 22, '2024-09-01', '2024-12-15', 28),
       ('Organic Chemistry', 'Structure, properties, and reactions of organic compounds.', 23, '2024-09-01',
        '2024-12-15', 25),
       ('Human Biology', 'Overview of human anatomy, physiology, and genetics.', 24, '2024-09-01', '2024-12-15', 32),
       ('World History: 1500-Present', 'Major global events and trends from 1500 to the present.', 25, '2024-09-01',
        '2024-12-15', 50),
       ('British Literature', 'Study of classic and modern British literary works.', 21, '2024-09-01', '2024-12-15',
        22),
       ('Fundamentals of Drawing', 'Techniques and principles of drawing for beginners.', 22, '2024-09-01',
        '2024-12-15',
        18),
       ('Music Theory and Composition', 'Basics of music theory and introductory composition.', 23, '2024-09-01',
        '2024-12-15', 20),
       ('Introduction to Programming with Java', 'Core Java programming concepts and hands-on projects.', 24,
        '2024-09-01', '2024-12-15', 35),
       ('Principles of Microeconomics', 'Microeconomic theory, market structures, and consumer behavior.', 25,
        '2024-09-01', '2024-12-15', 18);

-- Enrollments
INSERT INTO enrollment (student_id, course_id, enrollment_date)
VALUES (1, 1, '2023-03-12'),
       (2, 1, '2024-07-08'),
       (3, 2, '2025-01-19'),
       (4, 2, '2023-11-05'),
       (5, 3, '2024-02-22'),
       (6, 3, '2025-09-14'),
       (7, 4, '2023-06-27'),
       (8, 4, '2025-04-03'),
       (9, 5, '2024-10-16'),
       (10, 5, '2023-08-29'),
       (11, 6, '2025-12-01'),
       (12, 6, '2023-05-21'),
       (13, 7, '2024-03-30'),
       (14, 7, '2025-07-25'),
       (15, 8, '2023-09-09'),
       (16, 8, '2024-12-18'),
       (17, 9, '2025-02-07'),
       (18, 9, '2023-04-14'),
       (19, 10, '2024-08-05'),
       (20, 10, '2025-06-11');
