package com.example.diesiedler.cheatTests;

import com.example.catangame.GameSession;
import com.example.catangame.Grab;
import com.example.catangame.Player;
import com.example.catanserver.businesslogic.model.Cheating;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheatingTest {

    private GameSession game;
    private Player player1, player2, player3;

    @Before
    public void before(){
        game = new GameSession();
        player1 = new Player("Player 1",0);
        player2 = new Player("Player 2",1);
        player3 = new Player("Player 3",2);
        game.setPlayer(player1);
        game.setPlayer(player2);
        game.setPlayer(player3);
    }

    @After
    public void after(){
        game = null;
        player1 = null;
        player2 = null;
        player3 = null;
    }

    @Test
    public void testRequestGrabStandard(){
        Assert.assertTrue(Cheating.requestGrab(game,player1.getUserId(),player2.getUserId(),"WOOD"));
        Assert.assertTrue(Cheating.requestGrab(game,player1.getUserId(),player3.getUserId(),"WOOD"));
        Assert.assertEquals(2,game.getGrabsFrom(player1.getUserId()).size());
        Assert.assertEquals(player1.getUserId(),game.getGrabOf(player2.getUserId()).getGrabber().getUserId());
        Assert.assertEquals(player1.getUserId(),game.getGrabOf(player3.getUserId()).getGrabber().getUserId());
    }

    @Test
    public void testRequestGrabWrongId(){
        Assert.assertFalse(Cheating.requestGrab(game,player1.getUserId(),5,"WOOD"));
        Assert.assertFalse(Cheating.requestGrab(game,5,player1.getUserId(),"WOOD"));
    }

    @Test
    public void testRequestGrabPresent(){
        Assert.assertTrue(Cheating.requestGrab(game,player1.getUserId(),player3.getUserId(),"WOOD"));
        Assert.assertFalse(Cheating.requestGrab(game,player2.getUserId(),player3.getUserId(), "WOOD"));
    }

    @Test
    public void testProcessGrabsPossible(){
        player1.getInventory().setWheat(5);
        player1.getInventory().setWood(5);
        player1.getInventory().setWool(5);
        player1.getInventory().setClay(5);
        player1.getInventory().setOre(5);
        game.addGrab(new Grab(player2,player1,"WHEAT"));
        Cheating.processGrabs(game,player1.getUserId());
        Assert.assertEquals(4,player1.getInventory().getWheat());
        Assert.assertEquals(1,player2.getInventory().getWheat());
        Assert.assertEquals(0,game.getGrabs().size());
        game.addGrab(new Grab(player2,player1,"WOOD"));
        Cheating.processGrabs(game,player1.getUserId());
        Assert.assertEquals(4,player1.getInventory().getWood());
        Assert.assertEquals(1,player2.getInventory().getWood());
        Assert.assertEquals(0,game.getGrabs().size());
        game.addGrab(new Grab(player2,player1,"WOOL"));
        Cheating.processGrabs(game,player1.getUserId());
        Assert.assertEquals(4,player1.getInventory().getWool());
        Assert.assertEquals(1,player2.getInventory().getWool());
        Assert.assertEquals(0,game.getGrabs().size());
        game.addGrab(new Grab(player2,player1,"CLAY"));
        Cheating.processGrabs(game,player1.getUserId());
        Assert.assertEquals(4,player1.getInventory().getClay());
        Assert.assertEquals(1,player2.getInventory().getClay());
        Assert.assertEquals(0,game.getGrabs().size());
        game.addGrab(new Grab(player2,player1,"ORE"));
        Cheating.processGrabs(game,player1.getUserId());
        Assert.assertEquals(4,player1.getInventory().getOre());
        Assert.assertEquals(1,player2.getInventory().getOre());
        Assert.assertEquals(0,game.getGrabs().size());
    }

    @Test
    public void testProcessGrabsNotPossible(){
        game.addGrab(new Grab(player2,player1,"WHEAT"));
        Cheating.processGrabs(game,player1.getUserId());
        Assert.assertEquals(0,player1.getInventory().getWheat());
        Assert.assertEquals(0,player2.getInventory().getWheat());
        Assert.assertEquals(0,game.getGrabs().size());
        game.addGrab(new Grab(player2,player1,"WOOD"));
        Cheating.processGrabs(game,player1.getUserId());
        Assert.assertEquals(0,player1.getInventory().getWood());
        Assert.assertEquals(0,player2.getInventory().getWood());
        Assert.assertEquals(0,game.getGrabs().size());
        game.addGrab(new Grab(player2,player1,"WOOL"));
        Cheating.processGrabs(game,player1.getUserId());
        Assert.assertEquals(0,player1.getInventory().getWool());
        Assert.assertEquals(0,player2.getInventory().getWool());
        Assert.assertEquals(0,game.getGrabs().size());
        game.addGrab(new Grab(player2,player1,"CLAY"));
        Cheating.processGrabs(game,player1.getUserId());
        Assert.assertEquals(0,player1.getInventory().getClay());
        Assert.assertEquals(0,player2.getInventory().getClay());
        Assert.assertEquals(0,game.getGrabs().size());
        game.addGrab(new Grab(player2,player1,"ORE"));
        Cheating.processGrabs(game,player1.getUserId());
        Assert.assertEquals(0,player1.getInventory().getOre());
        Assert.assertEquals(0,player2.getInventory().getOre());
        Assert.assertEquals(0,game.getGrabs().size());
    }

    @Test
    public void testCounterPossible(){
        player1.getInventory().setWheat(5);
        player2.getInventory().setOre(5);
        Grab grab = new Grab(player2,player1,"WHEAT");
        game.addGrab(grab);
        Cheating.counter(grab,"ORE");
        Assert.assertEquals(1,player1.getInventory().getOre());
        Assert.assertEquals(4,player2.getInventory().getOre());
        grab.setRevealed(true);
        Cheating.processGrabs(game,player1.getUserId());
        Assert.assertEquals(5,player1.getInventory().getWheat());
    }

    @Test
    public void testCounterNotPossible(){
        player1.getInventory().setWheat(5);
        Grab grab = new Grab(player2,player1,"WHEAT");
        game.addGrab(grab);
        Cheating.counter(grab,"ORE");
        Assert.assertEquals(0,player1.getInventory().getOre());
        Assert.assertEquals(0,player2.getInventory().getOre());
        grab.setRevealed(true);
        Cheating.processGrabs(game,player1.getUserId());
        Assert.assertEquals(5,player1.getInventory().getWheat());
    }
}
