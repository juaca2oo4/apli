package com.example.rappi_ando.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    private Graph graph = Graph.getInstance();

    private void setUp1(){
        graph.loadData("Data");

    }

    private void setUp2(){
        graph.addNode(1,1,"66");
        graph.addNode(2,2,"67");
    }

    @Test
    public void dijkstraTest1(){
        setUp1();
        assertEquals(graph.getPath("1","61"),"1->7->12->20->21->29->30->43->39->47->51->53->57->61");
    }
    @Test
    public void dijkstraTest2(){
        setUp1();
        assertEquals(graph.getPath("9","27"),"9->10->15->16->23->27");
    }
    @Test
    public void dijkstraTest3(){
        setUp1();
        assertEquals(graph.getPath("36","58"),"36->37->48->50->52->53->58");
    }

    

}