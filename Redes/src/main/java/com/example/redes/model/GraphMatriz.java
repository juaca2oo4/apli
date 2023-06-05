package com.example.redes.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphMatriz<T> {
    private List<Edge<T>>[][] adjacencyMatrix;
    private List<Vertex<T>> vertices;

    private static final GraphMatriz<String> instance = new GraphMatriz<String>(0);

    public GraphMatriz(int numVertices) {
        adjacencyMatrix = new ArrayList[numVertices][numVertices];
        vertices = new ArrayList<>();
    }

    public static GraphMatriz<String> getInstance() {
        return instance;
    }

    public Vertex<T> searchVertex(String vertex) {
        for (int i = 0; i < vertices.size(); i++) {
            if(vertices.get(i).getDato().equals(vertex)){
                return vertices.get(i);
            }
        }

        return null;
    }

    public void addVertex(Vertex<T> vertex) {
        if(vertex == null || vertices.indexOf(vertex)!=-1){
            return;
        }
        vertices.add(vertex);

        int size = adjacencyMatrix.length;
        ArrayList[][] newMatrix = new ArrayList[size+1][size+1];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newMatrix[i][j] = (ArrayList) adjacencyMatrix[i][j];
            }
        }

        for (int i = 0; i <= size; i++) {
            newMatrix[i][size] = null;
            newMatrix[size][i] = null;
        }

        for (int i = 0; i <= size; i++) {
            newMatrix[i][i] = null;
        }

        adjacencyMatrix = newMatrix;

        return;
    }

    public void addEdge(Vertex<T> source, Vertex<T> destination,double weight) {
        if (!vertices.contains(source) || !vertices.contains(destination)) {
            throw new IllegalArgumentException("Los vértices de origen y destino deben existir en el grafo.");
        }
        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        if (adjacencyMatrix[sourceIndex][destinationIndex] == null) {
            adjacencyMatrix[sourceIndex][destinationIndex] = new ArrayList<>();
        }
        adjacencyMatrix[sourceIndex][destinationIndex].add(new Edge(source ,destination,weight));

        if (adjacencyMatrix[destinationIndex][sourceIndex] == null) {
            adjacencyMatrix[destinationIndex][sourceIndex] = new ArrayList<>();
        }
        adjacencyMatrix[destinationIndex][sourceIndex].add(new Edge(destination ,source,weight));
    }

    public List<Vertex<T>> getVertices() {
        return vertices;
    }

    public List<Vertex<T>> dijkstra(Vertex<T> startVertex, Vertex<T> endVertex) {
        int start = vertices.indexOf(startVertex);
        int end = vertices.indexOf(endVertex);

        if (start == -1 || end == -1) {
            return null;
        }
        int numVertices = vertices.size();
        double[] distances = new double[numVertices];
        int[] parents = new int[numVertices];
        boolean[] visited = new boolean[numVertices];

        Arrays.fill(distances, Double.MAX_VALUE);
        Arrays.fill(parents, -1);
        distances[start] = 0.0;

        for (int i = 0; i < numVertices - 1; i++) {
            int minVertex = findMinDistance(distances, visited);
            visited[minVertex] = true;

            for (int j = 0; j < numVertices; j++) {
                if (!visited[j] && adjacencyMatrix[minVertex][j] != null) {
                    List<Edge<T>> edges = adjacencyMatrix[minVertex][j];
                    for (Edge<T> edge : edges) {
                        double currentDistance = distances[minVertex] + edge.getWeight();
                        if (currentDistance < distances[j]) {
                            distances[j] = currentDistance;
                            parents[j] = minVertex;
                        }
                    }
                }
            }
        }
        List<Vertex<T>> shortestPath = new ArrayList<>();
        int currentVertex = end;
        while (currentVertex != -1) {
            shortestPath.add(0, vertices.get(currentVertex));
            currentVertex = parents[currentVertex];
        }

        return shortestPath;
    }



    private int findMinDistance(double[] distances, boolean[] visited) {
        double minDistance = Double.MAX_VALUE;
        int minVertex = -1;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < minDistance) {
                minDistance = distances[i];
                minVertex = i;
            }
        }

        return minVertex;
    }



    public double[][] floydL() {
        int v = vertices.size();
        double[][] distances = new double[v][v];

        // Initialize distances with infinity for all pairs of vertices
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                distances[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        // Set distance 0 for self-loops
        for (int i = 0; i < v; i++) {
            distances[i][i] = 0;
        }

        // Set initial distances for existing edges
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                List<Edge<T>> edges = adjacencyMatrix[i][j];
                if (edges != null) {
                    for (Edge<T> edge : edges) {
                        double weight = edge.getWeight();
                        distances[i][j] = weight;
                    }
                }
            }
        }

        // Perform Floyd-Warshall algorithm
        for (int k = 0; k < v; k++) {
            for (int i = 0; i < v; i++) {
                for (int j = 0; j < v; j++) {
                    if (distances[i][k] + distances[k][j] < distances[i][j]) {
                        distances[i][j] = distances[i][k] + distances[k][j];
                    }
                }
            }
        }

        return distances;
    }

    public boolean remVertex(Vertex<T> vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }

        int vertexIndex = vertices.indexOf(vertex);
        vertices.remove(vertex);

        // Eliminar las conexiones del vértice en la matriz de adyacencia
        for (int i = 0; i < vertices.size(); i++) {
            adjacencyMatrix[i][vertexIndex] = null; // Eliminar conexiones de otros vértices al vértice eliminado
            adjacencyMatrix[vertexIndex][i] = null; // Eliminar conexiones del vértice eliminado a otros vértices
        }

        return true;
    }

    public boolean remEdge(Vertex<T> source, Vertex<T> destination) {
        if (!vertices.contains(source) || !vertices.contains(destination)) {
            return false;
        }

        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        // Eliminar el borde de la matriz de adyacencia
        adjacencyMatrix[sourceIndex][destinationIndex] = null;
        adjacencyMatrix[destinationIndex][sourceIndex] = null;

        return true;
    }

    public GraphMatriz<T> adjustedWeights(double amountData) {
        GraphMatriz<T> g = new GraphMatriz<>(adjacencyMatrix.length);

        // Create a copy of the vertices in the original graph
        for (Vertex<T> vertex : vertices) {
            g.addVertex(new Vertex<>(vertex.getDato(), vertex.getX(), vertex.getY(), vertex.getSpeed()));
        }

        // Adjust the weights in the copied graph
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                List<Edge<T>> edges = adjacencyMatrix[i][j];
                if (edges != null) {
                    for (Edge<T> edge : edges) {
                        double originalWeight = edge.getWeight();
                        double adjustedWeight = amountData / originalWeight;

                        Vertex<T> source = g.searchVertex((String) vertices.get(i).getDato());
                        Vertex<T> destination = g.searchVertex((String) vertices.get(j).getDato());

                        g.addEdge(source, destination, adjustedWeight);
                    }
                }
            }
        }

        return g;
    }

    public void multiplyWeights(double factor) {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                List<Edge<T>> edges = adjacencyMatrix[i][j];
                if (edges != null) {
                    for (Edge<T> edge : edges) {
                        double originalWeight = edge.getWeight();
                        double multipliedWeight = originalWeight * factor;
                        edge.setWeight(multipliedWeight);
                    }
                }
            }
        }
    }




    public List<Edge<T>>[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public void setAdjacencyMatrix(List<Edge<T>>[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public void setVertices(List<Vertex<T>> vertices) {
        this.vertices = vertices;
    }




}