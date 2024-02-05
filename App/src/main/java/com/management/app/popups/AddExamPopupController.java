package com.management.app.popups;

import com.management.app.components.GradeLimitListCell;
import com.management.app.dtos.EditExamDto;
import com.management.app.managemenetapp.DatabaseManager;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.managemenetapp.ValidationManager;
import com.management.app.types.Course;
import com.management.app.types.Exam;
import com.management.app.types.GradeLimits;
import com.management.app.types.enums.BonusType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddExamPopupController {
    @FXML
    private TextField examNameTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField maximumPointsTextField;
    @FXML
    private TextField passingLimitTextField;
    @FXML
    private ChoiceBox<BonusType> bonusChoiceBox;
    @FXML
    private ListView<GradeLimits> gradeLimitListView;
    private ValidationManager validationManager;
    private Course associatedCourse;
    private EditExamDto editExamDto;
    private Boolean isUpdate = false;

    @FXML
    private void initialize() {
        gradeLimitListView.setCellFactory(gradeLimitListView -> new GradeLimitListCell());
        bonusChoiceBox.getItems().addAll(BonusType.OFF, BonusType.STANDARD, BonusType.ALWAYSACTIVE);
        bonusChoiceBox.setValue(BonusType.OFF);
        Platform.runLater(this::setup);
    }

    private void setup() {
        setupValidation();

        editExamDto = (EditExamDto) examNameTextField.getScene().getUserData();
        associatedCourse = editExamDto.getCourse();

        Exam exam = editExamDto.getExam();

        if (exam == null)
            return;

        isUpdate = true;
        examNameTextField.setText(exam.getName());
        datePicker.setValue(exam.getDate());
        maximumPointsTextField.setText(String.valueOf(exam.getMaximumPoints()));
        passingLimitTextField.setText(String.valueOf(exam.getPassingLimit()));
        bonusChoiceBox.setValue(exam.getRecordBonus());
        gradeLimitListView.getItems().addAll(exam.getGradeLimits());
    }

    private void setupValidation() {
        validationManager = new ValidationManager();
        validationManager.setupRequiredValidation(examNameTextField);
        validationManager.setupDatePicker(datePicker);
        validationManager.setupRequiredValidation(maximumPointsTextField);
        validationManager.setupRequiredValidation(passingLimitTextField);
        validationManager.setupChoiceBoxValidation(bonusChoiceBox);
    }

    public void addGradeLimit() {
        SceneManager.getInstance().openPopupWithData("Add Grade Limit", "add-grade-limits-popup.fxml", 500.0, 250, this);
    }

    public void onAddLimit(GradeLimits gradeLimit) {
        gradeLimitListView.getItems().add(gradeLimit);
    }

    public void createExam() {
        if (validationManager.isInvalid())
            return;

        String examName = examNameTextField.getText();
        LocalDate examDate = datePicker.getValue();
        double passingLimit = Double.parseDouble(passingLimitTextField.getText());
        double maximumPoints = Double.parseDouble(maximumPointsTextField.getText());
        BonusType bonusType = bonusChoiceBox.getValue();

        Exam newExam = new Exam(examName, associatedCourse, examDate, gradeLimitListView.getItems(), passingLimit, maximumPoints, bonusType);

        if (isUpdate) {
            DatabaseManager.getInstance().updateExam(newExam, editExamDto.getExam().getExamId());
        } else {
            DatabaseManager.getInstance().saveExam(newExam);
        }

        SceneManager.getInstance().loadScene("courses-view.fxml");
        Stage stage = (Stage) examNameTextField.getScene().getWindow();
        stage.close();
    }
}
