package com.example.diesiedler.buildUpdateTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Edge;
import com.example.catanserver.businesslogic.model.building.BuildRoad;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BuildRoadTest {

    private GameSession gameSession;
    private Player playerOne;
    private Player playerTwo;
    private Edge toBeBuildOne, toBeBuildTwo, toBeBuildThree, toBeBuildFour;
    private PlayerInventory playerInventoryOne;
    private PlayerInventory playerInventoryTwo;

    @Before
    public void setUp() {
        gameSession = new GameSession();
        playerOne = new Player("TestOne", 0);
        playerTwo = new Player("TestTwo", 1);
        toBeBuildOne = gameSession.getGameboard().getEdges()[0];
        toBeBuildTwo = gameSession.getGameboard().getEdges()[5];
        toBeBuildThree = gameSession.getGameboard().getEdges()[7];
        toBeBuildFour = gameSession.getGameboard().getEdges()[8];
        playerInventoryOne = playerOne.getInventory();
        playerInventoryTwo = playerTwo.getInventory();
        gameSession.setPlayer(playerOne);
        gameSession.setPlayer(playerTwo);
    }

    @After
    public void tearDown() {
        gameSession = null;
        playerOne = null;
        toBeBuildOne = null;
        toBeBuildTwo = null;
        playerInventoryOne = null;
    }


    @Test
    public void testSetUp() {
        Assert.assertEquals(playerOne, gameSession.getCurr());
        Assert.assertEquals(playerTwo, gameSession.getPlayers().get(1));

        assertionsBeforeTurnOne();
    }


    @Test
    public void firstTwoTurns() {
        BuildRoad.updateGameSession(gameSession, 0, playerOne.getUserId());
        assertionsAfterPlayerOne();

        BuildRoad.updateGameSession(gameSession, 5, playerTwo.getUserId());
        assertionsAfterPlayerTwo();

        BuildRoad.updateGameSession(gameSession, 7, playerTwo.getUserId());
        assertionsAfterPlayerTwoTurnTwo();

        BuildRoad.updateGameSession(gameSession, 8, playerOne.getUserId());
        assertionsAfterPlayerOneTurnTwo();
    }

    @Test
    public void regularBuilding() {
        playerInventoryOne.addRoad(gameSession.getGameboard().getEdges()[20]);
        playerInventoryOne.addRoad(gameSession.getGameboard().getEdges()[21]);
        playerInventoryOne.addRoadKnots(gameSession.getGameboard().getEdges()[20].getOne());
        playerInventoryOne.addRoadKnots(gameSession.getGameboard().getEdges()[20].getTwo());
        playerInventoryOne.addRoadKnots(gameSession.getGameboard().getEdges()[21].getOne());
        playerInventoryOne.addRoadKnots(gameSession.getGameboard().getEdges()[21].getTwo());
        playerInventoryOne.addWood(1);
        playerInventoryOne.addClay(1);

        assertionsBeforeRegularBuilding();

        BuildRoad.updateGameSession(gameSession, 0, playerOne.getUserId());

        assertionsAfterRegularBuilding();
    }

    @Test
    public void buildWithDevCard() {
        playerInventoryOne.addRoad(gameSession.getGameboard().getEdges()[20]);
        playerInventoryOne.addRoad(gameSession.getGameboard().getEdges()[21]);
        playerInventoryOne.addRoadKnots(gameSession.getGameboard().getEdges()[20].getOne());
        playerInventoryOne.addRoadKnots(gameSession.getGameboard().getEdges()[20].getTwo());
        playerInventoryOne.addRoadKnots(gameSession.getGameboard().getEdges()[21].getOne());
        playerInventoryOne.addRoadKnots(gameSession.getGameboard().getEdges()[21].getTwo());
        playerOne.getInventory().addBuildStreetCard(1);

        BuildRoad.buildRoadWithCard(gameSession, 0, playerOne.getUserId());
        BuildRoad.buildRoadWithCard(gameSession, 5, playerOne.getUserId());

        assertionsAfterBuildingWithCard();
    }

    private void assertionsBeforeTurnOne() {
        Assert.assertEquals(0, playerInventoryOne.getWood());
        Assert.assertEquals(0, playerInventoryOne.getClay());
        Assert.assertEquals(0, playerInventoryOne.getRoads().size());
        Assert.assertEquals(0, playerInventoryOne.getRoadKnots().size());
        Assert.assertNull(toBeBuildOne.getPlayer());

        Assert.assertEquals(0, playerInventoryTwo.getWood());
        Assert.assertEquals(0, playerInventoryTwo.getClay());
        Assert.assertEquals(0, playerInventoryTwo.getRoads().size());
        Assert.assertEquals(0, playerInventoryTwo.getRoadKnots().size());
        Assert.assertNull(toBeBuildOne.getPlayer());
    }


    private void assertionsAfterPlayerOne() {
        Assert.assertEquals(0, playerInventoryOne.getWood());
        Assert.assertEquals(0, playerInventoryOne.getClay());
        Assert.assertEquals(1, playerInventoryOne.getRoads().size());
        Assert.assertEquals(2, playerInventoryOne.getRoadKnots().size());
        Assert.assertEquals(playerOne, toBeBuildOne.getPlayer());
        Assert.assertEquals(playerTwo, gameSession.getCurr());
    }

    private void assertionsAfterPlayerTwo() {
        Assert.assertEquals(0, playerInventoryTwo.getWood());
        Assert.assertEquals(0, playerInventoryTwo.getClay());
        Assert.assertEquals(1, playerInventoryTwo.getRoads().size());
        Assert.assertEquals(2, playerInventoryTwo.getRoadKnots().size());
        Assert.assertEquals(playerTwo, toBeBuildTwo.getPlayer());
        Assert.assertEquals(playerTwo, gameSession.getCurr());
    }

    private void assertionsAfterPlayerOneTurnTwo() {
        Assert.assertEquals(0, playerInventoryOne.getWood());
        Assert.assertEquals(0, playerInventoryOne.getClay());
        Assert.assertEquals(2, playerInventoryOne.getRoads().size());
        Assert.assertEquals(4, playerInventoryOne.getRoadKnots().size());
        Assert.assertEquals(playerOne, toBeBuildFour.getPlayer());
        Assert.assertEquals(playerOne, gameSession.getCurr());
    }

    private void assertionsAfterPlayerTwoTurnTwo() {
        Assert.assertEquals(0, playerInventoryTwo.getWood());
        Assert.assertEquals(0, playerInventoryTwo.getClay());
        Assert.assertEquals(2, playerInventoryTwo.getRoads().size());
        Assert.assertEquals(4, playerInventoryTwo.getRoadKnots().size());
        Assert.assertEquals(playerTwo, toBeBuildThree.getPlayer());
        Assert.assertEquals(playerOne, gameSession.getCurr());
    }

    private void assertionsBeforeRegularBuilding() {
        Assert.assertEquals(1, playerInventoryOne.getWood());
        Assert.assertEquals(1, playerInventoryOne.getClay());
        Assert.assertEquals(2, playerInventoryOne.getRoads().size());
        Assert.assertEquals(4, playerInventoryOne.getRoadKnots().size());
        Assert.assertNull(toBeBuildOne.getPlayer());
    }

    private void assertionsAfterRegularBuilding() {
        Assert.assertEquals(0, playerInventoryOne.getWood());
        Assert.assertEquals(0, playerInventoryOne.getClay());
        Assert.assertEquals(3, playerInventoryOne.getRoads().size());
        Assert.assertEquals(6, playerInventoryOne.getRoadKnots().size());
        Assert.assertEquals(playerOne, toBeBuildOne.getPlayer());
    }

    private void assertionsAfterBuildingWithCard() {
        Assert.assertEquals(0, playerInventoryOne.getWood());
        Assert.assertEquals(0, playerInventoryOne.getClay());
        Assert.assertEquals(4, playerInventoryOne.getRoads().size());
        Assert.assertEquals(8, playerInventoryOne.getRoadKnots().size());
        Assert.assertEquals(0, playerInventoryOne.getBuildStreetCard());
        Assert.assertEquals(0, playerInventoryOne.getBuildStreetCardLinkedList().size());
        Assert.assertEquals(playerOne, toBeBuildOne.getPlayer());
        Assert.assertEquals(playerOne, toBeBuildTwo.getPlayer());
    }
}
