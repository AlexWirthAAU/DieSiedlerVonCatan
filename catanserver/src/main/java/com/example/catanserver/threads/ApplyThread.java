package com.example.catanserver.threads;

import com.example.catanserver.Server;
import com.example.catanserver.User;

import java.net.Socket;

/**
 * @author Fabian Schaffenrath
 * This Thread is used to nominate a player for player selection.
 *
 * It sends a complete list of nominees to the client.
 */

public class ApplyThread extends Thread{

    private Socket connection;
    private User user;

    public ApplyThread(Socket connection, User user){
        this.connection = connection;
        this.user = user;
    }

    public void run(){
        Server.currentlySearching.add(user);
        SendToClient.sendSearchingList(connection,Server.currentlySearching);
    }
}
