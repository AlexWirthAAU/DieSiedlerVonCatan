package com.example.diesiedler.beforeGameTests;

import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.threads.SendToClient;
import com.example.catanserver.threads.beforegame.StopThread;

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
import java.util.HashSet;

public class StopTest {

    @Mock
    Socket connection;
    @Mock
    ObjectInputStream ois;
    @Mock
    ObjectOutputStream oos;

    @Mock
    User user;

    @Mock
    SendToClient sendToClient;


    @InjectMocks
    StopThread stopThread;

    @Before
    public void setUp() throws IOException {
        try {
            connection = Mockito.mock(Socket.class);
            Mockito.when(connection.getOutputStream()).thenReturn(oos);
            Mockito.when(connection.getInputStream()).thenReturn(ois);

        } catch (IOException e) {
            e.printStackTrace();
        }
        user = Mockito.mock(User.class);
        Server.currentlySearching.add(user);
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        connection = null;
        oos = null;
        ois = null;
        stopThread = null;
    }

    @Test
    public void send() {
        stopThread = new StopThread(user);
        stopThread.start();
        Mockito.verify(sendToClient, Mockito.times(1)).sendSearchingListBroadcast(new HashSet<>());
    }
}
