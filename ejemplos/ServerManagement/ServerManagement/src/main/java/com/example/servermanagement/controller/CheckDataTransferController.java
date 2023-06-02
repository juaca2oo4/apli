package com.example.servermanagement.controller;

import com.example.servermanagement.model.GraphListaadyacencia;
import com.example.servermanagement.model.Vertex;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.testng.internal.junit.ArrayAsserts;
import com.example.servermanagement.model.*;

import java.util.ArrayList;

public class CheckDataTransferController<V extends Vertex<V>> {
    @FXML
    private TextField originServer;

    @FXML
    private TextField goalServer;

    @FXML
    private TextField amountData;

    @FXML
    private Button confirm;

    @FXML
    private Label path;

    @FXML
    private Label speed;

    private GraphListaadyacencia graph;

    @FXML
    public void onCheckDataTransfer(ActionEvent actionEvent){
        String origin = originServer.getText();
        String goal = goalServer.getText();
        String amountD = amountData.getText();
        double amount = Double.parseDouble(amountD);

        GraphListaadyacencia<V> g = graph.AdjustedWeights(graph, amount);

        Vertex v1 = g.findVertex(origin);
        Vertex v2 = g.findVertex(goal);


       ArrayList<Vertex<String>> copy = g.Dijsktra(v1, v2);

       String print ="";
       for(int i =0; i<copy.size(); i++){
           if(i==0){
               print += String.valueOf(copy.get(i).getDato());
           } else {
               print += ", " + String.valueOf(copy.get(i).getDato());
           }
       }

       int j = 0;
       int k = 0;

       for (int i=0; i<g.getVertices().size(); i++){
           if(g.getVertices().get(i)==v1){
               j=i;
           } else if (g.getVertices().get(i)==v2){
               k=i;
           }
       }
        double v = g.floydL()[j][k];

        path.setText(print);

        speed.setText(String.valueOf(v));
    }

    @FXML
    public void onClose(ActionEvent event) {
        Stage stage = (Stage) originServer.getScene().getWindow();
        stage.close();
    }

    public GraphListaadyacencia getGraph() {
        return graph;
    }

    public void setGraph(GraphListaadyacencia graph) {
        this.graph = graph;
    }
}
