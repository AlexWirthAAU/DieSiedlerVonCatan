package com.example.diesiedler.buildingtests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Knot;
import com.example.diesiedler.presenter.UpdateBuildRoadView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

public class possibleEdgesRoadTest {

    GameSession gameSession;
    LinkedList<Edge> posssibleEdges;
    Player player;
    PlayerInventory playerInventory;
    Knot settlement1;
    Knot settlement2;
    Edge edge1;
    Edge edge2;

    @Before
    public void setUp() {
        this.gameSession = new GameSession();
        this.posssibleEdges = new LinkedList<>();
        this.player = new Player("Test", 0);
        this.playerInventory = player.getInventory();
        this.settlement1 = gameSession.getGameboard().getKnots()[0];
        this.settlement2 = gameSession.getGameboard().getKnots()[6];
        this.edge1 = gameSession.getGameboard().getEdges()[0];
        this.edge2 = gameSession.getGameboard().getEdges()[5];
        gameSession.getGameboard().getEdges()[9].setPlayer(new Player("Opponent", 1));


        playerInventory.addSettlement(settlement1);
        playerInventory.addSettlement(settlement2);

        gameSession.setPlayer(player);
    }

    @After
    public void tearDown() {
        this.gameSession = null;
        this.posssibleEdges = null;
        this.player = null;
    }

    @Test
    public void initialRoadBuildFirstRoad() {
        posssibleEdges = UpdateBuildRoadView.possibleEdges(gameSession, null);
        Assert.assertEquals(2, posssibleEdges.size());
        Assert.assertEquals(1, UpdateBuildRoadView.status(gameSession, null));
    }

    @Test
    public void initialRoadBuildSecondRoad() {
        playerInventory.addRoad(edge1);
        posssibleEdges = UpdateBuildRoadView.possibleEdges(gameSession, null);
        Assert.assertEquals(1, posssibleEdges.size());
        Assert.assertEquals(1, UpdateBuildRoadView.status(gameSession, null));
    }

    @Test
    public void notEnoughResources() {
        Assert.assertEquals(0, posssibleEdges.size());
        playerInventory.addRoad(edge1);
        playerInventory.addRoad(edge2);


        playerInventory.setWood(0);
        playerInventory.setClay(0);
        posssibleEdges = UpdateBuildRoadView.possibleEdges(gameSession, null);
        Assert.assertEquals(null, posssibleEdges);
        Assert.assertEquals(0, UpdateBuildRoadView.status(gameSession, null));

        playerInventory.setClay(10);
        posssibleEdges = UpdateBuildRoadView.possibleEdges(gameSession, null);
        Assert.assertEquals(null, posssibleEdges);
        Assert.assertEquals(0, UpdateBuildRoadView.status(gameSession, null));

        playerInventory.setClay(0);
        playerInventory.setWood(10);
        posssibleEdges = UpdateBuildRoadView.possibleEdges(gameSession, null);
        Assert.assertEquals(null, posssibleEdges);
        Assert.assertEquals(0, UpdateBuildRoadView.status(gameSession, null));
    }

    @Test
    public void enoughResourcesAndCard() {
        playerInventory.addRoad(edge1);
        playerInventory.addRoad(edge2);

        posssibleEdges = UpdateBuildRoadView.possibleEdges(gameSession, "card");
        Assert.assertEquals(3, posssibleEdges.size());
        Assert.assertEquals(1, UpdateBuildRoadView.status(gameSession, "card"));
    }

    @Test
    public void buildWithCard() {
        playerInventory.addRoad(edge1);
        playerInventory.addRoad(edge2);
        playerInventory.setWood(0);
        playerInventory.setClay(0);

        posssibleEdges = UpdateBuildRoadView.possibleEdges(gameSession, "Card");

        Assert.assertEquals(3, posssibleEdges.size());
        Assert.assertEquals(1, UpdateBuildRoadView.status(gameSession, "card"));
    }
}
