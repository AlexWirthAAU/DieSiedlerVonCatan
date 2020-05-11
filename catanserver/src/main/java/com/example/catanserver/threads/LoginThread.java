package com.example.catanserver.threads;

import com.example.catanserver.User;

/**
 * @author Fabian Schaffenrath
 * This Thread sends the own UserId to the client.
 */

public class LoginThread extends Thread{

    private User user;

    public LoginThread(User user) {
        this.user = user;
    }

    public void run(){
        SendToClient.sendUserId(user, user.getUserId());
    }
}
