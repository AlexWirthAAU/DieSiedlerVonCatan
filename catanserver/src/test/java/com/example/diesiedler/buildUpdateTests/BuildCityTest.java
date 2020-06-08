package com.example.diesiedler.buildUpdateTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Knot;
import com.example.catanserver.businesslogic.model.building.BuildCity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BuildCityTest {


    private GameSession gameSession;
    private Player player;
    private Knot toBeSettled;

    @Before
    public void setUp() {
        gameSession = new GameSession();
        player = new Player("Alex", 1);
        toBeSettled = gameSession.getGameboard().getKnots()[10];
        toBeSettled.setPlayer(player);
        gameSession.setPlayer(player);
        player.getInventory().addWheat(2);
        player.getInventory().addOre(3);
    }

    @After
    public void tearDown() {
        gameSession = null;
        player = null;
        toBeSettled = null;
    }

    @Test
    public void testGameSessionUpdate() {
        //Assertions before building
        beforeBuilding();

        BuildCity.updateGameSession(gameSession, 10, player.getUserId());

        //Assertions after building
        afterBuilding();
    }

    private void beforeBuilding() {
        PlayerInventory playerInventory = player.getInventory();

        Assert.assertFalse(toBeSettled.hasCity());
        Assert.assertEquals(player, toBeSettled.getPlayer());
        Assert.assertEquals(0, gameSession.getCities().size());
        Assert.assertEquals(0, player.getInventory().getCities().size());

        Assert.assertEquals(2, playerInventory.getWheat());
        Assert.assertEquals(3, playerInventory.getOre());
        Assert.assertEquals(0, playerInventory.getVictoryPoints());
    }

    private void afterBuilding() {
        PlayerInventory playerInventory = player.getInventory();

        Assert.assertTrue(toBeSettled.hasCity());
        Assert.assertEquals(player, toBeSettled.getPlayer());
        Assert.assertEquals(1, gameSession.getCities().size());
        Assert.assertEquals(1, player.getInventory().getCities().size());

        Assert.assertEquals(0, playerInventory.getWheat());
        Assert.assertEquals(0, playerInventory.getOre());
        Assert.assertEquals(3, playerInventory.getVictoryPoints());
    }

}
