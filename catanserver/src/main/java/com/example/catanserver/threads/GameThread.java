package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.Cheating;
import com.example.catanserver.businessLogic.model.resourceallocation.InitResAllocation;

/**
 * @author Fabian Schaffenrath
 * @author Christina Senger (Documentation edit)
 *
 * This class implements the typical inputs every normal connection string should contain.
 * Practically every Thread responsible for actions after the game start should inherit from this.
 */
public abstract class GameThread extends Thread {

    public User user;
    public GameSession game;

    /**
     * Constructor - Sets User and his Game.
     *
     * @param user the currents User
     * @param game the Users Game
     */
    public GameThread(User user, GameSession game) {
        this.game = game;
        this.user = user;
    }

    public void run(){
        System.out.println("Thread is started.");
    }

    /**
     * After every turn, a not noticed Grab containing the User who finished his turn as the Player
     * to be stolen from is executed.
     * If the user has won true is returned, false otherwise.
     */
    public boolean endTurn(){
        checkInitialized();
        Cheating.processGrabs(game,user.getUserId());
        return checkForWin();
    }

    private void checkInitialized(){
        if(!game.isInitialized()){
            boolean isFinished = true;
            for (Player player:game.getPlayers()) {
                if(player.getInventory().getSettlements().size() < 2 || player.getInventory().getRoads().size() < 2){
                    isFinished = false;
                }
            }
            if(isFinished){
                InitResAllocation.allocateInit(game);
                game.setInitialized(true);
            }
        }
    }

    private boolean checkForWin(){
        if(game.getPlayer(user.getUserId()).getInventory().getVictoryPoints() >= 10){
            SendToClient.sendGameSessionBroadcast(game);
            SendToClient.sendStringMessage(user,SendToClient.HEADER_WON);
            for (Player player:game.getPlayers()) {
                if(player.getUserId() != user.getUserId()){
                    User losingUser = Server.findUser(player.getUserId());
                    if(losingUser != null) {
                        SendToClient.sendStringMessage(losingUser,SendToClient.HEADER_LOST);
                    }
                }
            }
            Server.currentGames.remove(game);
            return true;
        }
        return false;
    }
}
