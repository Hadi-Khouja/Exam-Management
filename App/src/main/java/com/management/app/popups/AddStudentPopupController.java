package com.management.app.popups;

import com.management.app.dtos.EditStudentDto;
import com.management.app.types.Course;
import com.management.app.types.Major;
import com.management.app.components.MajorListViewCell;
import com.management.app.managemenetapp.DatabaseManager;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.managemenetapp.Translator;
import com.management.app.managemenetapp.ValidationManager;
import com.management.app.types.Student;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * the Controller responsible for Controlling the new Student Input Popup
 */
public class AddStudentPopupController {
    @FXML
    protected ListView<Major> majorsListView;
    @FXML
    protected TextField matriculationTextField;
    @FXML
    protected TextField firstNameTextField;
    @FXML
    protected TextField lastNameTextField;
    private ValidationManager validationManager;
    private EditStudentDto editStudentDto;
    private Student student;

    /**
     * initializes the majorsListView setting it editable
     */
    @FXML
    private void initialize() {
        majorsListView.setEditable(true);
        majorsListView.setCellFactory(majorsListView -> new MajorListViewCell());
        Platform.runLater(this::setup);
    }

    private void setup() {
        setupValidation();
        editStudentDto = (EditStudentDto) firstNameTextField.getScene().getUserData();
        student = editStudentDto.getStudent();

        firstNameTextField.setText(student.getFirstName());
        lastNameTextField.setText(student.getLastName());
        matriculationTextField.setText(student.getMatriculationNumber());
        majorsListView.getItems().addAll(student.getMajors());
    }

    private void setupValidation() {
        validationManager = new ValidationManager();
        validationManager.setupMatriculationTextField(matriculationTextField);
        validationManager.setupNameValidation(firstNameTextField);
        validationManager.setupNameValidation(lastNameTextField);
    }

    /**
     * adds a new item in the majorsList
     */
    @FXML
    private void onAddMajor() {
        String name = Translator.getInstance().translate("NewMajor");
        majorsListView.getItems().add(new Major(name));
    }

    /**
     * creates a new Student from the entered input
     */
    @FXML
    private void createStudent() {
        if (validationManager.isInvalid())
            return;

        String id = matriculationTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        Student newStudent = new Student(firstName, lastName, id);
        newStudent.addMajors(majorsListView.getItems());

        if (student.getMatriculationNumber().isBlank()) {
            DatabaseManager.getInstance().saveStudent(newStudent, editStudentDto.getExamId());
        } else {
            DatabaseManager.getInstance().updateStudent(student, newStudent);
        }

        if (editStudentDto.getExamId() == null)
            SceneManager.getInstance().loadScene("students-view.fxml");

        Stage stage = (Stage) matriculationTextField.getScene().getWindow();
        stage.close();
    }
}
