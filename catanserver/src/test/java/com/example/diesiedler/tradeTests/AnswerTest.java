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
    GameSession gameSession;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Trade trade;
    Map<String, Integer> map;
    Map<String, Integer> map2;

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
    public void setAnswerListNull() {

        Assert.assertEquals(1, Answer.setAnswerList(player1, trade).size());
        Assert.assertEquals(0, Answer.setAnswerList(player1, trade).get(0).getUserId());
    }

    @Test
    public void setAnswerList() {

        Answer.addAnsweredPlayer("accepted", trade, player2);

        Assert.assertEquals(2, Answer.setAnswerList(player1, trade).size());
        Assert.assertEquals(1, Answer.setAnswerList(player1, trade).get(1).getUserId());
    }
}
