package com.example.diesiedler.threadTests;

import com.example.catangame.GameSession;
import com.example.catanserver.threads.NextThread;
import com.example.catanserver.threads.SendToClient;

import org.junit.After;
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

public class NextTest {

    @Mock
    Socket connection;
    @Mock
    ObjectInputStream ois;
    @Mock
    ObjectOutputStream oos;

    @Mock
    SendToClient sendToClient;
    @Mock
    GameSession gameSession;


    @InjectMocks
    NextThread nextThread;

    @Before
    public void setUp() {
        try {
            connection = Mockito.mock(Socket.class);
            Mockito.when(connection.getOutputStream()).thenReturn(oos);
            Mockito.when(connection.getInputStream()).thenReturn(ois);

        } catch (IOException e) {
            e.printStackTrace();
        }
        gameSession = Mockito.mock(GameSession.class);
        Mockito.when(gameSession.getGameId()).thenReturn(0);
        Mockito.when(gameSession.getPlayers()).thenReturn(new ArrayList<>());
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        connection = null;
        oos = null;
        ois = null;
        nextThread = null;
    }

    @Test
    public void send() {
        nextThread.start();
        Mockito.verify(sendToClient, Mockito.times(1)).sendGameSessionBroadcast(gameSession);
    }

    @Test
    public void game() {
        nextThread.start();
        Mockito.verify(gameSession, Mockito.times(1)).nextPlayer();
        Mockito.verify(gameSession, Mockito.times(1)).getGameId();
    }
}
