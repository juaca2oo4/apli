package com.example.redes.controller;

import com.example.redes.MainApplication;
import com.example.redes.model.Graph;
import com.example.redes.model.Vertex;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
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
import java.util.Map;
import java.util.ResourceBundle;


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
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();

                Vertex<String> newVertex = new Vertex<>(String.valueOf(nodesCounter), x, y);
                graph.addVertex(newVertex);
                nodesCounter++;
                drawVerticesAndEdges();
                dselect();
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
    }


    //Este es el Dijkstra
    public void CheckDataTransfer(ActionEvent actionEvent) {
        MainApplication.showWindow("DataTransfer");
    }

    public void dselect() {
        ToggleButton[] buttons = {deleteNodeTBTN, addEdgeSBTN, addNodeTBTN, deleteEdgeTBTN, dijkstraBTN};
        for (ToggleButton button : buttons) {
            button.setSelected(false);
        }
    }

    public void saveGraph(){
        saveGraphToJson("savedGraph.json");
    }

    public void loadGraph(){
        loadGraphFromJson("savedGraph.json");
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

    public void saveGraphToJson(String filePath) {
        Gson gson = new Gson();
        String folderPath = "src/main/java/com/example/redes/Json/";

        try {
            Path folder = Path.of("").toAbsolutePath().resolve(folderPath);
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }

            Path jsonFilePath = folder.resolve(filePath);
            if (!Files.exists(jsonFilePath)) {
                Files.createFile(jsonFilePath);
            }

            FileWriter writer = new FileWriter(jsonFilePath.toFile());
            String json = gson.toJson(Graph.getInstance());
            writer.write(json);
            writer.close();

            System.out.println("Graph saved to: " + jsonFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public void savedGraphToJson(String filePath) {
        Gson gson = new Gson();
        String json = gson.toJson(Graph.getInstance().getVertices());

        try {
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            fos.write(json.getBytes(StandardCharsets.UTF_8));
            fos.close();
            System.out.println("Vertex information saved to: " + filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     */
    public Graph<String> loadGraphFromJson(String filePath) {
        Gson gson = new Gson();
        File dataDirectory = new File("src/main/java/com/example/redes/Json/");
        File fileInfo = new File(dataDirectory, filePath);
        try (FileReader reader = new FileReader(fileInfo)) {
            Type graphType = new TypeToken<Graph<String>>() {}.getType();
            return gson.fromJson(reader, graphType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}