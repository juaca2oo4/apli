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

public class RemoveConnection {

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
        String from="",to="";
        if(fromNodeTF.getText().isEmpty()) {
            alert.setTitle("Error!");
            alert.setHeaderText("Please complete de field 'From node:'");
            alert.showAndWait();
        } else if(toNodeTF.getText().isEmpty()) {
            alert.setTitle("Error!");
            alert.setHeaderText("Please complete de field 'To node:'");
            alert.showAndWait();
        }else {
            try {
                from=fromNodeTF.getText();
            }catch (NumberFormatException er){
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid input of 'From node:'");
                alert.showAndWait();
            }
            try {
                to=toNodeTF.getText();
            }catch (NumberFormatException er){
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid input of 'To node:'");
                alert.showAndWait();
            }
            Vertex v1= Graph.getInstance().findVertex(from);
            Vertex v2= Graph.getInstance().findVertex(to);
            Graph.getInstance().remEdge(v1,v2);
            Graph.getInstance().remEdge(v2,v1);
        }
        fromNodeTF.setText("");
        toNodeTF.setText("");
        weightFT.setText("");
    }

}
