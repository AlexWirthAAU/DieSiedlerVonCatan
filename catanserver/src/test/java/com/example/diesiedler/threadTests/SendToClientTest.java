package com.example.diesiedler.threadTests;

import com.example.catangame.GameSession;
import com.example.catanserver.User;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SendToClientTest {

    @Mock
    Socket connection;
    @Mock
    ObjectInputStream ois;
    @Mock
    ObjectOutputStream oos;
    @Mock
    GameSession gameSession;
    @Mock
    User user;
    private Set<User> set = new HashSet();
    private List<String> list = new ArrayList();
    @InjectMocks
    private SendToClient sendToClient;

    @Before
    public void setUp() throws IOException {
        try {
            connection = Mockito.mock(Socket.class);
            Mockito.when(connection.getOutputStream()).thenReturn(oos);
            Mockito.when(connection.getInputStream()).thenReturn(ois);

        } catch (IOException e) {
            e.printStackTrace();
        }
        sendToClient = Mockito.mock(SendToClient.class);
        gameSession = Mockito.mock(GameSession.class);
        user = Mockito.mock(User.class);
        Mockito.doReturn("").when(sendToClient).sendToClient(user, 0);
        Mockito.when(user.getConnectionOutputStream()).thenReturn(oos);
        MockitoAnnotations.initMocks(this);
        Mockito.when(gameSession.getGameId()).thenReturn(0);
        Mockito.when(gameSession.getPlayers()).thenReturn(new ArrayList<>());
        set.add(user);
    }

    @After
    public void tearDown() {
        connection = null;
        oos = null;
        ois = null;
        sendToClient = null;
        //Mockito.validateMockitoUsage();
    }

    @Test
    public void sendUserId() throws IOException {
        SendToClient.sendUserId(user, 0);
        Mockito.verify(sendToClient, Mockito.times(1)).sendToClient(user, 0);
    }
/*
    @Test
    public void sendSearchingList() throws IOException {
        sendToClient.sendSearchingListBroadcast(set);
        Mockito.when(sendToClient.createSearchingList(set)).thenReturn(new ArrayList<>());
        Mockito.verify(sendToClient, Mockito.times(1)).createSearchingList(set);
        Mockito.verify(sendToClient, Mockito.times(1)).sendToClient(user, list);
    }

    @Test
    public void sendGameStart() throws IOException {
        sendToClient.sendSearchingListBroadcast(set);
        Mockito.verify(sendToClient, Mockito.times(1)).createSearchingList(set);
        Mockito.verify(sendToClient, Mockito.times(1)).sendToClient(user, list);
    }

    @Test
    public void sendGameSession() throws IOException {
        sendToClient.sendSearchingListBroadcast(set);
        Mockito.verify(sendToClient, Mockito.times(1)).createSearchingList(set);
        Mockito.verify(sendToClient, Mockito.times(1)).sendToClient(user, list);
    }

    @Test
    public void sendGameSessionBroadcast() throws IOException {
        sendToClient.sendSearchingListBroadcast(set);
        Mockito.verify(sendToClient, Mockito.times(1)).createSearchingList(set);
        Mockito.verify(sendToClient, Mockito.times(1)).sendToClient(user, list);
    }

    @Test
    public void sendTradeMessageBroadcast() throws IOException {
        sendToClient.sendSearchingListBroadcast(set);
        Mockito.verify(sendToClient, Mockito.times(1)).createSearchingList(set);
        Mockito.verify(sendToClient, Mockito.times(1)).sendToClient(user, list);
    }

    @Test
    public void sendTradeMessage() throws IOException {
        sendToClient.sendSearchingListBroadcast(set);
        Mockito.verify(sendToClient, Mockito.times(1)).createSearchingList(set);
        Mockito.verify(sendToClient, Mockito.times(1)).sendToClient(user, list);
    }

    @Test
    public void sendKnightMessage() throws IOException {
        sendToClient.sendSearchingListBroadcast(set);
        Mockito.verify(sendToClient, Mockito.times(1)).createSearchingList(set);
        Mockito.verify(sendToClient, Mockito.times(1)).sendToClient(user, list);
    }

    @Test
    public void sendKnightMessageBroadcast() throws IOException {
        sendToClient.sendSearchingListBroadcast(set);
        Mockito.verify(sendToClient, Mockito.times(1)).createSearchingList(set);
        Mockito.verify(sendToClient, Mockito.times(1)).sendToClient(user, list);
    }

    @Test
    public void sendErrorMessage() throws IOException {
        sendToClient.sendSearchingListBroadcast(set);
        Mockito.verify(sendToClient, Mockito.times(1)).createSearchingList(set);
        Mockito.verify(sendToClient, Mockito.times(1)).sendToClient(user, list);
    }

    @Test
    public void createGameUserList() throws IOException {
        sendToClient.sendSearchingListBroadcast(set);
        Mockito.verify(sendToClient, Mockito.times(1)).createSearchingList(set);
        Mockito.verify(sendToClient, Mockito.times(1)).sendToClient(user, list);
    }

    @Test
    public void createSearchingListTest(Set<User> set) throws IOException {
        sendToClient.sendSearchingListBroadcast(this.set);
        Mockito.verify(sendToClient, Mockito.times(1)).createSearchingList(this.set);
        Mockito.verify(sendToClient, Mockito.times(1)).sendToClient(user, list);
    }

    @Test
    public void createTradeList() throws IOException {
        sendToClient.sendSearchingListBroadcast(set);
        Mockito.verify(sendToClient, Mockito.times(1)).createSearchingList(set);
        Mockito.verify(sendToClient, Mockito.times(1)).sendToClient(user, list);
    }

    @Test
    public void sendToClient() throws IOException {
        sendToClient.sendToClient(user, 0);
        Mockito.verify(user, Mockito.times(3)).getConnectionOutputStream();
    }*/
}
