package com.example.catanserver.threads;

import com.example.catanserver.Server;
import com.example.catanserver.User;

/**
 * @author Fabian Schaffenrath
 * This Thread removes a User from the searching list.
 *
 * It sends an updated list of searching users to everyone still present in the list.
 */

public class StopThread extends Thread{

    private User user;

    public StopThread(User user){
        this.user = user;
    }

    public void run(){
        if(Server.currentlySearching.remove(user)){
            SendToClient.sendSearchingListBroadcast(Server.currentlySearching);
        }
    }

}
