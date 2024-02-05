module com.management.app.managemenetapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.mail;
    requires mysql.connector.j;

    requires org.apache.pdfbox;
    requires java.desktop;
    requires io;
    requires kernel;
    requires layout;


    opens com.management.app.managemenetapp to javafx.fxml;
    exports com.management.app.managemenetapp;
    exports com.management.app.controllers;
    opens com.management.app.controllers to javafx.fxml;
    exports com.management.app.components;
    opens com.management.app.components to javafx.fxml;
    exports com.management.app.types;
    opens com.management.app.types to javafx.fxml;
    exports com.management.app.types.enums;
    opens com.management.app.types.enums to javafx.fxml;
    exports com.management.app.popups;
    opens com.management.app.popups to javafx.fxml;
}