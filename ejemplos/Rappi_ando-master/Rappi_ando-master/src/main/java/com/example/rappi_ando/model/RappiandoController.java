package com.example.rappi_ando.model;

import com.example.rappi_ando.HelloApplication;
import javafx.animation.PathTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;


public class RappiandoController implements Initializable {

    private Graph graph = Graph.getInstance();

    @FXML
    private ToggleButton addEdgeSBTN;

    @FXML
    private ToggleButton editEdgeBTN;

    @FXML
    private ToggleButton addNodeTBTN;

    @FXML
    private ToggleButton deleteEdgeTBTN;

    @FXML
    private ToggleButton deleteNodeTBTN;

    @FXML
    private AnchorPane paneAP;

    @FXML
    private ScrollPane mapSP;
    private boolean flag=false;
/*
    @FXML
    private ToggleButton primTBTN;


 */
    @FXML
    private ToggleButton dijkstraBTN;

    private DoubleProperty endX;
    private DoubleProperty endY;
    private Line newEdge ;
    private int nodesCounter=1;

    private static class Delta { double x, y; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nodesCounter=graph.getNodes().size();
        showGraph();
    }
    void showGraph(){
        AnchorPane tempPane = new AnchorPane();
        tempPane.setPrefWidth(820);
        tempPane.setPrefHeight(1420);

        tempPane.setOnMouseClicked(mouseEvent ->{
            if(addNodeTBTN.isSelected()) {
                tempPane.getChildren().clear();
                graph.addNode(mouseEvent.getX() ,mouseEvent.getY(),nodesCounter+"");
                deSelect();
                getGraphPane(tempPane);
                nodesCounter=nodesCounter+1;
            } else if (dijkstraBTN.isSelected()) {
                animatePath(tempPane,graph.getFrom(),graph.getTo());
                deSelect();
            } else {
                tempPane.getChildren().clear();
                getGraphPane(tempPane);
            }
        });
        paneAP.getChildren().add(tempPane);
    }
    @FXML
    void deleteEdge(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deleting edge");
        alert.setHeaderText("Click the edge you want to delete");
        alert.showAndWait();
    }

    @FXML
    void deleteNode(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deleting Node");
        alert.setHeaderText("Click the node you want to remove");
        alert.showAndWait();
    }
    @FXML
    void editEdge(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Editing edge");
        alert.setHeaderText("Click the edge you want to edit");
        alert.showAndWait();
    }
    @FXML
    void dijkstraA(ActionEvent event) {
        AnchorPane tempPane = new AnchorPane();
        tempPane.setPrefWidth(820);
        tempPane.setPrefHeight(1420);
        HelloApplication.showTransparentWindow("dijkstra.fxml");
    }

    @FXML
    void prim(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Prim!");
        alert.setHeaderText("Select a nodo in which you want to perform prim");
        alert.showAndWait();
    }
    @FXML
    void addEdge(ActionEvent event) {
        HelloApplication.showTransparentWindow("addEdge.fxml");
        deSelect();
    }

    @FXML
    void addNode(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Adding Node");
        alert.setHeaderText("Click on where you want to place the new node");
        alert.showAndWait();
    }
    private void enableEditDelete(AnchorPane pane, final Line line, Edge edge){
        line.setOnMouseReleased(event -> {
            line.getScene().setCursor(Cursor.HAND);
            if(deleteEdgeTBTN.isSelected()){
                graph.deleteEdge(edge.source.name,edge.destination.name);
                pane.getChildren().clear();
                getGraphPane(pane);
                deSelect();
            }
            if(editEdgeBTN.isSelected()){
                graph.setFrom(edge.source.name);
                graph.setTo(edge.destination.name);
                HelloApplication.showTransparentWindow("editEdge.fxml");
                deSelect();
                pane.getChildren().clear();
                getGraphPane(pane);
            }
        });
        line.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                line.getScene().setCursor(Cursor.HAND);
            }
        });
        line.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                line.getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }
    public void deSelect(){
        deleteNodeTBTN.setSelected(false);
        addEdgeSBTN.setSelected(false);
        addNodeTBTN.setSelected(false);
        editEdgeBTN.setSelected(false);
        deleteEdgeTBTN.setSelected(false);
        dijkstraBTN.setSelected(false);
    }

    void getGraphPane(AnchorPane tempPane){
        ArrayList<Edge> edgeArrayList = new ArrayList<>();
        graph.copyEdge(edgeArrayList);

        for(Edge e : edgeArrayList){
            tempPane.getChildren().addAll(e.getLine(),e.getText());
            enableEditDelete(tempPane,e.getLine(),e);
        }
        for(int i=0;i<graph.getNodes().size();i++){
            tempPane.getChildren().add(graph.getNodes().get(i).getCircle());
            tempPane.getChildren().add(graph.getNodes().get(i).getText());
            enableDrag(tempPane, graph.getNodes().get(i).getCircle(), graph.getNodes().get(i));

        }
        graph.saveData("Data");
    }
    private void enableDrag(AnchorPane tempPane,final Circle circle,Node node) {
        final Delta dragDelta = new Delta();
        final String[] source = new String[1];
        circle.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            if(!addNodeTBTN.isSelected() && !addEdgeSBTN.isSelected() && !deleteNodeTBTN.isSelected() && !deleteEdgeTBTN.isSelected()&& !dijkstraBTN.isSelected()) {
                dragDelta.x = circle.getCenterX() - mouseEvent.getX();
                dragDelta.y = circle.getCenterY() - mouseEvent.getY();
                circle.getScene().setCursor(Cursor.MOVE);
            }
            else if(deleteNodeTBTN.isSelected()){
                graph.deleteNode(node.name);
                deSelect();
                tempPane.getChildren().clear();
                getGraphPane(tempPane);
            } /*else if (primTBTN.isSelected()) {
                ArrayList<Pair<Node,Node>> where= graph.prim(node);
                for (Pair<Node,Node> n : where){
                    if(n.getKey()!=null&&n.getValue()!=null) {
                        System.out.println(n.getKey().name + " " + n.getValue().name);
                    }
                }

            }*/
        });
        circle.setOnMouseReleased(mouseEvent -> {
            if(!deleteNodeTBTN.isSelected()&& circle.getScene() != null){
                circle.getScene().setCursor(Cursor.HAND);
            }
        });
        circle.setOnMouseDragged(mouseEvent -> {
            if(!addNodeTBTN.isSelected() && !addEdgeSBTN.isSelected() && !deleteNodeTBTN.isSelected() && !deleteEdgeTBTN.isSelected()) {
                circle.setCenterX(mouseEvent.getX() + dragDelta.x);
                circle.setCenterY(mouseEvent.getY() + dragDelta.y);
                node.x = (mouseEvent.getX() + dragDelta.x);
                node.y = (mouseEvent.getY() + dragDelta.y);
                node.getText();
            }

        });
        circle.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                circle.getScene().setCursor(Cursor.HAND);
            }
        });
        circle.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                circle.getScene().setCursor(Cursor.DEFAULT);
            }
        });

    }
    private boolean isInside(double x1, double y1, Node node){
        double distance;
        distance = Math.sqrt((x1-(node.x))*(x1-(node.x))+(y1-(node.y))*(y1-(node.y)));
        return distance < 10;
    }
    void animatePath(Pane pane, String from, String to){
        Node fromNode = graph.getNode(from);
        Path path = new Path();
        if(fromNode!=null) {
            path.getElements().add(new MoveTo(fromNode.x, fromNode.y));
            Stack<Node> nodeStack = graph.getNodePath(from, to);
            if (nodeStack.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setContentText("No Path Exist");
                alert.initOwner(pane.getScene().getWindow());
                alert.show();
            } else {
                while (!nodeStack.isEmpty()) {
                    Node node = nodeStack.pop();
                    path.getElements().add(new LineTo(node.x, node.y));
                }
                graph.resetNodesVisited();
                PathTransition pathTransition = new PathTransition();
                Rectangle rectangle = new Rectangle(0, 0, 30, 30);

                Image img = new Image(new File("src/main/resources/com/example/rappi_ando/Repartidor.png").toURI().toString());
                rectangle.setFill(new ImagePattern(img));
                //rectangle.setFill(Color.DARKVIOLET);
                pane.getChildren().add(rectangle);
                pathTransition.setNode(rectangle);

                pathTransition.setDuration(Duration.seconds(2));
                pathTransition.setPath(path);
                pathTransition.setCycleCount(PathTransition.INDEFINITE);
                pathTransition.play();
            }
        }
        deSelect();
    }
}
