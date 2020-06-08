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

    private GameSession gameSession;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private PlayerInventory playerInventory1;
    private PlayerInventory playerInventory2;
    private PlayerInventory playerInventory3;
    private PlayerInventory playerInventory4;

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
    }

    @Test
    public void firstKnightBuy() {
        player1.getInventory().setKnightCard(1);
        KnightPower.checkKnightPowerOnBuy(gameSession, playerInventory1.getKnightCard(), player1);
        Assert.assertEquals(1, playerInventory1.getKnightCard());
        Assert.assertEquals(1, gameSession.getKnightPowerCount());
        Assert.assertEquals(2, playerInventory1.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player1.getUserId());
    }

    @Test
    public void noUpdateOnBuy() {
        playerInventory1.setKnightCard(1);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        playerInventory2.setKnightCard(1);
        KnightPower.checkKnightPowerOnBuy(gameSession, playerInventory2.getKnightCard(), player2);
        Assert.assertEquals(1, playerInventory1.getKnightCard());
        Assert.assertEquals(1, playerInventory2.getKnightCard());
        Assert.assertEquals(1, gameSession.getKnightPowerCount());
        Assert.assertEquals(2, playerInventory1.getVictoryPoints());
        Assert.assertEquals(0, playerInventory2.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player1.getUserId());
    }

    @Test
    public void playerAlreadyHasPowerOnBuy() {
        playerInventory1.setKnightCard(2);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        KnightPower.checkKnightPowerOnBuy(gameSession, playerInventory1.getKnightCard(), player1);
        Assert.assertEquals(2, playerInventory1.getKnightCard());
        Assert.assertEquals(2, gameSession.getKnightPowerCount());
        Assert.assertEquals(2, playerInventory1.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player1.getUserId());
    }

    @Test
    public void updateOnBuy() {
        playerInventory1.setKnightCard(1);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        playerInventory2.setKnightCard(2);
        KnightPower.checkKnightPowerOnBuy(gameSession, playerInventory2.getKnightCard(), player2);
        Assert.assertEquals(1, playerInventory1.getKnightCard());
        Assert.assertEquals(2, playerInventory2.getKnightCard());
        Assert.assertEquals(2, gameSession.getKnightPowerCount());
        Assert.assertEquals(0, playerInventory1.getVictoryPoints());
        Assert.assertEquals(2, playerInventory2.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player2.getUserId());
    }

    @Test
    public void noKnightPower() {
        playerInventory1.setKnightCard(5);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        playerInventory2.setKnightCard(2);
        KnightPower.checkKnightPowerOnPlay(gameSession, playerInventory2.getKnightCard(), player2);
        Assert.assertEquals(5, playerInventory1.getKnightCard());
        Assert.assertEquals(2, playerInventory2.getKnightCard());
        Assert.assertEquals(5, gameSession.getKnightPowerCount());
        Assert.assertEquals(2, playerInventory1.getVictoryPoints());
        Assert.assertEquals(0, playerInventory2.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player1.getUserId());
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
        KnightPower.checkKnightPowerOnPlay(gameSession, playerInventory1.getKnightCard(), player1);
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
    }

    @Test
    public void noUpdateOnPlayCauseEquals() {
        playerInventory1.setKnightCard(5);
        playerInventory1.setVictoryPoints(2);
        gameSession.setKnightPowerCount(playerInventory1.getKnightCard());
        gameSession.setKnightPowerOwner(player1);
        playerInventory2.setKnightCard(5);
        KnightPower.checkKnightPowerOnPlay(gameSession, playerInventory1.getKnightCard(), player1);
        Assert.assertEquals(5, playerInventory1.getKnightCard());
        Assert.assertEquals(5, playerInventory2.getKnightCard());
        Assert.assertEquals(5, gameSession.getKnightPowerCount());
        Assert.assertEquals(2, playerInventory1.getVictoryPoints());
        Assert.assertEquals(0, playerInventory2.getVictoryPoints());
        Assert.assertNotNull(gameSession.getKnightPowerOwner());
        Assert.assertEquals(gameSession.getKnightPowerOwner().getUserId(), player1.getUserId());
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
        KnightPower.checkKnightPowerOnPlay(gameSession, playerInventory1.getKnightCard(), player1);
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
        KnightPower.checkKnightPowerOnPlay(gameSession, playerInventory1.getKnightCard(), player1);
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
        KnightPower.checkKnightPowerOnPlay(gameSession, playerInventory1.getKnightCard(), player1);
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
    }
}
