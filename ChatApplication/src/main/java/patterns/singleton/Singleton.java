package com.example.tiktaktoe.patterns.singleton;

/**
 * Creational pattern
 */
public class Singleton {
    public static Singleton uniqueInstance;

    private  Singleton() {}
    public static Singleton instance (){
        if (uniqueInstance == null){
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }

    public void getSingletonData (){}
    public void single(){}

}
