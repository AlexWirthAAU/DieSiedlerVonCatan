package com.example.catanserver.threads;

import java.io.ObjectOutputStream;

/**
 * @author Fabian Schaffenrath
 * @author Christina Senger (Documentation)
 *
 * This Thread is only used to send an error string to the client.
 */
public class ErrorThread extends Thread {

    private ObjectOutputStream connectionOutputStream;
    private String errorMessage;

    /**
     * Constructor
     *
     * @param connectionOutputStream Stream on which the Message should be sended
     * @param errorMessage           specific Error Message
     */
    public ErrorThread(ObjectOutputStream connectionOutputStream, String errorMessage) {
        this.connectionOutputStream = connectionOutputStream;
        this.errorMessage = errorMessage;
    }

    /**
     * Sends an error command with an error message to the client.
     */
    @Override
    public void run(){
        SendToClient.sendStringMessage(connectionOutputStream, SendToClient.HEADER_ERROR + " " + errorMessage);
    }
}
