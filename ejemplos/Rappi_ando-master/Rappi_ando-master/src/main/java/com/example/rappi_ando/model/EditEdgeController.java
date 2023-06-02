package com.example.rappi_ando.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditEdgeController {

    private Graph graph = Graph.getInstance();

    @FXML
    private Button cancelBTN;

    @FXML
    private Button continueBTN;

    @FXML
    private Label label1;

    @FXML
    private TextField weightFT;

    @FXML
    void cancelA(ActionEvent event) {
        weightFT.setText("");
        Stage currentStage = (Stage) weightFT.getScene().getWindow();
        currentStage.hide();
    }

    @FXML
    void continueA(ActionEvent event) {
        System.out.println("hola");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        double weight = 0;
        if(weightFT.getText().isEmpty()) {
            alert.setTitle("Error!");
            alert.setHeaderText("Please complete de field 'Edge Weight:'");
            alert.showAndWait();
        }else {
            try {
                weight = Double.parseDouble(weightFT.getText());
            } catch (NumberFormatException er) {
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid input of 'Edge Weight:'");
                alert.showAndWait();
                weightFT.setText("");
            }
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText("The weight of this edge has been changed");
            graph.modifyEdge(graph.getFrom(),graph.getTo(),weight);
            weightFT.setText("");
            Stage currentStage = (Stage) weightFT.getScene().getWindow();
            currentStage.hide();
        }
    }

}
