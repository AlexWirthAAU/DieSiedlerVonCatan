package com.example.diesiedler;

import com.example.catanserver.businessLogic.model.gameboard.Knot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void simpleTest() {
        Knot k = new Knot(1, 3);
        assertEquals(k.getRow(), 1);
    }
}
