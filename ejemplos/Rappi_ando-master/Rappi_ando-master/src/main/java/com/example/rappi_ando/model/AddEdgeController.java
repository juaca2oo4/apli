package com.example.rappi_ando.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEdgeController {

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
        int from=0,to=0;
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
                    from=Integer.parseInt(fromNodeTF.getText());
                }catch (NumberFormatException er){
                    alert.setTitle("Error!");
                    alert.setHeaderText("Invalid input of 'From node:'");
                    alert.showAndWait();
                }
                try {
                    to=Integer.parseInt(toNodeTF.getText());
                }catch (NumberFormatException er){
                    alert.setTitle("Error!");
                    alert.setHeaderText("Invalid input of 'To node:'");
                    alert.showAndWait();
                }
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText(Graph.getInstance().addEdge(from+"",to+"",weight));
                alert2.showAndWait();
            }
        fromNodeTF.setText("");
        toNodeTF.setText("");
        weightFT.setText("");
    }

}
