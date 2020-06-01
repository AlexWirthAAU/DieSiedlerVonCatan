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
    int status;
    Player player;
    PlayerInventory playerInventory;

    @Before
    public void setUp() {
        this.gameSession = new GameSession();
        this.player = new Player("Test", 0);
        this.playerInventory = player.getInventory();
        playerInventory.addWood(2);
        playerInventory.addClay(2);
        playerInventory.addWheat(2);
        playerInventory.addOre(2);
        playerInventory.addWool(2);

        gameSession.setPlayer(player);
    }

    @After
    public void tearDown() {
        this.gameSession = null;
        this.player = null;
    }

    @Test
    public void notEnoughResourcesTest() {
        Assert.assertEquals(1, UpdateBuildSettlementView.status(gameSession));

        playerInventory.setWood(0);
        playerInventory.setClay(0);
        playerInventory.setWool(0);
        playerInventory.setWheat(0);
        playerInventory.addSettlement(gameSession.getGameboard().getKnots()[0]);
        playerInventory.addSettlement(gameSession.getGameboard().getKnots()[10]);

        status = UpdateBuildSettlementView.status(gameSession);

        Assert.assertEquals(0, status);
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

        status = UpdateBuildSettlementView.status(gameSession);

        Assert.assertEquals(1, status);
    }

    @Test
    public void enoughResNoKnot() {
        playerInventory.addSettlement(gameSession.getGameboard().getKnots()[0]);
        playerInventory.addSettlement(gameSession.getGameboard().getKnots()[10]);

        status = UpdateBuildSettlementView.status(gameSession);

        Assert.assertEquals(-1, status);
    }


}
