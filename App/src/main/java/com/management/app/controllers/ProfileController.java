package com.management.app.controllers;

import com.management.app.managemenetapp.*;
import com.management.app.types.Lecturer;
import com.management.app.types.User;
import com.management.app.types.enums.AcademicGrade;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller class for the Profile view responsible for managing user profile information.
 * Allows the user, specifically a Lecturer, to view and edit their profile details.
 */
public class ProfileController {
    @FXML
    protected Button saveButton;
    @FXML
    protected TextField usernameTextField;
    @FXML
    protected TextField firstNameTextField;
    @FXML
    protected TextField emailTextField;
    @FXML
    protected TextField telephoneNumberField;
    @FXML
    protected ChoiceBox<AcademicGrade> academicGradeChoiceBox;
    @FXML
    protected TextField lastNameTextField;
    @FXML
    protected TextField workingGroupTextField;
    @FXML
    protected TextField roomTextField;

    private Lecturer lecturer;
    private boolean isEditing = false;
    private ValidationManager validationManager;

    /**
     * Initializes the Profile view. This method is automatically called by JavaFX after loading the FXML.
     * Retrieves the current user's (Lecturer) profile information and populates the UI fields with the data.
     */
    @FXML
    protected void initialize() {
        String userId = UserManager.getInstance().getCurrentUser().getId();
        lecturer = DatabaseManager.getInstance().getLecturer(userId);

        setupInitialValues();
        Platform.runLater(this::setupValidation);
    }

    /**
     * fills in the Values of the Input Fields from the Lecturers data
     */
    private void setupInitialValues() {
        usernameTextField.setText(lecturer.getUserName());
        firstNameTextField.setText(lecturer.getFirstName());
        emailTextField.setText(lecturer.getEmailAddress());
        telephoneNumberField.setText(lecturer.getPhoneNumber());
        lastNameTextField.setText(lecturer.getLastName());
        roomTextField.setText(lecturer.getRoom());
        workingGroupTextField.setText(lecturer.getResearchGroup());

        academicGradeChoiceBox.setValue(lecturer.getAcademicDegree());
        academicGradeChoiceBox.getItems().addAll(AcademicGrade.None, AcademicGrade.Dr, AcademicGrade.Prof);
    }

    /**
     * sets up the Validation for the Input Fields using ValidationManager
     */
    private void setupValidation() {
        validationManager = new ValidationManager();
        validationManager.setupUsernameValidation(usernameTextField);

        validationManager.setupNameValidation(firstNameTextField);
        validationManager.setupNameValidation(lastNameTextField);

        validationManager.setupEmailValidation(emailTextField);

        validationManager.setupRequiredValidation(roomTextField);
        validationManager.setupRequiredValidation(workingGroupTextField);

        validationManager.setupPhoneNumberValidation(telephoneNumberField);
        validationManager.setupChoiceBoxValidation(academicGradeChoiceBox);
    }

    /**
     * Toggles the edit mode for the profile. Enables or disables UI fields based on the current edit mode status.
     */
    @FXML
    protected void onEdit() {
        isEditing = !isEditing;
        usernameTextField.setDisable(isEditing);
        firstNameTextField.setDisable(isEditing);
        emailTextField.setDisable(isEditing);
        telephoneNumberField.setDisable(isEditing);
        academicGradeChoiceBox.setDisable(isEditing);
        lastNameTextField.setDisable(isEditing);
        workingGroupTextField.setDisable(isEditing);
        roomTextField.setDisable(isEditing);
        saveButton.setDisable(isEditing);
    }

    /**
     * Saves the edited profile information. Prints the updated values to the console.
     * This method is typically called after the user clicks the "Save" button.
     */
    @FXML
    protected void onSave() {
        if (validationManager.isInvalid())
            return;

        String username = usernameTextField.getText();
        String firstname = firstNameTextField.getText();
        String email = emailTextField.getText();
        String telephonenumber = telephoneNumberField.getText();
        AcademicGrade academicgrade = academicGradeChoiceBox.getValue();
        String lastname = lastNameTextField.getText();
        String workingGroup = workingGroupTextField.getText();
        String room = roomTextField.getText();

        Lecturer newLecturer = new Lecturer(firstname, lastname, email, telephonenumber, room, lecturer.getId(), username, "", academicgrade, workingGroup);
        DatabaseManager.getInstance().updateLecturer(newLecturer);

        onEdit();
    }
}
