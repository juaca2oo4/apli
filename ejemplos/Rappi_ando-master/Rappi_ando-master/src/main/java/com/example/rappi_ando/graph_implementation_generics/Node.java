package com.example.rappi_ando.graph_implementation_generics;

import javafx.util.Pair;
import java.util.*;

public class Node<T> {
    private T value;
    private int key;

    private int forward;

    private int color; //0= white , 1= gray , 2= black

    private int d;

    private Node<T> pi;
    private ArrayList<Pair<Node<T>,Integer>> nodes;
    public Node(){

    }

    public Node(T value, int key) {
        this.value = value;
        this.key = key;
        nodes= new ArrayList<>();
    }

    public void addNode(Node<T>a, Integer b){
        Pair<Node<T>,Integer> node= new Pair<>(a,b);
        nodes.add(node);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getForward() {
        return forward;
    }

    public void setForward(int forward) {
        this.forward = forward;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public Node<T> getPi() {
        return pi;
    }

    public void setPi(Node<T> pi) {
        this.pi = pi;
    }

    public ArrayList<Pair<Node<T>, Integer>> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Pair<Node<T>, Integer>> nodes) {
        this.nodes = nodes;
    }
}