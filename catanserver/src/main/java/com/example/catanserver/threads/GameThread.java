package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.Cheating;

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

    /**
     * After every turn, a not noticed Grab containing the User who finished his turn as the Player
     * to be stolen from is executed.
     */
    public void endTurn(){
        checkInitialized();
        Cheating.processGrabs(game,user);
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
                game.setInitialized(true);
            }
        }
    }
}
