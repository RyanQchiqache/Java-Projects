package com.example.tiktaktoe.patterns.observer;

public class ConcreteObserver implements Observer{
    private final String name;

    public ConcreteObserver(String name) {
        this.name = name;
    }


    @Override
    public void update( int state) {
        System.out.println("YOYO" + name + "received the update" + state );

    }
}
