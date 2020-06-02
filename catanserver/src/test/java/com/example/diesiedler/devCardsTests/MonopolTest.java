package com.example.diesiedler.devCardsTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catanserver.businessLogic.model.cards.Monopol;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MonopolTest {
    GameSession gameSession;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    PlayerInventory playerInventory1;

    @Before
    public void setUp() {
        gameSession = new GameSession();
        player1 = new Player("Test", 0);
        player2 = new Player("Test2", 1);
        player3 = new Player("Test3", 2);
        player4 = new Player("Test4", 3);
        gameSession.setPlayer(player1);
        gameSession.setPlayer(player2);
        gameSession.setPlayer(player3);
        gameSession.setPlayer(player4);
    }

    @After
    public void tearDown() {
        gameSession = null;
        player1 = null;
        player2 = null;
        player3 = null;
        player4 = null;
    }

    @Test
    public void assertBeforeAll() {
        playerInventory1 = player1.getInventory();
        Assert.assertEquals(0, playerInventory1.getVictoryPoints());
        Assert.assertEquals(0, playerInventory1.getBuildStreetCard());
        Assert.assertEquals(0, playerInventory1.getKnightCard());
        Assert.assertEquals(0, playerInventory1.getMonopolCard());
        Assert.assertEquals(0, playerInventory1.getInventionCard());
        Assert.assertEquals(0, playerInventory1.getBuildStreetCardLinkedList().size());
        Assert.assertEquals(1, playerInventory1.getWood());
        Assert.assertEquals(1, playerInventory1.getWool());
        Assert.assertEquals(1, playerInventory1.getWheat());
        Assert.assertEquals(1, playerInventory1.getOre());
        Assert.assertEquals(1, playerInventory1.getClay());
        Assert.assertEquals(0, playerInventory1.getCards());
    }

    @Test
    public void checkFailed() {
        Assert.assertFalse(Monopol.checkCards(player1));
    }

    @Test
    public void checkSucceed() {
        player1.getInventory().addMonopolCard(1);
        Assert.assertTrue(Monopol.checkCards(player1));
    }

    @Test
    public void buildMessage() {

        StringBuilder message = new StringBuilder();

        message.append("Du hast eine Monopolkarte gespielt und ");
        message.append(5).append(" ").append("Ressourcen").append(" erhalten");

        Assert.assertEquals(message.toString(), Monopol.buildMessage("Ressourcen", 5));
    }

    @Test
    public void getWood() {
        player1.getInventory().addMonopolCard(1);
        Assert.assertEquals("Holz", Monopol.playCard(player1, "wood", gameSession));
        Assert.assertEquals(0, player1.getInventory().getMonopolCard());
        Assert.assertEquals(0, player1.getInventory().getCards());
        Assert.assertEquals(4, player1.getInventory().getWood());
        Assert.assertEquals(0, player2.getInventory().getWood());
        Assert.assertEquals(0, player3.getInventory().getWood());
        Assert.assertEquals(0, player4.getInventory().getWood());
        Assert.assertEquals(3, Monopol.getNumber());
    }

    @Test
    public void getWool() {
        player1.getInventory().addMonopolCard(1);
        player2.getInventory().addWool(1);
        Assert.assertEquals("Wolle", Monopol.playCard(player1, "wool", gameSession));
        Assert.assertEquals(0, player1.getInventory().getMonopolCard());
        Assert.assertEquals(0, player1.getInventory().getCards());
        Assert.assertEquals(5, player1.getInventory().getWool());
        Assert.assertEquals(0, player2.getInventory().getWool());
        Assert.assertEquals(0, player3.getInventory().getWool());
        Assert.assertEquals(0, player4.getInventory().getWool());
        Assert.assertEquals(4, Monopol.getNumber());
    }

    @Test
    public void getWheat() {
        player1.getInventory().addMonopolCard(1);
        player3.getInventory().removeWheat(1);
        Assert.assertEquals("Weizen", Monopol.playCard(player1, "wheat", gameSession));
        Assert.assertEquals(0, player1.getInventory().getMonopolCard());
        Assert.assertEquals(0, player1.getInventory().getCards());
        Assert.assertEquals(3, player1.getInventory().getWheat());
        Assert.assertEquals(0, player2.getInventory().getWheat());
        Assert.assertEquals(0, player3.getInventory().getWheat());
        Assert.assertEquals(0, player4.getInventory().getWheat());
        Assert.assertEquals(2, Monopol.getNumber());
    }

    @Test
    public void getOre() {
        player1.getInventory().addMonopolCard(1);
        player2.getInventory().removeOre(1);
        player3.getInventory().removeOre(1);
        player4.getInventory().removeOre(1);
        Assert.assertEquals("Erz", Monopol.playCard(player1, "ore", gameSession));
        Assert.assertEquals(0, player1.getInventory().getMonopolCard());
        Assert.assertEquals(0, player1.getInventory().getCards());
        Assert.assertEquals(1, player1.getInventory().getOre());
        Assert.assertEquals(0, player2.getInventory().getOre());
        Assert.assertEquals(0, player3.getInventory().getOre());
        Assert.assertEquals(0, player4.getInventory().getOre());
        Assert.assertEquals(0, Monopol.getNumber());
    }

    @Test
    public void getClay() {
        player1.getInventory().addMonopolCard(1);
        player2.getInventory().addClay(1);
        player3.getInventory().addClay(1);
        Assert.assertEquals("Lehm", Monopol.playCard(player1, "clay", gameSession));
        Assert.assertEquals(0, player1.getInventory().getMonopolCard());
        Assert.assertEquals(0, player1.getInventory().getCards());
        Assert.assertEquals(6, player1.getInventory().getClay());
        Assert.assertEquals(0, player2.getInventory().getClay());
        Assert.assertEquals(0, player3.getInventory().getClay());
        Assert.assertEquals(0, player4.getInventory().getClay());
        Assert.assertEquals(5, Monopol.getNumber());
    }
}
