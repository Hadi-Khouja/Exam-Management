package com.management.app.popups;

import com.management.app.managemenetapp.ExamManagementApp;
import com.management.app.managemenetapp.Translator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class ErrorPopup {
    @FXML
    private ImageView image;
    @FXML
    private Label title;
    @FXML
    private Label errorMessageText;

    public void setErrorMessage(String errorMessage) {
        errorMessageText.setText(errorMessage);
    }

    public void setInfoImage() {
        title.setText(Translator.getInstance().translate("Info"));
        title.getStyleClass().removeLast();
        title.getStyleClass().add("text-fill-info");

        InputStream imageStream = ExamManagementApp.class.getResourceAsStream("images/info.png");
        if (imageStream == null)
            return;

        Image icon = new Image(imageStream);
        image.setImage(icon);
    }
}
