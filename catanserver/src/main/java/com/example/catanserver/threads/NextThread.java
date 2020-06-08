package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catanserver.Server;
import com.example.catanserver.User;

/**
 * @author Christina Senger
 * <p>
 * This Thread only goes ahed to the next Player.
 */
public class NextThread extends GameThread {

    /**
     * {@inheritDoc}
     */
    public NextThread(User user, GameSession game) {
        super(user, game);
    }

    /**
     * Goes on to the next Player, broadcasts an updated
     * GameSession and also sends the end turn command to the current user as well as the begin
     * turn command to the next user.
     */
    @Override
    public void run() {
        game.nextPlayer();
        if(!endTurn()) {
            SendToClient.sendGameSessionBroadcast(game);
            SendToClient.sendStringMessage(user, SendToClient.HEADER_ENDTURN);
            User nextUser = Server.findUser(game.getCurr().getUserId());
            if (nextUser != null) {
                SendToClient.sendStringMessage(nextUser, SendToClient.HEADER_BEGINTURN);
            }
        }
        Server.currentlyThreaded.remove(game.getGameId());
    }
}
