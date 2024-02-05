USE exam_management_app;

INSERT INTO Exams (Name, Date, MaxPoints, PassingLimit, BonusType, AssociatedCourse)
VALUES ('WS 17/18', '2023-01-15 09:00:00', 100.0, 50.0, 'ALWAYSACTIVE', 1),
       ('SS 18', '2023-02-20 10:30:00', 90.0, 40.0, 'OFF', 1),
       ('WS 18/19', '2023-03-25 14:00:00', 120.0, 60.0, 'ALWAYSACTIVE', 1),
       ('SS 19', '2023-04-10 11:00:00', 110.0, 55.0, 'STANDARD', 2),
       ('WS 19/20', '2023-05-05 13:45:00', 95.0, 45.0, 'OFF', 2),
       ('SS 20', '2023-06-20 09:30:00', 105.0, 52.0, 'ALWAYSACTIVE', 2),
       ('WS 20/21', '2023-07-15 14:20:00', 115.0, 58.0, 'STANDARD', 3),
       ('SS 21', '2023-08-10 10:00:00', 98.0, 48.0, 'OFF', 3),
       ('WS 21/22', '2023-09-05 12:30:00', 125.0, 65.0, 'STANDARD', 3),
       ('SS 22', '2023-10-18 11:45:00', 88.0, 38.0, 'OFF', 4),
       ('SS 10', '2023-11-20 15:00:00', 130.0, 70.0, 'ALWAYSACTIVE', 4),
       ('WS 10/11', '2023-12-22 08:45:00', 102.0, 50.0, 'OFF', 4),
       ('SS 11', '2024-01-10 10:15:00', 97.0, 47.0, 'STANDARD', 5),
       ('WS 11/12', '2024-02-25 12:00:00', 108.0, 54.0, 'OFF', 5),
       ('SS 12', '2024-03-18 09:30:00', 112.0, 57.0, 'STANDARD', 5),
       ('WS 12/13', '2024-04-05 14:20:00', 93.0, 43.0, 'OFF', 6),
       ('SS 13', '2024-05-20 11:00:00', 118.0, 62.0, 'STANDARD', 6),
       ('WS 13/14', '2024-06-15 10:45:00', 99.0, 49.0, 'STANDARD', 6),
       ('SS 14', '2024-07-10 09:30:00', 104.0, 51.0, 'OFF', 7),
       ('WS 14/15', '2024-08-25 13:00:00', 122.0, 64.0, 'ALWAYSACTIVE', 7),
       ('SS 15', '2024-09-18 15:20:00', 96.0, 46.0, 'STANDARD', 7),
       ('WS 15/16', '2024-10-05 10:30:00', 107.0, 53.0, 'OFF', 8),
       ('SS 16', '2024-11-20 12:15:00', 131.0, 71.0, 'ALWAYSACTIVE', 8),
       ('WS 16/17', '2024-12-22 09:00:00', 103.0, 51.0, 'OFF', 8),
       ('SS 17', '2025-01-15 10:30:00', 94.0, 44.0, 'STANDARD', 9),
       ('WS 17/18', '2025-02-20 14:00:00', 109.0, 55.0, 'OFF', 9),
       ('SS 18', '2025-03-25 11:00:00', 111.0, 56.0, 'STANDARD', 9),
       ('WS 18/19', '2025-04-10 08:30:00', 92.0, 42.0, 'ALWAYSACTIVE', 10),
       ('SS 19', '2025-05-05 12:45:00', 119.0, 63.0, 'OFF', 10),
       ('WS 19/20', '2025-06-20 10:00:00', 100.0, 50.0, 'STANDARD', 10),
       ('SS 20', '2025-07-15 09:20:00', 106.0, 52.0, 'OFF', 11),
       ('WS 20/21', '2025-08-10 13:45:00', 123.0, 65.0, 'ALWAYSACTIVE', 11),
       ('SS 21', '2025-09-05 12:00:00', 95.0, 45.0, 'STANDARD', 11),
       ('WS 21/22', '2025-10-18 10:45:00', 87.0, 37.0, 'OFF', 12),
       ('SS 22', '2025-11-20 14:00:00', 132.0, 72.0, 'ALWAYSACTIVE', 12),
       ('WS 22/23', '2025-12-22 11:30:00', 101.0, 49.0, 'OFF', 12);

-- Tasks
INSERT INTO Tasks (TaskNumber, Question, Points, ExamId)
SELECT 1, 'Question', MaxPoints / 10, Id
FROM Exams;

INSERT INTO Tasks (TaskNumber, Question, Points, ExamId)
SELECT 2, 'Question', MaxPoints / 10, Id
FROM Exams;

INSERT INTO Tasks (TaskNumber, Question, Points, ExamId)
SELECT 3, 'Question', MaxPoints / 10, Id
FROM Exams;

INSERT INTO Tasks (TaskNumber, Question, Points, ExamId)
SELECT 4, 'Question', MaxPoints / 10, Id
FROM Exams;

INSERT INTO Tasks (TaskNumber, Question, Points, ExamId)
SELECT 5, 'Question', MaxPoints / 10, Id
FROM Exams;

INSERT INTO Tasks (TaskNumber, Question, Points, ExamId)
SELECT 6, 'Question', MaxPoints / 10, Id
FROM Exams;

INSERT INTO Tasks (TaskNumber, Question, Points, ExamId)
SELECT 7, 'Question', MaxPoints / 10, Id
FROM Exams;

INSERT INTO Tasks (TaskNumber, Question, Points, ExamId)
SELECT 8, 'Question', MaxPoints / 10, Id
FROM Exams;

INSERT INTO Tasks (TaskNumber, Question, Points, ExamId)
SELECT 9, 'Question', MaxPoints / 10, Id
FROM Exams;

INSERT INTO Tasks (TaskNumber, Question, Points, ExamId)
SELECT 10, 'Question', MaxPoints / 10, Id
FROM Exams;

-- ownsExam
INSERT INTO ownsExam(LecturerId, ExamId)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (2, 10),
       (2, 11),
       (2, 12),
       (2, 13),
       (2, 14),
       (2, 15),
       (2, 16),
       (2, 17),
       (2, 18),
       (2, 19),
       (3, 20),
       (3, 21),
       (3, 22),
       (3, 23),
       (3, 24),
       (3, 25),
       (3, 26),
       (3, 27),
       (3, 28),
       (3, 29),
       (4, 30),
       (4, 31),
       (4, 32),
       (4, 33),
       (4, 34),
       (4, 35),
       (4, 36);