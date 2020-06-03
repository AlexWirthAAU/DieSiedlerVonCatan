package com.example.catanserver.threads.cheating;

import com.example.catangame.GameSession;
import com.example.catangame.Grab;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.Cheating;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

/**
 * @author Fabian Schaffenrath
 *
 * This Thread processes the taking from a revealed Grab.
 */
public class CounterThread extends GameThread {

    private String grabberId;
    private String resource;

    public CounterThread(User user, GameSession game, String grabberId, String resource) {
        super(user, game);
        this.grabberId = grabberId;
        this.resource = resource;
    }

    /**
     * If a grab is revealed, a chosen resource is tried to be stolen from the cheating player.
     * A Message with the cheated command and the Outcome is sent to the countering User, a message
     * with the cheater and the outcome is sent to the cheater and a GameSession is sent to all players.
     */
    public void run(){
        try {
            int grabber = Integer.parseInt(grabberId);
            Grab grab = game.getGrabOf(user.getUserId());
            if (grab != null && grab.getGrabber().getUserId() == grabber && grab.getRevealed()) {
                if(Cheating.counter(grab,resource)){
                    SendToClient.sendStringMessage(user,SendToClient.HEADER_CHEATED + " Du konntest einen Rohstoff zurückstehlen");
                    User grabberUser = Server.findUser(grab.getGrabber().getUserId());
                    if(grabberUser != null){
                        SendToClient.sendStringMessage(grabberUser,SendToClient.HEADER_CHEATER + " Dir wurde ein Rohstoff gestohlen.");
                    }
                }
                else{
                    SendToClient.sendStringMessage(user,SendToClient.HEADER_CHEATED + " Der Dieb besitzt diesen Rohstoff leider nicht.");
                }
                game.removeGrab(grab);
                SendToClient.sendGameSessionBroadcast(game);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
