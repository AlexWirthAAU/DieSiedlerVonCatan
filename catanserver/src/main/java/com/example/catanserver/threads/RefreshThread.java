package com.example.catanserver.threads;

import com.example.catanserver.Server;

import java.net.Socket;

/**
 * @author Fabian Schaffenrath
 * This Thread is used to refresh the List of searching Players on the Client
 *
 * It sends a List of Ids and Displaynames to the client.
 */

public class RefreshThread extends Thread {

    private Socket connection;

    public RefreshThread(Socket connection){
        this.connection = connection;
    }

    public void run(){
        SendToClient.sendSearchingList(connection, Server.currentlySearching);
    }
}
