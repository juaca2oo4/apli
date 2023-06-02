package com.example.rappi_ando.model;

import java.util.LinkedList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

class Node {
    public double x;
    public double y;
    public String name;
    private boolean visited;
    private Circle circle;
    private Text text;
    LinkedList<Edge> edges;

    Node(double x, double y, String name) {
        //this.circle=new Circle(x,y,10, Color.rgb(48,48,48));
        circle=new Circle(x,y,10);
        circle.setFill(Color.BLACK);
        circle.setStroke(Color.WHITE);
        text = new Text(name);
        this.x = x;
        this.y = y;
        this.name = name;
        this.visited = false;
        this.edges = new LinkedList();
    }

    Circle getCircle() {
        circle.setCenterX(x);
        circle.setCenterY(y);
        return circle;
    }

    Text getText() {
        text.layoutXProperty().bind(circle.centerXProperty().add(-text.getLayoutBounds().getWidth() / 2));
        text.layoutXProperty().unbind();
        text.layoutYProperty().bind(circle.centerYProperty().add(5));
        text.layoutYProperty().unbind();
        text.setFill(Color.WHITE);
        return text;
    }

    boolean isVisited() {
        return this.visited;
    }

    void visit() {
        this.visited = true;
    }

    void unvisited() {
        this.visited = false;
    }
}