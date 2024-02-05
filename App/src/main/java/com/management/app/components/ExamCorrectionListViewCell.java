package com.management.app.components;

import com.management.app.managemenetapp.DatabaseManager;
import com.management.app.managemenetapp.ExamManagementApp;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.types.EarnedPoints;
import com.management.app.types.GradeLimits;
import com.management.app.types.enums.ExamListCellType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.io.InputStream;

/**
 * Custom JavaFX ListCell for displaying earned points in an exam correction view.
 * This cell is designed to be used within a ListView and provides functionality for editing earned points.
 */
public class ExamCorrectionListViewCell extends ListCell<EarnedPoints> {
    @FXML
    public ImageView image;
    @FXML
    public Label title;
    @FXML
    public Label earnedPointsLabel;
    @FXML
    public TextField earnedPointsTextField;
    @FXML
    public Label taskPoints;
    @FXML
    public HBox listItem;
    private FXMLLoader fxmlLoader;
    private ExamListCellType type;

    /**
     * Updates the content of the cell with the provided EarnedPoints object.
     *
     * @param earnedPoints The EarnedPoints object to be displayed in the cell.
     * @param empty        A boolean indicating whether the cell is empty.
     */
    @Override
    protected void updateItem(EarnedPoints earnedPoints, boolean empty) {
        super.updateItem(earnedPoints, empty);

        if (empty || earnedPoints == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(ExamManagementApp.class.getResource("components/exam-correction-list-cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    SceneManager.getInstance().showError(e.getLocalizedMessage());
                }
            }

            type = earnedPoints.getListCellType();
            switch (type) {
                case SUM -> setupSum(earnedPoints);
                case STUDENT -> setupStudent(earnedPoints);
                case TASK -> setupTask(earnedPoints);
                case BONUS -> setupBonus(earnedPoints);
            }

            setText(null);
            setGraphic(listItem);
        }
    }

    /**
     * Sets up the visual components of the cell based on the provided EarnedPoints.
     *
     * @param earnedPoints The EarnedPoints object to be displayed in the cell.
     */
    private void setup(EarnedPoints earnedPoints) {
        taskPoints.setText(String.valueOf(earnedPoints.getTask().getPoints()));
        earnedPointsLabel.setText(String.valueOf(earnedPoints.getPoints()));

        earnedPointsTextField.setOnAction(e -> {
            EarnedPoints newValue = new EarnedPoints(earnedPoints.getExam(), earnedPoints.getStudent(), earnedPoints.getTask(), Double.parseDouble(earnedPointsTextField.getText()));
            newValue.setListCellType(earnedPoints.getListCellType());
            commitEdit(newValue);
            DatabaseManager.getInstance().saveEarnedPoints(newValue);
            earnedPointsTextField.setVisible(false);
            earnedPointsLabel.setVisible(true);
        });
    }

    private void setupStudent(EarnedPoints earnedPoints) {
        title.setText(earnedPoints.getStudent().getMatriculationNumber() + " " + earnedPoints.getStudent().getFullName());
        setImage("images/person.png");
        setup(earnedPoints);
    }

    private void setupTask(EarnedPoints earnedPoints) {
        title.setText(earnedPoints.getTask().getQuestion());
        setImage("images/task.png");
        setup(earnedPoints);
    }

    private void setupBonus(EarnedPoints earnedPoints) {
        title.setText(earnedPoints.getStudent().getMatriculationNumber() + " " + earnedPoints.getStudent().getFullName());
        setImage("images/bonus.png");
        setup(earnedPoints);
    }

    private void setupSum(EarnedPoints earnedPoints) {
        title.setText("Sum");
        setImage("images/sum.png");
        taskPoints.setText(String.valueOf(earnedPoints.getTask().getPoints()));

        String sum = String.valueOf(calculateNote(earnedPoints));
        earnedPointsLabel.setText(sum);
    }

    private double calculateNote(EarnedPoints earnedPoints) {
        double note = DatabaseManager.getInstance().getSumOfPoints(earnedPoints.getExam().getExamId(), earnedPoints.getStudent());

        for (GradeLimits limit : earnedPoints.getExam().getGradeLimits()) {
            if (limit.getMin() <= note && note <= limit.getMax()) {
                String studentId = earnedPoints.getStudent().getMatriculationNumber();
                String examId = earnedPoints.getExam().getExamId();
                Boolean passed = note > earnedPoints.getExam().getPassingLimit();
                DatabaseManager.getInstance().saveGrade(studentId, examId, note, limit.getNote(), passed);
            }
        }

        return note;
    }

    /**
     * Overrides the startEdit method to handle the start of editing.
     * Displays the TextField for editing earned points.
     */
    @Override
    public void startEdit() {
        if (type.equals(ExamListCellType.SUM))
            return;

        super.startEdit();
        earnedPointsLabel.setVisible(false);
        earnedPointsTextField.setVisible(true);
        earnedPointsTextField.setText(earnedPointsLabel.getText());
        earnedPointsTextField.requestFocus();
        earnedPointsTextField.selectAll();
    }

    /**
     * Overrides the cancelEdit method to handle the cancellation of editing.
     * Hides the TextField and shows the earned points label.
     */
    @Override
    public void cancelEdit() {
        if (type.equals(ExamListCellType.SUM))
            return;

        super.cancelEdit();
        earnedPointsTextField.setVisible(false);
        earnedPointsLabel.setVisible(true);
    }

    /**
     * sets the icon of the ListItem
     *
     * @param pathToImage the path of the icon to set
     */
    private void setImage(String pathToImage) {
        InputStream imageStream = ExamManagementApp.class.getResourceAsStream(pathToImage);
        if (imageStream == null)
            return;

        Image icon = new Image(imageStream);
        image.setImage(icon);
    }
}
