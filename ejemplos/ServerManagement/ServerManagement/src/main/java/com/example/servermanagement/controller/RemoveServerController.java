package com.example.servermanagement.controller;

import com.example.servermanagement.model.GraphListaadyacencia;
import com.example.servermanagement.model.Vertex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemoveServerController {

    @FXML
    private TextField serverName;

    private GraphListaadyacencia graph;

    @FXML
    public void onRemoveServer(ActionEvent actionEvent){
        String name = serverName.getText();
        graph.remVertex(graph.findVertex(name));
    }

    @FXML
    public void onClose(ActionEvent event) {
        Stage stage = (Stage) serverName.getScene().getWindow();
        stage.close();
    }

    public GraphListaadyacencia getGraph() {
        return graph;
    }

    public void setGraph(GraphListaadyacencia graph) {
        this.graph = graph;
    }
}
