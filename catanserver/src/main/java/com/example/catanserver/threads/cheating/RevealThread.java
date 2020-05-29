package com.example.catanserver.threads.cheating;

import com.example.catangame.GameSession;
import com.example.catangame.Grab;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

/**
 * @author Fabian Schaffenrath
 *
 * This Thread processes the guess of the to be stolen resource.
 */
public class RevealThread extends GameThread {

    private String grabberId;
    private String resource;

    public RevealThread(User user, GameSession game, String grabberId, String resource) {
        super(user, game);
        this.grabberId = grabberId;
        this.resource = resource;
    }

    /**
     * Depending on the guess, the revealed value of the grab is set and a message to the
     * revealing user is sent.
     */
    public void run(){
        int grabberIdInt = -1;
        try{
            grabberIdInt = Integer.parseInt(grabberId);
        }catch(Exception e){
            e.printStackTrace();
        }
        Grab grab = game.getGrabOf(user.getUserId());
        if(grab != null && grab.getGrabber().getUserId() == grabberIdInt){
            if(grab.getResource().equals(resource) && grab.getRevealed() == null) {
                grab.setRevealed(true);
                grab.getGrabber().addSkip();
                SendToClient.sendStringMessage(user,"CHEAT REVEALED");

                User grabber = Server.findUser(grab.getGrabber().getUserId());
                if(grabber != null){
                    SendToClient.sendStringMessage(grabber,"XCHEAT REVEALED");
                }
            }
            else if(grab.getRevealed() != null && grab.getRevealed()){
                SendToClient.sendStringMessage(user,"CHEAT ALREADY REVEALED");
            }
            else{
                grab.setRevealed(false);
                SendToClient.sendStringMessage(user,"CHEAT NOT REVEALED");
            }
        }
        else{
            SendToClient.sendStringMessage(user,"ERROR");
        }
    }
}
