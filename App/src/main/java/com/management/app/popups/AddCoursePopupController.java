package com.management.app.popups;

import com.management.app.managemenetapp.DatabaseManager;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.managemenetapp.UserManager;
import com.management.app.managemenetapp.ValidationManager;
import com.management.app.types.Course;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * the Controller responsible for Controlling the new Course Input Popup
 */
public class AddCoursePopupController {
    @FXML
    private TextField courseNameTextField;
    private ValidationManager validationManager;
    private Course course;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            validationManager = new ValidationManager();
            validationManager.setupRequiredValidation(courseNameTextField);
            course = (Course) courseNameTextField.getScene().getUserData();
            courseNameTextField.setText(course.getName());
        });
    }

    /**
     * this method creates a new Course from the name entered by the user
     */
    public void createNewCourse() {
        if (validationManager.isInvalid())
            return;

        String courseName = courseNameTextField.getText();

        if (courseName == null || courseName.isBlank()) {
            return;
        }

        course.setName(courseName);

        DatabaseManager.getInstance().saveCourse(course, UserManager.getInstance().getCurrentUser().getId());
        SceneManager.getInstance().loadScene("courses-view.fxml");
        Stage stage = (Stage) courseNameTextField.getScene().getWindow();
        stage.close();
    }
}
