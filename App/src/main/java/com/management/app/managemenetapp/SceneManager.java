package com.management.app.managemenetapp;

import com.management.app.popups.ErrorPopup;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;

import java.net.URL;

/**
 * The SceneManager class is responsible for managing scenes and the primary application window.
 * It follows the Singleton pattern to ensure only one instance is created.
 */
public class SceneManager {
    private final Stage window;
    private static SceneManager instance;

    /**
     * Private constructor to prevent external instantiation.
     * Initializes the window and loads the default scene "login-view.fxml".
     */
    private SceneManager() {
        window = new Stage();
        window.setResizable(false);
        window.initStyle(StageStyle.UNDECORATED);
        loadScene("login-view.fxml", 1000, 650);
    }

    /**
     * Returns the singleton instance of the SceneManager.
     * If the instance does not exist, a new one is created.
     *
     * @return The SceneManager instance.
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    /**
     * Retrieves the primary window of the application.
     *
     * @return The primary window (Stage).
     */
    public Stage getWindow() {
        return window;
    }

    /**
     * Sets user data on the window and loads the scene from the provided FXML file path.
     *
     * @param fxmlFilePath The file path of the FXML file to load
     * @param value        The user data to be set on the window
     */
    public void loadSceneWithData(String fxmlFilePath, Object value) {
        window.setUserData(value);
        loadScene(fxmlFilePath);
    }

    /**
     * Loads a scene from the specified FXML file path, setting its width and height to default values (1000, 650).
     *
     * @param fxmlFilePath The file path of the FXML file to load
     */
    public void loadScene(String fxmlFilePath) {
        loadScene(fxmlFilePath, 1000, 650);
    }

    /**
     * Loads a scene from the specified FXML file path with the provided width and height.
     *
     * @param fxmlFilePath The file path of the FXML file to load
     * @param width        The width of the scene
     * @param height       The height of the scene
     */
    public void loadScene(String fxmlFilePath, double width, double height) {
        Scene currentScene = getSceneFromFxml("views/" + fxmlFilePath, width, height);

        if (currentScene == null) {
            showError("Could not load the fxml view");
            return;
        }

        window.setTitle(Translator.getInstance().translate("AppName"));
        window.setScene(currentScene);
    }

    /**
     * loads a Scene from a fxml file
     *
     * @param fxmlFilePath the path of the fxml file
     * @param width        the width of the Scene
     * @param height       the height of the Scene
     * @return the loaded Scene from the fxml file
     */
    public Scene getSceneFromFxml(String fxmlFilePath, double width, double height) {
        FXMLLoader fxmlLoader = new FXMLLoader(ExamManagementApp.class.getResource(fxmlFilePath));
        fxmlLoader.setResources(Translator.getInstance().getBundle());

        try {
            Scene scene = new Scene(fxmlLoader.load(), width, height);

            URL stylePath = ExamManagementApp.class.getResource("styles/styles.css");
            if (stylePath != null)
                scene.getStylesheets().add(stylePath.toExternalForm());

            return scene;
        } catch (Exception e) {
            showError(e.getMessage());
        }
        return null;
    }

    /**
     * Displays an error message in a separate stage with a predefined layout.
     *
     * @param errorMessage The message for the error
     */
    public void showError(String errorMessage) {
        showError(errorMessage, false);
    }

    public void showError(String errorMessage, Boolean isInfo) {
        String title = isInfo ? Translator.getInstance().translate("Info") : Translator.getInstance().translate("Error");

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(true);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = new FXMLLoader(ExamManagementApp.class.getResource("popups/error-popup.fxml"));
        fxmlLoader.setResources(Translator.getInstance().getBundle());

        try {
            Parent pane = fxmlLoader.load();
            ErrorPopup popup = fxmlLoader.getController();
            popup.setErrorMessage(errorMessage);

            if (isInfo) {
                popup.setInfoImage();
            }

            Scene scene = new Scene(pane);

            URL stylePath = ExamManagementApp.class.getResource("styles/styles.css");
            if (stylePath != null)
                scene.getStylesheets().add(stylePath.toExternalForm());

            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * opens a new non-resizable window that waits for user input
     *
     * @param title        the title of the window
     * @param fxmlFilePath the file path the fxml layout (must be under popups)
     * @param width        the width of the new window
     * @param height       the height of the new window
     */
    public void openPopupWithData(String title, String fxmlFilePath, double width, double height, Object value) {
        openPopupWithData(title, fxmlFilePath, width, height, false, true, value);
    }

    /**
     * opens a new window
     *
     * @param title        the title of the window
     * @param fxmlFilePath the file path the fxml layout (must be under popups)
     * @param width        the width of the new window
     * @param height       the height of the new window
     * @param resizable    if true the window can be resized
     * @param waitForClose if true the window blocks input to the parent window
     */
    public void openPopupWithData(String title, String fxmlFilePath, double width, double height, boolean resizable, boolean waitForClose, Object value) {
        Stage stage = new Stage();
        Scene scene = getSceneFromFxml("popups/" + fxmlFilePath, width, height);
        scene.setUserData(value);
        stage.setTitle(title);
        stage.setResizable(resizable);
        stage.setScene(scene);

        if (waitForClose) {
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } else {
            stage.show();
        }
    }

    /**
     * opens a new non-resizable window that waits for user input
     *
     * @param title        the title of the window
     * @param fxmlFilePath the file path the fxml layout (must be under popups)
     * @param width        the width of the new window
     * @param height       the height of the new window
     */
    public void openPopup(String title, String fxmlFilePath, double width, double height) {
        openPopup(title, fxmlFilePath, width, height, false, true);
    }

    /**
     * opens a new window
     *
     * @param title        the title of the window
     * @param fxmlFilePath the file path the fxml layout (must be under popups)
     * @param width        the width of the new window
     * @param height       the height of the new window
     * @param resizable    if true the window can be resized
     * @param waitForClose if true the window blocks input to the parent window
     */
    public void openPopup(String title, String fxmlFilePath, double width, double height, boolean resizable, boolean waitForClose) {
        Stage stage = new Stage();
        Scene scene = getSceneFromFxml("popups/" + fxmlFilePath, width, height);
        stage.setTitle(title);
        stage.setResizable(resizable);
        stage.setScene(scene);

        if (waitForClose) {
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } else {
            stage.show();
        }
    }
}