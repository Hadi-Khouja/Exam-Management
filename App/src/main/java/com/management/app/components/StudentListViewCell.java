package com.management.app.components;

import com.management.app.dtos.EditStudentDto;
import com.management.app.managemenetapp.DatabaseManager;
import com.management.app.managemenetapp.ExamManagementApp;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.managemenetapp.Translator;
import com.management.app.types.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class StudentListViewCell extends ListCell<Student> {
    @FXML
    public Label studentId;
    @FXML
    public Label studentName;
    @FXML
    public Label major;
    @FXML
    public HBox listItem;
    @FXML
    public MenuItem editButton;
    @FXML
    public MenuItem deleteButton;
    private FXMLLoader fxmlLoader;
    private Student item;

    @Override
    protected void updateItem(Student student, boolean empty) {
        super.updateItem(student, empty);
        if (empty || student == null) {
            setText(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(ExamManagementApp.class.getResource("components/student-listview-cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    SceneManager.getInstance().showError(e.getLocalizedMessage());
                }
            }

            editButton.setOnAction(event -> editStudent());
            deleteButton.setOnAction(event -> deleteStudent());

            item = student;
            studentId.setText(student.getMatriculationNumber());
            studentName.setText(student.getFullName());
            major.setText(student.getMajors().getFirst().getName());

            setText(null);
            setGraphic(listItem);
        }
    }

    public void editStudent() {
        EditStudentDto editStudentDto = new EditStudentDto(null, item);
        SceneManager.getInstance().openPopupWithData("edit Student", "add-student-popup.fxml", 700.0, 600.0, editStudentDto);
    }

    private void deleteStudent() {
        DatabaseManager.getInstance().deleteStudent(item);
        SceneManager.getInstance().loadScene("students-view.fxml");
    }
}
