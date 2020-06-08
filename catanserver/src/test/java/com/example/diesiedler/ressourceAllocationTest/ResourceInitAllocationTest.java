package com.example.diesiedler.ressourceAllocationTest;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.businesslogic.model.resourceallocation.InitResAllocation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResourceInitAllocationTest {

    private GameSession gameSession;
    private Player p;

    @Before
    public void setUp() {
        this.gameSession = new GameSession();
        this.p = new Player("Caesar", 1);
        gameSession.setPlayer(p);
        //Knot at Tile Wood
        gameSession.getGameboard().getKnots()[0].setPlayer(p);
        //Knot at Tile Ore
        gameSession.getGameboard().getKnots()[3].setPlayer(p);
        //Knot at Tile Wheat
        gameSession.getGameboard().getKnots()[5].setPlayer(p);
        //Knot at Tile Clay
        gameSession.getGameboard().getKnots()[47].setPlayer(p);
        //Knot at Tile Wool
        gameSession.getGameboard().getKnots()[38].setPlayer(p);
    }

    @Test
    public void testInitAllocation() {
        Assert.assertEquals(0, p.getInventory().getWood());
        Assert.assertEquals(0, p.getInventory().getWool());
        Assert.assertEquals(0, p.getInventory().getWheat());
        Assert.assertEquals(0, p.getInventory().getOre());
        Assert.assertEquals(0, p.getInventory().getClay());

        InitResAllocation.allocateInit(gameSession);

        Assert.assertEquals(1, p.getInventory().getWood());
        Assert.assertEquals(1, p.getInventory().getWool());
        Assert.assertEquals(1, p.getInventory().getWheat());
        Assert.assertEquals(1, p.getInventory().getClay());
        Assert.assertEquals(1, p.getInventory().getOre());

    }

}
