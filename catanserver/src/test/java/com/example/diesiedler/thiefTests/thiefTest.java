package com.example.diesiedler.thiefTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.gameboard.Knot;
import com.example.catangame.gameboard.Tile;
import com.example.catanserver.businessLogic.model.Thief;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class thiefTest {

    private GameSession testGameSession;
    private GameSession correctGameSession;
    private Tile[] testGameSessionTiles;
    private Tile[] correctGameSessionTiles;
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
        player.getInventory().addKnightCard(1);
        player.getInventory().addWood(1);
        player.getInventory().addWool(1);
        player.getInventory().addWheat(1);
        player.getInventory().addOre(1);
        player.getInventory().addClay(1);
        player2.getInventory().addWood(1);
        player2.getInventory().addWool(1);
        player2.getInventory().addWheat(1);
        player2.getInventory().addOre(1);
        player2.getInventory().addClay(1);
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
        Assert.assertEquals(0, player.getInventory().getKnightCard());
    }

    @Test
    public void updateRessourcesWood() {
        Tile tile = testGameSessionTiles[12];
        Knot[] knotes = tile.getKnots();

        for (Knot knot : knotes) {
            knot.setPlayer(player2);
        }

        player2.getInventory().removeAllWool();
        player2.getInventory().removeAllWheat();
        player2.getInventory().removeAllOre();
        player2.getInventory().removeAllClay();
        Assert.assertTrue(Thief.updateRessources(testGameSession, 12, player));
        Assert.assertEquals(2, player.getInventory().getWood());
        Assert.assertEquals(0, player2.getInventory().getWood());
        Assert.assertEquals(0, player.getInventory().getKnightCard());
    }

    @Test
    public void updateRessourcesWool() {
        Tile tile = testGameSessionTiles[12];
        Knot[] knotes = tile.getKnots();

        for (Knot knot : knotes) {
            knot.setPlayer(player2);
        }
        player2.getInventory().removeAllWood();
        player2.getInventory().removeAllWheat();
        player2.getInventory().removeAllOre();
        player2.getInventory().removeAllClay();
        Assert.assertTrue(Thief.updateRessources(testGameSession, 12, player));
        Assert.assertEquals(2, player.getInventory().getWool());
        Assert.assertEquals(0, player2.getInventory().getWool());
        Assert.assertEquals(0, player.getInventory().getKnightCard());
    }

    @Test
    public void updateRessourcesWheat() {
        Tile tile = testGameSessionTiles[12];
        Knot[] knotes = tile.getKnots();

        for (Knot knot : knotes) {
            knot.setPlayer(player2);
        }

        player2.getInventory().removeAllWool();
        player2.getInventory().removeAllWood();
        player2.getInventory().removeAllOre();
        player2.getInventory().removeAllClay();
        Assert.assertTrue(Thief.updateRessources(testGameSession, 12, player));
        Assert.assertEquals(2, player.getInventory().getWheat());
        Assert.assertEquals(0, player2.getInventory().getWheat());
        Assert.assertEquals(0, player.getInventory().getKnightCard());
    }

    @Test
    public void updateRessourcesOre() {
        Tile tile = testGameSessionTiles[12];
        Knot[] knotes = tile.getKnots();

        for (Knot knot : knotes) {
            knot.setPlayer(player2);
        }

        player2.getInventory().removeAllWool();
        player2.getInventory().removeAllWood();
        player2.getInventory().removeAllWheat();
        player2.getInventory().removeAllClay();
        Assert.assertTrue(Thief.updateRessources(testGameSession, 12, player));
        Assert.assertEquals(2, player.getInventory().getOre());
        Assert.assertEquals(0, player2.getInventory().getOre());
        Assert.assertEquals(0, player.getInventory().getKnightCard());
    }

    @Test
    public void updateRessourcesClay() {
        Tile tile = testGameSessionTiles[12];
        Knot[] knotes = tile.getKnots();

        for (Knot knot : knotes) {
            knot.setPlayer(player2);
        }

        player2.getInventory().removeAllWool();
        player2.getInventory().removeAllWood();
        player2.getInventory().removeAllOre();
        player2.getInventory().removeAllWheat();
        Assert.assertTrue(Thief.updateRessources(testGameSession, 12, player));
        Assert.assertEquals(2, player.getInventory().getClay());
        Assert.assertEquals(0, player.getInventory().getKnightCard());
    }

    @Test
    public void updateNoRessources() {
        Tile tile = testGameSessionTiles[12];
        Knot[] knotes = tile.getKnots();

        for (Knot knot : knotes) {
            knot.setPlayer(player2);
        }

        player2.getInventory().removeAllWool();
        player2.getInventory().removeAllWood();
        player2.getInventory().removeAllWheat();
        player2.getInventory().removeAllOre();
        player2.getInventory().removeAllClay();
        Assert.assertTrue(Thief.updateRessources(testGameSession, 12, player));
        Assert.assertEquals(0, player.getInventory().getKnightCard());
    }

    @Test
    public void buildMessage() {
        testGameSession.setKnightPowerOwner(player);

        StringBuilder builder = new StringBuilder();
        builder.append("KNIGHT ");
        builder.append(player.getDisplayName()).append(" hat 1 ").append("Holz");
        builder.append(" von ").append(player2.getDisplayName()).append(" gestohlen");

        if (testGameSession.getKnightPowerOwner() != null) {
            builder.append(" ").append(testGameSession.getKnightPowerOwner().getDisplayName()).append(" hat jetzt die größte Rittermacht");
        }

        Assert.assertEquals(builder.toString(), Thief.sendMessage(testGameSession, "Holz", player, player2));
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
}
