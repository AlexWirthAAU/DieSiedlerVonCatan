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
     * A Message with the Outcome is sent to the countering User and a GameSession is sent to all players.
     */
    public void run(){
        try {
            int grabber = Integer.parseInt(grabberId);
            Grab grab = game.getGrabOf(user.getUserId());
            if (grab != null && grab.getGrabber().getUserId() == grabber && grab.getRevealed()) {
                if(Cheating.counter(grab,resource)){
                    SendToClient.sendStringMessage(user,"CHEAT COUNTERED");
                    for (User user:Server.currentUsers) {
                        if(user.getUserId() == grab.getGrabber().getUserId()){
                            SendToClient.sendStringMessage(user,"XCHEAT COUNTERED");
                            break;
                        }
                    }
                }
                else{
                    SendToClient.sendStringMessage(user,"CHEAT BLOCKED");
                    for (User user:Server.currentUsers) {
                        if(user.getUserId() == grab.getGrabber().getUserId()){
                            SendToClient.sendStringMessage(user,"XCHEAT BLOCKED");
                            break;
                        }
                    }
                }
                // TODO: implement Player skip
                game.removeGrab(grab);
                SendToClient.sendGameSessionBroadcast(game);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
