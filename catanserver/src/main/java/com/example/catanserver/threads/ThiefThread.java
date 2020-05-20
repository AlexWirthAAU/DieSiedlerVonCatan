package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.Thief;

/**
 * @author Fabian Schaffenrath
 * This Thread tries to move the Thief to a new location.
 * If successful, a new GameSession is broadcastet.
 */

public class ThiefThread extends GameThread {

    String tileIndex;

    public ThiefThread(User user, GameSession game, String tileIndex) {
        super(user, game);
        this.tileIndex = tileIndex;
    }

    public void run(){
        try {
            int thiefIndex = Integer.parseInt(tileIndex);
            if (game.getCurr().getUserId() == user.getUserId()) {
                if(Thief.moveThief(game,thiefIndex)){
                    SendToClient.sendGameSessionBroadcast(game);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Server.currentlyThreaded.remove(game.getGameId());
        }
    }

}
