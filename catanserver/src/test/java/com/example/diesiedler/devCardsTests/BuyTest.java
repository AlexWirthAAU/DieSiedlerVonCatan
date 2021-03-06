package com.example.diesiedler.devCardsTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.devcards.DevCard;
import com.example.catanserver.businesslogic.model.cards.Buy;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class BuyTest {

    private GameSession gameSession;
    private Player player1;
    private PlayerInventory playerInventory1;

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
        Assert.assertEquals(26, gameSession.getDevCards().size());
    }

    @Test
    public void checkFailedOnInventory() {
        player1.getInventory().setWool(0);
        player1.getInventory().setWheat(0);
        player1.getInventory().setOre(0);
        playerInventory1 = player1.getInventory();
        Assert.assertFalse(Buy.checkStack(player1, gameSession));
    }

    @Test
    public void checkFailedOnWheat() {
        player1.getInventory().setWool(1);
        player1.getInventory().setWheat(0);
        player1.getInventory().setOre(0);
        playerInventory1 = player1.getInventory();
        Assert.assertFalse(Buy.checkStack(player1, gameSession));
    }

    @Test
    public void checkFailedOnOre() {
        player1.getInventory().setWool(1);
        player1.getInventory().setWheat(1);
        player1.getInventory().setOre(0);
        playerInventory1 = player1.getInventory();
        Assert.assertFalse(Buy.checkStack(player1, gameSession));
    }

    @Test
    public void checkFailedOnStack() {
        ArrayList<DevCard> stack = (ArrayList<DevCard>) gameSession.getDevCards();
        stack.clear();
        gameSession.setDevCards(stack);
        Assert.assertFalse(Buy.checkStack(player1, gameSession));
    }

    @Test
    public void checkSucceed() {
        Assert.assertTrue(Buy.checkStack(player1, gameSession));
    }

    @Test
    public void buildMessage() {

        Assert.assertEquals("Du hast eine " + "Karte" + " gekauft", Buy.buildMessage("Karte"));
    }

    @Test
    public void buildVictoryMessage() {

        String mess = "Du hast eine " + "Siegpunktkarte" + " gekauft" +
                " und einen Siegpunkt erhalten";
        Assert.assertEquals(mess, Buy.buildMessage("Siegpunktkarte"));
    }

    @Test
    public void buyFirstKnight() {
        Assert.assertEquals("Ritterkarte", Buy.buyCard(player1, gameSession.getDevCards(), gameSession));
        Assert.assertEquals(0, player1.getInventory().getWool());
        Assert.assertEquals(0, player1.getInventory().getWheat());
        Assert.assertEquals(0, player1.getInventory().getOre());
        Assert.assertEquals(25, gameSession.getDevCards().size());
        Assert.assertEquals(1, player1.getInventory().getKnightCard());
        Assert.assertEquals(1, player1.getInventory().getCards());
    }

    @Test
    public void buyRandom() {
        Buy.buyCard(player1, gameSession.getDevCards(), gameSession);
        player1.getInventory().setWool(1);
        player1.getInventory().setWheat(1);
        player1.getInventory().setOre(1);
        Buy.buyCard(player1, gameSession.getDevCards(), gameSession);
        player1.getInventory().setWool(1);
        player1.getInventory().setWheat(1);
        player1.getInventory().setOre(1);
        Buy.buyCard(player1, gameSession.getDevCards(), gameSession);
        player1.getInventory().setWool(1);
        player1.getInventory().setWheat(1);
        player1.getInventory().setOre(1);
        Buy.buyCard(player1, gameSession.getDevCards(), gameSession);
        Assert.assertEquals(0, player1.getInventory().getWool());
        Assert.assertEquals(0, player1.getInventory().getWheat());
        Assert.assertEquals(0, player1.getInventory().getOre());
        Assert.assertEquals(22, gameSession.getDevCards().size());
        Assert.assertTrue(player1.getInventory().getVictoryPoints() >= 3);
        Assert.assertTrue(player1.getInventory().getCards() >= 2);

    }

    @Test
    public void buySecondBuildStreet() {
        Buy.buyCard(player1, gameSession.getDevCards(), gameSession);
        player1.getInventory().setWool(1);
        player1.getInventory().setWheat(1);
        player1.getInventory().setOre(1);
        Assert.assertEquals("Strassenbaukarte", Buy.buyCard(player1, gameSession.getDevCards(), gameSession));
        Assert.assertEquals(0, player1.getInventory().getWool());
        Assert.assertEquals(0, player1.getInventory().getWheat());
        Assert.assertEquals(0, player1.getInventory().getOre());
        Assert.assertEquals(24, gameSession.getDevCards().size());
        Assert.assertEquals(1, player1.getInventory().getBuildStreetCard());
        Assert.assertEquals(1, player1.getInventory().getBuildStreetCardLinkedList().size());
        Assert.assertEquals(2, player1.getInventory().getCards());
    }

    @Test
    public void buyVictoryCard() {
        Buy.buyCard(player1, gameSession.getDevCards(), gameSession);
        player1.getInventory().setWool(1);
        player1.getInventory().setWheat(1);
        player1.getInventory().setOre(1);
        Buy.buyCard(player1, gameSession.getDevCards(), gameSession);
        player1.getInventory().setWool(1);
        player1.getInventory().setWheat(1);
        player1.getInventory().setOre(1);
        Assert.assertEquals("Siegpunktkarte", Buy.buyCard(player1, gameSession.getDevCards(), gameSession));
        Assert.assertEquals(0, player1.getInventory().getWool());
        Assert.assertEquals(0, player1.getInventory().getWheat());
        Assert.assertEquals(0, player1.getInventory().getOre());
        Assert.assertEquals(23, gameSession.getDevCards().size());
        Assert.assertEquals(3, player1.getInventory().getVictoryPoints());
        Assert.assertEquals(2, player1.getInventory().getCards());
    }
}
