package com.management.app.components;

import com.management.app.managemenetapp.ExamManagementApp;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.types.GradeLimits;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class GradeLimitListCell extends ListCell<GradeLimits> {
    @FXML
    public Label noteLabel;
    @FXML
    public Label minLabel;
    @FXML
    public Label maxLabel;
    @FXML
    public HBox listItem;
    private FXMLLoader fxmlLoader;

    /**
     * Override method to update the appearance of the ListCell based on the Exam object.
     *
     * @param gradeLimit The Exam object to be displayed.
     * @param empty      Indicates whether the cell is empty or not.
     */
    @Override
    protected void updateItem(GradeLimits gradeLimit, boolean empty) {
        super.updateItem(gradeLimit, empty);
        if (empty || gradeLimit == null) {
            setText(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(ExamManagementApp.class.getResource("components/grade-limit-list-cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    SceneManager.getInstance().showError(e.getLocalizedMessage());
                }
            }

            noteLabel.setText(gradeLimit.getNote());
            minLabel.setText(String.valueOf(gradeLimit.getMin()));
            maxLabel.setText(String.valueOf(gradeLimit.getMax()));

            setText(null);
            setGraphic(listItem);
        }
    }
}
