package com.example.catanserver.businessLogic.model;

import java.util.List;

public interface Game {

    int getGameId();

    void setGameId(int gameId);

    List<PlayerImpl> getPlayers();

    void setPlayer(PlayerImpl player);
}
