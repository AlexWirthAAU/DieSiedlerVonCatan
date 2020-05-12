package com.example.catanserver.threads;

import com.example.catanserver.Server;
import com.example.catanserver.User;

/**
 * @author Fabian Schaffenrath
 * This Thread is used to nominate a player for player selection.
 *
 * It sends a complete list of nominees to the client and all other searching Users.
 */

public class ApplyThread extends Thread{

    private User user;

    public ApplyThread(User user) {
        this.user = user;
    }

    public void run(){
        Server.currentlySearching.add(user);
        SendToClient.sendSearchingListBroadcast(Server.currentlySearching);
    }
}
