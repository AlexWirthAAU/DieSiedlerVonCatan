package com.example.diesiedler.tradeTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.Trade;
import com.example.catanserver.businessLogic.model.trading.Answer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerTest {
    private GameSession gameSession;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Trade trade;
    private Map<String, Integer> map;
    private Map<String, Integer> map2;

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

        map = new HashMap<>();
        map2 = new HashMap<>();

        map.put("Holz", 1);
        map.put("Wolle", 0);
        map.put("Weizen", 0);
        map.put("Erz", 0);
        map.put("Lehm", 0);

        map2.put("Holz", 0);
        map2.put("Wolle", 0);
        map2.put("Weizen", 0);
        map2.put("Erz", 1);
        map2.put("Lehm", 0);

        List<Player> list = new ArrayList<>();
        list.add(player2);
        list.add(player3);

        trade = new Trade(map, map2, player1, list, "TestMessage", gameSession);
        gameSession.setTrade(trade);
        gameSession.setIsTradeOn(true);

        player1.getInventory().setWood(1);
        player1.getInventory().setWool(1);
        player1.getInventory().setWheat(1);
        player1.getInventory().setOre(1);
        player1.getInventory().setClay(1);
        player2.getInventory().setWood(1);
        player2.getInventory().setWool(1);
        player2.getInventory().setWheat(1);
        player2.getInventory().setOre(1);
        player2.getInventory().setClay(1);
        player3.getInventory().setWood(1);
        player3.getInventory().setWool(1);
        player3.getInventory().setWheat(1);
        player3.getInventory().setOre(1);
        player3.getInventory().setClay(1);
        player4.getInventory().setWood(1);
        player4.getInventory().setWool(1);
        player4.getInventory().setWheat(1);
        player4.getInventory().setOre(1);
        player4.getInventory().setClay(1);
    }

    @After
    public void tearDown() {
        gameSession = null;
        player1 = null;
        player2 = null;
        player3 = null;
        player4 = null;
        map = null;
        map2 = null;
    }

    @Test
    public void assertBeforeAll() {
        Assert.assertEquals(1, player1.getInventory().getWood());
        Assert.assertEquals(1, player1.getInventory().getWool());
        Assert.assertEquals(1, player1.getInventory().getWheat());
        Assert.assertEquals(1, player1.getInventory().getOre());
        Assert.assertEquals(1, player1.getInventory().getClay());
        Assert.assertNotNull(gameSession.getTrade());
    }

    @Test
    public void answerAccepted() {
        Answer.addAnsweredPlayer("accepted", trade, player2);
        Assert.assertEquals(1, trade.getAnsweredPlayers().size());
        Assert.assertEquals(1, trade.getAnsweredPlayers().get(0).getUserId());
        Assert.assertEquals(1, trade.getAnswers().size());
        Assert.assertTrue(trade.getAnswers().get(player2));
    }

    @Test
    public void answerDismiss() {
        Answer.addAnsweredPlayer("accepted", trade, player2);
        Answer.addAnsweredPlayer("dismiss", trade, player3);
        Assert.assertEquals(2, trade.getAnsweredPlayers().size());
        Assert.assertEquals(2, trade.getAnsweredPlayers().get(1).getUserId());
        Assert.assertEquals(2, trade.getAnswers().size());
        Assert.assertFalse(trade.getAnswers().get(player3));
    }

    @Test
    public void setPartner() {
        Answer.addAnsweredPlayer("accepted", trade, player2);
        Answer.addAnsweredPlayer("dismiss", trade, player3);
        Answer.addAnsweredPlayer("accepted", trade, player4);
        Assert.assertEquals(1, Answer.setPartner(trade).getUserId());
    }

    @Test
    public void setAnotherPartner() {
        Answer.addAnsweredPlayer("accepted", trade, player4);
        Answer.addAnsweredPlayer("dismiss", trade, player3);
        Answer.addAnsweredPlayer("accepted", trade, player2);
        Assert.assertEquals(3, Answer.setPartner(trade).getUserId());
    }

    @Test
    public void setNoPartner() {
        Assert.assertNull(Answer.setPartner(trade));
    }

    @Test
    public void exchangeRessources() {

        Answer.exchangeRessources(player2, player1, trade);
        Assert.assertEquals(0, player2.getInventory().getOre());
        Assert.assertEquals(2, player2.getInventory().getWood());
        Assert.assertEquals(2, player1.getInventory().getOre());
        Assert.assertEquals(0, player1.getInventory().getWood());
    }

    @Test
    public void tradeWithoutPartner(){
        Assert.assertFalse(Answer.trade(player1,trade));
    }

    @Test
    public void tradeWithPartner(){
        Answer.addAnsweredPlayer("accepted", trade, player2);
        Assert.assertTrue(Answer.trade(player1,trade));
    }
}
