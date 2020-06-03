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
    PlayerInventory playerInventory;
    @Before
    public void setUp() {
        gameSession = new GameSession();
        player = new Player("Alex", 1);
        toBeSettled = gameSession.getGameboard().getKnots()[10];
        gameSession.setPlayer(player);
        player.getInventory().addWheat(2);
        player.getInventory().addClay(2);
        player.getInventory().addWood(2);
        player.getInventory().addWool(2);
        playerInventory = player.getInventory();
    }

    @After
    public void tearDown() {
        gameSession = null;
        player = null;
        toBeSettled = null;
    }

    @Test
    public void initialBuilding() {
        //Assertions before building a settlement
        assertionsBeforeInitBuild();

        BuildSettlement.updateGameSession(gameSession, 10, player.getUserId());

        //Assertions after building a settlement
        assertionsAfterInitBuild();
    }

    @Test
    public void regularBuilding() {
        //Assertions before building a settlement
        assertionsBeforeRegularBuild();

        BuildSettlement.updateGameSession(gameSession, 10, player.getUserId());

        //Assertions after building a settlement
        assertionsAfterRegularBuild();
    }


    @Test
    public void updatePortsRegularBuild() {
        assertionsBeforeRegularBuild();
        buildAtClayPort();
        buildAtOrePort();
        buildAtWheatPort();
        buildAtWoolPort();
        buildAtWoodPort();
    }


    @Test
    public void updatePortsInitBuild() {
        assertionsBeforeInitBuild();
        buildAtClayPort();
        buildAtOrePort();
        buildAtWheatPort();
        buildAtWoolPort();
        buildAtWoodPort();
    }

    private void assertionsBeforeRegularBuild() {
        player.getInventory().addSettlement(gameSession.getGameboard().getKnots()[0]);
        player.getInventory().addSettlement(gameSession.getGameboard().getKnots()[40]);
        gameSession.addSettlement(gameSession.getGameboard().getKnots()[0]);
        gameSession.addSettlement(gameSession.getGameboard().getKnots()[40]);

        Assert.assertEquals(false, toBeSettled.isSettled());
        Assert.assertEquals(null, toBeSettled.getPlayer());
        Assert.assertEquals(null, gameSession.getGameboard().getKnots()[10].getPlayer());
        Assert.assertEquals(2, gameSession.getSettlements().size());
        Assert.assertEquals(2, player.getInventory().getSettlements().size());
        Assert.assertEquals(2, playerInventory.getWood());
        Assert.assertEquals(2, playerInventory.getWool());
        Assert.assertEquals(2, playerInventory.getWheat());
        Assert.assertEquals(2, playerInventory.getClay());
        Assert.assertEquals(4, playerInventory.getVictoryPoints());
    }

    private void assertionsAfterRegularBuild() {
        Assert.assertEquals(true, toBeSettled.isSettled());
        Assert.assertEquals(player, toBeSettled.getPlayer());
        Assert.assertEquals(player, gameSession.getGameboard().getKnots()[10].getPlayer());
        Assert.assertEquals(3, gameSession.getSettlements().size());
        Assert.assertEquals(3, player.getInventory().getSettlements().size());
        Assert.assertEquals(1, playerInventory.getWood());
        Assert.assertEquals(1, playerInventory.getWool());
        Assert.assertEquals(1, playerInventory.getWheat());
        Assert.assertEquals(1, playerInventory.getClay());
        Assert.assertEquals(6, playerInventory.getVictoryPoints());
    }

    private void assertionsBeforeInitBuild() {
        Assert.assertEquals(false, toBeSettled.isSettled());
        Assert.assertEquals(null, toBeSettled.getPlayer());
        Assert.assertEquals(null, gameSession.getGameboard().getKnots()[10].getPlayer());
        Assert.assertEquals(0, gameSession.getSettlements().size());
        Assert.assertEquals(0, player.getInventory().getSettlements().size());
        Assert.assertEquals(2, playerInventory.getWood());
        Assert.assertEquals(2, playerInventory.getWool());
        Assert.assertEquals(2, playerInventory.getWheat());
        Assert.assertEquals(2, playerInventory.getClay());
        Assert.assertEquals(0, playerInventory.getVictoryPoints());
    }

    private void assertionsAfterInitBuild() {
        Assert.assertEquals(true, toBeSettled.isSettled());
        Assert.assertEquals(player, toBeSettled.getPlayer());
        Assert.assertEquals(player, gameSession.getGameboard().getKnots()[10].getPlayer());
        Assert.assertEquals(1, gameSession.getSettlements().size());
        Assert.assertEquals(1, player.getInventory().getSettlements().size());
        Assert.assertEquals(2, playerInventory.getWood());
        Assert.assertEquals(2, playerInventory.getWool());
        Assert.assertEquals(2, playerInventory.getWheat());
        Assert.assertEquals(2, playerInventory.getClay());
        Assert.assertEquals(2, playerInventory.getVictoryPoints());
    }

    private void buildAtClayPort() {
        Assert.assertEquals(true, gameSession.getGameboard().getKnots()[47].isHarbourKnot());
        BuildSettlement.updateGameSession(gameSession, 47, player.getUserId());
        Assert.assertEquals(true, playerInventory.isClayport());
    }

    private void buildAtOrePort() {
        Assert.assertEquals(true, gameSession.getGameboard().getKnots()[3].isHarbourKnot());
        BuildSettlement.updateGameSession(gameSession, 3, player.getUserId());
        Assert.assertEquals(true, playerInventory.isOreport());
    }

    private void buildAtWheatPort() {
        Assert.assertEquals(true, gameSession.getGameboard().getKnots()[5].isHarbourKnot());
        BuildSettlement.updateGameSession(gameSession, 5, player.getUserId());
        Assert.assertEquals(true, playerInventory.isWheatport());
    }

    private void buildAtWoolPort() {
        Assert.assertEquals(true, gameSession.getGameboard().getKnots()[15].isHarbourKnot());
        BuildSettlement.updateGameSession(gameSession, 15, player.getUserId());
        Assert.assertEquals(true, playerInventory.isWoolport());
    }

    private void buildAtWoodPort() {
        Assert.assertEquals(true, gameSession.getGameboard().getKnots()[53].isHarbourKnot());
        BuildSettlement.updateGameSession(gameSession, 53, player.getUserId());
        Assert.assertEquals(true, playerInventory.isWoodport());
    }
}
