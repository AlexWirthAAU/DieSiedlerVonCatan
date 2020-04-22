package com.example.catanserver.businessLogic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Christina Senger
 * <p>
 * Ein Game kennt seine GameId und seine
 * Spieler und deren Id und hat entsprechende
 * Getter und Setter.
 */
public class GameImpl implements Game, Serializable {

    private static int currGameId = 0; //fortlaufende Id

    private List<PlayerImpl> list = new ArrayList<>(4);
    private int gameId;
    private int nextPlayersId;

    public GameImpl() {

        this.gameId = ++currGameId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public List<PlayerImpl> getPlayers() {
        return this.list;
    }

    public void setPlayers(List<PlayerImpl> players) {
        this.list = players;
    }

    public void setPlayer(PlayerImpl player) {
        player.setPlayerId(this.nextPlayersId++);
        this.list.add(player);
    }


}
