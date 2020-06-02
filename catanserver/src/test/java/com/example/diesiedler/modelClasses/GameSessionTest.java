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

    Player player1;
    Player player2;
    Player player3;

    GameSession gameSession;

    List<Player> list = new ArrayList<>();

    @Before
    public void setUp() {
        gameSession = new GameSession();
        player1 = new Player("Test1", 0);
        player2 = new Player("Test2", 1);
        player3 = new Player("Test3", 2);
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
        gameSession.previosPlayer();
        Assert.assertEquals(1, gameSession.getCurr().getUserId());
    }

    @Test
    public void prevPlayer0() {
        gameSession.previosPlayer();
        Assert.assertEquals(2, gameSession.getCurr().getUserId());
    }

}
