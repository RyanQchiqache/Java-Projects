package com.example.tiktaktoe.patterns.visitor;

public class ComputerPartDIsplayVisitor implements ComputerPartVisitor{

    @Override
    public void visit(Computer computer) {
        System.out.println("displaying Computer");
    }

    @Override
    public void visit(Keyboard keyboard) {
        System.out.println("Displaying keyboard");

    }

    @Override
    public void visit(Monitor monitor) {
        System.out.println("Displaying monitor");

    }

    public static void main(String[] args) {
        ComputerParts coomputer = new Computer();
        coomputer.accept(new ComputerPartDIsplayVisitor());
    }
}
