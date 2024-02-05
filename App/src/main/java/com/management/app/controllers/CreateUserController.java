package com.management.app.controllers;

import com.management.app.managemenetapp.*;
import com.management.app.types.Lecturer;
import com.management.app.types.User;
import com.management.app.types.enums.AcademicGrade;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller class for handling user creation functionality in the Create User view.
 */
public class CreateUserController {
    @FXML
    protected TextField usernameTextField;
    @FXML
    protected TextField firstNameTextField;
    @FXML
    protected TextField emailTextField;
    @FXML
    protected PasswordField passwordField;
    @FXML
    protected TextField telephoneNumberField;
    @FXML
    protected ChoiceBox<AcademicGrade> academicGradeChoiceBox;
    @FXML
    protected TextField lastNameTextField;
    @FXML
    protected PasswordField repeatPasswordField;
    @FXML
    protected TextField workingGroupTextField;
    @FXML
    protected TextField roomTextField;
    private ValidationManager validationManager;

    /**
     * Initializes the Password Recovery view by setting up the validation
     */
    @FXML
    private void initialize() {
        academicGradeChoiceBox.getItems().addAll(AcademicGrade.None, AcademicGrade.Dr, AcademicGrade.Prof);
        academicGradeChoiceBox.setValue(AcademicGrade.None);
        Platform.runLater(this::setupValidation);
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
        validationManager.setupPasswordValidation(passwordField, repeatPasswordField);

        validationManager.setupRequiredValidation(roomTextField);
        validationManager.setupRequiredValidation(workingGroupTextField);

        validationManager.setupPhoneNumberValidation(telephoneNumberField);
        validationManager.setupChoiceBoxValidation(academicGradeChoiceBox);
    }

    /**
     * Handles the event when the user wants to go back to the login view.
     * Loads the "login-view.fxml" scene using the SceneManager.
     */
    @FXML
    protected void backToLogin() {
        SceneManager.getInstance().loadScene("login-view.fxml");
    }

    /**
     * Handles the event when the user wants to register a new user.
     * Loads the "login-view.fxml" scene using the SceneManager.
     * This method may include additional logic for user registration.
     */
    @FXML
    private void registerUser() {
        if (validationManager.isInvalid())
            return;

        String username = usernameTextField.getText();
        String firstname = firstNameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordField.getText();
        String telephonenumber = telephoneNumberField.getText();
        AcademicGrade academicgrade = academicGradeChoiceBox.getValue();
        String lastname = lastNameTextField.getText();
        String repeatPassword = repeatPasswordField.getText();
        String workingGroup = workingGroupTextField.getText();
        String room = roomTextField.getText();

        if (!password.equals(repeatPassword))
            return;

        Lecturer lecturer = new Lecturer(firstname, lastname, email, telephonenumber, room, "", username, password, academicgrade, workingGroup);
        User user = new User(username, password);

        DatabaseManager.getInstance().createUser(user, lecturer);
        SceneManager.getInstance().showError("Create User successful", true);
        SceneManager.getInstance().loadScene("login-view.fxml");
    }
}