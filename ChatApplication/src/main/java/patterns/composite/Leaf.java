package com.example.tiktaktoe.patterns.composite;

public class Leaf implements Component{
    private String name;

    public Leaf(String name) {
        this.name = name;
    }

    @Override
    public void operation() {
        System.out.println("YOYO" + name + "doing something");

    }
}
