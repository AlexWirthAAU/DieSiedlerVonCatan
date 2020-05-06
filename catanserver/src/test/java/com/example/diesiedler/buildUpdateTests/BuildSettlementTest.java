package com.example.diesiedler.buildUpdateTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Knot;
import com.example.catanserver.businessLogic.model.building.BuildSettlement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BuildSettlementTest {

    GameSession gameSession;
    Player player;
    Knot toBeSettled;

    @Before
    public void setUp() {
        gameSession = new GameSession();
        player = new Player("Alex", 1);
        toBeSettled = gameSession.getGameboard().getKnots()[10];
        gameSession.setPlayer(player);
        player.getInventory().addWheat(1);
        player.getInventory().addClay(1);
        player.getInventory().addWood(1);
        player.getInventory().addWool(1);
    }

    @After
    public void tearDown() {
        gameSession = null;
        player = null;
        toBeSettled = null;
    }

    @Test
    public void testGameSessionUpdate() {
        //Assertions before building a settlement
        assertionsBeforeBuilding();

        BuildSettlement.updateGameSession(gameSession, 10, player.getUserId());

        //Assertions after building a settlement
        assertionsAfterBuilding();
    }

    private void assertionsBeforeBuilding() {
        PlayerInventory playerInventory = player.getInventory();

        Assert.assertEquals(false, toBeSettled.isSettled());
        Assert.assertEquals(null, toBeSettled.getPlayer());
        Assert.assertEquals(null, gameSession.getGameboard().getKnots()[10].getPlayer());
        Assert.assertEquals(0, gameSession.getSettlements().size());
        Assert.assertEquals(0, player.getInventory().getSettlements().size());

        Assert.assertEquals(1, playerInventory.getWood());
        Assert.assertEquals(1, playerInventory.getWool());
        Assert.assertEquals(1, playerInventory.getWheat());
        Assert.assertEquals(1, playerInventory.getClay());
    }

    private void assertionsAfterBuilding() {
        PlayerInventory playerInventory = player.getInventory();

        Assert.assertEquals(true, toBeSettled.isSettled());
        Assert.assertEquals(player, toBeSettled.getPlayer());
        Assert.assertEquals(player, gameSession.getGameboard().getKnots()[10].getPlayer());
        Assert.assertEquals(1, gameSession.getSettlements().size());
        Assert.assertEquals(1, player.getInventory().getSettlements().size());

        Assert.assertEquals(0, playerInventory.getWood());
        Assert.assertEquals(0, playerInventory.getWool());
        Assert.assertEquals(0, playerInventory.getWheat());
        Assert.assertEquals(0, playerInventory.getClay());
    }

}
