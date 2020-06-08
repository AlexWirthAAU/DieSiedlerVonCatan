package com.example.catanserver.threads.building;

import com.example.catangame.GameSession;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businesslogic.model.building.BuildSettlement;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

// TODO: kommentieren
public class BuildSettlementThread extends GameThread {

    int knotIndex;
    int userID;

    public BuildSettlementThread(User user, GameSession g, int k) {
        super(user, g);
        this.knotIndex = k;
        this.userID = user.getUserId();
    }


    public void run() {
        BuildSettlement.updateGameSession(game, knotIndex, userID);
        if(!endTurn()) {
            SendToClient.sendGameSessionBroadcast(game);
            if (!game.isInitialized()) {
                SendToClient.sendStringMessage(user, SendToClient.HEADER_CONTINUEINIT);
            } else {
                SendToClient.sendStringMessage(user, SendToClient.HEADER_ENDTURN);
                User nextUser = Server.findUser(game.getCurr().getUserId());
                if (nextUser != null) {
                    SendToClient.sendStringMessage(nextUser, SendToClient.HEADER_BEGINTURN);
                }
            }
        }
        Server.currentlyThreaded.remove(game.getGameId());
    }

}
