package com.example.catanserver.threads;

import com.example.catanserver.Server;
import com.example.catanserver.User;

import java.net.Socket;

/**
 * @author Fabian Schaffenrath
 * This Thread creates a new User on login or returns information of an already existing user;
 *
 * It send the own UserId to the client.
 */

public class LoginThread extends Thread{

    private String displayName;
    private Socket connection;

    public LoginThread(Socket connection, String displayName){
        this.connection = connection;
        this.displayName = displayName;
    }

    public void run(){
        for (User user: Server.currentUsers) {
            if(user.getConnectionAddress() == connection.getInetAddress() && user.getConnectionPort() == connection.getPort()){
                // User already exists
                SendToClient.sendUserId(connection,user.getUserId());
                return;
            }
        }
        User user = new User(displayName,connection);
        Server.currentUsers.add(user);
        SendToClient.sendUserId(connection,user.getUserId());
    }

}
