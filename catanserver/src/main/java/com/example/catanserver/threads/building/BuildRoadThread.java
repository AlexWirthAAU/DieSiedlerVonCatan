package com.example.catanserver.threads.building;

import com.example.catangame.GameSession;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.building.BuildRoad;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

// TODO: kommentieren

/**
 * @author Alex Wirth
 * @author Christina Senger (edit)
 */
public class BuildRoadThread extends GameThread {

    GameSession gameSession;
    int edgeIndex;
    int userID;
    private String card;

    /**
     *
     * @param user
     * @param game
     * @param edgeIndex
     * @param card "CARD" when the Thread was loaded playing a BuildStreetCard, else null
     */
    public BuildRoadThread(User user, GameSession game, int edgeIndex, String card) {
        super(user, game);
        this.gameSession = game;
        this.userID = user.getUserId();
        this.edgeIndex = edgeIndex;
        this.card = card;
    }

    /**
     * When card is "CARD" is executes the <code>buildRoadWithCard</code>
     * Method in <code>BuildRoad</code>. Else it executes the <code>updateGameSession</code>.
     * It send the new GameSession broadcast.
     */
    public void run() {
        if (card.equals("CARD")) {
            BuildRoad.buildRoadWithCard(gameSession, edgeIndex, userID);
        } else {
            BuildRoad.updateGameSession(gameSession, edgeIndex, userID);
        }
        SendToClient.sendGameSessionBroadcast(gameSession);
        Server.currentlyThreaded.remove(gameSession.getGameId());
    }
}
