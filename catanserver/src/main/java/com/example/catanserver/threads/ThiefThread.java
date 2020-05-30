package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
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
     * @param "CARD"    when the Thread was loaded playing a BuildStreetCard, else " "
     */
    public ThiefThread(User user, GameSession game, String tileIndex, String card) {
        super(user, game);
        this.tileIndex = tileIndex;
        this.card = card;
    }

    /**
     * When card is "CARD" is executes the <code>updateRessources</code>
     * Method in <code>Thief</code>. It broadcasts a new GameSession, as well as sends a
     * command String to the current user and possibly the next user.
     */
    public void run(){
        try {
            int thiefIndex = Integer.parseInt(tileIndex);
            if (game.getCurr().getUserId() == user.getUserId()) {
                if(Thief.moveThief(game,thiefIndex)){
                    if (card.equals("CARD")) {
                        Thief.updateRessources(game, thiefIndex, game.getPlayer(user.getUserId()));
                        SendToClient.sendGameSessionBroadcast(game);
                    }
                    else{
                        Player player = game.getPlayer(user.getUserId());
                        if(player.hasToSkip()){
                            player.skip();
                            endTurn();
                            game.nextPlayer();
                            SendToClient.sendGameSessionBroadcast(game);
                            SendToClient.sendStringMessage(user,SendToClient.HEADER_ENDTURN);
                            User nextUser = Server.findUser(game.getCurr().getUserId());
                            if(nextUser != null) {
                                SendToClient.sendStringMessage(nextUser, SendToClient.HEADER_BEGINTURN);
                            }
                        }
                        else{
                            SendToClient.sendGameSessionBroadcast(game);
                            SendToClient.sendStringMessage(user,SendToClient.HEADER_ROLLED);
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Server.currentlyThreaded.remove(game.getGameId());
        }
    }

}
