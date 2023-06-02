module com.example.redes {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.redes to javafx.fxml;
    exports com.example.redes;
}