package com.example.catanserver.threads.beforegame;

import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.threads.SendToClient;

/**
 * @author Fabian Schaffenrath
 * @author Christina Senger (Documentation)
 *
 * This Thread is used to nominate a player for player selection.
 * It sends a complete list of nominees to the client and all other searching Users.
 */

public class ApplyThread extends Thread{

    private User user;

    /**
     * Constructor - stores applying User
     *
     * @param user applying User
     */
    public ApplyThread(User user) {
        this.user = user;
    }

    /**
     * Adds applying User to the List of currently searching Users and sends the
     * new Searching-List Broadcast to all searching Users.
     */
    @Override
    public void run(){
        Server.currentlySearching.add(user);
        SendToClient.sendSearchingListBroadcast(Server.currentlySearching);
    }
}
