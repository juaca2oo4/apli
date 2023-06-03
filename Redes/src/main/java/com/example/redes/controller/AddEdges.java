package com.example.redes.controller;

import com.example.redes.model.Graph;
import com.example.redes.model.Vertex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEdges {

    @FXML
    private Button cancelBTN;

    @FXML
    private Button continueBTN;

    @FXML
    private TextField fromNodeTF;

    @FXML
    private Label label1;

    @FXML
    private TextField toNodeTF;

    @FXML
    private TextField weightFT;

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
            Vertex<String> v1 = Graph.getInstance().findVertex(from);
            Vertex<String> v2 = Graph.getInstance().findVertex(to);

            if (v1 == null) {
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid 'From node'. Node not found.");
                alert.showAndWait();
            } else if (v2 == null) {
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid 'To node'. Node not found.");
                alert.showAndWait();
            } else {
                double weight = calculateWeight(v1, v2);
                double weightFinal= Math.min(v1.getSpeed(),v2.getSpeed())*(1-(weight/299792));
                Graph.getInstance().addEdge(v1, v2, weightFinal);
            }
        }

        fromNodeTF.setText("");
        toNodeTF.setText("");
    }

    private double calculateWeight(Vertex<String> v1, Vertex<String> v2) {
        double lat1 = Math.toRadians(v1.getY()); // Coordenada Y representa la latitud
        double lon1 = Math.toRadians(v1.getX()); // Coordenada X representa la longitud
        double lat2 = Math.toRadians(v2.getY());
        double lon2 = Math.toRadians(v2.getX());

        double earthRadius = 6371; // Radio de la Tierra en kilómetros

        // Fórmula de Haversine para calcular la distancia entre dos puntos en la Tierra
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;
        System.out.println(distance);
        return distance;
    }
}
