package com.example.tiktaktoe.patterns.observer;

public class ObserverTryout {
    public static void main(String[] args) {
        //Create concrete subject
        ConcreteSubject subject = new ConcreteSubject();
        //Create concrete observers
        Observer observer1 = new ConcreteObserver("observer1");
        Observer observer2 = new ConcreteObserver("oberver2");
        // Register the observers with the subject
        subject.add(observer1);
        subject.add(observer2);
        //set the state subject
        subject.setState(5);
        //remove 1 observer
        subject.remove(observer1);
        // notify all remaining observers
        subject.setState(10);


     }
}
