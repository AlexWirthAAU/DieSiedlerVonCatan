package com.example.diesiedler.modelClasses;

import com.example.catangame.devcards.BuildStreetCard;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BuildStreetCardTest {

    BuildStreetCard buildStreetCard;

    @Before
    public void setUp() {
        buildStreetCard = new BuildStreetCard();
    }

    @After
    public void tearDown() {
        buildStreetCard = null;
    }

    @Test
    public void getCounter() {
        Assert.assertEquals(2, buildStreetCard.getCounter());
    }

    @Test
    public void removeCounter() {
        buildStreetCard.removeCounter();
        Assert.assertEquals(1, buildStreetCard.getCounter());
    }
}
