package com.example.diesiedler.tradeTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.businessLogic.model.trading.Port;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PortTest {
    GameSession gameSession;
    Player player1;

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
        Assert.assertEquals(1, player1.getInventory().getWood());
        Assert.assertEquals(1, player1.getInventory().getWool());
        Assert.assertEquals(1, player1.getInventory().getWheat());
        Assert.assertEquals(1, player1.getInventory().getOre());
        Assert.assertEquals(1, player1.getInventory().getClay());
        Assert.assertFalse(player1.getInventory().canBankTrade);
        Assert.assertFalse(player1.getInventory().canPortTrade);
        Assert.assertFalse(player1.getInventory().hasPorts);
        Assert.assertTrue(player1.getInventory().canTrade);
    }

    @Test
    public void checkFailedOnCanTrade() {
        player1.getInventory().setHasPorts(true);
        Assert.assertFalse(Port.checkTrade(player1, "Holz"));
    }

    @Test
    public void checkFailedOnPorts() {
        player1.getInventory().addWood(2);
        Assert.assertFalse(Port.checkTrade(player1, "Holz"));
    }

    @Test
    public void checkFailedOnRessource() {
        player1.getInventory().setHasPorts(true);
        player1.getInventory().setWoolport(true);
        player1.getInventory().addWood(2);
        Assert.assertFalse(Port.checkTrade(player1, "Holz"));
    }

    @Test
    public void checkSucceedWood() {
        player1.getInventory().setHasPorts(true);
        player1.getInventory().setWoodport(true);
        player1.getInventory().addWood(2);
        Assert.assertTrue(Port.checkTrade(player1, "Holz"));
    }

    @Test
    public void checkSucceedWool() {
        player1.getInventory().setHasPorts(true);
        player1.getInventory().setWoolport(true);
        player1.getInventory().addWool(3);
        Assert.assertTrue(Port.checkTrade(player1, "Wolle"));
    }

    @Test
    public void checkSucceedWheat() {
        player1.getInventory().setHasPorts(true);
        player1.getInventory().setWheatport(true);
        player1.getInventory().addWheat(3);
        Assert.assertTrue(Port.checkTrade(player1, "Weizen"));
    }

    @Test
    public void checkSucceedOre() {
        player1.getInventory().setHasPorts(true);
        player1.getInventory().setOreport(true);
        player1.getInventory().addOre(3);
        Assert.assertTrue(Port.checkTrade(player1, "Erz"));
    }

    @Test
    public void checkSucceedClay() {
        player1.getInventory().setHasPorts(true);
        player1.getInventory().setClayport(true);
        player1.getInventory().addClay(3);
        Assert.assertTrue(Port.checkTrade(player1, "Lehm"));
    }

    @Test
    public void buildMessage() {

        StringBuilder message = new StringBuilder();

        message.append("Du hast erfolgreich 3 ").append("Holz").append(" gegen 1 ").append("Wolle").append(" getauscht");

        Assert.assertEquals(message.toString(), Port.buildMessage("Holz", "Wolle"));
    }

    @Test
    public void setTradeData() {

        StringBuilder message = new StringBuilder();

        message.append("Holz").append("/").append("Wolle");

        Port.setTradeData(message.toString());

        Assert.assertEquals("Holz", Port.getOffered());
        Assert.assertEquals("Wolle", Port.getDesired());
        Assert.assertNull(Port.getOffered());
    }

    @Test
    public void giveWoodgetWool() {
        player1.getInventory().addWood(2);
        Port.exchangeRessources("Holz", "Wolle", player1);
        Assert.assertEquals(2, player1.getInventory().getWool());
        Assert.assertEquals(0, player1.getInventory().getWood());
        Assert.assertFalse(player1.getInventory().canPortTrade);
    }

    @Test
    public void giveWoolgetWheat() {
        player1.getInventory().addWool(2);
        Port.exchangeRessources("Wolle", "Weizen", player1);
        Assert.assertEquals(2, player1.getInventory().getWheat());
        Assert.assertEquals(0, player1.getInventory().getWool());
        Assert.assertFalse(player1.getInventory().canPortTrade);
    }

    @Test
    public void giveWheatgetOre() {
        player1.getInventory().addWheat(2);
        Port.exchangeRessources("Weizen", "Erz", player1);
        Assert.assertEquals(2, player1.getInventory().getOre());
        Assert.assertEquals(0, player1.getInventory().getWheat());
        Assert.assertFalse(player1.getInventory().canPortTrade);
    }

    @Test
    public void giveOregetClay() {
        player1.getInventory().addOre(2);
        Port.exchangeRessources("Erz", "Lehm", player1);
        Assert.assertEquals(2, player1.getInventory().getClay());
        Assert.assertEquals(0, player1.getInventory().getOre());
        Assert.assertFalse(player1.getInventory().canPortTrade);
    }

    @Test
    public void giveClaygetWood() {
        player1.getInventory().addClay(2);
        Port.exchangeRessources("Lehm", "Holz", player1);
        Assert.assertEquals(2, player1.getInventory().getWood());
        Assert.assertEquals(0, player1.getInventory().getClay());
        Assert.assertFalse(player1.getInventory().canPortTrade);
    }
}
