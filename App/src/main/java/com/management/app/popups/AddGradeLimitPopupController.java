package com.management.app.popups;

import com.management.app.managemenetapp.ValidationManager;
import com.management.app.types.GradeLimits;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller responsible for the add grades popup
 */
public class AddGradeLimitPopupController {
    @FXML
    private TextField noteTextField;
    @FXML
    private TextField minTextField;
    @FXML
    private TextField maxTextField;
    private AddExamPopupController controller;
    private ValidationManager validationManager;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            controller = (AddExamPopupController) noteTextField.getScene().getUserData();
            setupValidation();
        });
    }

    private void setupValidation() {
        validationManager = new ValidationManager();
        validationManager.setupRequiredValidation(noteTextField);
        validationManager.setupNumbersTextField(minTextField);
        validationManager.setupNumbersTextField(maxTextField);
    }

    /**
     * creates a new GradesLimit from the provided input
     */
    @FXML
    public void createNewGradeLimit() {
        if (validationManager.isInvalid())
            return;

        String note = noteTextField.getText();
        int min = Integer.parseInt(minTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());

        controller.onAddLimit(new GradeLimits(note, min, max));
        Stage stage = (Stage) noteTextField.getScene().getWindow();
        stage.close();
    }
}
