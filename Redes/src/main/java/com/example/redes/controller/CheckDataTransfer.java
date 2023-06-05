package com.example.redes.controller;

import com.example.redes.model.GraphMatriz;
import com.example.redes.model.Vertex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CheckDataTransfer{
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




    @FXML
    public void onCheckDataTransfer(ActionEvent actionEvent){

        String origin = originServer.getText();
        String goal = goalServer.getText();
        String amountD = amountData.getText();
        double amount = Double.parseDouble(amountD);

        GraphMatriz<String> g =  GraphMatriz.getInstance();

        g=g.adjustedWeights(amount);

        Vertex<String> v1 = g.searchVertex(origin);
        Vertex<String> v2 = g.searchVertex(goal);


       List<Vertex<String>> copy = g.dijkstra(v1, v2);

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

}
