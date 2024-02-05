package com.management.app.components;

import com.management.app.managemenetapp.ExamManagementApp;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.types.Major;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Custom JavaFX ListCell for displaying majors in add student popup.
 * This cell is designed to be used within a ListView and provides functionality for editing majors.
 */
public class MajorListViewCell extends ListCell<Major> {
    @FXML
    public Label majorName;
    @FXML
    public TextField majorNameTextField;
    @FXML
    public HBox listItem;
    private FXMLLoader fxmlLoader;

    /**
     * Updates the content of the cell with the provided EarnedPoints object.
     *
     * @param major The EarnedPoints object to be displayed in the cell.
     * @param empty A boolean indicating whether the cell is empty.
     */
    @Override
    protected void updateItem(Major major, boolean empty) {
        super.updateItem(major, empty);

        if (empty || major == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(ExamManagementApp.class.getResource("components/major-list-cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    SceneManager.getInstance().showError(e.getLocalizedMessage());
                }
            }

            setup(major);

            setText(null);
            setGraphic(listItem);
        }
    }

    /**
     * Sets up the visual components of the cell based on the provided EarnedPoints.
     *
     * @param major The EarnedPoints object to be displayed in the cell.
     */
    private void setup(Major major) {
        majorName.setText(String.valueOf(major.getName()));

        majorNameTextField.setOnAction(e -> {
            commitEdit(new Major(majorNameTextField.getText()));
            majorNameTextField.setVisible(false);
            majorName.setVisible(true);
        });
    }

    /**
     * Overrides the startEdit method to handle the start of editing.
     * Displays the TextField for editing earned points.
     */
    @Override
    public void startEdit() {
        super.startEdit();
        majorName.setVisible(false);
        majorNameTextField.setVisible(true);
        majorNameTextField.setText(majorName.getText());
        majorNameTextField.requestFocus();
        majorNameTextField.selectAll();
    }

    /**
     * Overrides the cancelEdit method to handle the cancellation of editing.
     * Hides the TextField and shows the earned points label.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        majorNameTextField.setVisible(false);
        majorName.setVisible(true);
    }
}
