package com.example.tiktaktoe.patterns.observer;

/**
 * Behavioural patten
 */
public interface Subject {
    void add(Observer observer);
    void remove (Observer observer);
    void Notify();

}
