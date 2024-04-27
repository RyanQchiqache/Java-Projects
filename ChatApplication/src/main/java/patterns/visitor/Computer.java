package com.example.tiktaktoe.patterns.visitor;

public class Computer implements ComputerParts{
    ComputerParts[] parts;
    public Computer(){
        parts = new ComputerParts[]{ new Keyboard(), new Monitor() };
    }

    @Override
    public void accept(ComputerPartVisitor computerPartVisitor) {
        for (ComputerParts part: parts) {
            part.accept( computerPartVisitor);

        }
        computerPartVisitor.visit(this);
    }
}
