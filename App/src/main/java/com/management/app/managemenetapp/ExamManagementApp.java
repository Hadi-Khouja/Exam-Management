package com.management.app.managemenetapp;

import com.management.app.types.enums.AcademicGrade;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for starting the Exam Management Application.
 */
public class ExamManagementApp extends Application {
    /**
     * Entry point for the application.
     *
     * @param stage The primary stage for the application.
     */
    @Override
    public void start(Stage stage) {
        stage = SceneManager.getInstance().getWindow();
        stage.show();
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}