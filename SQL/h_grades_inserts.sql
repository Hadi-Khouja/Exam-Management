USE exam_management_app;

INSERT INTO Grades (StudentId, ExamId, Points, Grade, Passed)
SELECT MatriculationNumber AS StudentId, 1, 80, '2.0', TRUE
FROM Students
WHERE MatriculationNumber BETWEEN 1 AND 26;

INSERT INTO Grades (StudentId, ExamId, Points, Grade, Passed)
SELECT MatriculationNumber AS StudentId, 3, 50, '4.0', TRUE
FROM Students;

INSERT INTO Grades (StudentId, ExamId, Points, Grade, Passed)
SELECT MatriculationNumber AS StudentId, 6, 40, '5.0', FALSE
FROM Students;