package com.book.codecoverage;

import org.junit.Assert;
import org.junit.Test;

public class SimpleCalculatorTest {
    @Test
    public void sum() {
        Assert.assertEquals(3, SimpleCalculator.sum(1, 2));
    }

    @Test
    public void mathOperation() {
        Assert.assertEquals(3, SimpleCalculator.mathOperation(SimpleCalculator.Operation.SUM, 1,2));
    }
}