USE exam_management_app;

-- students
INSERT INTO Students (Firstname, Lastname)
VALUES ('John', 'Doe'),
       ('Alice', 'Smith'),
       ('Michael', 'Johnson'),
       ('Emma', 'Williams'),
       ('James', 'Brown'),
       ('Olivia', 'Garcia'),
       ('William', 'Martinez'),
       ('Sophia', 'Anderson'),
       ('Daniel', 'Thomas'),
       ('Ava', 'Hernandez'),
       ('Alexander', 'Lopez'),
       ('Mia', 'Gonzalez'),
       ('Ethan', 'Wilson'),
       ('Charlotte', 'Taylor'),
       ('David', 'Moore'),
       ('Abigail', 'Jackson'),
       ('Joseph', 'White'),
       ('Harper', 'Lee'),
       ('Samuel', 'Scott'),
       ('Evelyn', 'Young'),
       ('Gabriel', 'Adams'),
       ('Sofia', 'King'),
       ('Avery', 'Hill'),
       ('Jackson', 'Rivera'),
       ('Amelia', 'Green'),
       ('Sebastian', 'Carter'),
       ('Lily', 'Perez'),
       ('Benjamin', 'Mitchell'),
       ('Chloe', 'Turner'),
       ('Ryan', 'Phillips'),
       ('Zoe', 'Campbell'),
       ('Lincoln', 'Parker'),
       ('Penelope', 'Evans'),
       ('Matthew', 'Edwards'),
       ('Riley', 'Collins'),
       ('Nora', 'Stewart'),
       ('Lucas', 'Morris'),
       ('Grace', 'Nguyen'),
       ('Leo', 'Murphy'),
       ('Aria', 'Cook'),
       ('Liam', 'Gutierrez'),
       ('Harper', 'Reed'),
       ('Ella', 'Bailey'),
       ('Mason', 'Sanchez'),
       ('Hannah', 'Harris'),
       ('Logan', 'Ortiz'),
       ('Layla', 'Ramirez'),
       ('Mateo', 'Torres'),
       ('Elijah', 'Fisher'),
       ('Victoria', 'Price'),
       ('Daniel', 'Bennett'),
       ('Avery', 'Wood'),
       ('Sofia', 'Flores'),
       ('Evelyn', 'Sanders'),
       ('Oliver', 'Nguyen'),
       ('Elena', 'Russell'),
       ('Alexander', 'Watson'),
       ('Madison', 'Diaz'),
       ('Grayson', 'Butler'),
       ('Skylar', 'Long'),
       ('Gabriel', 'Ross'),
       ('Stella', 'Gomez'),
       ('Christopher', 'Howard'),
       ('Addison', 'Cruz'),
       ('Zoey', 'Hill'),
       ('Carter', 'Sullivan'),
       ('Ellie', 'Barnes'),
       ('Hunter', 'Coleman'),
       ('Hazel', 'Alexander'),
       ('Wyatt', 'Jenkins'),
       ('Samantha', 'Ward'),
       ('Luke', 'Baker'),
       ('Leah', 'Patterson'),
       ('Dylan', 'Griffin'),
       ('Maya', 'Gordon'),
       ('Owen', 'Sullivan'),
       ('Natalie', 'Murray'),
       ('Aiden', 'Simmons'),
       ('Audrey', 'Foster'),
       ('Tyler', 'Woods'),
       ('Claire', 'Kim'),
       ('Eli', 'Richardson'),
       ('Bella', 'Perry'),
       ('Lincoln', 'Bryant'),
       ('Paisley', 'King'),
       ('Connor', 'Ferguson'),
       ('Nova', 'Harrison'),
       ('Brooklyn', 'Barnes'),
       ('Jordan', 'Morgan'),
       ('Emma', 'Henderson'),
       ('Xavier', 'Cole'),
       ('Aurora', 'West'),
       ('Aiden', 'Gardner'),
       ('Mila', 'Knight'),
       ('Katherine', 'Coleman'),
       ('Zachary', 'Fletcher'),
       ('Sarah', 'Lynch'),
       ('Evan', 'Bishop'),
       ('Naomi', 'Hayes'),
       ('John', 'Reed');

-- studies
INSERT INTO studies (StudentId, MajorName)
VALUES (1, 'Bachelor Informatik'),
       (2, 'KombiBachelor Elektrotechnik'),
       (3, 'Bachelor Elektrotechnik'),
       (4, 'Bachelor Wirtschaftswissenschaft'),
       (5, 'Bachelor Informatik'),
       (5, 'Lehramt'),
       (6, 'Bachelor Informatik'),
       (7, 'KombiBachelor Elektrotechnik'),
       (8, 'Bachelor Elektrotechnik'),
       (9, 'Bachelor Wirtschaftswissenschaft'),
       (10, 'KombiBachelor Elektrotechnik'),
       (10, 'Lehramt'),
       (11, 'Bachelor Informatik'),
       (12, 'KombiBachelor Elektrotechnik'),
       (13, 'Bachelor Elektrotechnik'),
       (14, 'Bachelor Wirtschaftswissenschaft'),
       (15, 'Bachelor Elektrotechnik'),
       (15, 'Lehramt'),
       (16, 'Bachelor Informatik'),
       (17, 'KombiBachelor Elektrotechnik'),
       (18, 'Bachelor Elektrotechnik'),
       (19, 'Bachelor Wirtschaftswissenschaft'),
       (20, 'Bachelor Wirtschaftswissenschaft'),
       (20, 'Lehramt'),
       (21, 'Bachelor Informatik'),
       (22, 'KombiBachelor Elektrotechnik'),
       (23, 'Bachelor Elektrotechnik'),
       (24, 'Bachelor Wirtschaftswissenschaft'),
       (25, 'Bachelor Informatik'),
       (25, 'Lehramt'),
       (26, 'Bachelor Informatik'),
       (27, 'KombiBachelor Elektrotechnik'),
       (28, 'Bachelor Elektrotechnik'),
       (29, 'Bachelor Wirtschaftswissenschaft'),
       (30, 'Bachelor Wirtschaftswissenschaft'),
       (30, 'Lehramt'),
       (31, 'Bachelor Informatik'),
       (32, 'KombiBachelor Elektrotechnik'),
       (33, 'Bachelor Elektrotechnik'),
       (34, 'Bachelor Wirtschaftswissenschaft'),
       (35, 'Bachelor Elektrotechnik'),
       (35, 'Lehramt'),
       (36, 'Bachelor Informatik'),
       (37, 'KombiBachelor Elektrotechnik'),
       (38, 'Bachelor Elektrotechnik'),
       (39, 'Bachelor Wirtschaftswissenschaft'),
       (40, 'Bachelor Wirtschaftswissenschaft'),
       (40, 'Lehramt'),
       (41, 'Bachelor Informatik'),
       (42, 'KombiBachelor Elektrotechnik'),
       (43, 'Bachelor Elektrotechnik'),
       (44, 'Bachelor Wirtschaftswissenschaft'),
       (45, 'Bachelor Informatik'),
       (45, 'Lehramt'),
       (46, 'Bachelor Informatik'),
       (47, 'KombiBachelor Elektrotechnik'),
       (48, 'Bachelor Elektrotechnik'),
       (49, 'Bachelor Wirtschaftswissenschaft'),
       (50, 'KombiBachelor Elektrotechnik'),
       (50, 'Lehramt'),
       (51, 'Bachelor Informatik'),
       (52, 'KombiBachelor Elektrotechnik'),
       (53, 'Bachelor Elektrotechnik'),
       (54, 'Bachelor Wirtschaftswissenschaft'),
       (55, 'Bachelor Elektrotechnik'),
       (55, 'Lehramt'),
       (56, 'Bachelor Informatik'),
       (57, 'KombiBachelor Elektrotechnik'),
       (58, 'Bachelor Elektrotechnik'),
       (59, 'Bachelor Wirtschaftswissenschaft'),
       (60, 'Bachelor Wirtschaftswissenschaft'),
       (60, 'Lehramt'),
       (61, 'Bachelor Informatik'),
       (62, 'KombiBachelor Elektrotechnik'),
       (63, 'Bachelor Elektrotechnik'),
       (64, 'Bachelor Wirtschaftswissenschaft'),
       (65, 'Bachelor Informatik'),
       (65, 'Lehramt'),
       (66, 'Bachelor Informatik'),
       (67, 'KombiBachelor Elektrotechnik'),
       (68, 'Bachelor Elektrotechnik'),
       (69, 'Bachelor Wirtschaftswissenschaft'),
       (70, 'KombiBachelor Elektrotechnik'),
       (70, 'Lehramt'),
       (71, 'Bachelor Informatik'),
       (72, 'KombiBachelor Elektrotechnik'),
       (73, 'Bachelor Elektrotechnik'),
       (74, 'Bachelor Wirtschaftswissenschaft'),
       (75, 'Bachelor Elektrotechnik'),
       (75, 'Lehramt'),
       (76, 'Bachelor Informatik'),
       (77, 'KombiBachelor Elektrotechnik'),
       (78, 'Bachelor Elektrotechnik'),
       (79, 'Bachelor Wirtschaftswissenschaft'),
       (80, 'Bachelor Wirtschaftswissenschaft'),
       (80, 'Lehramt'),
       (81, 'Bachelor Informatik'),
       (82, 'KombiBachelor Elektrotechnik'),
       (83, 'Bachelor Elektrotechnik'),
       (84, 'Bachelor Wirtschaftswissenschaft'),
       (85, 'Bachelor Informatik'),
       (85, 'Lehramt'),
       (86, 'Bachelor Informatik'),
       (87, 'KombiBachelor Elektrotechnik'),
       (88, 'Bachelor Elektrotechnik'),
       (89, 'Bachelor Wirtschaftswissenschaft'),
       (90, 'KombiBachelor Elektrotechnik'),
       (90, 'Lehramt'),
       (91, 'Bachelor Informatik'),
       (92, 'KombiBachelor Elektrotechnik'),
       (93, 'Bachelor Elektrotechnik'),
       (94, 'Bachelor Wirtschaftswissenschaft'),
       (95, 'Bachelor Elektrotechnik'),
       (95, 'Lehramt'),
       (96, 'Bachelor Informatik'),
       (97, 'KombiBachelor Elektrotechnik'),
       (98, 'Bachelor Elektrotechnik'),
       (99, 'Bachelor Wirtschaftswissenschaft'),
       (100, 'Bachelor Wirtschaftswissenschaft'),
       (100, 'Lehramt');