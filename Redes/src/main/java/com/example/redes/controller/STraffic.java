package com.example.redes.controller;

import com.example.redes.model.GraphMatriz;
import com.example.redes.model.Vertex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class STraffic {
    @FXML
    private Button cancelBTN;

    @FXML
    private Button continueBTN;

    @FXML
    private TextField fromNodeTF;

    @FXML
    private TextField toNodeTF;

    @FXML
    void cancelA(ActionEvent event) {
        fromNodeTF.setText("");
        toNodeTF.setText("");
        Stage currentStage = (Stage) toNodeTF.getScene().getWindow();
        currentStage.hide();
    }

    @FXML
    void continueA(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String from = fromNodeTF.getText();
        String to = toNodeTF.getText();

        if (from.isEmpty()) {
            alert.setTitle("Error!");
            alert.setHeaderText("Please complete the 'From node' field.");
            alert.showAndWait();
        } else if (to.isEmpty()) {
            alert.setTitle("Error!");
            alert.setHeaderText("Please complete the 'To node' field.");
            alert.showAndWait();
        } else {
            Vertex<String> v1 = GraphMatriz.getInstance().searchVertex(from);
            Vertex<String> v2 = GraphMatriz.getInstance().searchVertex(to);

            if (v1 == null) {
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid 'From node'. Node not found.");
                alert.showAndWait();
            } else if (v2 == null) {
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid 'To node'. Node not found.");
                alert.showAndWait();
            } else {
            }
        }

        fromNodeTF.setText("");
        toNodeTF.setText("");
    }
}
