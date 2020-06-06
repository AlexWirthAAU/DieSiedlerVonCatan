package com.example.catanserver.threads.building;

import com.example.catangame.GameSession;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.building.BuildCity;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

/**
 * @author Alex Wirth
 * <p>
 * Thread for processing the BuildCity Action. Game, user and affected KnotIndex are passed to the updateGamesession.
 * New Gamesession broadcasted to all players. The currentplayer will additionally get an message which ends his turn.
 * Also the next Player is choosen and informed.
 */

public class BuildCityThread extends GameThread {

    int knotIndex;
    int userID;

    public BuildCityThread(GameSession game, User user, int k) {
        super(user, game);
        this.knotIndex = k;
        this.userID = user.getUserId();
    }

    @Override
    public void run() {
        BuildCity.updateGameSession(game, knotIndex, userID);
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
