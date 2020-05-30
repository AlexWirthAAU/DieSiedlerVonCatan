package com.example.catanserver.threads.cheating;

import com.example.catangame.GameSession;
import com.example.catanserver.User;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.businessLogic.model.Cheating;
import com.example.catanserver.threads.SendToClient;

/**
 * @author Fabian Schaffenrath
 *
 * This Thread processes a new cheating request.
 */
public class CheatThread extends GameThread {

    private String grabbedId;
    private String resource;

    public CheatThread(User user, GameSession game, String grabbedId, String resource) {
        super(user, game);
        this.grabbedId = grabbedId;
        this.resource = resource;
    }

    /**
     * Tries to create a new Grab in the GameSession. If successful, a GameSession is sent
     * to all players. Regardless of the Outcome, the cheat command and a String
     * containing the Outcome is sent to the requesting User.
     */
    public void run(){
        try{
            int grabbed = Integer.parseInt(grabbedId);
            if(Cheating.requestGrab(game,user.getUserId(),grabbed,resource)){
                SendToClient.sendGameSessionBroadcast(game);
                SendToClient.sendStringMessage(user,SendToClient.HEADER_CHEATER + " SET");
            }
            else{
                SendToClient.sendStringMessage(user,SendToClient.HEADER_CHEATER + " NOT SET");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
