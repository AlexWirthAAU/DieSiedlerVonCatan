package com.example.diesiedler.threadTests;

import com.example.catanserver.threads.ErrorThread;
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

public class ErrorTest {

    @Mock
    Socket connection;
    @Mock
    ObjectInputStream ois;
    @Mock
    ObjectOutputStream oos;

    @Mock
    SendToClient sendToClient;


    @InjectMocks
    ErrorThread errorThread;

    @Before
    public void setUp() {
        try {
            connection = Mockito.mock(Socket.class);
            Mockito.when(connection.getOutputStream()).thenReturn(oos);
            Mockito.when(connection.getInputStream()).thenReturn(ois);

        } catch (IOException e) {
            e.printStackTrace();
        }
        sendToClient = new SendToClient();
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        connection = null;
        oos = null;
        ois = null;
        sendToClient = null;
        errorThread = null;
    }

    @Test
    public void runTest() {
        Mockito.verify(sendToClient, Mockito.times(1));
    }
}
