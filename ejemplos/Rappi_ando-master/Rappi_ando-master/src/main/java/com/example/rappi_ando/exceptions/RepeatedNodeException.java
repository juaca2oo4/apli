package com.example.rappi_ando.exceptions;

public class RepeatedNodeException extends RuntimeException{
    public RepeatedNodeException() {
        super("Repeated node in the graph :(");
    }
}
