package com.example.tiktaktoe.patterns.visitor;

public class Keyboard implements ComputerParts{
    @Override
    public void accept(ComputerPartVisitor visitor) {
        visitor.visit(this);
    }
}
