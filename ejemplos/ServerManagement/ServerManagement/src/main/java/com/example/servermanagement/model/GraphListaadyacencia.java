package com.example.servermanagement.model;

import java.util.*;

public class GraphListaadyacencia<V extends Vertex<V>> implements  Graph<V>{
    private ArrayList<Vertex<V>> vertices;
    private int time=0;

    public ArrayList<Vertex<V>> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex<V>> vertices) {
        this.vertices = vertices;
    }

    public  GraphListaadyacencia() {
        vertices = new ArrayList<>();
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

    @Override
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

    @Override
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
    @Override
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

    @Override
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

    @Override
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

    public GraphListaadyacencia<V> AdjustedWeights(GraphListaadyacencia<V> g, double amountData) {
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

    @Override
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


    public GraphListaadyacencia<V> primL() {
        if (vertices.size() == 0) {
            return null;
        }
        // Estructuras auxiliares
        Set<Vertex<V>> visited = new HashSet<>();
        PriorityQueue<Map.Entry<Vertex<V>, Double>> minHeap = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));
        Map<Vertex<V>, Double> key = new HashMap<>();
        Map<Vertex<V>, Vertex<V>> parent = new HashMap<>();
        for (Vertex<V> v : vertices) {
            key.put(v, Double.MAX_VALUE);
            parent.put(v, null);
        }
        Vertex<V> origen = vertices.get(0);
        key.put(origen, 0.0);
        minHeap.add(new AbstractMap.SimpleEntry<>(origen, 0.0));
        while (!minHeap.isEmpty()) {
            Vertex<V> u = minHeap.poll().getKey();
            visited.add(u);

            for (Map.Entry<Vertex<V>,Double> entry : u.getAdyacentes()) {
                Vertex<V> v = entry.getKey();
                double peso = entry.getValue();

                if (!visited.contains(v) && peso < key.get(v)) {
                    key.put(v, peso);
                    parent.put(v, u);

                    // Actualizar el peso en el minHeap
                    for (Map.Entry<Vertex<V>, Double> heapEntry : minHeap) {
                        if (heapEntry.getKey().equals(v)) {
                            minHeap.remove(heapEntry);
                            break;
                        }
                    }
                    minHeap.add(new AbstractMap.SimpleEntry<>(v, peso));
                }
            }
        }
        // Construir el árbol mínimo
        GraphListaadyacencia<V> arbolMinimo = new GraphListaadyacencia<>();
        Map<Vertex<V>, Vertex<V>> vertexMap = new HashMap<>();

        for (Vertex<V> v : vertices) {
            Vertex<V> newVertex = new Vertex<>(v.getDato());
            arbolMinimo.addVertex(newVertex);
            vertexMap.put(v, newVertex);
        }

        for (Vertex<V> v : parent.keySet()) {
            Vertex<V> p = parent.get(v);
            if (p != null) {
                double peso = 0;
                for (Map.Entry<Vertex<V>,Double> entry : p.getAdyacentes()) {
                    if (entry.getKey().equals(v)) {
                        peso = entry.getValue();
                        break;
                    }
                }
                Vertex<V> newV = vertexMap.get(v);
                Vertex<V> newP = vertexMap.get(p);
                arbolMinimo.addEdge(newP, newV, peso);
            }
        }

        return arbolMinimo;
    }

    public GraphListaadyacencia<V> kruskal() {
        // Create a new graph to store the minimum spanning tree
        GraphListaadyacencia<V> minimumSpanningTree = new GraphListaadyacencia<>();

        // Create a list to store all the edges in the graph
        List<Map.Entry<Vertex<V>, Double>> allEdges = new ArrayList<>();

        // Populate the list with all the edges from the graph
        for (Vertex<V> vertex : vertices) {
            allEdges.addAll(vertex.getAdyacentes());
        }

        // Sort the edges in non-decreasing order based on their weights
        allEdges.sort(Comparator.comparingDouble(Map.Entry::getValue));

        // Create a map to keep track of the connected components
        Map<Vertex<V>, List<Vertex<V>>> connectedComponents = new HashMap<>();

        // Process each edge in the sorted order
        for (Map.Entry<Vertex<V>, Double> edge : allEdges) {
            // Get the source and destination vertices of the edge
            Vertex<V> source = edge.getKey();
            Vertex<V> destination = edge.getKey();

            // Check if the source and destination vertices belong to different components
            List<Vertex<V>> component1 = connectedComponents.get(source);
            List<Vertex<V>> component2 = connectedComponents.get(destination);

            // If the source vertex doesn't belong to any component, create a new component for it
            if (component1 == null) {
                component1 = new ArrayList<>();
                component1.add(source);
                connectedComponents.put(source, component1);
            }

            // If the destination vertex doesn't belong to any component, create a new component for it
            if (component2 == null) {
                component2 = new ArrayList<>();
                component2.add(destination);
                connectedComponents.put(destination, component2);
            }

            // If the source and destination vertices belong to different components, merge the components
            if (!component1.equals(component2)) {
                // Add the edge to the minimum spanning tree
                minimumSpanningTree.addEdge(source, destination, edge.getValue());

                // Merge the connected components
                component1.addAll(component2);

                // Update the component mapping for all vertices in component2
                for (Vertex<V> vertex : component2) {
                    connectedComponents.put(vertex, component1);
                }
            }
        }

        // Return the minimum spanning tree graph
        return minimumSpanningTree;
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