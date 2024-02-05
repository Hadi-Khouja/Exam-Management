package com.management.app.components;

import com.management.app.managemenetapp.ExamManagementApp;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.managemenetapp.Translator;
import com.management.app.managemenetapp.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;

/**
 * Custom JavaFX component representing the application taskbar.
 * It includes buttons for navigating between different views and options like language settings and user profile.
 */
public class Taskbar extends VBox {
    @FXML
    protected Button studentsButton;
    @FXML
    protected Button majorsButton;
    @FXML
    protected Button lecturesButton;
    @FXML
    protected MenuButton settingsButton;
    @FXML
    protected MenuItem profileButton;
    @FXML
    protected MenuItem englishMenuItem;
    @FXML
    protected MenuItem germanMenuItem;
    @FXML
    protected MenuItem logoutButton;
    @FXML
    protected Button minusButton;
    @FXML
    protected Button closeButton;
    @FXML
    protected HBox titleBox;

    /**
     * Constructor for the Taskbar component.
     * Loads the associated FXML file, sets the root, controller, and resources for localization.
     * Initializes button actions.
     */
    public Taskbar() {
        URL path = ExamManagementApp.class.getResource("components/taskbar.fxml");

        FXMLLoader fxmlLoader = new FXMLLoader(path);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setResources(Translator.getInstance().getBundle());

        try {
            fxmlLoader.load();
        } catch (Exception e) {
            SceneManager.getInstance().showError("Could not load the Taskbar");
        }

        setupButton();
    }

    /**
     * Sets up the button actions for the Taskbar.
     */
    private void setupButton() {
        studentsButton.setOnAction(event -> onStudentsClick());
        lecturesButton.setOnAction(event -> onLecturesClick());
        profileButton.setOnAction(event -> onProfileClick());
        englishMenuItem.setOnAction(event -> onChangeLanguageToEnglish());
        germanMenuItem.setOnAction(event -> onChangeLanguageToGerman());
        logoutButton.setOnAction(event -> onLogout());
        majorsButton.setOnAction(event -> onMajorsClick());
        minusButton.setOnAction(event -> onMinimizeClick());
        closeButton.setOnAction(event -> onCloseClick());
        titleBox.setOnMouseDragged(this::onTitleDragged);
    }

    /**
     * Handles the action when the "Students" button is clicked.
     * Navigates to the students view.
     */
    @FXML
    private void onStudentsClick() {
        SceneManager.getInstance().loadScene("students-view.fxml");
    }

    /**
     * Handles the action when the "Majors" button is clicked.
     * Navigates to the majors view.
     */
    @FXML
    private void onMajorsClick() {
        SceneManager.getInstance().loadScene("major-view.fxml");
    }

    /**
     * Handles the action when the "Lectures" button is clicked.
     * Navigates to the courses view.
     */
    @FXML
    private void onLecturesClick() {
        SceneManager.getInstance().loadScene("courses-view.fxml");
    }

    /**
     * Handles the action when the "Profile" button is clicked.
     * Navigates to the user profile view.
     */
    @FXML
    private void onProfileClick() {
        SceneManager.getInstance().loadScene("profile-view.fxml");
    }

    @FXML
    private void onMinimizeClick() {
        Stage stage = (Stage) minusButton.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Handles the action when the "Profile" button is clicked.
     * Navigates to the user profile view.
     */
    @FXML
    private void onCloseClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onTitleDragged(MouseEvent event) {
        Stage stage = (Stage) titleBox.getScene().getWindow();
        stage.setX(event.getScreenX());
        stage.setY(event.getScreenY());
    }

    /**
     * Handles the action when the "Change Language to English" menu item is clicked.
     * Changes the application language to English.
     */
    @FXML
    private void onChangeLanguageToEnglish() {
        Translator.getInstance().changeLanguage(Locale.ENGLISH);
    }

    /**
     * Handles the action when the "Change Language to German" menu item is clicked.
     * Changes the application language to German.
     */
    @FXML
    private void onChangeLanguageToGerman() {
        Translator.getInstance().changeLanguage(Locale.GERMAN);
    }

    /**
     * Handles the action when the "Logout" menu item is clicked.
     * Logs out the current user and navigates to the login view.
     */
    @FXML
    private void onLogout() {
        UserManager.getInstance().logout();
        SceneManager.getInstance().loadScene("login-view.fxml");
    }

    /**
     * Gets the visibility status of the "Students" button.
     *
     * @return true if the button is visible, false otherwise.
     */
    public Boolean getHideStudents() {
        return studentsButton.isVisible();
    }

    /**
     * Gets the visibility status of the "Lectures" button.
     *
     * @return true if the button is visible, false otherwise.
     */
    public Boolean getHideLectures() {
        return lecturesButton.isVisible();
    }

    /**
     * Gets the visibility status of the "Settings" button.
     *
     * @return true if the button is visible, false otherwise.
     */
    public Boolean getHideSettings() {
        return settingsButton.isVisible();
    }

    /**
     * Gets the visibility status of the "Majors" button.
     *
     * @return true if the button is visible, false otherwise.
     */
    public Boolean getHideMajor() {
        return majorsButton.isVisible();
    }

    /**
     * Sets the visibility status of the "Majors" button.
     *
     * @param hideStudents true to hide the button, false to show it.
     */
    public void setHideMajor(Boolean hideStudents) {
        majorsButton.setVisible(!hideStudents);
    }

    /**
     * Sets the visibility status of the "Students" button.
     *
     * @param hideStudents true to hide the button, false to show it.
     */
    public void setHideStudents(Boolean hideStudents) {
        studentsButton.setVisible(!hideStudents);
    }

    /**
     * Sets the visibility status of the "Lectures" button.
     *
     * @param hideLectures true to hide the button, false to show it.
     */
    public void setHideLectures(Boolean hideLectures) {
        lecturesButton.setVisible(!hideLectures);
    }

    /**
     * Sets the visibility status of the "Settings" button.
     *
     * @param hideSettings true to hide the button, false to show it.
     */
    public void setHideSettings(Boolean hideSettings) {
        settingsButton.setVisible(!hideSettings);
    }
}
