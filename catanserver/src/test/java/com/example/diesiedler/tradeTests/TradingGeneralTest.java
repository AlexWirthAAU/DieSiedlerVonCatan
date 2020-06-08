package com.example.diesiedler.tradeTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.businesslogic.model.trading.TradingGeneral;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TradingGeneralTest {

    private GameSession gameSession;
    private Player player1;
    private TradingGeneral tradingGeneral;

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
        tradingGeneral = new TradingGeneral();
    }

    @After
    public void tearDown() {
        gameSession = null;
        player1 = null;
        tradingGeneral = null;
    }

    @Test
    public void assertBeforeAll() {
        Assert.assertEquals(1, player1.getInventory().getWood());
        Assert.assertEquals(1, player1.getInventory().getWool());
        Assert.assertEquals(1, player1.getInventory().getWheat());
        Assert.assertEquals(1, player1.getInventory().getOre());
        Assert.assertEquals(1, player1.getInventory().getClay());
        Assert.assertFalse(player1.getInventory().isCanBankTrade());
        Assert.assertFalse(player1.getInventory().isCanPortTrade());
        Assert.assertFalse(player1.getInventory().isHasPorts());
        Assert.assertTrue(player1.getInventory().isCanTrade());
    }

    @Test
    public void buildMessage() {

        Assert.assertEquals("Du hast erfolgreich 4 " + "Holz" + " gegen 1 " + "Wolle" + " getauscht", tradingGeneral.buildMessage("Holz", "Wolle", 4));
    }

    @Test
    public void buildMessagePort() {

        Assert.assertEquals("Du hast erfolgreich 3 " + "Holz" + " gegen 1 " + "Wolle" + " getauscht", tradingGeneral.buildMessage("Holz", "Wolle", 3));
    }

    @Test
    public void setTradeData() {

        tradingGeneral.setTradeData("Holz" + "/" + "Wolle");

        Assert.assertEquals("Holz", tradingGeneral.getOffered());
        Assert.assertEquals("Wolle", tradingGeneral.getDesired());
    }

    @Test
    public void giveWoodgetWool() {
        player1.getInventory().addWood(3);
        tradingGeneral.exchangeResources("Holz", "Wolle", player1, 4);
        Assert.assertEquals(2, player1.getInventory().getWool());
        Assert.assertEquals(0, player1.getInventory().getWood());
        Assert.assertFalse(player1.getInventory().isCanBankTrade());
    }

    @Test
    public void giveWoolgetWheat() {
        player1.getInventory().addWool(3);
        tradingGeneral.exchangeResources("Wolle", "Weizen", player1, 4);
        Assert.assertEquals(2, player1.getInventory().getWheat());
        Assert.assertEquals(0, player1.getInventory().getWool());
        Assert.assertFalse(player1.getInventory().isCanBankTrade());
    }

    @Test
    public void giveWheatgetOre() {
        player1.getInventory().addWheat(3);
        tradingGeneral.exchangeResources("Weizen", "Erz", player1, 4);
        Assert.assertEquals(2, player1.getInventory().getOre());
        Assert.assertEquals(0, player1.getInventory().getWheat());
        Assert.assertFalse(player1.getInventory().isCanBankTrade());
    }

    @Test
    public void giveOregetClay() {
        player1.getInventory().addOre(3);
        tradingGeneral.exchangeResources("Erz", "Lehm", player1, 4);
        Assert.assertEquals(2, player1.getInventory().getClay());
        Assert.assertEquals(0, player1.getInventory().getOre());
        Assert.assertFalse(player1.getInventory().isCanBankTrade());
    }

    @Test
    public void giveClaygetWood() {
        player1.getInventory().addClay(3);
        tradingGeneral.exchangeResources("Lehm", "Holz", player1, 4);
        Assert.assertEquals(2, player1.getInventory().getWood());
        Assert.assertEquals(0, player1.getInventory().getClay());
        Assert.assertFalse(player1.getInventory().isCanBankTrade());
    }


    @Test
    public void giveWoodgetWoolPort() {
        player1.getInventory().addWood(2);
        tradingGeneral.exchangeResources("Holz", "Wolle", player1, 3);
        Assert.assertEquals(2, player1.getInventory().getWool());
        Assert.assertEquals(0, player1.getInventory().getWood());
        Assert.assertFalse(player1.getInventory().isCanPortTrade());
    }

    @Test
    public void giveWoolgetWheatPort() {
        player1.getInventory().addWool(2);
        tradingGeneral.exchangeResources("Wolle", "Weizen", player1, 3);
        Assert.assertEquals(2, player1.getInventory().getWheat());
        Assert.assertEquals(0, player1.getInventory().getWool());
        Assert.assertFalse(player1.getInventory().isCanPortTrade());
    }

    @Test
    public void giveWheatgetOrePort() {
        player1.getInventory().addWheat(2);
        tradingGeneral.exchangeResources("Weizen", "Erz", player1, 3);
        Assert.assertEquals(2, player1.getInventory().getOre());
        Assert.assertEquals(0, player1.getInventory().getWheat());
        Assert.assertFalse(player1.getInventory().isCanPortTrade());
    }

    @Test
    public void giveOregetClayPort() {
        player1.getInventory().addOre(2);
        tradingGeneral.exchangeResources("Erz", "Lehm", player1, 3);
        Assert.assertEquals(2, player1.getInventory().getClay());
        Assert.assertEquals(0, player1.getInventory().getOre());
        Assert.assertFalse(player1.getInventory().isCanPortTrade());
    }

    @Test
    public void giveClaygetWoodPort() {
        player1.getInventory().addClay(2);
        tradingGeneral.exchangeResources("Lehm", "Holz", player1, 3);
        Assert.assertEquals(2, player1.getInventory().getWood());
        Assert.assertEquals(0, player1.getInventory().getClay());
        Assert.assertFalse(player1.getInventory().isCanPortTrade());
    }
}
