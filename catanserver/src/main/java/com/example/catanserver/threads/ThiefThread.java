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
    private String card;

    /**
     * @param user
     * @param game
     * @param tileIndex
     * @param "CARD"    when the Thread was loaded playing a BuildStreetCard, else null
     */
    public ThiefThread(User user, GameSession game, String tileIndex, String card) {
        super(user, game);
        this.tileIndex = tileIndex;
        this.card = card;
    }

    /**
     * When card is "CARD" is executes the <code>updateRessources</code>
     * Method in <code>Thief</code>. It send the new GameSession broadcast.
     */
    public void run(){
        try {
            int thiefIndex = Integer.parseInt(tileIndex);
            if (game.getCurr().getUserId() == user.getUserId()) {
                if(Thief.moveThief(game,thiefIndex)){
                    if (card.equals("CARD")) {
                        Thief.updateRessources(game, thiefIndex, game.getPlayer(user.getUserId()));
                    }
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
