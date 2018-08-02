package com.seek.generation.map.builder.exception;

public class ShapeNotDefinedException extends Exception {
    public ShapeNotDefinedException(String s) {
        super(s);
    }

    public ShapeNotDefinedException() {
        super("Shape is not defined");
    }
}
