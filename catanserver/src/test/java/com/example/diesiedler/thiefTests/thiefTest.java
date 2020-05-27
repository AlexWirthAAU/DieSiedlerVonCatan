package com.example.diesiedler.thiefTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.gameboard.Knot;
import com.example.catangame.gameboard.Tile;
import com.example.catanserver.businessLogic.model.Thief;
import com.example.catanserver.threads.SendToClient;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class thiefTest {

    private GameSession testGameSession;
    private GameSession correctGameSession;
    private Tile[] testGameSessionTiles;
    private Tile[] correctGameSessionTiles;
    @Mock
    SendToClient sendToClient;
    private Player player;
    private Player player2;
    private int[] res = new int[]{0, 0, 0, 0, 0};
    private int[] res2 = new int[]{1, 1, 1, 1, 1};

    @Before
    public void before(){
        testGameSession = new GameSession();
        testGameSessionTiles = testGameSession.getGameboard().getTiles();
        correctGameSession = new GameSession();
        correctGameSessionTiles = correctGameSession.getGameboard().getTiles();
        player = new Player("Test", 0);
        player2 = new Player("Test2", 1);
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void after(){
        testGameSession = null;
        testGameSessionTiles = null;
        correctGameSession = null;
        correctGameSessionTiles = null;
        player = null;
        player2 = null;
    }


     @Test public void testMoveThiefNormal(){
     int testIndex = 12;
     for (Tile tile: correctGameSessionTiles) {
     tile.setThief(false);
     }
     correctGameSessionTiles[testIndex].setThief(true);
     Assert.assertTrue(Thief.moveThief(testGameSession,testIndex));

     for (int i = 0; i < testGameSessionTiles.length; i++) {
     Assert.assertEquals(correctGameSessionTiles[i].isThief(), testGameSessionTiles[i].isThief());
     }
     }


    @Test
    public void testMoveThiefNegative(){
        int testIndex = -1;
        Assert.assertFalse(Thief.moveThief(testGameSession,testIndex));

        for (int i = 0; i < testGameSessionTiles.length; i++) {
            Assert.assertEquals(correctGameSessionTiles[i].isThief(), testGameSessionTiles[i].isThief());
        }
    }

    @Test
    public void testMoveThiefBigIndex(){
        int testIndex = 500;
        Assert.assertFalse(Thief.moveThief(testGameSession,testIndex));

        for (int i = 0; i < testGameSessionTiles.length; i++) {
            Assert.assertEquals(correctGameSessionTiles[i].isThief(), testGameSessionTiles[i].isThief());
        }
    }

    @Test
    public void updateRessourcesBigIndex() {
        Assert.assertFalse(Thief.updateRessources(testGameSession, 500, player));
    }

    @Test
    public void updateRessourcesNoPlayer() {
        Assert.assertTrue(Thief.updateRessources(testGameSession, 12, player));
        Assert.assertEquals(1, player.getInventory().getWood());
        Assert.assertEquals(1, player.getInventory().getWool());
        Assert.assertEquals(1, player.getInventory().getWheat());
        Assert.assertEquals(1, player.getInventory().getOre());
        Assert.assertEquals(1, player.getInventory().getClay());
        Mockito.verify(sendToClient, Mockito.times(1)).sendKnightMessageBroadcast(testGameSession, "");
    }

    @Test
    public void updateRessourcesWood() {
        Tile tile = testGameSessionTiles[12];
        Knot[] knotes = tile.getKnots();

        for (Knot knot : knotes) {
            knot.setPlayer(player2);
        }

        player2.getInventory().setWool(0);
        player2.getInventory().setWheat(0);
        player2.getInventory().setOre(0);
        player2.getInventory().setClay(0);
        Assert.assertTrue(Thief.updateRessources(testGameSession, 12, player));
        Assert.assertEquals(2, player.getInventory().getWood());
        Assert.assertEquals(0, player2.getInventory().getWood());
        Mockito.verify(sendToClient, Mockito.times(1)).sendKnightMessageBroadcast(testGameSession, "");
    }

    @Test
    public void updateRessourcesWool() {
        Tile tile = testGameSessionTiles[12];
        Knot[] knotes = tile.getKnots();

        for (Knot knot : knotes) {
            knot.setPlayer(player2);
        }

        player2.getInventory().setWood(0);
        player2.getInventory().setWheat(0);
        player2.getInventory().setOre(0);
        player2.getInventory().setClay(0);
        Assert.assertTrue(Thief.updateRessources(testGameSession, 12, player));
        Assert.assertEquals(2, player.getInventory().getWool());
        Assert.assertEquals(0, player2.getInventory().getWool());
        Mockito.verify(sendToClient, Mockito.times(1)).sendKnightMessageBroadcast(testGameSession, "");
    }

    @Test
    public void updateRessourcesWheat() {
        Tile tile = testGameSessionTiles[12];
        Knot[] knotes = tile.getKnots();

        for (Knot knot : knotes) {
            knot.setPlayer(player2);
        }

        player2.getInventory().setWood(0);
        player2.getInventory().setWool(0);
        player2.getInventory().setOre(0);
        player2.getInventory().setClay(0);
        Assert.assertTrue(Thief.updateRessources(testGameSession, 12, player));
        Assert.assertEquals(2, player.getInventory().getWheat());
        Assert.assertEquals(0, player2.getInventory().getWheat());
        Mockito.verify(sendToClient, Mockito.times(1)).sendKnightMessageBroadcast(testGameSession, "");
    }

    @Test
    public void updateRessourcesOre() {
        Tile tile = testGameSessionTiles[12];
        Knot[] knotes = tile.getKnots();

        for (Knot knot : knotes) {
            knot.setPlayer(player2);
        }

        player2.getInventory().setWood(0);
        player2.getInventory().setWool(0);
        player2.getInventory().setWheat(0);
        player2.getInventory().setClay(0);
        Assert.assertTrue(Thief.updateRessources(testGameSession, 12, player));
        Assert.assertEquals(2, player.getInventory().getOre());
        Assert.assertEquals(0, player2.getInventory().getOre());
        Mockito.verify(sendToClient, Mockito.times(1)).sendKnightMessageBroadcast(testGameSession, "");
    }

    @Test
    public void updateRessourcesClay() {
        Tile tile = testGameSessionTiles[12];
        Knot[] knotes = tile.getKnots();

        for (Knot knot : knotes) {
            knot.setPlayer(player2);
        }

        player2.getInventory().setWood(0);
        player2.getInventory().setWool(0);
        player2.getInventory().setWheat(0);
        player2.getInventory().setOre(0);
        Assert.assertTrue(Thief.updateRessources(testGameSession, 12, player));
        Assert.assertEquals(2, player.getInventory().getClay());
        Assert.assertEquals(0, player2.getInventory().getClay());
        Mockito.verify(sendToClient, Mockito.times(1)).sendKnightMessageBroadcast(testGameSession, "");
    }

    @Test
    public void updateNoRessources() {
        Tile tile = testGameSessionTiles[12];
        Knot[] knotes = tile.getKnots();

        for (Knot knot : knotes) {
            knot.setPlayer(player2);
        }

        player2.getInventory().setWood(0);
        player2.getInventory().setWool(0);
        player2.getInventory().setWheat(0);
        player2.getInventory().setOre(0);
        player2.getInventory().setClay(0);
        Assert.assertTrue(Thief.updateRessources(testGameSession, 12, player));
        Mockito.verify(sendToClient, Mockito.times(1)).sendKnightMessageBroadcast(testGameSession, "");
    }

    @Test
    public void buildMessage() {
        Thief.sendMessage(testGameSession, "Holz", player, player2);
        Mockito.verify(sendToClient, Mockito.times(1)).sendKnightMessageBroadcast(testGameSession, "");
    }

    @Test
    public void selectResNormal() {
        int ret = Thief.selectRes(res2);
        Assert.assertTrue(ret > -1);
        Assert.assertTrue(ret < 5);
    }

    @Test
    public void selectResNegative() {
        Assert.assertEquals(-1, Thief.selectRes(res));
    }

    //TODO: CardTest
}
