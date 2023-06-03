package com.example.redes.controller;

import com.example.redes.MainApplication;
import com.example.redes.model.Graph;
import com.example.redes.model.Vertex;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class Controller implements Initializable {




    public AnchorPane paneAP;
    public ToggleButton editEdgeBTN;
    public Pane pane;
    // private Gra graph = Graph.getInstance();

    private final Graph<String> graph =Graph.getInstance();
    public TextField speed;
    private int nodesCounter = 1;


    private static class Delta {
        double x, y;
    }


    public ToggleButton addNodeTBTN;
    public ToggleButton deleteNodeTBTN;
    public ToggleButton addEdgeSBTN;
    public ToggleButton deleteEdgeTBTN;
    public ToggleButton dijkstraBTN;
    public ToggleButton saveBTN;
    public ToggleButton loadBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showGraph();
        pane= new Pane();
        paneAP.getChildren().add(pane);

    }

    void showGraph() {
        pane.setOnMouseClicked(mouseEvent -> {
            if (addNodeTBTN.isSelected()) {
                if(speed.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Please complete the 'From node' field.");
                    alert.showAndWait();
                    dselect();

                }else{
                    double x = mouseEvent.getX();
                    double y = mouseEvent.getY();

                    Vertex<String> newVertex = new Vertex<>(String.valueOf(nodesCounter), x, y,Double.parseDouble(speed.getText()));
                    graph.addVertex(newVertex);
                    nodesCounter++;
                    drawVerticesAndEdges();
                    dselect();
                }

            } else if (addEdgeSBTN.isSelected()) {
                // HelloApplication.hideWindow((Stage) pane.getScene().getWindow());

            } else if (deleteNodeTBTN.isSelected()) {
                // Aquí puedes implementar la lógica para eliminar un nodo
            } else if (deleteEdgeTBTN.isSelected()) {
                // Aquí puedes implementar la lógica para eliminar una arista
            } else if (dijkstraBTN.isSelected()) {
                // Aquí puedes implementar la lógica para ejecutar el algoritmo de Dijkstra
            } else if (saveBTN.isSelected()) {
                // Aquí puedes implementar la lógica para guardar en json
            } else if (loadBTN.isSelected()){
                // Aquí puedes implementar la lógica para load en json
            }
            drawVerticesAndEdges();
        });
    }

    public void addNode(ActionEvent actionEvent) {
    }

    public void deleteNode(ActionEvent actionEvent) {
        MainApplication.showWindow("removeServer");
    }

    public void addEdge(ActionEvent actionEvent) {
        MainApplication.showWindow("addEdge");

    }

    public void editEdge(ActionEvent actionEvent) {
        drawVerticesAndEdges();
        dselect();
    }

    public void deleteEdge(ActionEvent actionEvent) {
        MainApplication.showWindow("removeConnection");
        dselect();
    }


    //Este es el Dijkstra
    public void CheckDataTransfer(ActionEvent actionEvent) {
        MainApplication.showWindow("DataTransfer");
        dselect();
    }

    public void dselect() {
        ToggleButton[] buttons = {deleteNodeTBTN, addEdgeSBTN, addNodeTBTN, deleteEdgeTBTN, dijkstraBTN};
        for (ToggleButton button : buttons) {
            button.setSelected(false);
        }
    }

    public void saveGraph(){
        writeDataVertex("src\\main\\java\\com\\example\\redes\\Json\\dataVertex.txt");
        writeDataEdges("src\\main\\java\\com\\example\\redes\\Json\\dataEdges.txt");
        dselect();
    }

    public void loadGraph(){
        addDataVertex("src\\main\\java\\com\\example\\redes\\Json\\dataVertex.txt");
        addDataEdgeList("src\\main\\java\\com\\example\\redes\\Json\\dataEdges.txt");
        dselect();
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


    public void addDataVertex(String archivo) {
        File file = new File(archivo);
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] atributs = line.split(" ");
                Vertex<String>  vertex= new Vertex<>(atributs[0],Double.parseDouble(atributs[1]),Double.parseDouble(atributs[2]),Double.parseDouble(atributs[3]));
                graph.addVertex(vertex);
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addDataEdgeList(String archivo) {
        File file = new File(archivo);
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] atributs = line.split(",");
                Vertex<String>  vertex1= graph.findVertex(atributs[0]);
                Vertex<String>  vertex2= graph.findVertex(atributs[1]);

                graph.addEdge(vertex1,vertex2,Double.parseDouble(atributs[2]));

            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataVertex(String archivo) {
        File file = new File(archivo);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

            for (Vertex<String> vertex : graph.getVertices()) {
                String line = vertex.getDato() + " " + vertex.getX() + " " + vertex.getY() + " " + vertex.getSpeed();
                writer.write(line);
                writer.newLine();
            }

            writer.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeDataEdges(String archivo) {
        File file = new File(archivo);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

            for (Vertex<String> vertex : graph.getVertices()) {
                for (Map.Entry<Vertex<String>, Double> entry : vertex.getAdyacentes()) {
                    Vertex<String> neighborVertex = entry.getKey();
                    Double weight = entry.getValue();

                    String line = vertex.getDato() + " " + neighborVertex.getDato() + " " + weight;
                    writer.write(line);
                    writer.newLine();
                }
            }

            writer.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}