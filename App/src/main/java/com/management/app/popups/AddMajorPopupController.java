package com.management.app.popups;

import com.management.app.dtos.EditStudentDto;
import com.management.app.managemenetapp.DatabaseManager;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.types.ExamOfficeEmployee;
import com.management.app.types.Major;
import com.management.app.managemenetapp.ValidationManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMajorPopupController {
    @FXML
    private TextField majorNameTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField roomTextField;
    private ValidationManager validationManager;
    private Major major;
    private Boolean isUpdate = false;

    @FXML
    private void initialize() {
        Platform.runLater(this::setup);
    }

    private void setup() {
        setupValidation();
        major = (Major) firstNameTextField.getScene().getUserData();

        if (major == null)
            return;

        isUpdate = true;
        majorNameTextField.setText(major.getName());
        firstNameTextField.setText(major.getEmployee().getFirstName());
        lastNameTextField.setText(major.getEmployee().getLastName());
        emailTextField.setText(major.getEmployee().getEmailAddress());
        phoneTextField.setText(major.getEmployee().getPhoneNumber());
        roomTextField.setText(major.getEmployee().getRoom());
    }

    private void setupValidation() {
        validationManager = new ValidationManager();
        validationManager.setupRequiredValidation(majorNameTextField);
        validationManager.setupNameValidation(firstNameTextField);
        validationManager.setupNameValidation(lastNameTextField);
        validationManager.setupEmailValidation(emailTextField);
        validationManager.setupPhoneTextField(phoneTextField);
        validationManager.setupRequiredValidation(roomTextField);
    }

    @FXML
    private void createMajor() {
        if (validationManager.isInvalid())
            return;

        String firstname = firstNameTextField.getText();
        String lastname = lastNameTextField.getText();
        String email = emailTextField.getText();
        String phone = phoneTextField.getText();
        String room = roomTextField.getText();
        String majorName = majorNameTextField.getText();

        ExamOfficeEmployee examOfficeEmployee = new ExamOfficeEmployee(firstname, lastname, email, phone, room);
        Major newMajor = new Major(majorName, examOfficeEmployee);

        if (isUpdate) {
            DatabaseManager.getInstance().updateMajor(major, newMajor);
        } else {
            DatabaseManager.getInstance().saveMajor(newMajor);
        }

        SceneManager.getInstance().loadScene("major-view.fxml");
        Stage stage = (Stage) firstNameTextField.getScene().getWindow();
        stage.close();
    }
}

