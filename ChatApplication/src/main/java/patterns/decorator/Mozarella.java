package com.example.tiktaktoe.patterns.decorator;

public class Mozarella extends ToppingDecorator{
    public Mozarella(Pizza newPizza) {
        super(newPizza);
        System.out.println("Adding dough");
        System.out.println("Adding Moz");
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", mozarella";
    }

    @Override
    public double getCost() {
        return super.getCost() + 50;
    }

    public static void main(String[] args) {
        Pizza basicPizza = new Mozarella(new PlainPizza());

        System.out.println("Ingredient : " + basicPizza.getDescription());
        System.out.println("Price : " + basicPizza.getCost() );

    }
}
