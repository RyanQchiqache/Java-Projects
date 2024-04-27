package com.example.tiktaktoe.patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class ConcreteSubject implements Subject{
    private int state;
    private List<Observer> obeservers = new ArrayList<>();

    @Override
    public void add(Observer observer){
        obeservers.add(observer);

    }

    public void setState(int state) {
        this.state = state;
    }
    @Override
    public void remove(Observer observer){
        obeservers.remove(observer);


    }
    @Override
    public void Notify(){
        for (Observer observer :obeservers) {
            observer.update(state);
        }

    }
}
