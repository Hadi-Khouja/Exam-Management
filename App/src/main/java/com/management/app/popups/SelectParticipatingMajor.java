package com.management.app.popups;

import com.management.app.components.MajorListViewCell;
import com.management.app.managemenetapp.DatabaseManager;
import com.management.app.managemenetapp.FilesManager;
import com.management.app.types.Exam;
import com.management.app.types.Major;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class SelectParticipatingMajor {
    @FXML
    private ListView<Major> majorsListView;
    private Exam exam;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            exam = (Exam) majorsListView.getScene().getUserData();
            ObservableList<Major> majors = DatabaseManager.getInstance().getParticipatingMajors(exam);
            majorsListView.setCellFactory(majorsListView -> new MajorListViewCell());
            majorsListView.getItems().addAll(majors);
        });
    }

    @FXML
    private void selectMajor() {
        Major selectedItem = majorsListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            FilesManager.getInstance().createPdfGradesForExamOffice(exam, selectedItem);
        }
    }
}
