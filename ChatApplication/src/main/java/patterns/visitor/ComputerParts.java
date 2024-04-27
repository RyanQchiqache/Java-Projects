package com.example.tiktaktoe.patterns.visitor;

/**
 * Behaviorral Pattern
 */
public interface ComputerParts {
    void accept(ComputerPartVisitor visitor);
}
