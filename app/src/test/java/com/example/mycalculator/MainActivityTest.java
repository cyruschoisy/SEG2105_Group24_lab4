package com.example.mycalculator;

import static org.junit.Assert.*;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


import org.junit.Test;

public class MainActivityTest {
    @Test
    public void calc_addition() {
        // Simulates the core logic of a calculator for an addition operation.
        String expressionStr = "3+5";
        Expression expression = new ExpressionBuilder(expressionStr).build();
        double result = expression.evaluate();
        // We use a delta for comparing doubles
        assertEquals(8.0, result, 0.001);
    }
    @Test
    public void calc_subtraction() {
        // Tests a subtraction operation.
        String expressionStr = "10-4";
        Expression expression = new ExpressionBuilder(expressionStr).build();
        double result = expression.evaluate();
        assertEquals(6.0, result, 0.001);
    }
}