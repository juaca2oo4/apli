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
    private TextField traffic;

    @FXML
    void cancelA(ActionEvent event) {
        fromNodeTF.setText("");
        toNodeTF.setText("");
        traffic.setText("");
        Stage currentStage = (Stage) traffic.getScene().getWindow();
        currentStage.hide();
    }

    @FXML
    void continueA(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String from = fromNodeTF.getText();
        String to = toNodeTF.getText();
        String amount = traffic.getText();
        Vertex<String> v1 = GraphMatriz.getInstance().searchVertex(from);
        Vertex<String> v2 = GraphMatriz.getInstance().searchVertex(to);
        double factor = 1/Double.parseDouble(amount+1);
        GraphMatriz.getInstance().addTraffic(v1, v2, factor);

        fromNodeTF.setText(" ");
        toNodeTF.setText(" ");
        traffic.setText(" ");

    }
}
