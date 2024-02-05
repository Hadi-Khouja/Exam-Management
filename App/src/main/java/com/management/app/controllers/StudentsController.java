package com.management.app.controllers;

import com.management.app.components.StudentListViewCell;
import com.management.app.dtos.EditStudentDto;
import com.management.app.managemenetapp.DatabaseManager;
import com.management.app.managemenetapp.FilesManager;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.managemenetapp.Translator;
import com.management.app.types.Student;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;

/**
 * Controller class for the Students view responsible for displaying a list of students.
 * Each list item includes the student's name, matriculation number, major(s), and allows navigation
 * to the student's grades view upon selection.
 */
public class StudentsController {
    @FXML
    private ListView<Student> listView;
    @FXML
    protected Button importButton;


    /**
     * Initializes the Students view. This method is automatically called by JavaFX after loading the FXML.
     * Retrieves the list of students from the database and populates the ListView with student information.
     */
    @FXML
    private void initialize() {
        ArrayList<Student> students = DatabaseManager.getInstance().getStudents();
        listView.setCellFactory(studentsListView -> new StudentListViewCell());
        listView.getItems().addAll(students);
    }

    /**
     * Handles the click event on the ListView items. Initiates navigation to the student's grades view.
     */
    @FXML
    private void onListClick() {
        Student selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            SceneManager.getInstance().loadSceneWithData("student-grades-view.fxml", selectedItem);
        }
    }

    /**
     * opens a new window for adding a Student
     */
    @FXML
    private void onAddStudent() {
        String title = Translator.getInstance().translate("AddStudent");
        Student student = new Student("", "", "");
        EditStudentDto editStudentDto = new EditStudentDto(null, student);

        SceneManager.getInstance().openPopupWithData(title, "add-student-popup.fxml", 700.0, 600.0, editStudentDto);
    }

    @FXML
    private void onImportClick() {
        FilesManager.getInstance().importFromCSV(null);
    }
}

