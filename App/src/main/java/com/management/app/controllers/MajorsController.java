package com.management.app.controllers;

import com.management.app.components.EditTableCell;
import com.management.app.managemenetapp.DatabaseManager;
import com.management.app.managemenetapp.SceneManager;
import com.management.app.managemenetapp.Translator;
import com.management.app.types.Major;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class for the Majors view responsible for displaying a list of academic majors.
 */
public class MajorsController {
    @FXML
    private TableView<Major> table;
    @FXML
    private TableColumn<Major, String> majorNameColumn;
    @FXML
    private TableColumn<Major, String> employeeFirstNameColumn;
    @FXML
    private TableColumn<Major, String> employeeLastNameColumn;
    @FXML
    private TableColumn<Major, String> employeeEmailColumn;
    @FXML
    private TableColumn<Major, String> employeeNumberColumn;
    @FXML
    private TableColumn<Major, String> employeeRoomColumn;
    @FXML
    private TableColumn<Major, Major> editColum;

    /**
     * Initializes the Majors view. This method is automatically called by JavaFX after loading the FXML.
     * Retrieves the list of academic majors from the database and populates the TableView with custom elements.
     */
    @FXML
    private void initialize() {
        ObservableList<Major> majors = DatabaseManager.getInstance().getMajors();

        majorNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeFirstNameColumn.setCellValueFactory(examOfficeEmployee
                -> new ReadOnlyObjectWrapper<>(examOfficeEmployee.getValue().getEmployee().getFirstName()));
        employeeLastNameColumn.setCellValueFactory(examOfficeEmployee
                -> new ReadOnlyObjectWrapper<>(examOfficeEmployee.getValue().getEmployee().getLastName()));
        employeeEmailColumn.setCellValueFactory(examOfficeEmployee
                -> new ReadOnlyObjectWrapper<>(examOfficeEmployee.getValue().getEmployee().getEmailAddress()));
        employeeNumberColumn.setCellValueFactory(examOfficeEmployee
                -> new ReadOnlyObjectWrapper<>(examOfficeEmployee.getValue().getEmployee().getPhoneNumber()));
        employeeRoomColumn.setCellValueFactory(examOfficeEmployee
                -> new ReadOnlyObjectWrapper<>(examOfficeEmployee.getValue().getEmployee().getRoom()));
        editColum.setCellFactory(param
                -> new EditTableCell(table));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        table.setItems(majors);
    }

    /**
     * opens a new Window for adding new Major
     */
    @FXML
    private void onAddMajor() {
        String title = Translator.getInstance().translate("NewMajor");
        SceneManager.getInstance().openPopup(title, "add-major-popup.fxml", 700.0, 500.0);
    }
}
