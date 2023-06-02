package com.example.rappi_ando.model;

import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.io.*;
import java.util.*;


public class Graph {

    private ArrayList<Node> nodes = new ArrayList();
    private Set<Node> nodes1= new HashSet();


    private static final Graph instance = new Graph();

    public static Graph getInstance() {
        return instance;
    }

    void addNode(double x, double y, String name) {
        Node temp = new Node(x, y, name);
        nodes.add(temp);
    }


    Node getNode(String from) {
        for (Node i : nodes) {
            if (i.name.equals(from)) {
                return i;
            }
        }
        return null;
    }

    String SearchNode(String node) {
        for (Node i : nodes) {
            if (i.name.equals(node)) {
                return ("X coordinate:" + i.x + "\n" + "Y coordinate:" + i.y);
            }
        }
        return ("Node not Found");
    }

    String deleteNode(String node) {
        for (Node n : nodes) {
            if (n.name.equals(node)) {
                deleteNo(n);
                nodes.remove(n);
                return "Node Deleted";
            }
        }
        return "Node Doesn't exist";
    }

    String modifyNode(String node, double x, double y) {
        for (Node i : nodes) {
            if (i.name.equals(node)) {
                i.x = x;
                i.y = y;
                return ("Node Modified");
            }
        }
        return ("Node not Found");
    }

    String addEdge(String from, String to, double weight) {
        Node fromNode = null, toNode = null;
        if (!from.equals(to)) {
            for (Node i : nodes) {
                if (i.name.equals(from)) {
                    fromNode = i;
                }
                if (i.name.equals(to)) {
                    toNode = i;
                }
            }
            if (fromNode == null)
                return ("From node not found");
            else if (toNode == null)
                return ("To node not Found");
            else {
                addEdge(fromNode, toNode, weight);
                return ("Edge added Successfully");
            }
        } else {
            return "The nodes from and to cannot be the same";
        }
    }

    String modifyEdge(String from, String to, double weight) {
        Node fromNode = null, toNode = null;
        for (Node i : nodes) {
            if (i.name.equals(from)) {
                fromNode = i;
            }
            if (i.name.equals(to)) {
                toNode = i;
            }
        }
        if (fromNode == null || toNode == null)
            return ("Edge not Found");
        else {
            modifyEdgeWeight(fromNode, toNode, weight);
            return ("Edge Modified Successfully");
        }
    }

    String deleteEdge(String from, String to) {
        Node fromNode = null, toNode = null;
        for (Node i : nodes) {
            if (i.name.equals(from)) {
                fromNode = i;
            }
            if (i.name.equals(to)) {
                toNode = i;
            }
        }
        if (fromNode == null || toNode == null) {
            return ("Edge not Found");
        } else if (fromNode == toNode) {
            return ("Both Nodes are same!");
        } else {

            if (deleteEd(fromNode, toNode)) {
                return ("Edge deleted");
            } else
                return ("Edge Not Found");
        }
    }

    String getPath(String from, String to) {
        String output;
        Node fromNode = null, toNode = null;
        for (Node i : nodes) {
            if (i.name.equals(from)) {
                fromNode = i;
            }
            if (i.name.equals(to)) {
                toNode = i;
            }
        }
        if (fromNode == null || toNode == null)
            return ("Edge not Found");
        else {
            output = dijkstraShortestPath(fromNode, toNode);
            resetNodesVisited();
            return output;
        }
    }

    ArrayList<Node> getNodes() {
        return nodes;
    }

    Stack<Node> getNodePath(String from, String to) {
        Node fromNode = null, toNode = null;
        for (Node i : nodes) {
            if (i.name.equals(from)) {
                fromNode = i;
            }
            if (i.name.equals(to)) {
                toNode = i;
            }
        }
        return animatePath(fromNode, toNode);
    }

    void saveData(String path) {
        try {
            ArrayList<Edge> edgeArrayList = new ArrayList<>();
            copyEdge(edgeArrayList);
            FileWriter fos = new FileWriter(path);
            fos.write(nodes.size()+"\n");
            for(Node n : nodes){
                fos.write(n.x+" "+n.y+" "+n.name+"\n");
            }
            fos.write(edgeArrayList.size()+"");
            fos.write("\n");
            for(Edge e :edgeArrayList){
                fos.write(e.source.name+" "+e.destination.name+" "+e.weight+"\n");
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    String loadData(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            int counter=0;
            boolean found=false;
            int no_of_nodes,no_of_edges;
            while ((line = reader.readLine()) != null) {
                String[] tem = line.split(" ");
                if(tem.length==1 && counter==0){
                    try {
                        no_of_nodes = Integer.parseInt(tem[0]);
                    } catch (NumberFormatException e) {
                        return "Invalid Input";
                    }
                }else if(tem.length==1&& counter!=0){
                    found=true;
                    try {
                        no_of_edges = Integer.parseInt(tem[0]);
                    } catch (NumberFormatException e) {
                        return "Invalid Input";
                    }
                }else if(!found){
                    String name=tem[2];
                    double x, y;
                    try {
                        x = Double.parseDouble(tem[0]);
                        y = Double.parseDouble(tem[1]);
                    } catch (NumberFormatException e) {
                        return "Invalid Input";
                    }
                    addNode(x, y, name);
                } else if(found){
                    String from = tem[0];
                    String to = tem[1];
                    double weight;
                    try {
                        weight = Double.parseDouble(tem[2]);
                    } catch (NumberFormatException e) {
                        return "Invalid Input";
                    }
                    addEdge(from, to, weight);
                }
                counter++;
            }
            reader.close();
        }
        catch (FileNotFoundException fe){
            return ("File not Found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Values Imported";
    }
    void addEdge(Node source, Node destination, double weight) {
        this.nodes1.add(source);
        this.nodes1.add(destination);
        this.addEdgeHelper(source, destination, weight);
        if (source != destination) {
            this.addEdgeHelper(destination, source, weight);
        }

    }

    void modifyEdgeWeight(Node a, Node b, double weight) {
        Iterator var5 = a.edges.iterator();

        Edge edge;
        do {
            if (!var5.hasNext()) {
                return;
            }

            edge = (Edge)var5.next();
        } while(edge.source != a || edge.destination != b);

        edge.setText(weight);
        edge.weight = weight;
    }

    boolean deleteEd(Node a, Node b) {
        Iterator var3 = a.edges.iterator();

        Edge edge;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            edge = (Edge)var3.next();
        } while(edge.source != a || edge.destination != b);

        a.edges.remove(edge);
        return true;
    }


    void deleteNo(Node from) {
        Iterator var2 = this.nodes1.iterator();

        label27:
        while (var2.hasNext()) {
            Node node = (Node) var2.next();
            Iterator var4 = node.edges.iterator();

            while (true) {
                Edge edge;
                do {
                    if (!var4.hasNext()) {
                        continue label27;
                    }

                    edge = (Edge) var4.next();
                } while (edge.source != from && edge.destination != from);

                node.edges.remove(edge);
            }
        }
    }


    public void copyEdge(ArrayList<Edge> edges) {
        Iterator var2 = this.nodes1.iterator();

        while(var2.hasNext()) {
            Node node = (Node)var2.next();
            edges.addAll(node.edges);
        }

    }

    private void addEdgeHelper(Node a, Node b, double weight) {
        Iterator var5 = a.edges.iterator();

        Edge edge;
        do {
            if (!var5.hasNext()) {
                a.edges.add(new Edge(a, b, weight));
                return;
            }

            edge = (Edge)var5.next();
        } while(edge.source != a || edge.destination != b);

        edge.weight = weight;
    }

    void resetNodesVisited() {
        Iterator var1 = this.nodes1.iterator();

        while(var1.hasNext()) {
            Node node = (Node)var1.next();
            node.unvisited();
        }

    }

    String dijkstraShortestPath(Node start, Node end) {
        String output = "";
        HashMap<Node, Node> changedAt = new HashMap();
        changedAt.put(start, null);
        HashMap<Node, Double> shortestPathMap = new HashMap();
        Iterator var6 = this.nodes1.iterator();

        Node child;
        while(var6.hasNext()) {
            child = (Node)var6.next();
            if (child == start) {
                shortestPathMap.put(start, 0.0);
            } else {
                shortestPathMap.put(child, Double.POSITIVE_INFINITY);
            }
        }

        var6 = start.edges.iterator();

        while(var6.hasNext()) {
            Edge edge = (Edge)var6.next();
            shortestPathMap.put(edge.destination, edge.weight);
            changedAt.put(edge.destination, start);
        }

        start.visit();

        while(true) {
            Node currentNode = this.closestReachableUnvisited(shortestPathMap);
            if (currentNode == null) {
                return "There isn't a path between " + start.name + " and " + end.name;
            }

            if (currentNode == end) {
                child = end;
                StringBuilder path = new StringBuilder(end.name);

                while(true) {
                    Node parent = changedAt.get(child);
                    if (parent == null) {
                        output = output + path;
                        return output;
                    }

                    path.insert(0, parent.name + "->");
                    child = parent;
                }
            }

            currentNode.visit();
            Iterator var12 = currentNode.edges.iterator();

            while(var12.hasNext()) {
                Edge edge = (Edge)var12.next();
                if (!edge.destination.isVisited() && shortestPathMap.get(currentNode) + edge.weight < shortestPathMap.get(edge.destination)) {
                    shortestPathMap.put(edge.destination, shortestPathMap.get(currentNode) + edge.weight);
                    changedAt.put(edge.destination, currentNode);
                }
            }
        }
    }

    Stack<Node> animatePath(Node start, Node end) {
        Stack<Node> path = new Stack();
        HashMap<Node, Node> changedAt = new HashMap();
        changedAt.put(start, null);
        HashMap<Node, Double> shortestPathMap = new HashMap();
        Iterator var6 = this.nodes1.iterator();

        Node child;
        while(var6.hasNext()) {
            child = (Node)var6.next();
            if (child == start) {
                shortestPathMap.put(start, 0.0);
            } else {
                shortestPathMap.put(child, Double.POSITIVE_INFINITY);
            }
        }

        var6 = start.edges.iterator();

        while(var6.hasNext()) {
            Edge edge = (Edge)var6.next();
            shortestPathMap.put(edge.destination, edge.weight);
            changedAt.put(edge.destination, start);
        }

        start.visit();

        while(true) {
            Node currentNode = this.closestReachableUnvisited(shortestPathMap);
            if (currentNode == null) {
                return null;
            }

            if (currentNode == end) {
                child = end;
                path.push(end);

                while(true) {
                    Node parent = changedAt.get(child);
                    if (parent == null) {
                        return path;
                    }

                    path.push(parent);
                    child = parent;
                }
            }

            currentNode.visit();
            Iterator var11 = currentNode.edges.iterator();

            while(var11.hasNext()) {
                Edge edge = (Edge)var11.next();
                if (!edge.destination.isVisited() && shortestPathMap.get(currentNode) + edge.weight < shortestPathMap.get(edge.destination)) {
                    shortestPathMap.put(edge.destination, shortestPathMap.get(currentNode) + edge.weight);
                    changedAt.put(edge.destination, currentNode);
                }
            }
        }
    }

    private Node closestReachableUnvisited(HashMap<Node, Double> shortestPathMap) {
        double shortestDistance = Double.POSITIVE_INFINITY;
        Node closestReachableNode = null;
        Iterator var5 = this.nodes1.iterator();

        while(var5.hasNext()) {
            Node node = (Node)var5.next();
            if (!node.isVisited()) {
                double currentDistance = shortestPathMap.get(node);
                if (currentDistance != Double.POSITIVE_INFINITY && currentDistance < shortestDistance) {
                    shortestDistance = currentDistance;
                    closestReachableNode = node;
                }
            }
        }

        return closestReachableNode;
    }

    public ArrayList<Pair<Node,Node>> prim(Node start){

        HashMap<Node,Double> weights = new HashMap<>();
        HashMap<Node,Boolean> wasVisited = new HashMap<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Pair<Node,Node>> where= new ArrayList<>();
        for(Node u: nodes){
            weights.put(u,10000000.0);//que tendra como value? ah si xd
            wasVisited.put(u,false);
        }
        Set<Node> nodes = new HashSet<>();

        //weights.replace(start,(double)0);

        Queue<Node> q= new LinkedList<>();
        q.add(start);
        while (!q.isEmpty()){
            Node u= q.poll();
            Node i=null;
            Node j=null;
            for(Edge a: u.edges){
                if(!wasVisited.get(u) && a.weight<weights.get(u)){
                    weights.replace(u, a.weight);
                    i=a.source;
                    j=a.destination;
                    //q.add(a.destination);
                    System.out.println("Hola");
                }
            }
            where.add(new Pair<>(i,j));
            wasVisited.replace(u,true);
        }
        return where;
    }

    private String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    private String to;
}

