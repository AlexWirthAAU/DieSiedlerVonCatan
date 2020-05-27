package com.example.diesiedler.modelClasses;

import com.example.catangame.GameSession;
import com.example.catangame.Player;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class GameSessionTest {

    @Mock
    Player player1;
    Player player2;
    Player player3;

    @InjectMocks
    GameSession gameSession;

    List<Player> list = new ArrayList<>();

    @Before
    public void setUp() {
        gameSession = new GameSession();
        MockitoAnnotations.initMocks(this);
        list.add(player1);
        list.add(player2);
        list.add(player3);
    }

    @After
    public void tearDown() {
        gameSession = null;
    }

    @Test
    public void nextPlayer0() {
        gameSession.nextPlayer();
        Assert.assertEquals(1, gameSession.getCurrPlayer());
    }

    @Test
    public void nextPlayerSize() {
        gameSession.setPlayers(list);
        gameSession.setCurrPlayer(2);
        gameSession.nextPlayer();
        Assert.assertEquals(0, gameSession.getCurrPlayer());
    }

    @Test
    public void prevPlayer() {
        gameSession.setCurrPlayer(2);
        gameSession.previosPlayer();
        Assert.assertEquals(1, gameSession.getCurrPlayer());
    }

    @Test
    public void prevPlayer0() {
        gameSession.setPlayers(list);
        gameSession.previosPlayer();
        Assert.assertEquals(2, gameSession.getCurrPlayer());
    }

}
