package com.example.diesiedler.tradeTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.businesslogic.model.trading.Bank;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BankTest {

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
    public void checkFailedOnCanTrade() {
        Assert.assertFalse(Bank.checkTrade(player1, "Holz"));
    }

    @Test
    public void checkFailedOnRessource() {
        player1.getInventory().setCanBankTrade(true);
        Assert.assertFalse(Bank.checkTrade(player1, "Holz"));
    }

    @Test
    public void checkSucceedWood() {
        player1.getInventory().addWood(3);
        Assert.assertTrue(Bank.checkTrade(player1, "Holz"));
    }

    @Test
    public void checkSucceedWool() {
        player1.getInventory().addWool(3);
        Assert.assertTrue(Bank.checkTrade(player1, "Wolle"));
    }

    @Test
    public void checkSucceedWheat() {
        player1.getInventory().addWheat(3);
        Assert.assertTrue(Bank.checkTrade(player1, "Weizen"));
    }

    @Test
    public void checkSucceedOre() {
        player1.getInventory().addOre(3);
        Assert.assertTrue(Bank.checkTrade(player1, "Erz"));
    }

    @Test
    public void checkSucceedClay() {
        player1.getInventory().addClay(3);
        Assert.assertTrue(Bank.checkTrade(player1, "Lehm"));
    }
}
