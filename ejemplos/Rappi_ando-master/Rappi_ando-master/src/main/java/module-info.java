module com.example.rappi_ando {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rappi_ando to javafx.fxml;
    exports com.example.rappi_ando;
    exports com.example.rappi_ando.model;
    opens com.example.rappi_ando.model to javafx.fxml;
}