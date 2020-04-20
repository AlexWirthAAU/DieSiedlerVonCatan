package com.example.catanserver.businessLogic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameImpl implements Game, Serializable {

    private static int currGameId = 0;
    protected List<PlayerImpl> list = new ArrayList<>();
    private int gameId;

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

    public void setPlayer(PlayerImpl player) {
        this.list.add(player);
        player.setGameId(this.gameId);
    }
}
