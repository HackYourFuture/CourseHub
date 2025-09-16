-- Students
INSERT INTO student (first_name, last_name, email)
VALUES ('Alice', 'Smith', 'alice1@example.com'),
       ('Bob', 'Johnson', 'bob2@example.com'),
       ('Carol', 'Lee', 'carol3@example.com'),
       ('David', 'Kim', 'david4@example.com'),
       ('Eva', 'Brown', 'eva5@example.com'),
       ('Frank', 'White', 'frank6@example.com'),
       ('Grace', 'Green', 'grace7@example.com'),
       ('Henry', 'Black', 'henry8@example.com'),
       ('Ivy', 'Young', 'ivy9@example.com'),
       ('Jack', 'Hall', 'jack10@example.com'),
       ('Kara', 'King', 'kara11@example.com'),
       ('Liam', 'Scott', 'liam12@example.com'),
       ('Mia', 'Adams', 'mia13@example.com'),
       ('Noah', 'Baker', 'noah14@example.com'),
       ('Olivia', 'Clark', 'olivia15@example.com'),
       ('Paul', 'Davis', 'paul16@example.com'),
       ('Quinn', 'Evans', 'quinn17@example.com'),
       ('Ruby', 'Foster', 'ruby18@example.com'),
       ('Sam', 'Gray', 'sam19@example.com'),
       ('Tina', 'Harris', 'tina20@example.com');

-- Instructors
INSERT INTO instructor (first_name, last_name, email)
VALUES ('Alan', 'Murray', 'alan@example.com'),
       ('Brenda', 'Stone', 'brenda@example.com'),
       ('Charles', 'Ford', 'charles@example.com'),
       ('Diana', 'Wells', 'diana@example.com'),
       ('Edward', 'Lane', 'edward@example.com');

-- Courses
INSERT INTO course (name, description, instructor_id, start_date, end_date, max_students)
VALUES ('Introduction to Calculus', 'Fundamental concepts of calculus including limits, derivatives, and integrals.', 1,
        '2024-09-01', '2024-12-15', 30),
       ('General Physics I', 'Mechanics, motion, energy, and basic physical laws.', 2, '2024-09-01', '2024-12-15', 28),
       ('Organic Chemistry', 'Structure, properties, and reactions of organic compounds.', 3, '2024-09-01',
        '2024-12-15', 25),
       ('Human Biology', 'Overview of human anatomy, physiology, and genetics.', 4, '2024-09-01', '2024-12-15', 32),
       ('World History: 1500-Present', 'Major global events and trends from 1500 to the present.', 5, '2024-09-01',
        '2024-12-15', 50),
       ('British Literature', 'Study of classic and modern British literary works.', 1, '2024-09-01', '2024-12-15', 22),
       ('Fundamentals of Drawing', 'Techniques and principles of drawing for beginners.', 2, '2024-09-01', '2024-12-15',
        18),
       ('Music Theory and Composition', 'Basics of music theory and introductory composition.', 3, '2024-09-01',
        '2024-12-15', 20),
       ('Introduction to Programming with Java', 'Core Java programming concepts and hands-on projects.', 4,
        '2024-09-01', '2024-12-15', 35),
       ('Principles of Microeconomics', 'Microeconomic theory, market structures, and consumer behavior.', 5,
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
