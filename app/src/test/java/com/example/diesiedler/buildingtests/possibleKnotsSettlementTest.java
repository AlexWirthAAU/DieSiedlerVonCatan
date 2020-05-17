package com.example.diesiedler.buildingtests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Knot;
import com.example.diesiedler.presenter.UpdateBuildSettlementView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

public class possibleKnotsSettlementTest {

    GameSession gameSession;
    LinkedList<Knot> possibleKnots;
    Player player;
    PlayerInventory playerInventory;

    @Before
    public void setUp() {
        this.gameSession = new GameSession();
        this.possibleKnots = new LinkedList<>();
        this.player = new Player("Test", 0);
        this.playerInventory = player.getInventory();

        gameSession.setPlayer(player);
    }

    @After
    public void tearDown() {
        this.gameSession = null;
        this.possibleKnots = null;
        this.player = null;
    }

    @Test
    public void initialChoiceTest() {
        gameSession.getGameboard().getKnots()[0].setPlayer(new Player("Test2", 1));
        possibleKnots = UpdateBuildSettlementView.possibleKnots(gameSession);

        Assert.assertEquals(51, possibleKnots.size());
    }

    @Test
    public void notEnoughResourcesTest() {
        Assert.assertEquals(0, possibleKnots.size());

        playerInventory.setWood(0);
        playerInventory.setClay(0);
        playerInventory.setWool(0);
        playerInventory.setWheat(0);
        playerInventory.addSettlement(gameSession.getGameboard().getKnots()[0]);
        playerInventory.addSettlement(gameSession.getGameboard().getKnots()[10]);

        possibleKnots = UpdateBuildSettlementView.possibleKnots(gameSession);

        Assert.assertEquals(null, possibleKnots);
    }

    @Test
    public void canBuild() {
        Knot roadKnot1 = gameSession.getGameboard().getKnots()[0];
        Knot roadKnot2 = gameSession.getGameboard().getKnots()[1];

        playerInventory.addRoadKnots(roadKnot1);
        playerInventory.addRoadKnots(roadKnot2);
        gameSession.getGameboard().getKnots()[2].setPlayer(new Player("Test2", 1));
        playerInventory.addSettlement(gameSession.getGameboard().getKnots()[30]);
        playerInventory.addSettlement(gameSession.getGameboard().getKnots()[10]);

        possibleKnots = UpdateBuildSettlementView.possibleKnots(gameSession);

        Assert.assertEquals(1, possibleKnots.size());
    }


}
