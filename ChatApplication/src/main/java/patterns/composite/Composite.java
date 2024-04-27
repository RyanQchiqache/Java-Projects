package com.example.tiktaktoe.patterns.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Structural pattern
 */
public class Composite implements Component {
    private List<Component> children = new ArrayList<>();

    @Override
    public void operation() {
        for (Component component : children ) {
            component.operation();
        }
    }

    public void remove (Leaf leaf){
        children.remove(leaf);
    }

    public void add (Leaf leaf){
        children.add(leaf);
    }

}
