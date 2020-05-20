package com.example.diesiedler.thiefTests;

import com.example.catangame.GameSession;
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

    @Before
    public void before(){
        testGameSession = new GameSession();
        testGameSessionTiles = testGameSession.getGameboard().getTiles();
        correctGameSession = new GameSession();
        correctGameSessionTiles = correctGameSession.getGameboard().getTiles();
    }

    @After
    public void after(){
        testGameSession = null;
        testGameSessionTiles = null;
        correctGameSession = null;
        correctGameSessionTiles = null;
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
}
