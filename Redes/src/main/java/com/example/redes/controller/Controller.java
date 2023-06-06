package com.example.redes.controller;

import com.example.redes.MainApplication;
import com.example.redes.model.Edge;
import com.example.redes.model.GraphMatriz;
import com.example.redes.model.Vertex;

import java.io.*;

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

import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class Controller implements Initializable {




    public AnchorPane paneAP;
    public ToggleButton editEdgeBTN;
    public Pane pane;
    // private Gra graph = Graph.getInstance();

    private GraphMatriz<String> graph =GraphMatriz.getInstance();
    public TextField speed;
    public TextField concurrence;
    private int nodesCounter = 1;




    public ToggleButton addNodeTBTN;
    public ToggleButton deleteNodeTBTN;
    public ToggleButton addEdgeSBTN;
    public ToggleButton deleteEdgeTBTN;
    public ToggleButton dijkstraBTN;
    public ToggleButton saveBTN;
    public ToggleButton loadBTN;

    public ToggleButton trafficBTN;

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

            }

        });
    }

    public void addNode(ActionEvent actionEvent) {
        showGraph();
    }

    public void deleteNode(ActionEvent actionEvent) {
        MainApplication.showWindow("removeServer");
    }

    public void addEdge(ActionEvent actionEvent) {
        MainApplication.showWindow("addEdge");
        dselect();
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
        graph = new GraphMatriz<>(0);
        GraphMatriz.instance=graph;
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
            nodeText.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            nodeText.setFill(Color.WHITE);
            nodeText.setX(x - 5);
            nodeText.setY(y + 5);
            pane.getChildren().add(nodeText);
        }

        // Dibujar aristas
        for (int i = 0; i < graph.getVertices().size(); i++) {
            Vertex<String> vertex = graph.getVertices().get(i);
            double startX = vertex.getX();
            double startY = vertex.getY();

            for (int j = 0; j < graph.getVertices().size(); j++) {
                List<Edge<String>> edges = graph.getAdjacencyMatrix()[i][j];
                if (edges != null) {
                    for (Edge<String> edge : edges) {
                        Vertex<String> adjacentVertex = edge.getDestination();
                        double endX = adjacentVertex.getX();
                        double endY = adjacentVertex.getY();

                        // Calcular la dirección y la longitud de la línea
                        double dx = endX - startX;
                        double dy = endY - startY;
                        double length = Math.sqrt(dx * dx + dy * dy);
                        dx /= length;
                        dy /= length;

                        // Calcular las coordenadas de inicio y fin ajustadas
                        double startAdjustedX = startX + dx * 10; // 10 es el radio del círculo
                        double startAdjustedY = startY + dy * 10;
                        double endAdjustedX = endX - dx * 10;
                        double endAdjustedY = endY - dy * 10;

                        Line edgeLine = new Line(startAdjustedX, startAdjustedY, endAdjustedX, endAdjustedY);
                        edgeLine.setStroke(Color.RED);
                        edgeLine.setStrokeWidth(2);
                        pane.getChildren().add(edgeLine);

                        // Calcular las coordenadas medias
                        double midX = (startAdjustedX + endAdjustedX) / 2;
                        double midY = (startAdjustedY + endAdjustedY) / 2;

                        // Obtener el peso del edge
                        double weight = edge.getWeight();
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        String roundedNumber = decimalFormat.format(weight);

                        // Crear el texto con el peso en las coordenadas medias
                        Text edgeText = new Text(String.valueOf(roundedNumber));
                        edgeText.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                        edgeText.setFill(Color.BLACK);
                        edgeText.setX(midX - 10);  // Ajusta la posición del texto horizontalmente
                        edgeText.setY(midY);       // Ajusta la posición del texto verticalmente
                        pane.getChildren().add(edgeText);
                    }
                }
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
                nodesCounter++;
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
                String[] atributs = line.split(" ");
                Vertex<String>  vertex1= graph.searchVertex(atributs[0]);
                Vertex<String>  vertex2= graph.searchVertex(atributs[1]);

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
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos))) {

            for (int i = 0; i < graph.getVertices().size(); i++) {
                Vertex<String> vertex = graph.getVertices().get(i);
                for (int j = 0; j < graph.getVertices().size(); j++) {
                    List<Edge<String>> edges = graph.getAdjacencyMatrix()[i][j];
                    if (edges != null) {
                        for (Edge<String> edge : edges) {
                            Vertex<String> neighborVertex = edge.getDestination();
                            double weight = edge.getWeight();

                            String line = vertex.getDato() + " " + neighborVertex.getDato() + " " + weight;
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void concurrence(ActionEvent actionEvent) {

        if(!concurrence.getText().isEmpty()){
            graph.multiplyWeights((1/Double.parseDouble(concurrence.getText())));
        }
    }

    public void selectedTraffic(ActionEvent actionEvent) {
        MainApplication.showWindow("STraffic");
        dselect();
    }




}