package com.management.app.popups;

import com.management.app.dtos.EditTaskDto;
import com.management.app.managemenetapp.DatabaseManager;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.types.Exam;
import com.management.app.types.Task;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTaskPopupController {
    public TextField questionTextField;
    public TextField taskNumberTextField;
    public TextField pointsTextField;
    private EditTaskDto editTaskDto;

    @FXML
    private void initialize() {
        Platform.runLater(this::setup);
    }

    private void setup() {
        editTaskDto = (EditTaskDto) questionTextField.getScene().getUserData();
        Task task = editTaskDto.getTask();

        if (task.getQuestionWithoutNumber().isBlank() && task.getNumber() == -1)
            return;

        questionTextField.setText(task.getQuestionWithoutNumber());
        taskNumberTextField.setText(String.valueOf(task.getNumber()));
        pointsTextField.setText(String.valueOf(task.getPoints()));
    }

    public void createNewTask() {
        int number = Integer.parseInt(taskNumberTextField.getText());
        double points = Double.parseDouble(pointsTextField.getText());
        String question = questionTextField.getText();

        Exam exam = editTaskDto.getExam();
        Task task = new Task(number, points, question);

        exam.getTasks().remove(editTaskDto.getTask());
        exam.addTask(task);

        DatabaseManager.getInstance().saveTask(task, exam.getExamId());
        SceneManager.getInstance().loadSceneWithData("exam-correction-view.fxml", exam);
        Stage stage = (Stage) questionTextField.getScene().getWindow();
        stage.close();
    }
}
