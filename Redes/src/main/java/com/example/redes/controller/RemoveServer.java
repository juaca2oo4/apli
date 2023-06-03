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

public class RemoveServer {

    @FXML
    private Button cancelBTN;

    @FXML
    private Button continueBTN;

    @FXML
    private TextField NodeTF;

    @FXML
    private Label label1;


    @FXML
    void cancelA(ActionEvent event) {
        NodeTF.setText("");
        Stage currentStage = (Stage) NodeTF.getScene().getWindow();
        currentStage.hide();
    }

    @FXML
    void continueA(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String node="";
        if(NodeTF.getText().isEmpty()) {
            alert.setTitle("Error!");
            alert.setHeaderText("Please complete de field 'From node:'");
            alert.showAndWait();
        } else {
            try {
                node=NodeTF.getText();
            }catch (NumberFormatException er){
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid input of 'From node:'");
                alert.showAndWait();
            }
            Vertex v1= Graph.getInstance().findVertex(node);
            Graph.getInstance().remVertex(v1);

        }
        NodeTF.setText("");
    }
}
