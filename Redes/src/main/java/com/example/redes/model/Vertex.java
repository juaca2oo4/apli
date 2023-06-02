package com.example.redes.model;

import java.util.*;
public class Vertex<V>  {

    private ArrayList<Map.Entry<Vertex<V>,Double>> adyacentes;
    private V dato;
    private Colors c;

    private double x;

    private double y;
    private int distance;
    private int distancefinal;

    public Vertex(V dato, double x, double y) {
        adyacentes= new ArrayList<>();
        this.x=x;
        this.y=y;
        this.dato = dato;
        c= Colors.WHITE;
        distance=0;
    }

    public int getDistancefinal() {
        return distancefinal;
    }

    public void setDistancefinal(int distancefinal) {
        this.distancefinal = distancefinal;
    }

    private Vertex<V> parent;
    public Vertex<V> getParent() {
        return parent;
    }
    public void setParent(Vertex<V> parent) {
        this.parent = parent;
    }
    public int getDistance() {
        return distance;
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }
    public Colors getC() {
        return c;
    }
    public void setC(Colors c) {
        this.c = c;
    }
    public ArrayList<Map.Entry<Vertex<V>, Double>> getAdyacentes() {
        return adyacentes;
    }
    public void setAdyacentes(ArrayList<Map.Entry<Vertex<V>, Double>> adyacentes) {
        this.adyacentes = adyacentes;
    }
    public V getDato() {
        return dato;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDato(V dato) {
        this.dato = dato;
    }
}