module com.example.servermanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.testng;
    requires junit;
    requires org.junit.jupiter.api;


    opens com.example.servermanagement to javafx.fxml;
    exports com.example.servermanagement;
    exports com.example.servermanagement.controller;
    opens com.example.servermanagement.controller to javafx.fxml;
}