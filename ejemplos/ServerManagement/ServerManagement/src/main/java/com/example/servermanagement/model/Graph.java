package com.example.servermanagement.model;

import java.util.ArrayList;

public interface Graph<V extends Vertex<V>> {
    public void addEdge(Vertex<V> origen, Vertex<V> destino, double peso);
    public void addVertex(Vertex<V> vertice);
    public boolean remEdge(Vertex<V> source, Vertex<V> destination);
    public boolean remVertex(Vertex<V> vertex);
    public Vertex<V> findVertex(String name);

    public GraphListaadyacencia<V> AdjustedWeights(GraphListaadyacencia<V> g, double amountData);
    public void bfs(Vertex<V> v);
    public void dfs();
    public ArrayList<Vertex<V>> Dijsktra(Vertex<V> start, Vertex<V> end);

    public double[][] floydL();

    public GraphListaadyacencia<V> primL();

    public GraphListaadyacencia<V> kruskal();

}
