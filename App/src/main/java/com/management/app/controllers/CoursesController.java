package com.management.app.controllers;

import com.management.app.dtos.EditExamDto;
import com.management.app.managemenetapp.*;
import com.management.app.types.Course;
import com.management.app.types.Exam;
import com.management.app.components.ExamListViewCell;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Controller class for managing courses and related UI components.
 */
public class CoursesController {
    public Accordion coursesAccordion;
    public Button addButton;

    /**
     * Initializes the CoursesController by populating the coursesAccordion with TitledPanes
     * containing ListView components for displaying exams related to each course.
     */
    @FXML
    private void initialize() {
        addButton.setOnAction(event -> onAdd(new Course("")));
        ArrayList<Course> courses = DatabaseManager.getInstance().getCourses(UserManager.getInstance().getCurrentUser());
        coursesAccordion.getPanes().addAll(getTitlePanes(courses));
    }

    public ArrayList<TitledPane> getTitlePanes(ArrayList<Course> courses) {
        ArrayList<TitledPane> titledPaneList = new ArrayList<>();

        for (Course course : courses) {
            ListView<Exam> listview = getExamListView(course);

            TitledPane titledPane = new TitledPane();
            titledPane.setContent(listview);
            titledPane.setGraphic(getBorderPane(course));
            titledPaneList.add(titledPane);
        }

        return titledPaneList;
    }

    public ListView<Exam> getExamListView(Course course) {
        ListView<Exam> listview = new ListView<>();
        listview.setCellFactory(examsListView -> new ExamListViewCell());
        listview.getItems().addAll(course.getExams());
        listview.setOnMouseClicked(event -> onListClick(listview));
        return listview;
    }


    /**
     * creates a container for TitledPane with course name and a button for adding new exams
     *
     * @return the container as a BorderPane
     */
    private BorderPane getBorderPane(Course course) {
        BorderPane borderPane = new BorderPane();
        borderPane.prefWidthProperty().setValue(948.0);

        Label title = new Label(course.getName());
        title.getStyleClass().add("text-fill-white");
        VBox vBox = new VBox(title);
        vBox.setAlignment(Pos.CENTER);

        ImageView addImage = getImage("images/add-accent.png");
        ImageView editImage = getImage("images/edit-square.png");
        ImageView deleteImage = getImage("images/trash.png");

        MenuButton editMenu = new MenuButton();
        editMenu.getStyleClass().add("button-icon");

        MenuItem addButton = new MenuItem("Add", addImage);
        addButton.setOnAction(event -> addExam(course));
        editMenu.getItems().add(addButton);

        MenuItem editButton = new MenuItem("edit", editImage);
        editButton.setOnAction(event -> onAdd(course));
        editMenu.getItems().add(editButton);

        MenuItem deleteButton = new MenuItem("delete", deleteImage);
        deleteButton.setOnAction(event -> onDeleteCourse(course));
        editMenu.getItems().add(deleteButton);

        borderPane.setLeft(vBox);
        borderPane.setRight(editMenu);
        return borderPane;
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

    /**
     * Handles the click event on the ListView of exams. If an exam is selected,
     * it loads the "exam-correction-view.fxml" scene with the selected exam as data.
     *
     * @param listView The ListView containing the exams.
     */
    @FXML
    private void onListClick(ListView<Exam> listView) {
        Exam selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            SceneManager.getInstance().loadSceneWithData("exam-correction-view.fxml", selectedItem);
        }
    }

    /**
     * opens a new window for adding a Course
     */
    @FXML
    private void onAdd(Course course) {
        String title = Translator.getInstance().translate("AddCourse");
        SceneManager.getInstance().openPopupWithData(title, "add-course-popup.fxml", 500, 250, course);
    }

    @FXML
    private void onDeleteCourse(Course course) {
        DatabaseManager.getInstance().deleteCourse(course);
        SceneManager.getInstance().loadScene("courses-view.fxml");
    }

    /**
     * opens a new window for adding a new Exam
     */
    @FXML
    private void addExam(Course course) {
        EditExamDto editExamDto = new EditExamDto(course, null);
        String title = course.getName() + " " + Translator.getInstance().translate("Exam");
        SceneManager.getInstance().openPopupWithData(title, "add-exam-popup.fxml", 700.0, 600.0, editExamDto);
    }
}
