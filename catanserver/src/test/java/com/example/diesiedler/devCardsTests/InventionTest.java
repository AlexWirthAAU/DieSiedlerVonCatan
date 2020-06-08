package com.example.diesiedler.devCardsTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catanserver.businessLogic.model.cards.Invention;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventionTest {


    private GameSession gameSession;
    private Player player1;

    @Before
    public void setUp() {
        gameSession = new GameSession();
        player1 = new Player("Test", 0);
        gameSession.setPlayer(player1);
        player1.getInventory().setWood(1);
        player1.getInventory().setWool(1);
        player1.getInventory().setWheat(1);
        player1.getInventory().setOre(1);
        player1.getInventory().setClay(1);
    }

    @After
    public void tearDown() {
        gameSession = null;
        player1 = null;
    }

    @Test
    public void assertBeforeAll() {
        PlayerInventory playerInventory1 = player1.getInventory();
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
        Assert.assertFalse(Invention.checkCards(player1));
    }

    @Test
    public void checkSucceed() {
        player1.getInventory().addInventianCard(1);
        Assert.assertTrue(Invention.checkCards(player1));
    }

    @Test
    public void buildMessage() {

        String message = "Du hast eine Erfindungskarte gespielt und zwei " +
                "Ressourcen" + " erhalten";
        Assert.assertEquals(message, Invention.buildMessage("Ressourcen"));
    }

    @Test
    public void getWood() {
        player1.getInventory().addInventianCard(1);
        Assert.assertEquals("Holz", Invention.playCard(player1, "wood"));
        Assert.assertEquals(0, player1.getInventory().getInventionCard());
        Assert.assertEquals(0, player1.getInventory().getCards());
    }

    @Test
    public void getWool() {
        player1.getInventory().addInventianCard(1);
        Assert.assertEquals("Wolle", Invention.playCard(player1, "wool"));
        Assert.assertEquals(0, player1.getInventory().getInventionCard());
        Assert.assertEquals(0, player1.getInventory().getCards());
    }

    @Test
    public void getWheat() {
        player1.getInventory().addInventianCard(1);
        Assert.assertEquals("Weizen", Invention.playCard(player1, "wheat"));
        Assert.assertEquals(0, player1.getInventory().getInventionCard());
        Assert.assertEquals(0, player1.getInventory().getCards());
    }

    @Test
    public void getOre() {
        player1.getInventory().addInventianCard(1);
        Assert.assertEquals("Erz", Invention.playCard(player1, "ore"));
        Assert.assertEquals(0, player1.getInventory().getInventionCard());
        Assert.assertEquals(0, player1.getInventory().getCards());
    }

    @Test
    public void getClay() {
        player1.getInventory().addInventianCard(1);
        Assert.assertEquals("Lehm", Invention.playCard(player1, "clay"));
        Assert.assertEquals(0, player1.getInventory().getInventionCard());
        Assert.assertEquals(0, player1.getInventory().getCards());
    }
}
