package com.example.catanserver.threads;

import java.io.ObjectOutputStream;

/**
 * @author Fabian Schaffenrath
 * This Thread is only used to send an error string to the client.
 */

public class ErrorThread extends Thread {

    private ObjectOutputStream connectionOutputStream;
    private String errorMessage;

    public ErrorThread(ObjectOutputStream connectionOutputStream, String errorMessage) {
        this.connectionOutputStream = connectionOutputStream;
        this.errorMessage = errorMessage;
    }

    public void run(){
        SendToClient.sendErrorMessage(connectionOutputStream, errorMessage);
    }
}
