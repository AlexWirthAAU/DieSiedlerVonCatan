package com.example.catanserver.threads;

import java.net.Socket;

/**
 * @author Fabian Schaffenrath
 * This Thread doesn't have any logic and is only used to send an error string to the client.
 */

public class ErrorThread {

    private Socket connection;
    private String errorMessage;

    public ErrorThread(Socket connection, String errorMessage){
        this.connection = connection;
        this.errorMessage = errorMessage;
    }

    public void run(){
        SendToClient.sendErrorMessage(connection,errorMessage);
    }
}
