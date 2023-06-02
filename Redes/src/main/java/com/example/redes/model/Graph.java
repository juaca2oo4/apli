package com.example.redes.model;

import java.util.*;

public class Graph<V> {
    private ArrayList<Vertex<V>> vertices=new ArrayList<>();
    private int time=0;

    private static final Graph<String> instance = new Graph<String>();

    public static Graph<String> getInstance() {
        return instance;
    }

    public ArrayList<Vertex<V>> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex<V>> vertices) {
        this.vertices = vertices;
    }


    public Vertex<V> findVertex(String name) {
        for (int i = 0; i < vertices.size(); i++) {
            String dato = String.valueOf(vertices.get(i).getDato());
            if (dato.equals(name)) {
                return vertices.get(i);
            }
        }
        return null;
    }
    public void addVertex(Vertex<V> vertex) {
        if(vertices.contains(vertex)){
            return;
        }
        if(vertex!=null){
            vertices.add(vertex);
        }
    }

    public void addEdge(Vertex<V> source, Vertex<V> destination, double peso) {
        if (source != null && destination != null) {
            for (int i = 0; i < source.getAdyacentes().size(); i++) {
                Map.Entry<Vertex<V>,Double> entry = (Map.Entry<Vertex<V>,Double>) source.getAdyacentes().get(i);
                Vertex<V> v = entry.getKey();
                if (v.equals(destination)) {
                    return;
                }
            }
            source.getAdyacentes().add(new AbstractMap.SimpleEntry<>(destination, peso));
            destination.getAdyacentes().add(new AbstractMap.SimpleEntry<>(source, peso));
        }
    }

    public boolean remEdge(Vertex<V> source, Vertex<V> destination) {
        // Verificar si los vértices existen en el grafo
        if (!vertices.contains(source) || !vertices.contains(destination)) {
            return false;
        }
        // Buscar la arista en el vértice de origen
        Map.Entry<Vertex<V>,Double> arista = null;
        for (Map.Entry<Vertex<V>,Double> entry : source.getAdyacentes()) {
            if (entry.getKey().equals(destination)) {
                arista = entry;
                break;
            }
        }
        // Verificar si la arista existe
        if (arista == null) {
            return false;
        }
        // Borrar la arista
        source.getAdyacentes().remove(arista);
        return true;
    }

    public boolean remVertex(Vertex<V> vertex) {
        if (vertex == null) {
            return false; // El vértice a eliminar es null, no se puede eliminar
        }
        if (!vertices.contains(vertex)) {
            return false; // El vértice no se encuentra en la lista de vértices, no se puede eliminar
        }
        // Eliminar todas las aristas que tienen como destino el vértice a eliminar
        for (Vertex<V> v : vertices) {
            Iterator<Map.Entry<Vertex<V>,Double>> iterator = v.getAdyacentes().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Vertex<V>,Double> entry = iterator.next();
                if (entry.getKey().equals(vertex)) {
                    iterator.remove();
                }
            }
        }
        // Eliminar el vértice de la lista de vértices
        vertices.remove(vertex);
        return true;
    }

    public Graph<V> AdjustedWeights(double amountData) {
        Graph<V> g = new Graph<V>();

        // Create a copy of the vertices in the original graph
        for (Vertex<V> vertex : vertices) {
            g.addVertex(new Vertex<>(vertex.getDato(),vertex.getX(),vertex.getY()));
        }

        // Adjust the weights in the copied graph
        for (Vertex<V> vertex : vertices) {
            Vertex<V> copiedVertex = g.findVertex((String) vertex.getDato());

            for (Map.Entry<Vertex<V>, Double> entry : vertex.getAdyacentes()) {
                Vertex<V> adjacentVertex = entry.getKey();
                double originalWeight = entry.getValue();
                double adjustedWeight = amountData / originalWeight;
                copiedVertex.getAdyacentes().add(new AbstractMap.SimpleEntry<>(g.findVertex((String) adjacentVertex.getDato()), adjustedWeight));
            }
        }

        return g;
    }


    public ArrayList<Vertex<V>> Dijsktra(Vertex<V> source, Vertex<V> objective) {
        // Initialize data structures
        Map<Vertex<V>, Double> distances = new HashMap<>();  // Stores the shortest distances from the source vertex
        Map<Vertex<V>, Vertex<V>> parents = new HashMap<>();  // Stores the previous vertex in the shortest path
        Set<Vertex<V>> visited = new HashSet<>();  // Set of visited vertices
        // Initialize distances with infinity for all vertices except the source
        for (Vertex<V> vertex : vertices) {
            distances.put(vertex, Double.MAX_VALUE);
        }
        distances.put(source, 0.0);
        // Create a minimum priority queue to store vertices based on their distances
        PriorityQueue<Vertex<V>> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        priorityQueue.add(source);
        // Perform Dijkstra's algorithm
        while (!priorityQueue.isEmpty()) {
            // Get the vertex with the minimum distance
            Vertex<V> minVertex = priorityQueue.poll();
            if (minVertex.equals(objective)) {
                break;  // Stop the algorithm if the objective vertex is reached
            }
            // Mark the current vertex as visited
            visited.add(minVertex);
            // Update distances and parents for adjacent vertices
            for (Map.Entry<Vertex<V>, Double> entry : minVertex.getAdyacentes()) {
                Vertex<V> adjacentVertex = entry.getKey();
                double edgeWeight = entry.getValue();
                if (!visited.contains(adjacentVertex)) {
                    double newDistance =  edgeWeight;
                    Double currentDistance = distances.get(adjacentVertex);  // Retrieve the current distance
                    if (currentDistance == null || newDistance < currentDistance) {
                        distances.put(adjacentVertex, newDistance);
                        parents.put(adjacentVertex, minVertex);
                        // Remove and re-add the adjacent vertex to update its position in the priority queue
                        priorityQueue.remove(adjacentVertex);
                        priorityQueue.add(adjacentVertex);
                    }
                }
            }
        }
        // Construct the shortest path
        ArrayList<Vertex<V>> shortestPath = new ArrayList<>();
        Vertex<V> currentVertex = objective;
        // Build the path by backtracking through parents
        while (currentVertex != null) {
            shortestPath.add(0, currentVertex);
            currentVertex = parents.get(currentVertex);
        }
        return shortestPath;
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
            Vertex<V> vertex = vertices.get(i);
            for (Map.Entry<Vertex<V>,Double> entry : vertex.getAdyacentes()) {
                Vertex<V> adj = entry.getKey();
                double weight = entry.getValue();
                int adjIndex = vertices.indexOf(adj);

                distances[i][adjIndex] = weight;
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


}