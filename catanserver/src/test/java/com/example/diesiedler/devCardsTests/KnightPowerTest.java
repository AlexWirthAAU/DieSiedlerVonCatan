package com.example.diesiedler.devCardsTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catanserver.businessLogic.model.KnightPower;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KnightPowerTest {

    GameSession gameSession;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    KnightPower knightPower;
    PlayerInventory playerInventory1;
    PlayerInventory playerInventory2;
    PlayerInventory playerInventory3;
    PlayerInventory playerInventory4;

    @Before
    public void setUp() {
        gameSession = new GameSession();
        player1 = new Player("TestOne", 0);
        player2 = new Player("TestTwo", 1);
        player3 = new Player("TestThree", 2);
        player4 = new Player("TestFour", 3);
        gameSession.setPlayer(player1);
        gameSession.setPlayer(player2);
        gameSession.setPlayer(player3);
        gameSession.setPlayer(player4);
        knightPower = new KnightPower();
        playerInventory1 = player1.getInventory();
        playerInventory2 = player2.getInventory();
        playerInventory3 = player3.getInventory();
        playerInventory4 = player4.getInventory();
    }

    @After
    public void tearDown() {
        gameSession = null;
        player1 = null;
        player2 = null;
        player3 = null;
        player4 = null;
        playerInventory1 = null;
        playerInventory2 = null;
        playerInventory3 = null;
        playerInventory4 = null;
    }

    @Test
    public void assertBeforeAll() {
        Assert.assertEquals(0, playerInventory1.getKnightCard());
        Assert.assertEquals(0, playerInventory1.getVictoryPoints());
        Assert.assertEquals(0, playerInventory2.getKnightCard());
        Assert.assertEquals(0, playerInventory2.getVictoryPoints());
        Assert.assertEquals(0, playerInventory3.getKnightCard());
        Assert.assertEquals(0, playerInventory3.getVictoryPoints());
        Assert.assertEquals(0, playerInventory4.getKnightCard());
        Assert.assertEquals(0, playerInventory4.getVictoryPoints());
        Assert.assertEquals(0, gameSession.getKnightPowerCount());
        Assert.assertNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(0, gameSession.getCurrPlayer());
    }

    @Test
    public void firstKnightBuy() {
        player1.getInventory().setKnightCard(1);
        knightPower.checkKnightPowerOnBuy(gameSession, playerInventory1.getKnightCard(), player1);
        Assert.assertEquals(1, playerInventory1.getKnightCard());
        Assert.assertEquals(1, gameSession.getKnightPowerCount());
        Assert.assertEquals(2, playerInventory1.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player1.getUserId());
        Assert.assertEquals(1, gameSession.getCurrPlayer());
    }

    @Test
    public void noUpdateOnBuy() {
        playerInventory1.setKnightCard(1);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        playerInventory2.setKnightCard(1);
        knightPower.checkKnightPowerOnBuy(gameSession, playerInventory2.getKnightCard(), player2);
        Assert.assertEquals(1, playerInventory1.getKnightCard());
        Assert.assertEquals(1, playerInventory2.getKnightCard());
        Assert.assertEquals(1, gameSession.getKnightPowerCount());
        Assert.assertEquals(2, playerInventory1.getVictoryPoints());
        Assert.assertEquals(0, playerInventory2.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player1.getUserId());
        Assert.assertEquals(1, gameSession.getCurrPlayer());
    }

    @Test
    public void playerAlreadyHasPowerOnBuy() {
        playerInventory1.setKnightCard(2);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        knightPower.checkKnightPowerOnBuy(gameSession, playerInventory1.getKnightCard(), player1);
        Assert.assertEquals(2, playerInventory1.getKnightCard());
        Assert.assertEquals(2, gameSession.getKnightPowerCount());
        Assert.assertEquals(2, playerInventory1.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player1.getUserId());
        Assert.assertEquals(1, gameSession.getCurrPlayer());
    }

    @Test
    public void updateOnBuy() {
        playerInventory1.setKnightCard(1);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        playerInventory2.setKnightCard(2);
        knightPower.checkKnightPowerOnBuy(gameSession, playerInventory2.getKnightCard(), player2);
        Assert.assertEquals(1, playerInventory1.getKnightCard());
        Assert.assertEquals(2, playerInventory2.getKnightCard());
        Assert.assertEquals(2, gameSession.getKnightPowerCount());
        Assert.assertEquals(0, playerInventory1.getVictoryPoints());
        Assert.assertEquals(2, playerInventory2.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player2.getUserId());
        Assert.assertEquals(1, gameSession.getCurrPlayer());
    }

    @Test
    public void noKnightPower() {
        playerInventory1.setKnightCard(5);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        playerInventory2.setKnightCard(2);
        knightPower.checkKnightPowerOnPlay(gameSession, playerInventory2.getKnightCard(), player2);
        Assert.assertEquals(5, playerInventory1.getKnightCard());
        Assert.assertEquals(2, playerInventory2.getKnightCard());
        Assert.assertEquals(5, gameSession.getKnightPowerCount());
        Assert.assertEquals(2, playerInventory1.getVictoryPoints());
        Assert.assertEquals(0, playerInventory2.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player1.getUserId());
        Assert.assertEquals(1, gameSession.getCurrPlayer());
    }

    @Test
    public void noUpdateOnPlay() {
        playerInventory1.setKnightCard(5);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        playerInventory2.setKnightCard(0);
        playerInventory3.setKnightCard(0);
        playerInventory4.setKnightCard(0);
        knightPower.checkKnightPowerOnPlay(gameSession, playerInventory1.getKnightCard(), player1);
        Assert.assertEquals(5, playerInventory1.getKnightCard());
        Assert.assertEquals(0, playerInventory2.getKnightCard());
        Assert.assertEquals(0, playerInventory3.getKnightCard());
        Assert.assertEquals(0, playerInventory4.getKnightCard());
        Assert.assertEquals(5, gameSession.getKnightPowerCount());
        Assert.assertEquals(2, playerInventory1.getVictoryPoints());
        Assert.assertEquals(0, playerInventory2.getVictoryPoints());
        Assert.assertEquals(0, playerInventory3.getVictoryPoints());
        Assert.assertEquals(0, playerInventory3.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player1.getUserId());
        Assert.assertEquals(1, gameSession.getCurrPlayer());
    }

    @Test
    public void noUpdateOnPlayCauseEquals() {
        playerInventory1.setKnightCard(5);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        playerInventory2.setKnightCard(5);
        knightPower.checkKnightPowerOnPlay(gameSession, playerInventory1.getKnightCard(), player1);
        Assert.assertEquals(5, playerInventory1.getKnightCard());
        Assert.assertEquals(5, playerInventory2.getKnightCard());
        Assert.assertEquals(5, gameSession.getKnightPowerCount());
        Assert.assertEquals(2, playerInventory1.getVictoryPoints());
        Assert.assertEquals(0, playerInventory2.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player1.getUserId());
        Assert.assertEquals(1, gameSession.getCurrPlayer());
    }

    @Test
    public void allNull() {
        playerInventory1.setKnightCard(0);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        playerInventory2.setKnightCard(0);
        playerInventory3.setKnightCard(0);
        playerInventory4.setKnightCard(0);
        knightPower.checkKnightPowerOnPlay(gameSession, playerInventory1.getKnightCard(), player1);
        Assert.assertEquals(0, playerInventory1.getKnightCard());
        Assert.assertEquals(0, playerInventory2.getKnightCard());
        Assert.assertEquals(0, playerInventory3.getKnightCard());
        Assert.assertEquals(0, playerInventory4.getKnightCard());
        Assert.assertEquals(0, gameSession.getKnightPowerCount());
        Assert.assertEquals(0, playerInventory1.getVictoryPoints());
        Assert.assertEquals(0, playerInventory2.getVictoryPoints());
        Assert.assertEquals(0, playerInventory3.getVictoryPoints());
        Assert.assertEquals(0, playerInventory3.getVictoryPoints());
        Assert.assertNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(1, gameSession.getCurrPlayer());
    }

    @Test
    public void UpdateOnPlay() {
        playerInventory1.setKnightCard(3);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        playerInventory2.setKnightCard(4);
        playerInventory3.setKnightCard(2);
        playerInventory4.setKnightCard(1);
        knightPower.checkKnightPowerOnPlay(gameSession, playerInventory1.getKnightCard(), player1);
        Assert.assertEquals(3, playerInventory1.getKnightCard());
        Assert.assertEquals(4, playerInventory2.getKnightCard());
        Assert.assertEquals(2, playerInventory3.getKnightCard());
        Assert.assertEquals(1, playerInventory4.getKnightCard());
        Assert.assertEquals(4, gameSession.getKnightPowerCount());
        Assert.assertEquals(0, playerInventory1.getVictoryPoints());
        Assert.assertEquals(2, playerInventory2.getVictoryPoints());
        Assert.assertEquals(0, playerInventory3.getVictoryPoints());
        Assert.assertEquals(0, playerInventory3.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player2.getUserId());
        Assert.assertEquals(1, gameSession.getCurrPlayer());
    }

    @Test
    public void UpdateFirstOnPlay() {
        playerInventory1.setKnightCard(3);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        playerInventory2.setKnightCard(4);
        playerInventory3.setKnightCard(4);
        playerInventory4.setKnightCard(1);
        knightPower.checkKnightPowerOnPlay(gameSession, playerInventory1.getKnightCard(), player1);
        Assert.assertEquals(3, playerInventory1.getKnightCard());
        Assert.assertEquals(4, playerInventory2.getKnightCard());
        Assert.assertEquals(4, playerInventory3.getKnightCard());
        Assert.assertEquals(1, playerInventory4.getKnightCard());
        Assert.assertEquals(4, gameSession.getKnightPowerCount());
        Assert.assertEquals(0, playerInventory1.getVictoryPoints());
        Assert.assertEquals(2, playerInventory2.getVictoryPoints());
        Assert.assertEquals(0, playerInventory3.getVictoryPoints());
        Assert.assertEquals(0, playerInventory3.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player2.getUserId());
        Assert.assertEquals(1, gameSession.getCurrPlayer());
    }
}
