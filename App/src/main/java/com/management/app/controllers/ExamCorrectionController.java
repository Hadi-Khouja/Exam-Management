package com.management.app.controllers;

import com.management.app.components.ExamCorrectionListViewCell;
import com.management.app.dtos.EditStudentDto;
import com.management.app.dtos.EditTaskDto;
import com.management.app.managemenetapp.*;
import com.management.app.types.*;
import com.management.app.types.enums.BonusType;
import com.management.app.types.enums.ExamListCellType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for handling the exam correction functionality in the Exam Correction view.
 */
public class ExamCorrectionController {
    @FXML
    private Button addTaskButton;
    @FXML
    private Label examTitle;
    @FXML
    private ChoiceBox<BonusType> bonusChoiceBox;
    @FXML
    private TabPane tabPane;
    @FXML
    private Accordion studentsAccordion;
    @FXML
    private Accordion tasksAccordion;

    private static TitledPane bonusPane;

    private Exam exam;

    //region setup

    /**
     * Initializes the ExamCorrectionController.
     * Sets up the UI components and populates them with relevant data from the provided Exam.
     */
    @FXML
    private void initialize() {
        exam = (Exam) SceneManager.getInstance().getWindow().getUserData();
        String name = Translator.getInstance().translate("Exam") + " " + exam.getAssociatedCourse().getName() + " " + exam.getName();
        examTitle.setText(name);
        System.out.println(exam.getExamId());
        addTaskButton.setOnAction(event -> onAddTask(new Task(-1, 0.0, "")));

        tabPane.tabMinWidthProperty().bind(tabPane.widthProperty().multiply(0.5));
        studentsAccordion.getPanes().addAll(getStudentPanes(exam));
        tasksAccordion.getPanes().addAll(getTasksPanes(exam));
        Platform.runLater(this::setupBonus);
    }

    /**
     * Retrieves a list of EarnedPoints for a specific exam, task, and a list of students.
     *
     * @param task     The task for which EarnedPoints are retrieved.
     * @param students The list of students for whom EarnedPoints are retrieved.
     * @return An ObservableList containing EarnedPoints for each student.
     */
    private ObservableList<EarnedPoints> getEarnedPoints(Task task, List<Student> students) {
        ObservableList<EarnedPoints> items = FXCollections.observableArrayList();
        for (Student student : students) {
            EarnedPoints earnedPoints = DatabaseManager.getInstance().getEarnedPoints(exam, task, student);
            earnedPoints.setListCellType(ExamListCellType.STUDENT);
            items.add(earnedPoints);
        }

        return items;
    }

    /**
     * Retrieves a list of EarnedPoints for a specific exam, a list of tasks, and a single student.
     *
     * @param tasks   The list of tasks for which EarnedPoints are retrieved.
     * @param student The student for whom EarnedPoints are retrieved.
     * @return An ObservableList containing EarnedPoints for each task and the specified student.
     */
    private ObservableList<EarnedPoints> getEarnedPointsStudent(List<Task> tasks, Student student) {
        ObservableList<EarnedPoints> items = FXCollections.observableArrayList();
        for (Task task : tasks) {
            EarnedPoints earnedPoints = DatabaseManager.getInstance().getEarnedPoints(exam, task, student);
            earnedPoints.setListCellType(ExamListCellType.TASK);
            items.add(earnedPoints);
        }

        EarnedPoints sum = new EarnedPoints(exam, student, new Task(exam.getMaximumPoints(), "Sum"));
        sum.setListCellType(ExamListCellType.SUM);
        items.addFirst(sum);

        return items;
    }

    /**
     * Sets up the bonus section in the Exam Correction view by populating and configuring
     * the bonusListView, bonusChoiceBox, and bonusPane based on the provided Exam.
     */
    private void setupBonus() {
        bonusChoiceBox.setValue(exam.getRecordBonus());
        bonusChoiceBox.getItems().addAll(BonusType.OFF, BonusType.STANDARD, BonusType.ALWAYSACTIVE);

        Task task = new Task(20, "Bonus");

        ListView<EarnedPoints> bonusListView = new ListView<>();
        bonusListView.setCellFactory(studentsListView -> new ExamCorrectionListViewCell());
        bonusListView.setEditable(true);

        for (Student student : exam.getParticipatingStudents()) {
            EarnedPoints earnedPoints = DatabaseManager.getInstance().getEarnedPoints(exam, task, student);
            earnedPoints.setListCellType(ExamListCellType.BONUS);
            bonusListView.getItems().add(earnedPoints);
        }

        bonusPane = new TitledPane();
        bonusPane.setText("Bonus");
        bonusPane.setContent(bonusListView);
    }

    /**
     * Retrieves a list of TitledPanes, each representing a task in the Exam Correction view.
     * Each TitledPane contains a ListView displaying EarnedPoints for the corresponding task.
     *
     * @param exam The Exam object containing information about tasks.
     * @return A List of TitledPanes, each representing a task with associated EarnedPoints.
     */
    private List<TitledPane> getTasksPanes(Exam exam) {
        List<TitledPane> paneList = new ArrayList<>();

        for (Task task : exam.getTasks()) {
            ListView<EarnedPoints> listView = new ListView<>(getEarnedPoints(task, exam.getParticipatingStudents()));
            listView.setEditable(true);
            listView.setCellFactory(studentsListView -> new ExamCorrectionListViewCell());
            TitledPane pane = new TitledPane();
            pane.setGraphic(getBorderPane(task));
            pane.setContent(listView);
            paneList.add(pane);
        }
        return paneList;
    }

    /**
     * Retrieves a list of TitledPanes, each representing a student in the Exam Correction view.
     * Each TitledPane contains a ListView displaying EarnedPoints for the corresponding student across all tasks.
     *
     * @param exam The Exam object containing information about participating students and tasks.
     * @return A List of TitledPanes, each representing a student with associated EarnedPoints.
     */
    private List<TitledPane> getStudentPanes(Exam exam) {
        List<TitledPane> paneList = new ArrayList<>();

        for (Student student : exam.getParticipatingStudents()) {
            ListView<EarnedPoints> listView = new ListView<>(getEarnedPointsStudent(exam.getTasks(), student));
            listView.setEditable(true);
            listView.setCellFactory(tasksListView -> new ExamCorrectionListViewCell());
            TitledPane pane = new TitledPane();
            pane.setGraphic(getStudentBorderPane(student));
            pane.setContent(listView);
            paneList.add(pane);
        }
        return paneList;
    }

    /**
     * creates a container for TitledPane with Task name and a button for editing and deleting tasks
     *
     * @return the container as a BorderPane
     */
    private BorderPane getBorderPane(Task task) {
        BorderPane borderPane = new BorderPane();
        borderPane.prefWidthProperty().setValue(948.0);

        Label title = new Label(task.getQuestion());
        title.getStyleClass().add("text-fill-white");
        VBox vBox = new VBox(title);
        vBox.setAlignment(Pos.CENTER);

        ImageView editImage = getImage("images/edit-square.png");
        ImageView deleteImage = getImage("images/trash.png");

        MenuButton editMenu = new MenuButton();
        editMenu.getStyleClass().add("button-icon");

        MenuItem editButton = new MenuItem("edit", editImage);
        editButton.setOnAction(event -> onAddTask(task));
        editMenu.getItems().add(editButton);

        MenuItem deleteButton = new MenuItem("delete", deleteImage);
        deleteButton.setOnAction(event -> deleteTask(task));
        editMenu.getItems().add(deleteButton);

        borderPane.setLeft(vBox);
        borderPane.setRight(editMenu);
        return borderPane;
    }

    /**
     * creates a container for TitledPane with course name and a button for adding new exams
     *
     * @return the container as a BorderPane
     */
    private BorderPane getStudentBorderPane(Student student) {
        BorderPane borderPane = new BorderPane();
        borderPane.prefWidthProperty().setValue(948.0);

        Label title = new Label(student.getMatriculationNumber() + " " + student.getFullName());
        title.getStyleClass().add("text-fill-white");
        VBox vBox = new VBox(title);
        vBox.setAlignment(Pos.CENTER);

        ImageView deleteImage = getImage("images/trash.png");

        MenuButton editMenu = new MenuButton();
        editMenu.getStyleClass().add("button-icon");

        MenuItem deleteButton = new MenuItem("delete", deleteImage);
        deleteButton.setOnAction(event -> deleteStudent(student));
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
    //endregion

    //region Input handling

    /**
     * Navigates back to the Courses view by loading the "courses-view.fxml" scene.
     */
    @FXML
    private void backToCourses() {
        SceneManager.getInstance().loadScene("courses-view.fxml");
    }

    @FXML
    private void onAddStudent() {
        String title = Translator.getInstance().translate("AddStudent");
        Student student = new Student("", "", "");
        EditStudentDto editStudentDto = new EditStudentDto(exam.getExamId(), student);
        SceneManager.getInstance().openPopupWithData(title, "add-student-popup.fxml", 700.0, 600.0, editStudentDto);
    }

    @FXML
    private void onAddTask(Task task) {
        EditTaskDto editTaskDto = new EditTaskDto(exam, task);
        SceneManager.getInstance().openPopupWithData("Add Task", "add-task-popup.fxml", 500.0, 250, editTaskDto);
    }

    @FXML
    private void deleteTask(Task task) {
        exam.getTasks().remove(task);
        DatabaseManager.getInstance().deleteTask(task, exam.getExamId());
        SceneManager.getInstance().loadSceneWithData("exam-correction-view.fxml", exam);
    }

    @FXML
    private void deleteStudent(Student student) {
        exam.getParticipatingStudents().remove(student);
        DatabaseManager.getInstance().deleteParticipatingStudent(student, exam.getExamId());
        SceneManager.getInstance().loadSceneWithData("exam-correction-view.fxml", exam);
    }

    /**
     * Toggles the display of the bonus section in the Exam Correction view based on the selected BonusType.
     * If the selected BonusType is STANDARD or ALWAYSACTIVE, the bonus section is added to the tasksAccordion.
     * If the selected BonusType is null or other, the bonus section is removed from the tasksAccordion.
     * Additionally, the corresponding EarnedPoints entries are adjusted in the studentsAccordion.
     */
    @FXML
    private void toggleBonus() {
        switch (bonusChoiceBox.getValue()) {
            case STANDARD, ALWAYSACTIVE -> {
                if (tasksAccordion.getPanes().isEmpty() || tasksAccordion.getPanes().getLast().equals(bonusPane))
                    return;

                tasksAccordion.getPanes().add(bonusPane);
            }
            case null, default -> tasksAccordion.getPanes().remove(bonusPane);
        }
    }
    //endregion

    //region Creating Statistics

    /**
     * Creates and displays a histogram statistical chart representing grade distribution.
     * The chart includes data points for different grade levels along with their corresponding frequencies.
     * The chart is customized with a specific color for better visibility.
     */
    @FXML
    private void createHistogramStatistic() {
        XYChart.Series<String, Number> series = DatabaseManager.getInstance().getHistogramData(exam.getExamId());

        String title = Translator.getInstance().translate("StatisticHistogram");
        createStatistic(series, title);
    }

    /**
     * Creates and displays a statistical chart representing point allocation for different tasks.
     * The chart includes data points for each task along with the corresponding point allocations.
     * The chart is customized with a specific color for better visibility.
     */
    @FXML
    private void createPointAllocationTaskStatistic() {
        XYChart.Series<String, Number> series = DatabaseManager.getInstance().getTasksStatistic(exam.getExamId());

        String title = Translator.getInstance().translate("StatisticPointAllocationTask");
        createStatistic(series, title);
    }

    /**
     * Creates and displays a statistical chart representing point allocation for different academic majors.
     * The chart includes data points for each major along with the corresponding point allocations.
     * The chart is customized with a specific color for better visibility.
     */
    @FXML
    private void createPointAllocationMajorStatistic() {
        XYChart.Series<String, Number> series2 = DatabaseManager.getInstance().getMajorPointsStatistic(exam.getExamId());

        String title = Translator.getInstance().translate("StatisticPointAllocationMajor");
        createStatistic(series2, title);
    }

    /***
     * Creates and displays a statistical chart representing the pass rate for different academic majors.
     * The chart includes stacked bars for each major, indicating the percentage of students who passed (Bestanden)
     * and the percentage of students who did not pass (Nicht Bestanden).
     * The chart is customized with specific colors for better visibility.
     */
    @FXML
    private void createPassRateStatistic() {
        ArrayList<XYChart.Series<String, Number>> list = DatabaseManager.getInstance().getMajorPassingRate(exam.getExamId());

        XYChart.Series<String, Number> passedSeries = list.getFirst();
        XYChart.Series<String, Number> failedSeries = list.getLast();

        String title = Translator.getInstance().translate("StatisticPassRate");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        StackedBarChart<String, Number> chart = new StackedBarChart<>(xAxis, yAxis);

        passedSeries.setName("Bestanden");
        failedSeries.setName("Nicht Bestanden");
        chart.getData().add(passedSeries);
        chart.getData().add(failedSeries);

        showStatistic(title, chart);
    }

    /**
     * Creates and displays a statistical chart with the specified series and title.
     * The chart includes data points for a specific statistical analysis.
     *
     * @param series The XYChart.Series containing data points for the statistical analysis.
     * @param title  The title for the statistical chart.
     */
    private void createStatistic(XYChart.Series<String, Number> series, String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);

        series.setName(title);
        chart.getData().add(series);
        showStatistic(title, chart);
    }

    /**
     * Displays a statistical chart with the specified title and BarChart.
     * The method creates a new stage and sets the provided chart as its scene.
     *
     * @param title The title for the statistical chart.
     * @param chart The BarChart to be displayed.
     * @param <X>   The type of the x-axis values in the chart.
     * @param <Y>   The type of the y-axis values in the chart
     */
    private <X, Y> void showStatistic(String title, XYChart<X, Y> chart) {
        Stage stage = new Stage();
        stage.setTitle(title);

        Scene scene = new Scene(chart, 700.0, 500.0);
        URL stylePath = ExamManagementApp.class.getResource("styles/statistic-styles.css");
        if (stylePath != null)
            scene.getStylesheets().add(stylePath.toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
    //endregion

    //region Creating Pdfs
    @FXML
    private void createGradesAnnouncement() {
        FilesManager.getInstance().createPdfGradesAnnouncement(exam);
    }

    @FXML
    private void createGradesForExamOffice() {
        SceneManager.getInstance().openPopupWithData("Select Exam", "select-major-popup.fxml", 400.0, 500.0, exam);
    }

    public void onImportClick() {
        FilesManager.getInstance().importFromCSV(exam.getExamId());
    }
    //endregion
}
