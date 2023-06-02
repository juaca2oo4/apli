package com.example.redes;

import com.example.redes.Vertex;

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
    public void addVertex(Vertex<V> vertice) {
        if(vertices.contains(vertice)){
            return;
        }
        if(vertice!=null){
            vertices.add(vertice);
        }
    }


    public void addEdge(Vertex<V> origen, Vertex<V> destino, double peso) {
        if (origen != null && destino != null) {
            for (int i = 0; i < origen.getAdyacentes().size(); i++) {
                Map.Entry<Vertex<V>,Double> entry = (Map.Entry<Vertex<V>,Double>) origen.getAdyacentes().get(i);
                Vertex<V> v = entry.getKey();
                if (v.equals(destino)) {
                    return;
                }
            }
            origen.getAdyacentes().add(new AbstractMap.SimpleEntry<>(destino, peso));
            destino.getAdyacentes().add(new AbstractMap.SimpleEntry<>(origen, peso));
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

    public void bfs(Vertex<V> v) {
        if(vertices.size()>0){
            v.setC(Colors.WHITE);
            v.setDistance(0);
            v.setParent(null);
            bfsinner(v);
        }
        //String msg="";
        //msg=createtree(v);
        //return msg;
    }
    private void bfsinner(Vertex<V> v) {
        for (Vertex<V> q: vertices) {
            q.setDistance(0);
            q.setC(Colors.WHITE);
            q.setParent(null);
        }
        v.setC(Colors.GREY);
        Queue<Vertex<V>> queue = new LinkedList<>();
        queue.add(v);
        while (!queue.isEmpty()) {
            Vertex<V> u = queue.poll();
            for (Map.Entry<Vertex<V>,Double> entry : u.getAdyacentes()) {
                Vertex<V> adj = entry.getKey();
                if (adj.getC() == Colors.WHITE) {
                    adj.setC(Colors.GREY);
                    adj.setDistance(u.getDistance() + 1);
                    adj.setParent(u);
                    queue.add(adj);
                }
            }
            u.setC(Colors.BLACK);
        }
    }


    public void dfs(){
        if(vertices.size()>0){
            for (Vertex<V> v:vertices) {
                v.setC(Colors.WHITE);
                v.setParent(null);
            }
            time=0;
            for (Vertex<V> v : vertices) {
                if (v.getC() == Colors.WHITE) {
                    dfs(v,time);
                }
            }
        }
    }
    private void dfs(Vertex<V> v,int t){
        time+=1;
        v.setDistance(t);
        v.setC(Colors.GREY);
        for (Map.Entry<Vertex<V>,Double> u : v.getAdyacentes()) {
            if (u.getKey().getC()==Colors.WHITE) {
                u.getKey().setParent(v);
                dfs(u.getKey(),t);
            }
        }
        v.setC(Colors.BLACK);
        time +=1;
        v.setDistancefinal(time);
    }

    public Graph<V> AdjustedWeights(Graph<V> g, double amountData) {
        // Iterate over each vertex in the original graph
        for (Vertex<V> vertex : g.getVertices()) {
            // Iterate over each adjacent vertex and adjust the weight
            for (Map.Entry<Vertex<V>, Double> entry : vertex.getAdyacentes()) {
                double originalWeight = entry.getValue();

                // Calculate the adjusted weight
                double adjustedWeight = amountData / originalWeight;

                entry.setValue(adjustedWeight);
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