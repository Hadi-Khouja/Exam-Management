package com.management.app.components;

import com.management.app.dtos.EditExamDto;
import com.management.app.managemenetapp.DatabaseManager;
import com.management.app.managemenetapp.ExamManagementApp;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.managemenetapp.Translator;
import com.management.app.types.Exam;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * class representing a custom ListCell for displaying Exam objects in Courses ListView.
 */
public class ExamListViewCell extends ListCell<Exam> {
    @FXML
    public Label examName;
    @FXML
    public Label participantsNumber;
    @FXML
    public Label examDate;
    @FXML
    public Label passRate;
    @FXML
    public Label mean;
    @FXML
    public HBox listItem;
    @FXML
    public MenuItem editButton;
    @FXML
    public MenuItem deleteButton;
    private FXMLLoader fxmlLoader;
    private Exam item;

    /**
     * Override method to update the appearance of the ListCell based on the Exam object.
     *
     * @param exam  The Exam object to be displayed.
     * @param empty Indicates whether the cell is empty or not.
     */
    @Override
    protected void updateItem(Exam exam, boolean empty) {
        super.updateItem(exam, empty);
        if (empty || exam == null) {
            setText(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(ExamManagementApp.class.getResource("components/exam-list-view-cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    SceneManager.getInstance().showError(e.getLocalizedMessage());
                }
            }

            item = exam;
            editButton.setOnAction(event -> editExam());
            deleteButton.setOnAction(event -> deleteExam());
            String name = Translator.getInstance().translate("Exam") + " " + exam.getAssociatedCourse().getName() + " " + exam.getName();
            examName.setText(name);
            participantsNumber.setText(String.valueOf(exam.getParticipatingStudents().size()));
            examDate.setText(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(exam.getDate()));
            passRate.setText(DatabaseManager.getInstance().getExamPassRate(exam.getExamId()));
            mean.setText(DatabaseManager.getInstance().getExamMeanGrade(exam.getExamId()));

            setText(null);
            setGraphic(listItem);
        }
    }

    private void editExam() {
        EditExamDto editExamDto = new EditExamDto(item.getAssociatedCourse(), item);
        SceneManager.getInstance().openPopupWithData("Edit Exam", "add-exam-popup.fxml", 700.0, 600.0, editExamDto);
        SceneManager.getInstance().loadScene("courses-view.fxml");
    }

    private void deleteExam() {
        DatabaseManager.getInstance().deleteExam(item.getExamId());
        SceneManager.getInstance().loadScene("courses-view.fxml");
    }
}
