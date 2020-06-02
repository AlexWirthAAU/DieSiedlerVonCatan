package com.example.diesiedler.tradeTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.businessLogic.model.trading.StartTrade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeTest {
    GameSession gameSession;
    Player player1;
    Player player2;
    Player player3;
    Player player4;

    @Before
    public void setUp() {
        gameSession = new GameSession();
        player1 = new Player("Test", 0);
        gameSession.setPlayer(player1);
        player2 = new Player("Test2", 1);
        player3 = new Player("Test3", 2);
        player4 = new Player("Test4", 3);
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
        Assert.assertEquals(1, player1.getInventory().getWood());
        Assert.assertEquals(1, player1.getInventory().getWool());
        Assert.assertEquals(1, player1.getInventory().getWheat());
        Assert.assertEquals(1, player1.getInventory().getOre());
        Assert.assertEquals(1, player1.getInventory().getClay());
        Assert.assertFalse(player1.getInventory().canBankTrade);
        Assert.assertFalse(player1.getInventory().canPortTrade);
        Assert.assertFalse(player1.getInventory().hasPorts);
        Assert.assertTrue(player1.getInventory().canTrade);
        Assert.assertNull(gameSession.getTrade());
    }

    @Test
    public void checkFailedOnCanTrade() {
        Map<String, Integer> map = new HashMap<>();
        player1.getInventory().removeAllWood();
        player1.getInventory().removeAllWool();
        player1.getInventory().removeAllWheat();
        player1.getInventory().removeAllOre();
        player1.getInventory().removeAllClay();
        Assert.assertFalse(StartTrade.checkTrade(map, player1));
    }

    @Test
    public void checkFailedOnRessource() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Holz", 2);
        map.put("Wolle", 0);
        map.put("Weizen", 0);
        map.put("Erz", 0);
        map.put("Lehm", 0);
        Assert.assertFalse(StartTrade.checkTrade(map, player1));
    }

    @Test
    public void checkSucceed() {
        player1.getInventory().addWood(1);
        Map<String, Integer> map = new HashMap<>();
        map.put("Holz", 2);
        map.put("Wolle", 0);
        map.put("Weizen", 0);
        map.put("Erz", 0);
        map.put("Lehm", 0);
        Assert.assertTrue(StartTrade.checkTrade(map, player1));
    }

    @Test
    public void buildMessage() {

        Map<String, Integer> map = new HashMap<>();
        map.put("Holz", 2);
        map.put("Wolle", 0);
        map.put("Weizen", 0);
        map.put("Erz", 0);
        map.put("Lehm", 0);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("Holz", 2);
        map2.put("Wolle", 0);
        map2.put("Weizen", 0);
        map2.put("Erz", 1);
        map2.put("Lehm", 0);

        StringBuilder message = new StringBuilder();

        message.append(player1.getDisplayName()).append(" bietet ").append("2 Holz und moechte dafuer 2 Holz, 1 Erz, ");

        Assert.assertEquals(message.toString(), StartTrade.buildMessage(player1, map, map2));
    }

    @Test
    public void setTradeData() {

        StringBuilder tradeMap = new StringBuilder();
        tradeMap.append("Holz/").append(0);
        tradeMap.append("/Wolle/").append(0);
        tradeMap.append("/Weizen/").append(0);
        tradeMap.append("/Erz/").append(0);
        tradeMap.append("/Lehm/").append(0);
        tradeMap.append("/splitter/").append(-1);
        tradeMap.append("/Holz/").append(0);
        tradeMap.append("/Wolle/").append(0);
        tradeMap.append("/Weizen/").append(0);
        tradeMap.append("/Erz/").append(0);
        tradeMap.append("/Lehm/").append(0);

        Assert.assertEquals(0, StartTrade.getOffered().size());
        Assert.assertEquals(0, StartTrade.getDesired().size());

        StartTrade.setTradeData(tradeMap.toString());

        Assert.assertEquals(5, StartTrade.getOffered().size());
        Assert.assertEquals(5, StartTrade.getDesired().size());

        Assert.assertEquals(0, StartTrade.getOffered().size());
    }

    @Test
    public void checkAllPartner() {

        Map<String, Integer> map = new HashMap<>();
        map.put("Holz", 1);
        map.put("Wolle", 1);
        map.put("Weizen", 1);
        map.put("Erz", 1);
        map.put("Lehm", 1);

        Assert.assertEquals(3, StartTrade.checkAndSetTradingPartners(gameSession, map, player1).size());
    }

    @Test
    public void checkTwoPartners() {
        player2.getInventory().addWood(1);
        player3.getInventory().addWood(1);

        Map<String, Integer> map = new HashMap<>();
        map.put("Holz", 2);
        map.put("Wolle", 1);
        map.put("Weizen", 1);
        map.put("Erz", 1);
        map.put("Lehm", 1);

        Assert.assertEquals(2, StartTrade.checkAndSetTradingPartners(gameSession, map, player1).size());
    }

    @Test
    public void checkOnePartner() {
        player2.getInventory().addWood(1);

        Map<String, Integer> map = new HashMap<>();
        map.put("Holz", 2);
        map.put("Wolle", 1);
        map.put("Weizen", 1);
        map.put("Erz", 1);
        map.put("Lehm", 1);

        Assert.assertEquals(1, StartTrade.checkAndSetTradingPartners(gameSession, map, player1).size());
    }

    @Test
    public void checkNoPartner() {

        Map<String, Integer> map = new HashMap<>();
        map.put("Holz", 2);
        map.put("Wolle", 1);
        map.put("Weizen", 1);
        map.put("Erz", 1);
        map.put("Lehm", 1);

        Assert.assertEquals(0, StartTrade.checkAndSetTradingPartners(gameSession, map, player1).size());
    }

    @Test
    public void setTrade() {

        List<Player> list = new ArrayList<>();
        list.add(player2);
        list.add(player3);

        Map<String, Integer> map = new HashMap<>();
        map.put("Holz", 2);
        map.put("Wolle", 0);
        map.put("Weizen", 0);
        map.put("Erz", 0);
        map.put("Lehm", 0);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("Holz", 2);
        map2.put("Wolle", 0);
        map2.put("Weizen", 0);
        map2.put("Erz", 1);
        map2.put("Lehm", 0);

        StartTrade.setTrade(map, map2, player1, list, "TestMessage", gameSession);
        Assert.assertTrue(gameSession.isTradeOn());
        Assert.assertEquals(2, gameSession.getTrade().getPotentialTradingPartners().size());
        Assert.assertEquals(0, gameSession.getTrade().getCurrPlayer().getUserId());
        Assert.assertEquals("TestMessage", gameSession.getTrade().getMessage());
    }
}
