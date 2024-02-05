package com.management.app.managemenetapp;

import com.management.app.types.*;
import com.management.app.types.enums.AcademicGrade;
import com.management.app.types.enums.BonusType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;

/**
 * The DatabaseManager class is responsible for managing database connections
 * and performing various database operations related to the exam management application.
 */
public class DatabaseManager {
    //region Setup
    private Connection connection;
    private static DatabaseManager instance;

    /**
     * Private constructor to ensure a single instance of DatabaseManager using the Singleton pattern.
     * Establishes a connection to the MySQL database upon instantiation.
     */
    private DatabaseManager() {
        String password = "admin";
        String user = "root";
        String databaseUrl = "jdbc:mysql://localhost:3306/exam_management_app";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(databaseUrl, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    /**
     * Retrieves the singleton instance of DatabaseManager.
     * If an instance does not exist, it creates one and returns it.
     *
     * @return The DatabaseManager instance.
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    //endregion

    //region fetch data

    /**
     * Retrieves a user from the 'users' table based on the provided username.
     *
     * @param username The username of the user to be retrieved.
     * @return A User object representing the retrieved user, or null if the user is not found.
     */
    public User getUser(String username) {
        try {
            String query = "SELECT * FROM users JOIN (SELECT ID AS LecturerId, Firstname, Lastname, AcademicGrade, Email, ResearchGroup, TelephoneNumber, Room FROM Lecturers) AS l USING (LecturerId) WHERE Username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Check if a user with the provided username exists
            if (resultSet.next()) {
                String firstname = resultSet.getString("Firstname");
                String lastname = resultSet.getString("Lastname");
                String email = resultSet.getString("Email");
                String telephoneNumber = resultSet.getString("TelephoneNumber");
                String room = resultSet.getString("Room");
                String userId = resultSet.getString("Id");
                String user = resultSet.getString("Username");
                String password = resultSet.getString("Password");

                return new User(firstname, lastname, email, telephoneNumber, room, userId, user, password);
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return null;
    }

    /**
     * Checks if the provided password matches the stored password for a given username.
     *
     * @param username      The username of the user.
     * @param inputPassword The plaintext password provided by the user.
     * @return True if the passwords match, false otherwise.
     */
    public boolean checkPassword(String username, String inputPassword) {
        try {
            String encryptedInputPassword = Encrypter.getInstance().hashPassword(inputPassword);
            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                return encryptedInputPassword.equals(storedPassword);
            } else {
                return false;
            }
        } catch (NoSuchAlgorithmException | SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return false;
    }

    /**
     * Retrieves a Lecturer object from the 'users' and 'Lecturers' tables based on the provided user ID.
     *
     * @param userid The user ID of the lecturer to be retrieved
     * @return A Lecturer object representing the retrieved lecturer, or null if not found.
     */
    public Lecturer getLecturer(String userid) {
        try {
            String query = "SELECT * FROM users JOIN (SELECT ID AS LecturerId, Firstname, Lastname, AcademicGrade, Email, ResearchGroup, TelephoneNumber, Room FROM Lecturers) AS l USING (LecturerId) WHERE Id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String firstname = resultSet.getString("Firstname");
                String lastname = resultSet.getString("Lastname");
                String email = resultSet.getString("Email");
                String telephoneNumber = resultSet.getString("TelephoneNumber");
                String room = resultSet.getString("Room");
                String userId = resultSet.getString("Id");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                AcademicGrade academicGrade = AcademicGrade.valueOf(resultSet.getString("AcademicGrade"));
                String researchGroup = resultSet.getString("ResearchGroup");

                return new Lecturer(firstname, lastname, email, telephoneNumber, room, userId, username, password, academicGrade, researchGroup);
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return null;
    }

    /**
     * Retrieves a list of Student objects by joining the 'Students', 'Majors', and 'studies' tables.
     *
     * @return An ArrayList of Student objects representing the students in the database.
     */
    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();

        try {
            // SQL query to select student details by joining the 'Students', 'Majors', and 'studies' tables
            String query = "SELECT * FROM Students JOIN (SELECT * FROM Majors JOIN (SELECT StudentId AS MatriculationNumber, MajorName AS Name FROM studies) AS s using (Name)) AS v USING (MatriculationNumber) ORDER BY MatriculationNumber;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process each row in the result set
            while (resultSet.next()) {
                String matriculationNumber = resultSet.getString("MatriculationNumber");
                Major major = new Major(resultSet.getString("Name"));

                if (!students.isEmpty() && students.getLast().getMatriculationNumber().equals(matriculationNumber)) {
                    students.getLast().addMajor(major);
                } else {
                    Student student = new Student(resultSet.getString("Firstname"), resultSet.getString("Lastname"), matriculationNumber, major);
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return students;
    }

    /**
     * Retrieves a list of Grades for a specific student by joining the 'Grades', 'Exams', and 'Courses' tables.
     *
     * @param student The Student object for which grades are to be retrieved.
     * @return An ObservableList of Grades representing the grades for the student.
     */
    public ObservableList<Grades> getGrades(Student student) {
        // Create an ObservableList to store the retrieved grades
        ObservableList<Grades> grades = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM Grades JOIN (SELECT Exams.Id AS ExamId, c.Name FROM Exams JOIN (SELECT Id AS AssociatedCourse, Name FROM Courses) AS c USING (AssociatedCourse)) AS e USING (ExamId) WHERE StudentId = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getMatriculationNumber());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String courseName = resultSet.getString("Name");
                double points = resultSet.getDouble("Points");
                String grade = resultSet.getString("Grade");
                Boolean passed = resultSet.getBoolean("Passed");

                grades.add(new Grades(courseName, points, grade, passed));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return grades;
    }

    public ArrayList<Grades> getGradesForPdf(Exam exam) {
        ArrayList<Grades> grades = new ArrayList<>();

        try {
            String query = """
                    SELECT *
                    FROM Students
                             JOIN (SELECT StudentId AS MatriculationNumber, Points, Grade, Passed
                                   FROM Grades
                                   WHERE ExamId = ?) AS g
                                  USING (MatriculationNumber);""";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, exam.getExamId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String courseName = exam.getAssociatedCourse().getName();
                double points = resultSet.getDouble("Points");
                String grade = resultSet.getString("Grade");
                Boolean passed = resultSet.getBoolean("Passed");

                String studentId = resultSet.getString("MatriculationNumber");
                String firstname = resultSet.getString("Firstname");
                String lastname = resultSet.getString("Lastname");
                Student student = new Student(firstname, lastname, studentId);

                grades.add(new Grades(student, courseName, points, grade, passed));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return grades;
    }

    public ArrayList<Grades> getGradesForExamOffice(Exam exam, Major major) {
        ArrayList<Grades> grades = new ArrayList<>();

        try {
            String query = """
                    SELECT *
                    FROM Students
                             JOIN
                         (SELECT StudentId AS MatriculationNumber, MajorName, Points, Grade, Passed
                          FROM studies
                                   JOIN (SELECT StudentId, Points, Grade, Passed FROM Grades WHERE ExamId = ?) AS g USING (StudentId)
                          WHERE MajorName = ?) AS s USING (MatriculationNumber);""";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, exam.getExamId());
            preparedStatement.setString(2, major.getName());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String courseName = exam.getAssociatedCourse().getName();
                double points = resultSet.getDouble("Points");
                String grade = resultSet.getString("Grade");
                Boolean passed = resultSet.getBoolean("Passed");

                String studentId = resultSet.getString("MatriculationNumber");
                String firstname = resultSet.getString("Firstname");
                String lastname = resultSet.getString("Lastname");
                Student student = new Student(firstname, lastname, studentId);

                grades.add(new Grades(student, courseName, points, grade, passed));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return grades;
    }

    public ObservableList<Major> getParticipatingMajors(Exam exam) {
        ObservableList<Major> majors = FXCollections.observableArrayList();

        try {
            String query = """
                    SELECT *
                    FROM ExamOfficeEmployees
                             JOIN (SELECT Name, ExamOfficeEmployee AS Id
                                   FROM Majors
                                            Join (SELECT MajorName AS Name
                                                  FROM participates
                                                           JOIN (SELECT * FROM studies) AS s USING (StudentId)
                                                  WHERE ExamId = ?
                                                  GROUP BY MajorName) AS p USING (Name)) AS m USING (Id);""";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, exam.getExamId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String employeeFirstName = resultSet.getString("FirstName");
                String employeeLastName = resultSet.getString("LastName");
                String employeeEmail = resultSet.getString("Email");
                String employeePhone = resultSet.getString("TelephoneNumber");
                String employeeRoom = resultSet.getString("Room");
                String employeeId = resultSet.getString("Id");
                String majorName = resultSet.getString("Name");

                ExamOfficeEmployee examOfficeEmployee = new ExamOfficeEmployee(employeeFirstName, employeeLastName, employeeEmail, employeePhone, employeeRoom, employeeId);
                majors.add(new Major(majorName, examOfficeEmployee));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return majors;
    }

    /**
     * Retrieves a list of Major objects by joining the 'Majors' and 'ExamOfficeEmployees' tables.
     *
     * @return An ArrayList of Major objects representing the majors in the database.
     */
    public ObservableList<Major> getMajors() {
        // Create an ArrayList to store the retrieved majors
        ObservableList<Major> majors = FXCollections.observableArrayList();

        try {
            String query = """ 
                    SELECT *
                    FROM Majors
                             JOIN (SELECT Id AS ExamOfficeEmployee, Room, TelephoneNumber, FirstName, LastName, Email
                                   FROM ExamOfficeEmployees) AS e USING (ExamOfficeEmployee);""";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String employeeFirstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String email = resultSet.getString("Email");
                String telephoneNumber = resultSet.getString("TelephoneNumber");
                String room = resultSet.getString("Room");
                String employeeId = resultSet.getString("ExamOfficeEmployee");

                ExamOfficeEmployee examOfficeEmployee = new ExamOfficeEmployee(employeeFirstName, lastName, email, telephoneNumber, room, employeeId);
                majors.add(new Major(resultSet.getString("Name"), examOfficeEmployee));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return majors;
    }

    /**
     * Retrieves a list of Course objects for a specific lecturer user by joining the 'Courses' table.
     *
     * @param user The User object representing the lecturer for whom courses are to be retrieved.
     * @return An ArrayList of Course objects representing the courses assigned to the lecturer.
     */
    public ArrayList<Course> getCourses(User user) {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            String query = "SELECT * FROM Courses WHERE LecturerId = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Lecturer owner = getLecturer(user.getId());
                Course course = new Course(resultSet.getString("Id"), resultSet.getString("Name"), owner);
                List<Exam> exams = getExams(course);
                course.addExams(exams);
                courses.add(course);
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return courses;
    }

    /**
     * Retrieves a list of Exam objects associated with a specific course by joining the 'Exams' table.
     *
     * @param associatedCourse The Course object for which exams are to be retrieved.
     * @return An ArrayList of Exam objects representing the exams associated with the course.
     */
    public ArrayList<Exam> getExams(Course associatedCourse) {
        ArrayList<Exam> exams = new ArrayList<>();

        try {
            String query = "SELECT * FROM Exams WHERE AssociatedCourse = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, associatedCourse.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("Id");
                String name = resultSet.getString("Name");
                LocalDate date = resultSet.getDate("Date").toLocalDate();
                double maxPoints = resultSet.getDouble("MaxPoints");
                double passingLimit = resultSet.getDouble("PassingLimit");
                String bonusType = resultSet.getString("BonusType");
                BonusType bonus = BonusType.valueOf(bonusType.toUpperCase());
                ArrayList<Task> tasks = getTasks(id);
                ArrayList<Student> participatingStudents = getParticipatingStudents(id);
                ArrayList<GradeLimits> gradeLimits = getGradeLimits(id);

                Exam exam = new Exam(id, name, associatedCourse, date, gradeLimits, passingLimit, tasks, maxPoints, bonus, participatingStudents);
                exams.add(exam);
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return exams;
    }

    /**
     * Retrieves a list of Task objects associated with a specific exam by joining the 'Tasks' table.
     *
     * @param examId The ID of the exam for which tasks are to be retrieved.
     * @return An ArrayList of Task objects representing the tasks associated with the exam.
     */
    public ArrayList<Task> getTasks(String examId) {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            String query = "SELECT * FROM Tasks WHERE ExamId = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(examId));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int number = resultSet.getInt("TaskNumber");
                double points = resultSet.getDouble("Points");
                String question = resultSet.getString("Question");

                tasks.add(new Task(number, points, question));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return tasks;
    }

    /**
     * Retrieves a list of Student objects participating in a specific exam by joining the 'participates' table.
     *
     * @param examId The ID of the exam for which participating students are to be retrieved.
     * @return An ArrayList of Student objects representing the students participating in the exam.
     */
    public ArrayList<Student> getParticipatingStudents(String examId) {
        ArrayList<Student> students = new ArrayList<>();

        try {
            String query = """
                    SELECT *
                    FROM participates
                             JOIN (SELECT MatriculationNumber AS StudentId, Firstname, Lastname FROM Students) AS s USING (StudentId)
                    WHERE ExamId = ?;""";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, examId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String matriculationNumber = resultSet.getString("StudentId");
                String firstname = resultSet.getString("Firstname");
                String lastname = resultSet.getString("Lastname");

                students.add(new Student(firstname, lastname, matriculationNumber));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return students;
    }

    public ArrayList<GradeLimits> getGradeLimits(String examId) {
        ArrayList<GradeLimits> gradeLimitsArrayList = new ArrayList<>();

        try {
            String query = "SELECT * from GradeLimits WHERE ExamId = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, examId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String note = resultSet.getString("Note");
                int min = resultSet.getInt("MinGrade");
                int max = resultSet.getInt("MaxGrade");

                gradeLimitsArrayList.add(new GradeLimits(note, min, max));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return gradeLimitsArrayList;
    }

    /**
     * Retrieves EarnedPoints object representing the points earned by a student for a specific task in an exam.
     *
     * @param exam    The  exam for which earned points are to be retrieved.
     * @param task    The Task object for which earned points are to be retrieved.
     * @param student The Student object for whom earned points are to be retrieved.
     * @return An EarnedPoints object representing the points earned by the student for the task in the exam.
     */
    public EarnedPoints getEarnedPoints(Exam exam, Task task, Student student) {
        try {
            String query = "SELECT * FROM EarnedPoints WHERE TaskNumber = ? AND ExamId = ? AND StudentId = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, task.getNumber());
            preparedStatement.setString(2, exam.getExamId());
            preparedStatement.setString(3, student.getMatriculationNumber());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new EarnedPoints(exam, student, task, resultSet.getDouble("Points"));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return new EarnedPoints(exam, student, task);
    }

    public double getSumOfPoints(String examId, Student student) {
        try {
            String query = "SELECT SUM(Points) AS Sum from EarnedPoints WHERE ExamId = ? AND StudentId = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, examId);
            preparedStatement.setString(2, student.getMatriculationNumber());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("Sum");
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return 0;
    }
    //endregion

    //region Statistics
    public XYChart.Series<String, Number> getHistogramData(String examId) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        try {
            String query = "SELECT Grade, COUNT(Grade) AS Count FROM Grades WHERE ExamId = ? GROUP BY Grade ORDER BY Grade;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, examId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String grade = resultSet.getString("Grade");
                int count = resultSet.getInt("Count");

                series.getData().add(new XYChart.Data<>(grade, count));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return series;
    }

    public XYChart.Series<String, Number> getTasksStatistic(String examId) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        try {
            String query = """
                    SELECT TaskNumber, ROUND((t.p / Points) * 100,2) AS Result
                    FROM Tasks
                             JOIN (SELECT TaskNumber, SUM(Points) / COUNT(StudentId) AS p
                                   FROM EarnedPoints
                                   WHERE ExamId = ? GROUP BY TaskNumber) AS t USING (TaskNumber)
                    WHERE ExamId = ?;
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, examId);
            preparedStatement.setString(2, examId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String taskNumber = Translator.getInstance().translate("Task") + " " + resultSet.getInt("TaskNumber");
                double result = resultSet.getDouble("Result");

                series.getData().add(new XYChart.Data<>(taskNumber, result));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return series;
    }

    public XYChart.Series<String, Number> getMajorPointsStatistic(String examId) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        try {
            String query = """
                    SELECT MajorName, ROUND((SUM(Points) / MaxPoints) * COUNT(StudentId), 2) AS Result
                    FROM EarnedPoints
                             JOIN (SELECT * FROM studies) AS s USING (StudentId)
                             JOIN (SELECT Id AS ExamId, MaxPoints FROM Exams) AS exams USING (ExamId)
                    WHERE ExamId = ?
                    GROUP BY MajorName;
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, examId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String taskNumber = resultSet.getString("MajorName");
                double result = resultSet.getDouble("Result");

                series.getData().add(new XYChart.Data<>(taskNumber, result));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return series;
    }

    public ArrayList<XYChart.Series<String, Number>> getMajorPassingRate(String examId) {
        XYChart.Series<String, Number> passedSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> failedSeries = new XYChart.Series<>();

        try {
            String query = """
                    SELECT SUM(Passed = 1) / COUNT(MajorName)  AS yes,
                           SUM(Passed = 0) / COUNT(MajorName) AS no,
                           MajorName
                    FROM Grades
                             JOIN studies USING (StudentId)
                    WHERE ExamId = ?
                    GROUP BY MajorName;
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, examId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String majorName = resultSet.getString("MajorName");
                double passedResult = resultSet.getDouble("yes");
                double failedResult = resultSet.getDouble("no");

                passedSeries.getData().add(new XYChart.Data<>(majorName, passedResult));
                failedSeries.getData().add(new XYChart.Data<>(majorName, failedResult));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        ArrayList<XYChart.Series<String, Number>> list = new ArrayList<>();
        list.add(passedSeries);
        list.add(failedSeries);

        return list;
    }

    public String getExamPassRate(String examId) {
        try {
            String query = """
                    SELECT (SUM(Passed = 1) / COUNT(StudentId)) * 100 AS passrate
                    FROM Grades
                    WHERE ExamId = ?;
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, examId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double result = resultSet.getDouble("passrate");
                return String.valueOf(result);
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return "0.0";
    }

    public String getExamMeanGrade(String examId) {
        try {
            String query = """
                    SELECT ROUND(AVG(Grade), 2) AS result
                    FROM Grades
                    WHERE ExamId = ?;
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, examId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double result = resultSet.getDouble("result");
                return String.valueOf(result);
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return "0.0";
    }
    //endregion


    //region Save data
    public void saveTask(Task task, String examId) {
        try {
            String query = """
                    INSERT INTO Tasks (TaskNumber, Question, Points, ExamId)
                    VALUES (?, ?, ?, ?)
                    ON DUPLICATE KEY UPDATE TaskNumber = VALUES(TaskNumber),
                                            Question     = VALUES(Question),
                                            Points  = VALUES(Points),
                                            ExamId     = VALUES(ExamId);
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, task.getNumber());
            preparedStatement.setString(2, task.getQuestionWithoutNumber());
            preparedStatement.setDouble(3, task.getPoints());
            preparedStatement.setString(4, examId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }


    public void saveEarnedPoints(EarnedPoints earnedPoints) {
        try {
            String query = """
                    INSERT INTO EarnedPoints (TaskNumber, ExamId, StudentId, Points)
                    VALUES (?, ?, ?, ?)
                    ON DUPLICATE KEY UPDATE TaskNumber = VALUES(TaskNumber),
                                            ExamId     = VALUES(ExamId),
                                            StudentId  = VALUES(StudentId),
                                            Points     = VALUES(Points);
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, earnedPoints.getTask().getNumber());
            preparedStatement.setString(2, earnedPoints.getExam().getExamId());
            preparedStatement.setString(3, earnedPoints.getStudent().getMatriculationNumber());
            preparedStatement.setDouble(4, earnedPoints.getPoints());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    public void saveGrade(String studentId, String examId, double points, String grade, Boolean passed) {
        try {
            String query = """
                    INSERT INTO Grades (StudentId, ExamId, Points, Grade, Passed)
                    VALUES (?, ?, ?, ?, ?)
                    ON DUPLICATE KEY UPDATE StudentId = VALUES(StudentId),
                                            ExamId     = VALUES(ExamId),
                                            Points  = VALUES(Points),
                                            Grade     = VALUES(Grade),
                                            Passed = VALUES(Passed);
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, examId);
            preparedStatement.setDouble(3, points);
            preparedStatement.setString(4, grade);
            preparedStatement.setBoolean(5, passed);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    public String saveLecturer(Lecturer lecturer) {
        try {
            String query = "INSERT IGNORE INTO Lecturers(Firstname, Lastname, AcademicGrade, Email, ResearchGroup, TelephoneNumber, Room) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, lecturer.getFirstName());
            preparedStatement.setString(2, lecturer.getLastName());
            preparedStatement.setString(3, String.valueOf(lecturer.getAcademicDegree()));
            preparedStatement.setString(4, lecturer.getEmailAddress());
            preparedStatement.setString(5, lecturer.getResearchGroup());
            preparedStatement.setString(6, lecturer.getPhoneNumber());
            preparedStatement.setString(7, lecturer.getRoom());

            preparedStatement.executeUpdate();

            String maxQuery = "SELECT MAX(Id) AS max FROM Lecturers";
            PreparedStatement maxPreparedStatement = connection.prepareStatement(maxQuery);
            ResultSet resultSet = maxPreparedStatement.executeQuery();

            if (resultSet.next()) {
                return String.valueOf(resultSet.getInt("max"));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return "";
    }

    /**
     * Inserts a new user into the 'users' table with an encrypted password.
     *
     * @param user     The user class
     * @param lecturer The lecturer of the user
     */
    public void createUser(User user, Lecturer lecturer) {
        try {
            String encryptedPassword = Encrypter.getInstance().hashPassword(user.getPassword());
            PreparedStatement query = connection.prepareStatement("INSERT INTO users (username,password,LecturerId) VALUES (?,?,?)");
            query.setString(1, user.getUserName());
            query.setString(2, encryptedPassword);
            query.setString(3, saveLecturer(lecturer));
            query.executeUpdate();
        } catch (SQLException | NoSuchAlgorithmException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    public String saveExamOfficeEmployee(ExamOfficeEmployee examOfficeEmployee) {
        try {
            String query = "INSERT IGNORE INTO ExamOfficeEmployees(Room, TelephoneNumber, FirstName, LastName, Email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, examOfficeEmployee.getRoom());
            preparedStatement.setString(2, examOfficeEmployee.getPhoneNumber());
            preparedStatement.setString(3, examOfficeEmployee.getFirstName());
            preparedStatement.setString(4, examOfficeEmployee.getLastName());
            preparedStatement.setString(5, examOfficeEmployee.getEmailAddress());

            preparedStatement.executeUpdate();

            String maxQuery = "SELECT MAX(Id) AS max FROM ExamOfficeEmployees;";
            PreparedStatement maxPreparedStatement = connection.prepareStatement(maxQuery);
            ResultSet resultSet = maxPreparedStatement.executeQuery();

            if (resultSet.next()) {
                return String.valueOf(resultSet.getInt("max"));
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }

        return "1";
    }

    /**
     * saves a new Major in the Database
     * if the examEmployee is null then it will assign an undefined employee
     *
     * @param major the Major to add to the Database
     */
    public void saveMajor(Major major) {
        try {
            String query = "INSERT IGNORE INTO Majors(Name, ExamOfficeEmployee) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, major.getName());

            if (major.getEmployee() == null) {
                ExamOfficeEmployee examOfficeEmployee = new ExamOfficeEmployee("", "", "", "", "");
                major.setEmployee(examOfficeEmployee);
            }

            preparedStatement.setString(2, saveExamOfficeEmployee(major.getEmployee()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    /**
     * saves a new Student in the Database
     *
     * @param student the Student to add to the Database
     */
    public void saveStudent(Student student, String examId) {
        try {
            String query = "INSERT IGNORE INTO Students(MatriculationNumber, Firstname, Lastname) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(student.getMatriculationNumber()));
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());

            preparedStatement.executeUpdate();

            //add major to database
            for (Major major : student.getMajors()) {
                saveMajor(major);

                //update studies
                String studiesQuery = "INSERT IGNORE INTO studies(StudentId, MajorName) VALUES (?,?)";
                PreparedStatement studiesPreparedStatement = connection.prepareStatement(studiesQuery);
                studiesPreparedStatement.setInt(1, Integer.parseInt(student.getMatriculationNumber()));
                studiesPreparedStatement.setString(2, major.getName());

                studiesPreparedStatement.executeUpdate();
            }

            if (examId != null) {
                String participatesQuery = "INSERT IGNORE INTO participates(StudentId, ExamId) VALUES (?,?)";
                PreparedStatement participatesPreparedStatement = connection.prepareStatement(participatesQuery);
                participatesPreparedStatement.setString(1, student.getMatriculationNumber());
                participatesPreparedStatement.setString(2, examId);

                participatesPreparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    /**
     * saves a new Course in the Database
     *
     * @param course     the course
     * @param lecturerId the id of the owner of the course
     */
    public void saveCourse(Course course, String lecturerId) {
        try {
            String query = "INSERT INTO Courses(Id, Name, LecturerId) VALUES (?, ?,?) ON DUPLICATE KEY UPDATE Name = VALUES(Name);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, course.getId());
            preparedStatement.setString(2, course.getName());
            preparedStatement.setString(3, lecturerId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    /**
     * saves a new Exam in the Database
     *
     * @param exam the Exam to be saved
     */
    public void saveExam(Exam exam) {
        try {
            String query = "INSERT INTO Exams (Name, Date, MaxPoints, PassingLimit, BonusType, AssociatedCourse) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, exam.getName());
            preparedStatement.setDate(2, Date.valueOf(exam.getDate()));
            preparedStatement.setDouble(3, exam.getMaximumPoints());
            preparedStatement.setDouble(4, exam.getPassingLimit());
            preparedStatement.setString(5, String.valueOf(exam.getRecordBonus()));
            preparedStatement.setString(6, exam.getAssociatedCourse().getId());

            preparedStatement.executeUpdate();

            String maxQuery = "SELECT MAX(Id) AS max FROM Exams;";
            PreparedStatement maxPreparedStatement = connection.prepareStatement(maxQuery);
            ResultSet resultSet = maxPreparedStatement.executeQuery();

            if (resultSet.next()) {
                String examId = String.valueOf(resultSet.getInt("max"));
                for (GradeLimits gradeLimit : exam.getGradeLimits()) {
                    saveGradeLimit(gradeLimit, examId);
                }
            }
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    public void saveGradeLimit(GradeLimits gradeLimit, String examId) {
        try {
            String query = "INSERT INTO GradeLimits (Note, MinGrade, MaxGrade, ExamId) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, gradeLimit.getNote());
            preparedStatement.setInt(2, gradeLimit.getMin());
            preparedStatement.setInt(3, gradeLimit.getMax());
            preparedStatement.setString(4, examId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }
    //endregion

    //region Updating Database
    public void updateLecturer(Lecturer lecturer) {
        try {
            String updateQuery = """
                    UPDATE Lecturers SET firstname = ?,
                    lastname = ?,
                    AcademicGrade = ?,
                    Email = ?,
                    ResearchGroup = ?,
                    TelephoneNumber = ?,
                    Room = ? WHERE id = ?;
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, lecturer.getFirstName());
            preparedStatement.setString(2, lecturer.getLastName());
            preparedStatement.setString(3, String.valueOf(lecturer.getAcademicDegree()));
            preparedStatement.setString(4, lecturer.getEmailAddress());
            preparedStatement.setString(5, lecturer.getResearchGroup());
            preparedStatement.setString(6, lecturer.getPhoneNumber());
            preparedStatement.setString(7, lecturer.getRoom());
            preparedStatement.setString(8, lecturer.getId());

            preparedStatement.executeUpdate();

            //Update username
            String updateUsernameQuery = "UPDATE users SET Username = ? WHERE LecturerId = ?";
            PreparedStatement preparedUsernameStatement = connection.prepareStatement(updateUsernameQuery);
            preparedUsernameStatement.setString(1, lecturer.getUserName());
            preparedUsernameStatement.setString(2, lecturer.getId());

            preparedUsernameStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    public void updatePassword(String email, String newPassword) {
        try {
            String updateQuery = """
                    UPDATE users
                    INNER JOIN Lecturers ON users.LecturerId = Lecturers.Id
                    SET users.password = ?
                    WHERE Lecturers.Email = ?;""";

            PreparedStatement preparedPasswordStatement = connection.prepareStatement(updateQuery);
            preparedPasswordStatement.setString(1, newPassword);
            preparedPasswordStatement.setString(2, email);


            preparedPasswordStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    public void updateStudent(Student student, Student newStudent) {
        try {
            String query = """
                    UPDATE Students
                    SET MatriculationNumber = ?,
                        Firstname           = ?,
                        Lastname            = ?
                    WHERE MatriculationNumber = ?;
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newStudent.getMatriculationNumber());
            preparedStatement.setString(2, newStudent.getFirstName());
            preparedStatement.setString(3, newStudent.getLastName());
            preparedStatement.setString(4, student.getMatriculationNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    /**
     * updates a Major in the Database
     *
     * @param major    the old Major in the Database
     * @param newMajor the new Major data to be updated
     */
    public void updateMajor(Major major, Major newMajor) {
        try {
            String query = """
                    UPDATE Majors SET Name               = ?,
                                      ExamOfficeEmployee = ? WHERE Name = ?;""";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newMajor.getName());
            preparedStatement.setString(2, major.getEmployee().getEmployeeId());
            preparedStatement.setString(3, major.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    /**
     * updates the Exam in the Database
     *
     * @param newExam the Exam to be updated
     */
    public void updateExam(Exam newExam, String oldExamId) {
        try {
            String query = """
                    UPDATE Exams SET Name = ?,
                                     Date = ?,
                                     MaxPoints = ?,
                                     PassingLimit = ?,
                                     BonusType = ?,
                                     AssociatedCourse = ? WHERE Id = ?;""";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newExam.getName());
            preparedStatement.setDate(2, Date.valueOf(newExam.getDate()));
            preparedStatement.setDouble(3, newExam.getMaximumPoints());
            preparedStatement.setDouble(4, newExam.getPassingLimit());
            preparedStatement.setString(5, String.valueOf(newExam.getRecordBonus()));
            preparedStatement.setString(6, newExam.getAssociatedCourse().getId());
            preparedStatement.setString(7, oldExamId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }
    //endregion

    //region deletion
    public void deleteStudent(Student student) {
        try {
            String deleteStudentQuery = "DELETE FROM Students WHERE MatriculationNumber = ?;";
            PreparedStatement preparedStudentStatement = connection.prepareStatement(deleteStudentQuery);
            preparedStudentStatement.setString(1, student.getMatriculationNumber());
            preparedStudentStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    public void deleteParticipatingStudent(Student student, String examId) {
        try {
            String deleteStudentQuery = "DELETE FROM participates WHERE StudentId = ? AND ExamId = ?;";
            PreparedStatement preparedStudentStatement = connection.prepareStatement(deleteStudentQuery);
            preparedStudentStatement.setString(1, student.getMatriculationNumber());
            preparedStudentStatement.setString(2, examId);
            preparedStudentStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    public void deleteCourse(Course course) {
        try {
            String deleteStudentQuery = "DELETE FROM Courses WHERE Id = ?;";
            PreparedStatement preparedStudentStatement = connection.prepareStatement(deleteStudentQuery);
            preparedStudentStatement.setString(1, course.getId());
            preparedStudentStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    public void deleteTask(Task task, String examId) {
        try {
            String deleteStudentQuery = "DELETE FROM Tasks WHERE TaskNumber = ? AND ExamId = ?;";
            PreparedStatement preparedStudentStatement = connection.prepareStatement(deleteStudentQuery);
            preparedStudentStatement.setInt(1, task.getNumber());
            preparedStudentStatement.setString(2, examId);
            preparedStudentStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    public void deleteExamOfficeEmployee(String employeeId) {
        try {
            String deleteStudentQuery = "DELETE FROM ExamOfficeEmployees WHERE Id = ?";
            PreparedStatement preparedStudentStatement = connection.prepareStatement(deleteStudentQuery);
            preparedStudentStatement.setString(1, employeeId);
            preparedStudentStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    public void deleteExam(String examId) {
        try {
            String deleteStudentQuery = "DELETE FROM Exams WHERE Id = ?";
            PreparedStatement preparedStudentStatement = connection.prepareStatement(deleteStudentQuery);
            preparedStudentStatement.setString(1, examId);
            preparedStudentStatement.executeUpdate();
        } catch (SQLException e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }
    //endregion
}