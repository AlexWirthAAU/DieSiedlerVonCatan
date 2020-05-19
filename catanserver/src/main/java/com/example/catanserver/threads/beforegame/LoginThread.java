package com.example.catanserver.threads.beforegame;

import com.example.catanserver.User;
import com.example.catanserver.threads.SendToClient;

/**
 * @author Fabian Schaffenrath
 * @author Christina Senger (Documentation)
 *
 * This Thread logs the User in, sending his own UserId to the client.
 */
public class LoginThread extends Thread{

    private User user;

    /**
     * @param user User trying to login
     */
    public LoginThread(User user) {
        this.user = user;
    }

    /**
     * Sends to UserId to the User.
     */
    public void run(){
        SendToClient.sendUserId(user, user.getUserId());
    }
}
