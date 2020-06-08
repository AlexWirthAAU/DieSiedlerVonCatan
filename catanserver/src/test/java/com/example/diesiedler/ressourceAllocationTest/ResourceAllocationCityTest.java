package com.example.diesiedler.ressourceAllocationTest;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.businessLogic.model.resourceallocation.ResourceAllocation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//gradlew build jacocoTestReport

public class ResourceAllocationCityTest {

    private GameSession gameSession;
    private Player p;

    @Before
    public void setUp() {
        this.gameSession = new GameSession();
        this.p = new Player("Dora", 1);
        gameSession.setPlayer(p);
        //Knot in line 2, row 4
        gameSession.getGameboard().getKnots()[9].setPlayer(p);
        gameSession.getGameboard().getKnots()[9].setHasCity(true);
        //Knot in line 2, row 8
        gameSession.getGameboard().getKnots()[12].setPlayer(p);
        gameSession.getGameboard().getKnots()[12].setHasCity(true);
    }

    @After
    public void tearDown() {
        this.gameSession = null;
        this.p = null;
    }

    @Test
    public void testWoodUpdate() {
        ResourceAllocation.updateResources(gameSession, 11);
        Assert.assertEquals(2, gameSession.getPlayer(1).getInventory().getWood());
    }

    @Test
    public void testOreUpdate() {
        ResourceAllocation.updateResources(gameSession, 3);
        Assert.assertEquals(2, gameSession.getPlayer(1).getInventory().getOre());
    }

    @Test
    public void testClayUpdate() {
        ResourceAllocation.updateResources(gameSession, 8);
        Assert.assertEquals(2, gameSession.getPlayer(1).getInventory().getClay());
    }

    @Test
    public void testWheatUpdate() {
        ResourceAllocation.updateResources(gameSession, 9);
        Assert.assertEquals(2, gameSession.getPlayer(1).getInventory().getWheat());
    }

    @Test
    public void testWoolUpdate() {
        ResourceAllocation.updateResources(gameSession, 12);
        Assert.assertEquals(2, gameSession.getPlayer(1).getInventory().getWool());
    }
}
