USE exam_management_app;

-- ExamOffice inserts
INSERT INTO ExamOfficeEmployees (Room, TelephoneNumber, FirstName, LastName, Email)
VALUES ('Undefined', 'Undefined', 'Undefined', 'Undefined', 'Undefined'),
       ('101', '1234567890', 'Alice', 'Johnson', 'alice.johnson@example.com'),
       ('202', '9876543210', 'Bob', 'Smith', 'bob.smith@example.com'),
       ('303', '5551234567', 'Charlie', 'Brown', 'charlie.brown@example.com'),
       ('404', '6669245337', 'Dove', 'Baum', 'dove.baum@example.com'),
       ('505', '7771234567', 'Christian', 'Luck', 'christian.luck@example.com');

-- Majors
INSERT INTO Majors(name, examofficeemployee)
VALUES ('Bachelor Informatik', 2),
       ('KombiBachelor Elektrotechnik', 3),
       ('Bachelor Elektrotechnik', 4),
       ('Bachelor Wirtschaftswissenschaft', 5),
       ('Lehramt', 6);