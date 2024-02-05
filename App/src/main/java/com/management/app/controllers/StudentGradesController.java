package com.management.app.controllers;

import com.management.app.managemenetapp.DatabaseManager;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.types.Grades;
import com.management.app.types.Student;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * Controller class for the Student Grades view responsible for displaying and managing a student's grades.
 * This view includes a table showing the courses, points, and grades for the given student.
 */
public class StudentGradesController {
    @FXML
    private Text studentName;
    @FXML
    private TableView<Grades> table;
    @FXML
    private TableColumn<Grades, String> courseNameColumn;
    @FXML
    private TableColumn<Grades, Double> pointsColumn;
    @FXML
    private TableColumn<Grades, String> gradesColumn;

    /**
     * Initializes the Student Grades view. This method is automatically called by JavaFX after loading the FXML.
     * Retrieves the student data from the scene and populates the table with the student's grades.
     * Sets up the columns in the table and configures column resizing policies.
     */
    @FXML
    private void initialize() {
        Student student = (Student) SceneManager.getInstance().getWindow().getUserData();
        ObservableList<Grades> grades = DatabaseManager.getInstance().getGrades(student);

        studentName.setText(student.getFullName() + " " + student.getMatriculationNumber());
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        gradesColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        table.setItems(grades);
    }
}
