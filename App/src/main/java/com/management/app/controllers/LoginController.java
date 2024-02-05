package com.management.app.controllers;

import com.management.app.managemenetapp.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Locale;

/**
 * Controller class for the login view responsible for handling user authentication.
 */
public class LoginController {
    @FXML
    HBox titleBox;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label failedLoginText;
    private ValidationManager validationManager;

    /**
     * Initializes the login view. This method is automatically called by JavaFX after loading the FXML.
     * Any additional setup for the login view can be done here.
     */
    @FXML
    private void initialize() {
        titleBox.setOnMouseDragged(this::onTitleDragged);
        Platform.runLater(() -> {
            validationManager = new ValidationManager();
            validationManager.setupUsernameValidation(usernameTextField);
            validationManager.setupRequiredValidation(passwordField);
        });
    }

    /**
     * Handles the login button click event.
     * Attempts to authenticate the user based on the provided username and password.
     * If authentication is successful, the user is logged in, and the Courses view is loaded.
     * If authentication fails, the "failedLoginText" label is made visible.
     */
    @FXML
    protected void onLoginButtonClick() {
        failedLoginText.setVisible(false);
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        if (!validationManager.isInvalid() && DatabaseManager.getInstance().checkPassword(username, password)) {
            UserManager.getInstance().login(DatabaseManager.getInstance().getUser(username));
            SceneManager.getInstance().loadScene("courses-view.fxml");
        } else {
            failedLoginText.setVisible(true);
        }
    }

    /**
     * Handles the button click event for creating a new user.
     * Loads the "create-user-view.fxml" scene to initiate the user creation process.
     */
    @FXML
    protected void onCreateButtonClick() {
        SceneManager.getInstance().loadScene("create-user-view.fxml");
    }

    /**
     * Handles the click event for the password recovery option.
     * Loads the "password-recovery-view.fxml" scene to initiate the password recovery process.
     */
    @FXML
    protected void onPasswordRecoveryClick() {
        SceneManager.getInstance().loadScene("password-recovery-view.fxml");
    }

    /**
     * Handles the click event for changing the application language to English.
     * Changes the language using the Translator and refreshes the view by calling the initialize method.
     */
    @FXML
    protected void onChangeLanguageToEnglish() {
        Translator.getInstance().changeLanguage(Locale.ENGLISH);
        this.initialize();
    }

    /**
     * Handles the click event for changing the application language to German.
     * Changes the language using the Translator and refreshes the view by calling the initialize method.
     */
    @FXML
    protected void onChangeLanguageToGerman() {
        Translator.getInstance().changeLanguage(Locale.GERMAN);

    }

    @FXML
    private void onMinimizeClick() {
        Stage stage = (Stage) usernameTextField.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Handles the action when the "Profile" button is clicked.
     * Navigates to the user profile view.
     */
    @FXML
    private void onCloseClick() {
        Stage stage = (Stage) usernameTextField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onTitleDragged(MouseEvent event) {
        Stage stage = (Stage) usernameTextField.getScene().getWindow();
        stage.setX(event.getScreenX());
        stage.setY(event.getScreenY());
    }
}