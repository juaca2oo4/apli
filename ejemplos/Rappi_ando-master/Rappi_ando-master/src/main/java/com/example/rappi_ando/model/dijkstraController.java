package com.example.rappi_ando.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class dijkstraController {

    private Graph graph = Graph.getInstance();

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
    void cancelA(ActionEvent event) {
        fromNodeTF.setText("");
        toNodeTF.setText("");
        Stage currentStage = (Stage) fromNodeTF.getScene().getWindow();
        currentStage.hide();
    }

    @FXML void continueA(ActionEvent event) {
        System.out.println("idjdijdij");
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
        }else {
            try {
                from = Integer.parseInt(fromNodeTF.getText());
            } catch (NumberFormatException er) {
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid input of 'From node:'");
                alert.showAndWait();
            }
            try {
                to = Integer.parseInt(toNodeTF.getText());
            } catch (NumberFormatException er) {
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid input of 'To node:'");
                alert.showAndWait();
            }
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setHeaderText(graph.getPath(from+"",to+""));
            alert2.showAndWait();
            graph.setFrom(from+"");
            graph.setTo(to+"");

        }
    }

}
