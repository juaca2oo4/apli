package com.example.redes.controller;

import com.example.redes.model.GraphMatriz;
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
                double weight = calculateWeight(v1, v2);
                System.out.println(weight);
                double weightFinal= Math.min(v1.getSpeed(),v2.getSpeed())*(1-(weight/299792));
                GraphMatriz.getInstance().addEdge(v1, v2, weightFinal);
            }
        }

        fromNodeTF.setText("");
        toNodeTF.setText("");
    }
    //8,246 alto
    //7,244 ancho
    private double calculateWeight(Vertex<String> v1, Vertex<String> v2) {
        double imageHeight = 1418.0; // Altura de la imagen en píxeles
        double imageWidth = 834.0; // Ancho de la imagen en píxeles
        double mapHeight = 8246.0; // Altura del mapa en kilómetros
        double mapWidth = 7244.0; // Ancho del mapa en kilómetros

        double scaleHorizontal = mapWidth / imageWidth;
        double scaleVertical = mapHeight / imageHeight;

        double mapX1 = v1.getX() * scaleHorizontal;
        double mapY1 = v1.getY() * scaleVertical;
        double mapX2 = v2.getX() * scaleHorizontal;
        double mapY2 = v2.getY() * scaleVertical;

        double lat1 = Math.toRadians(mapY1); // Coordenada Y representa la latitud
        double lon1 = Math.toRadians(mapX1)*-1; // Coordenada X representa la longitud
        double lat2 = Math.toRadians(mapY2);
        double lon2 = Math.toRadians(mapX2)*-1;

        if(v1.getY()<240){
            lat1 =lat1*-1;
        }
        if(v2.getY()<240){
            lat2 =lat2*-1;
        }

        double earthRadius = 6371; // Radio de la Tierra en kilómetros

        // Fórmula de Haversine para calcular la distancia entre dos puntos en la Tierra
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;
        return distance;
    }
}
