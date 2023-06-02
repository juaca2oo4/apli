package com.example.rappi_ando.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Edge {
    public Node source;
    public Node destination;
    double weight;
    private Line line ;
    private DoubleProperty startX;
    private DoubleProperty startY;
    private DoubleProperty endX;
    private DoubleProperty endY;

    public void setText(double weight) {
        text.setText(weight+"");
        text.xProperty().bind((startX.add(endX)).divide(2));
        text.yProperty().bind((startY.add(endY)).divide(2));
    }

    private Text text;
    Edge(Node s, Node d, double w) {
        line = new Line(100,100,100,100);
        text = new Text();
        source = s;
        destination = d;
        weight = w;

        startX = new SimpleDoubleProperty(source.x);
        startY = new SimpleDoubleProperty(source.y);
        endX = new SimpleDoubleProperty(destination.x);
        endY = new SimpleDoubleProperty(destination.y);
        line.startXProperty().bind(startX);
        line.startYProperty().bind(startY);
        line.endXProperty().bind(endX);
        line.endYProperty().bind(endY);
        startX.bind(source.getCircle().centerXProperty());
        startY.bind(source.getCircle().centerYProperty());
        endX.bind(destination.getCircle().centerXProperty());
        endY.bind(destination.getCircle().centerYProperty());
        text.setText(weight+"");
        text.xProperty().bind((startX.add(endX)).divide(2));
        text.yProperty().bind((startY.add(endY)).divide(2));

    }

    public Line getLine() {
        return line;
    }

    public Text getText() {
        return text;
    }

    public String toString() {
        return String.format("(%s -> %s,%f)", source.name, destination.name, weight);
    }
}
