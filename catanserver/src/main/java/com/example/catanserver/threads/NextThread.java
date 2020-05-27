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
     * Goes on to the next Player and sends to updated
     * GameSession broadcast.
     */
    public void run() {
        game.nextPlayer();
        endTurn();
        SendToClient.sendGameSessionBroadcast(game);
        Server.currentlyThreaded.remove(game.getGameId());
    }
}
