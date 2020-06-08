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
     * Depending on the guess, the revealed value of the grab is set and a cheated command with a
     * message is sent to the revealing user. If revealed, a cheater command with a message is sent
     * to the cheating user as well.
     */
    @Override
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
                SendToClient.sendStringMessage(user,SendToClient.HEADER_CHEATEDREVEAL + " Richtig geraten. Du kannst nun versuchen, einen Rohstoff zurückzustehlen.");

                User grabber = Server.findUser(grab.getGrabber().getUserId());
                if(grabber != null){
                    SendToClient.sendStringMessage(grabber,SendToClient.HEADER_CHEATER + " Dein Diebstahlsversuch wurde entdeckt.");
                }
            }
            else if(grab.getRevealed() != null && grab.getRevealed()){
                SendToClient.sendStringMessage(user,SendToClient.HEADER_CHEATEDREVEAL);
            }
            else{
                grab.setRevealed(false);
                SendToClient.sendStringMessage(user,SendToClient.HEADER_CHEATED + " Leider falsch geraten. Der Rohstoff wird dir am Ende des Spielzuges abgezogen.");
            }
            SendToClient.sendGameSessionBroadcast(game);
        }
        else{
            SendToClient.sendStringMessage(user,"ERROR");
        }
    }
}
