package com.management.app.components;

import com.management.app.managemenetapp.DatabaseManager;
import com.management.app.managemenetapp.ExamManagementApp;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.types.Major;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.InputStream;

public class EditTableCell extends TableCell<Major, Major> {
    private final TableView<Major> table;

    public EditTableCell(TableView<Major> table) {
        this.table = table;
    }

    @Override
    public void updateItem(Major item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
            setText(null);

        } else {
            ImageView editImage = getImage("images/edit-square.png");
            ImageView deleteImage = getImage("images/trash.png");

            Button editButton = new Button("", editImage);
            editButton.setOnAction(event -> edit());
            editButton.getStyleClass().add("button-icon");

            Button deleteButton = new Button("", deleteImage);
            deleteButton.setOnAction(event -> delete());
            deleteButton.getStyleClass().add("button-icon");

            setGraphic(new HBox(editButton, deleteButton));
            setText(null);
        }
    }

    private void edit() {
        Major major = getTableRow().getItem();
        SceneManager.getInstance().openPopupWithData("Edit Major", "add-major-popup.fxml", 700.0, 500.0, major);
    }

    private void delete() {
        Major major = getTableRow().getItem();
        DatabaseManager.getInstance().deleteExamOfficeEmployee(major.getEmployee().getEmployeeId());
        table.getItems().remove(major);
        table.refresh();
    }

    /**
     * Creates an ImageView for an add icon in white
     *
     * @return add-white icon as an ImageView
     */
    private ImageView getImage(String path) {
        InputStream imageStream = ExamManagementApp.class.getResourceAsStream(path);
        if (imageStream == null)
            return null;

        Image icon = new Image(imageStream);
        return new ImageView(icon);
    }
}
