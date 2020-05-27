package com.example.diesiedler.modelClasses;

import com.example.catangame.GameSession;
import com.example.catanserver.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserTest {

    @Mock
    Socket connection;
    @Mock
    ObjectInputStream ois;
    @Mock
    ObjectOutputStream oos;
    @Mock
    GameSession gameSession;

    @InjectMocks
    User user;


    @Before
    public void setUp() {
        try {
            gameSession = Mockito.mock(GameSession.class);
            connection = Mockito.mock(Socket.class);
            Mockito.when(connection.getOutputStream()).thenReturn(oos);
            Mockito.when(connection.getInputStream()).thenReturn(ois);

        } catch (IOException e) {
            e.printStackTrace();
        }
        user = new User("Test", connection, ois, oos);
    }

    @After
    public void tearDown() {
        connection = null;
        oos = null;
        ois = null;
        user = null;
    }

    @Test
    public void addWins() {
        Assert.assertEquals(0, user.getWins());
        user.addWin();
        Assert.assertEquals(1, user.getWins());
    }

    @Test
    public void addGameSession() {
        Assert.assertEquals(0, user.getGameSessions().size());
        user.addGameSession(gameSession);
        Assert.assertEquals(1, user.getGameSessions().size());
    }

    @Test
    public void removeGameSession() {
        user.addGameSession(gameSession);
        user.removeGameSession(gameSession);
        Assert.assertEquals(0, user.getGameSessions().size());
    }
}
