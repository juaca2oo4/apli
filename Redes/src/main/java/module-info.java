module com.example.redes {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.redes to javafx.fxml;
    exports com.example.redes;
    exports com.example.redes.model;
    opens com.example.redes.model to javafx.fxml;
    exports com.example.redes.controller;
    opens com.example.redes.controller to javafx.fxml;
}