package com.example.catanserver.threads.building;

import com.example.catangame.GameSession;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businesslogic.model.building.BuildRoad;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

/**
 * @author Alex Wirth
 * @author Christina Senger (edit)
 * @author Fabian Schaffenrath (edit)
 */
public class BuildRoadThread extends GameThread {

    private int edgeIndex;
    private int userID;
    private String card;

    /**
     *
     * @param user current User
     * @param game current Game
     * @param edgeIndex Index of the Edge the Road should be build on
     * @param card "CARD" when the Thread was loaded playing a BuildStreetCard, else null
     */
    public BuildRoadThread(User user, GameSession game, int edgeIndex, String card) {
        super(user, game);
        this.userID = user.getUserId();
        this.edgeIndex = edgeIndex;
        this.card = card;
    }

    /**
     * When card is "CARD" is executes the <code>buildRoadWithCard</code>
     * Method in <code>BuildRoad</code>. Else it executes the <code>updateGameSession</code>.
     * It broadcasts an updated GameSession and sends the endturn command to the user, as well as
     * the begin turn command to the next user.
     */

    @Override
    public void run() {
        if (card.equals("CARD")) {
            BuildRoad.buildRoadWithCard(game, edgeIndex, userID);
        } else {
            BuildRoad.updateGameSession(game, edgeIndex, userID);
        }
        if(card.equals("CARD") & game.getCurr().getUserId() == user.getUserId()) {
            SendToClient.sendGameSessionBroadcast(game);
            SendToClient.sendStringMessage(user, SendToClient.HEADER_ROAD);
        }
        else {
            if (!endTurn()) {
                SendToClient.sendGameSessionBroadcast(game);
                SendToClient.sendStringMessage(user, SendToClient.HEADER_ENDTURN);
                User nextUser = Server.findUser(game.getCurr().getUserId());
                if (nextUser != null) {
                    if (!game.isInitialized()) {
                        SendToClient.sendStringMessage(nextUser, SendToClient.HEADER_BEGININIT);
                    } else {
                        SendToClient.sendStringMessage(nextUser, SendToClient.HEADER_BEGINTURN);
                    }
                }
            }
        }
        Server.currentlyThreaded.remove(game.getGameId());
    }
}
