package com.example.diesiedler.modelClasses;

import com.example.catangame.GameSession;
import com.example.catangame.Player;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameSessionTest {

    private GameSession gameSession;

    private List<Player> list = new ArrayList<>();

    @Before
    public void setUp() {
        gameSession = new GameSession();
        Player player1 = new Player("Test1", 0);
        Player player2 = new Player("Test2", 1);
        Player player3 = new Player("Test3", 2);
        list.add(player1);
        list.add(player2);
        list.add(player3);
        gameSession.setPlayers(list);
    }

    @After
    public void tearDown() {
        gameSession = null;
    }

    @Test
    public void nextPlayer0() {
        gameSession.nextPlayer();
        Assert.assertEquals(1, gameSession.getCurr().getUserId());
    }

    @Test
    public void nextPlayerSize() {
        gameSession.setCurrPlayer(2);
        gameSession.nextPlayer();
        Assert.assertEquals(0, gameSession.getCurr().getUserId());
    }

    @Test
    public void prevPlayer() {
        gameSession.setCurrPlayer(2);
        gameSession.previousPlayer();
        Assert.assertEquals(1, gameSession.getCurr().getUserId());
    }

    @Test
    public void prevPlayer0() {
        gameSession.previousPlayer();
        Assert.assertEquals(2, gameSession.getCurr().getUserId());
    }

}
