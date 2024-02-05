package com.management.app.controllers;

import com.management.app.managemenetapp.*;
import com.management.app.types.Lecturer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Controller class for the Password Recovery view responsible for managing the password recovery process.
 * The view includes a set of containers for displaying different stages of the recovery process, such as
 * requesting a code, entering the code, and setting a new password.
 */
public class PasswordRecoveryController {
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField codeTextField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private Label falseCodeText;
    @FXML
    protected VBox container;
    @FXML
    protected VBox codeInputContainer;
    @FXML
    protected VBox passwordInputContainer;
    private ValidationManager validationManager;

    /**
     * Initializes the Password Recovery view. This method is automatically called by JavaFX after loading the FXML.
     * Sets the visibility of the main container to true, displaying the initial stage of the password recovery process.
     */
    @FXML
    private void initialize() {
        container.setVisible(true);
        Platform.runLater(this::setupValidation);
    }

    /**
     * sets up the Validation for the Input Fields using ValidationManager
     */
    private void setupValidation() {
        validationManager = new ValidationManager();
        validationManager.setupEmailValidation(emailTextField);
        validationManager.setupPasswordValidation(newPasswordField, repeatPasswordField);
        validationManager.setupCode(codeTextField);
    }

    /**
     * Toggles the visibility of the password input container and hides other containers.
     * This method is typically called when transitioning to the password input stage of the recovery process.
     */
    @FXML
    private void togglePasswordInputContainer() {
        container.setVisible(false);
        codeInputContainer.setVisible(false);
        passwordInputContainer.setVisible(true);
    }

    /**
     * Initiates the process of sending a recovery code. Hides other containers and displays the code input container.
     * This method is typically called when the user requests a recovery code.
     */
    @FXML
    protected void sendCode() {
        container.setVisible(false);
        codeInputContainer.setVisible(true);
        passwordInputContainer.setVisible(false);

        Encrypter.getInstance().generateResetCode();
        EmailSender.getInstance().sendRecoveryEmail(emailTextField.getText());
    }


    @FXML
    private void updatePassword() {
        String email = emailTextField.getText();
        String password = newPasswordField.getText();
        String repeatPassword = repeatPasswordField.getText();

        if (validationManager.isInvalid() || !password.equals(repeatPassword))
            return;

        try {
            String hashedPassword = Encrypter.getInstance().hashPassword(password);
            DatabaseManager.getInstance().updatePassword(email, hashedPassword);
            SceneManager.getInstance().showError("Password Update Successful", true);
            SceneManager.getInstance().loadScene("login-view.fxml");
        } catch (Exception e) {
            SceneManager.getInstance().showError(e.getLocalizedMessage());
        }
    }

    /**
     * this method checks if the Recovery Code is valid
     */
    @FXML
    protected void checkCode() {
        if (Encrypter.getInstance().checkCode(codeTextField.getText())) {
            togglePasswordInputContainer();
        } else {
            falseCodeText.setVisible(true);
        }
    }


    /**
     * Navigates back to the login view. This method is typically called when the user wants to cancel
     * the password recovery process and return to the login view.
     */
    @FXML
    protected void backToLoginSite() {
        SceneManager.getInstance().loadScene("login-view.fxml");
    }
}
