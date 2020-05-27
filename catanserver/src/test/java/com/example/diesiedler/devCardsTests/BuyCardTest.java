package com.example.diesiedler.devCardsTests;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.devcards.DevCard;
import com.example.catangame.devcards.DevCardStack;
import com.example.catanserver.User;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.SendToClient;
import com.example.catanserver.threads.cards.BuyCardThread;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class BuyCardTest {

    @Mock
    User user;

    GameSession gameSession;

    @Mock
    Player player1;
    PlayerInventory playerInventory1;
    String cardName;
    String message;

    ArrayList<DevCard> devCardStack;

    @Mock
    Socket connection;
    @Mock
    ObjectInputStream ois;
    @Mock
    ObjectOutputStream oos;

    @Mock
    SendToClient sendToClient;
    @Mock
    ErrorThread errorThread;

    @InjectMocks
    BuyCardThread buyCardThread;

    @Before
    public void setUp() throws IOException {
        try {
            connection = Mockito.mock(Socket.class);
            Mockito.when(connection.getOutputStream()).thenReturn(oos);
            Mockito.when(connection.getInputStream()).thenReturn(ois);

        } catch (IOException e) {
            e.printStackTrace();
        }
        devCardStack = new DevCardStack().getDevCardStack();
        user = Mockito.mock(User.class);
        gameSession = Mockito.mock(GameSession.class);
        player1 = Mockito.mock(Player.class);
        Mockito.when(gameSession.getDevCards()).thenReturn(devCardStack);
        Mockito.when(gameSession.getPlayer(0)).thenReturn(player1);
        Mockito.when(user.getUserId()).thenReturn(0);
        Mockito.when(user.getDisplayName()).thenReturn("Test");
        //Mockito.doNothing().when(sendToClient.spy());
        Mockito.when(user.getConnection()).thenReturn(connection);
        Mockito.when(user.getConnectionInputStream()).thenReturn(ois);
        Mockito.when(user.getConnectionOutputStream()).thenReturn(oos);
        playerInventory1 = new PlayerInventory();
        Mockito.when(player1.getInventory()).thenReturn(playerInventory1);
        gameSession.setPlayer(player1);
        playerInventory1.setWood(0);
        playerInventory1.setWool(0);
        playerInventory1.setWheat(0);
        playerInventory1.setOre(0);
        playerInventory1.setClay(0);
        sendToClient = new SendToClient();
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        connection = null;
        oos = null;
        ois = null;
        user = null;
        gameSession = null;
        player1 = null;
        playerInventory1 = null;
        buyCardThread = null;

    }

    @Test
    public void assertBeforeAll() {

        Assert.assertEquals(0, playerInventory1.getVictoryPoints());
        Assert.assertEquals(0, playerInventory1.getBuildStreetCard());
        Assert.assertEquals(0, playerInventory1.getKnightCard());
        Assert.assertEquals(0, playerInventory1.getMonopolCard());
        Assert.assertEquals(0, playerInventory1.getInventionCard());
        Assert.assertEquals(0, playerInventory1.getBuildStreetCardLinkedList().size());
        Assert.assertEquals(0, playerInventory1.getWood());
        Assert.assertEquals(0, playerInventory1.getWool());
        Assert.assertEquals(0, playerInventory1.getWheat());
        Assert.assertEquals(0, playerInventory1.getOre());
        Assert.assertEquals(0, playerInventory1.getClay());
    }

    @Test
    public void checkFailedOnInventory() {
        buyCardThread = new BuyCardThread(user, gameSession);
        buyCardThread.start();
        Mockito.verify(errorThread, Mockito.times(1));
    }

    @Test
    public void checkFailedOnStack() {
        devCardStack.clear();
        buyCardThread = new BuyCardThread(user, gameSession);
        buyCardThread.start();
        Mockito.verify(errorThread, Mockito.times(1));
    }

    @Test
    public void mockTest() {
        playerInventory1.setWool(1);
        playerInventory1.setWheat(1);
        playerInventory1.setOre(1);
        buyCardThread = new BuyCardThread(user, gameSession);
        buyCardThread.start();
        Mockito.verify(sendToClient, Mockito.times(2));
    }

    @Test
    public void buyFirstKnight() {
        playerInventory1.setWool(1);
        playerInventory1.setWheat(1);
        playerInventory1.setOre(1);
        buyCardThread = new BuyCardThread(user, gameSession);
        buyCardThread.start();
        //Assert.assertEquals(0, playerInventory1.getWool());
        //Assert.assertEquals(0, playerInventory1.getWheat());
        //Assert.assertEquals(0, playerInventory1.getOre());
        //Assert.assertEquals(1, playerInventory1.getKnightCard());
        //Assert.assertEquals(25, devCardStack.size());
        //Assert.assertEquals("Du hast eine Ritterkarte gekauft", buyCardThread.getMessage());
        //Mockito.verify(gameSession, Mockito.times(1)).nextPlayer();
        Mockito.verify(buyCardThread, Mockito.times(3));
        Mockito.verify(sendToClient, Mockito.times(2));

    }

    @Test
    public void buyRandom() {

    }

    @Test
    public void buyMonopol() {

    }

    @Test
    public void buyInvention() {

    }

    @Test
    public void buyBuildStreet() {

    }

    @Test
    public void buyVictoryCard() {

    }

    @Test
    public void buyFirst() {

    }

}
