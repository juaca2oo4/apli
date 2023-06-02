package com.example.redes;

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
        weightFT.setText("");
        Stage currentStage = (Stage) weightFT.getScene().getWindow();
        currentStage.hide();
    }

    @FXML
    void continueA(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        double weight = 0;
        String from="",to="";
        if(fromNodeTF.getText().isEmpty()) {
            alert.setTitle("Error!");
            alert.setHeaderText("Please complete de field 'From node:'");
            alert.showAndWait();
        } else if(toNodeTF.getText().isEmpty()) {
            alert.setTitle("Error!");
            alert.setHeaderText("Please complete de field 'To node:'");
            alert.showAndWait();
        }else if(weightFT.getText().isEmpty()) {
            alert.setTitle("Error!");
            alert.setHeaderText("Please complete de field 'Edge Weight:'");
            alert.showAndWait();
        }else {
            try {
                weight = Double.parseDouble(weightFT.getText());
            }catch (NumberFormatException er){
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid input of 'Edge Weight:'");
                alert.showAndWait();
            }
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
            Vertex<String> v1= Graph.getInstance().findVertex(from);
            Vertex<String> v2= Graph.getInstance().findVertex(to);
            Graph.getInstance().addEdge(v1,v2,weight);

        }
        fromNodeTF.setText("");
        toNodeTF.setText("");
        weightFT.setText("");
    }
}
