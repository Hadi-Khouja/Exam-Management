USE exam_management_app;

-- Lecturers inserts
INSERT INTO Lecturers (Firstname, Lastname, AcademicGrade, Email, ResearchGroup, TelephoneNumber, Room)
VALUES ('John', 'Doe', 'Prof', 'johndoe@example.com', 'Computer Science', '1234567890', 'Room 101'),
       ('Jane', 'Smith', 'Dr', 'janesmith@example.com', 'Electrical Engineering', '9876543210', 'Room 202'),
       ('Alice', 'Johnson', 'Dr', 'alicejohnson@example.com', 'Mechanical Engineering', '5551234567', 'Room 303'),
       ('Michael', 'Williams', 'Prof', 'michaelwilliams@example.com', 'Physics', '9998887776', 'Room 404');

-- Users table inserts
INSERT INTO users (username, password, LecturerId)
VALUES ('john', '1b39da52b31d0344e29485d205e996aa45a677bdb93744b37236488914384c9e', 1),
       ('JaneSmith', '1b39da52b31d0344e29485d205e996aa45a677bdb93744b37236488914384c9e', 2),
       ('admin', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 3),
       ('MichaelWilliams', '1b39da52b31d0344e29485d205e996aa45a677bdb93744b37236488914384c9e', 4);
