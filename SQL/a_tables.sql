CREATE SCHEMA IF NOT EXISTS exam_management_app;
USE exam_management_app;

CREATE TABLE IF NOT EXISTS Lecturers
(
    Id              int                         not NULL auto_increment,
    Firstname       CHAR(100)                   not null,
    Lastname        CHAR(100)                   not null,
    AcademicGrade   ENUM ('None', 'Dr', 'Prof') not null,
    Email           CHAR(100)                   not null,
    ResearchGroup   CHAR(100)                   not null,
    TelephoneNumber CHAR(10)                    not null,
    Room            CHAR(50)                    not null,
    constraint Lectures_pk
        primary key (Id)
);

CREATE TABLE IF NOT EXISTS users
(
    Id         int       not NULL auto_increment,
    Username   CHAR(100) not null,
    Password   CHAR(100) not null,
    LecturerId int       not null,
    constraint users_pk
        primary key (Id),
    foreign key (LecturerId) references Lecturers (Id)
);

CREATE TABLE IF Not EXISTS ExamOfficeEmployees
(
    Id              int       not NULL auto_increment,
    Room            CHAR(10)  not Null,
    TelephoneNumber CHAR(10)  not Null,
    FirstName       CHAR(100) not Null,
    LastName        CHAR(100) not Null,
    Email           CHAR(100) not NULL,
    constraint ExamOfficeEmployees_pk
        primary key (Id)
);

CREATE TABLE IF Not EXISTS Majors
(
    Name               CHAR(100) not Null,
    ExamOfficeEmployee int,
    constraint Majors_pk
        primary key (Name),
    foreign key (ExamOfficeEmployee) references ExamOfficeEmployees (Id) ON DELETE CASCADE
);

CREATE TABLE IF Not EXISTS Students
(
    MatriculationNumber int       not NULL auto_increment,
    Firstname           CHAR(100) not null,
    Lastname            CHAR(100) not null,
    constraint Students_pk
        primary key (MatriculationNumber)
);

CREATE TABLE IF NOT EXISTS studies
(
    StudentId int       not null,
    MajorName CHAR(100) not null,
    constraint Students_pk
        primary key (StudentId, MajorName),
    foreign key (StudentId) references Students (MatriculationNumber) ON DELETE CASCADE ON UPDATE CASCADE,
    foreign key (MajorName) references Majors (Name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF Not EXISTS Courses
(
    Id         int       not NULL auto_increment,
    Name       CHAR(100) not null,
    LecturerId int       not null,
    constraint Courses_pk
        primary key (Id),
    foreign key (LecturerId) references Lecturers (Id) ON DELETE CASCADE
);

CREATE TABLE IF Not EXISTS Exams
(
    Id               int                                      not NULL auto_increment,
    Name             CHAR(100)                                not NULL,
    Date             datetime                                 not null,
    MaxPoints        double                                   not null,
    PassingLimit     double                                   not null,
    BonusType        enum ('OFF', 'STANDARD' ,'ALWAYSACTIVE') not null,
    AssociatedCourse int,
    constraint Exams_pk
        primary key (Id),
    foreign key (AssociatedCourse) references Courses (Id) ON DELETE CASCADE
);

CREATE TABLE IF Not EXISTS Tasks
(
    TaskNumber int       not null,
    Question   CHAR(100) not null,
    Points     double    not null,
    ExamId     int       not null,
    constraint Tasks_pk
        primary key (TaskNumber, ExamId),
    foreign key (ExamId) references Exams (Id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS EarnedPoints
(
    TaskNumber int,
    ExamId     int,
    StudentId  int,
    Points     double,
    constraint EarnedPoints_pk
        primary key (TaskNumber, ExamId, StudentId),
    foreign key (TaskNumber) references Tasks (TaskNumber) ON DELETE CASCADE ON UPDATE CASCADE,
    foreign key (ExamId) references Exams (Id) ON DELETE CASCADE,
    foreign key (StudentId) references Students (MatriculationNumber) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF Not EXISTS Grades
(
    StudentId int not null,
    ExamId    int not null,
    Points    double,
    Grade     CHAR(10),
    Passed    boolean,
    constraint Grades_pk
        primary key (StudentId, ExamId),
    foreign key (StudentId) references Students (MatriculationNumber) ON DELETE CASCADE ON UPDATE CASCADE,
    foreign key (ExamId) references Exams (Id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS GradeLimits
(
    Note     CHAR(10) not null,
    MinGrade int,
    MaxGrade int,
    ExamId   int,
    foreign key (ExamId) references Exams (Id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS participates
(
    StudentId int not null,
    ExamId    int not null,
    foreign key (StudentId) references Students (MatriculationNumber) ON DELETE CASCADE ON UPDATE CASCADE,
    foreign key (ExamId) references Exams (Id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ownsExam
(
    LecturerId int not null,
    ExamId     int not null,
    foreign key (LecturerId) references Lecturers (Id) ON DELETE CASCADE,
    foreign key (ExamId) references Exams (Id) ON DELETE CASCADE
);