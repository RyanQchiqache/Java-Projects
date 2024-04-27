package com.example.tiktaktoe.patterns.composite;

public class Tryout {
    public static void main(String[] args) {
        Component leaf1 = new Leaf("leaf1");
        Component leaf2 = new Leaf("leaf2");
        Component leaf3 = new Leaf("leaf3");

        Composite composite = new Composite();
        composite.add((Leaf) leaf1);
        composite.add((Leaf) leaf2);

        leaf1.operation();
        leaf2.operation();
        leaf3.operation();

        System.out.print(composite);

        composite.operation();



    }
}
