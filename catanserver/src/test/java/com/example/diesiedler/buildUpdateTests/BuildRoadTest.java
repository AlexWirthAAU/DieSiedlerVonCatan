package com.example.diesiedler.buildUpdateTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Edge;
import com.example.catanserver.businessLogic.model.building.BuildRoad;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BuildRoadTest {

    GameSession gameSession;
    Player player;
    Edge toBeBuild;
    PlayerInventory playerInventory;

    @Before
    public void setUp() {
        gameSession = new GameSession();
        player = new Player("Test", 0);
        toBeBuild = gameSession.getGameboard().getEdges()[0];
        playerInventory = player.getInventory();
        gameSession.setPlayer(player);
    }

    @After
    public void tearDown() {
        gameSession = null;
        player = null;
        toBeBuild = null;
        playerInventory = null;
    }

    @Test
    public void initalBuilding() {
        assertionsBeforeInitBuilding();

        BuildRoad.updateGameSession(gameSession, 0, player.getUserId());

        assertionsAfterInitBuilding();
    }

    @Test
    public void regularBuilding() {
        playerInventory.addRoad(gameSession.getGameboard().getEdges()[20]);
        playerInventory.addRoad(gameSession.getGameboard().getEdges()[21]);
        playerInventory.addRoadKnots(gameSession.getGameboard().getEdges()[20].getOne());
        playerInventory.addRoadKnots(gameSession.getGameboard().getEdges()[20].getTwo());
        playerInventory.addRoadKnots(gameSession.getGameboard().getEdges()[21].getOne());
        playerInventory.addRoadKnots(gameSession.getGameboard().getEdges()[21].getTwo());

        assertionsBeforeRegularBuilding();

        BuildRoad.updateGameSession(gameSession, 0, player.getUserId());

        assertionsAfterRegularBuilding();
    }

    @Test
    public void buildWithDevCard() {
        //TODO: test for building roads with a devcard
    }

    private void assertionsBeforeInitBuilding() {
        Assert.assertEquals(1, playerInventory.getWood());
        Assert.assertEquals(1, playerInventory.getClay());
        Assert.assertEquals(0, playerInventory.getRoads().size());
        Assert.assertEquals(0, playerInventory.getRoadKnots().size());
        Assert.assertEquals(null, toBeBuild.getPlayer());
    }

    private void assertionsAfterInitBuilding() {
        Assert.assertEquals(1, playerInventory.getWood());
        Assert.assertEquals(1, playerInventory.getClay());
        Assert.assertEquals(1, playerInventory.getRoads().size());
        Assert.assertEquals(2, playerInventory.getRoadKnots().size());
        Assert.assertEquals(player, toBeBuild.getPlayer());
    }

    private void assertionsBeforeRegularBuilding() {
        Assert.assertEquals(1, playerInventory.getWood());
        Assert.assertEquals(1, playerInventory.getClay());
        Assert.assertEquals(2, playerInventory.getRoads().size());
        Assert.assertEquals(4, playerInventory.getRoadKnots().size());
        Assert.assertEquals(null, toBeBuild.getPlayer());
    }

    private void assertionsAfterRegularBuilding() {
        Assert.assertEquals(0, playerInventory.getWood());
        Assert.assertEquals(0, playerInventory.getClay());
        Assert.assertEquals(3, playerInventory.getRoads().size());
        Assert.assertEquals(6, playerInventory.getRoadKnots().size());
        Assert.assertEquals(player, toBeBuild.getPlayer());
    }
}
