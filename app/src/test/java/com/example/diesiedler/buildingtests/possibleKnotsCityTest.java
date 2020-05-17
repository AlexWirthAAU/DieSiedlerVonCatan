package com.example.diesiedler.buildingtests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Knot;
import com.example.diesiedler.presenter.UpdateBuildCityView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

public class possibleKnotsCityTest {

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
    public void notEnoughResources() {
        Assert.assertEquals(0, possibleKnots.size());

        playerInventory.setWheat(0);
        playerInventory.setOre(0);
        possibleKnots = UpdateBuildCityView.possibleKnots(gameSession);
        Assert.assertEquals(null, possibleKnots);

        playerInventory.setOre(10);
        possibleKnots = UpdateBuildCityView.possibleKnots(gameSession);
        Assert.assertEquals(null, possibleKnots);

        playerInventory.setWheat(10);
        playerInventory.setOre(0);
        possibleKnots = UpdateBuildCityView.possibleKnots(gameSession);
        Assert.assertEquals(null, possibleKnots);
    }

    @Test
    public void canBuildCity() {
        Knot settlement = gameSession.getGameboard().getKnots()[0];
        Knot settlement2 = gameSession.getGameboard().getKnots()[10];
        settlement.setPlayer(player);
        settlement2.setPlayer(new Player("Test2", 1));
        playerInventory.addSettlement(settlement);
        playerInventory.addWheat(3);
        playerInventory.addOre(3);
        gameSession.addSettlement(settlement);
        gameSession.addSettlement(settlement2);

        possibleKnots = UpdateBuildCityView.possibleKnots(gameSession);

        Assert.assertEquals(1, possibleKnots.size());
    }

}
