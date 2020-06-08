package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.resourceallocation.ResourceAllocation;

public class ResourceAllocationThread extends GameThread {

    int diceValue;


    public ResourceAllocationThread(User user, GameSession game, int diceValue) {
        super(user, game);
        this.diceValue = diceValue;
    }

    public void run() {
        ResourceAllocation.updateResources(game, diceValue);
        Player player = game.getPlayer(user.getUserId());
        if(player.hasToSkip()){
            player.skip();
            if(!endTurn()) {
                game.nextPlayer();
                SendToClient.sendGameSessionBroadcast(game);
                SendToClient.sendStringMessage(user, SendToClient.HEADER_ENDTURN);
                User nextUser = Server.findUser(game.getCurr().getUserId());
                if (nextUser != null) {
                    SendToClient.sendStringMessage(nextUser, SendToClient.HEADER_BEGINTURN);
                }
            }
        }
        else {
            SendToClient.sendGameSessionBroadcast(game);
            SendToClient.sendStringMessage(user, SendToClient.HEADER_ROLLED);
        }
        Server.currentlyThreaded.remove(game.getGameId());
    }
}
