package com.example.diesiedler.buildingtests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Knot;

import org.junit.After;
import org.junit.Before;

import java.util.LinkedList;

public class possibleEdgesRoadTest {

    GameSession gameSession;
    LinkedList<Edge> posssibleEdges;
    Player player;
    PlayerInventory playerInventory;

    @Before
    public void setUp() {
        this.gameSession = new GameSession();
        this.posssibleEdges = new LinkedList<>();
        this.player = new Player("Test", 0);
        this.playerInventory = player.getInventory();

        gameSession.setPlayer(player);
    }

    @After
    public void tearDown() {
        this.gameSession = null;
        this.posssibleEdges = null;
        this.player = null;
    }
}
