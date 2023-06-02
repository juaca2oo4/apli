package com.example.redes;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;


public class Controller implements Initializable {

   


    public AnchorPane paneAP;
    public ToggleButton editEdgeBTN;
    public Pane pane;
   // private Gra graph = Graph.getInstance();

    private final Graph<String> graph =Graph.getInstance();
    private int nodesCounter = 1;


    private static class Delta {
        double x, y;
    }


    public ToggleButton addNodeTBTN;
    public ToggleButton deleteNodeTBTN;
    public ToggleButton addEdgeSBTN;
    public ToggleButton deleteEdgeTBTN;
    public ToggleButton dijkstraBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showGraph();
        pane= new Pane();
        paneAP.getChildren().add(pane);

    }

    void showGraph() {
        pane.setOnMouseClicked(mouseEvent -> {
            if (addNodeTBTN.isSelected()) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();

                Vertex<String> newVertex = new Vertex<>(String.valueOf(nodesCounter), x, y);
                graph.addVertex(newVertex);
                nodesCounter++;
                drawVerticesAndEdges();
                dselect();
            } else if (addEdgeSBTN.isSelected()) {
                // HelloApplication.hideWindow((Stage) pane.getScene().getWindow());
                HelloApplication.showWindow("addEdge");
                drawVerticesAndEdges();

            } else if (deleteNodeTBTN.isSelected()) {
                // Aquí puedes implementar la lógica para eliminar un nodo
            } else if (deleteEdgeTBTN.isSelected()) {
                // Aquí puedes implementar la lógica para eliminar una arista
            } else if (dijkstraBTN.isSelected()) {
                // Aquí puedes implementar la lógica para ejecutar el algoritmo de Dijkstra
            }
            drawVerticesAndEdges();
        });
    }

    public void addNode(ActionEvent actionEvent) {

    }

    public void deleteNode(ActionEvent actionEvent) {
    }

    public void addEdge(ActionEvent actionEvent) {
    }

    public void editEdge(ActionEvent actionEvent) {
    }

    public void deleteEdge(ActionEvent actionEvent) {
    }

    public void dijkstraA(ActionEvent actionEvent) {
    }

    public void dselect() {
        ToggleButton[] buttons = {deleteNodeTBTN, addEdgeSBTN, addNodeTBTN, deleteEdgeTBTN, dijkstraBTN};
        for (ToggleButton button : buttons) {
            button.setSelected(false);
        }
    }

    private void drawVerticesAndEdges() {
        pane.getChildren().clear();

        // Dibujar vértices
        for (Vertex<String> vertex : graph.getVertices()) {
            double x = vertex.getX();
            double y = vertex.getY();

            Circle nodeCircle = new Circle(x, y, 10);
            nodeCircle.setFill(Color.BLACK);
            pane.getChildren().add(nodeCircle);

            Text nodeText = new Text(vertex.getDato());
            nodeText.setFont(Font.font("Arial", FontWeight.BOLD, 5));
            nodeText.setFill(Color.WHITE);
            nodeText.setX(x - 5);
            nodeText.setY(y + 5);
            pane.getChildren().add(nodeText);
        }

        // Dibujar aristas
        for (Vertex<String> vertex : graph.getVertices()) {
            double startX = vertex.getX();
            double startY = vertex.getY();

            for (Map.Entry<Vertex<String>, Double> entry : vertex.getAdyacentes()) {
                Vertex<String> adjacentVertex = entry.getKey();
                double endX = adjacentVertex.getX();
                double endY = adjacentVertex.getY();

                Line edgeLine = new Line(startX, startY, endX, endY);
                edgeLine.setStroke(Color.RED);
                edgeLine.setStrokeWidth(2);
                pane.getChildren().add(edgeLine);
            }
        }
    }

}
