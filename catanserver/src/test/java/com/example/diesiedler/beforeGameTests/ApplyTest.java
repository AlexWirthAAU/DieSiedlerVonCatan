package com.example.diesiedler.beforeGameTests;

import com.example.catanserver.User;
import com.example.catanserver.threads.SendToClient;
import com.example.catanserver.threads.beforegame.ApplyThread;

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

public class ApplyTest {
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
    ApplyThread applyThread;

    @Before
    public void setUp() {
        try {
            connection = Mockito.mock(Socket.class);
            Mockito.when(connection.getOutputStream()).thenReturn(oos);
            Mockito.when(connection.getInputStream()).thenReturn(ois);

        } catch (IOException e) {
            e.printStackTrace();
        }
        user = Mockito.mock(User.class);
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        connection = null;
        oos = null;
        ois = null;
        applyThread = null;
    }

    @Test
    public void send() {
        applyThread = new ApplyThread(user);
        applyThread.start();
        Mockito.verify(sendToClient, Mockito.times(1)).sendSearchingListBroadcast(new HashSet<>());
    }
}
