package com.example.servermanagement.controller;

import com.example.servermanagement.MainApplication;
import com.example.servermanagement.model.GraphListaadyacencia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;


public class MainController {

    private GraphListaadyacencia graph;

    public void initialize() {
        GraphListaadyacencia g = new GraphListaadyacencia<>();
        this.graph = g;
    }

    @FXML
    public void onAddServer(ActionEvent event) {
        FXMLLoader loader = MainApplication.renderView("addServer-view.fxml");
        AddServerController addServerController = loader.getController();
        addServerController.setGraph(graph);
    }

    public void onRemoveServer(ActionEvent actionEvent) {
        FXMLLoader loader = MainApplication.renderView("removeServer-view.fxml");
        RemoveServerController removeServerController = loader.getController();
        removeServerController.setGraph(graph);
    }

    public void onAddConnection(ActionEvent actionEvent) {
        FXMLLoader loader = MainApplication.renderView("addConnection-view.fxml");
        AddConnectionController addConnectionController = loader.getController();
        addConnectionController.setGraph(graph);
    }

    public void onRemoveConnection(ActionEvent actionEvent) {
        FXMLLoader loader = MainApplication.renderView("removeConnection-view.fxml");
        RemoveConnectionController removeConnectionController = loader.getController();
        removeConnectionController.setGraph(graph);
    }

    public void onCheckDataTransfer(ActionEvent actionEvent) {
        FXMLLoader loader = MainApplication.renderView("checkDataTransfer-view.fxml");
        CheckDataTransferController checkDataTransferController = loader.getController();
        checkDataTransferController.setGraph(graph);
    }



    public GraphListaadyacencia getGraph() {
        return graph;
    }

    public void setGraph(GraphListaadyacencia graph) {
        this.graph = graph;
    }
}