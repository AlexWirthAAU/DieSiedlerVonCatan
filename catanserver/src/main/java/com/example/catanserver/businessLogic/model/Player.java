package com.example.catanserver.businessLogic.model;

public interface Player extends User {

    int getPlayerId();

    void setPlayerId(int playerId);

    int getGameId();

    void setGameId(int gameId);

    Colors getColor();

    void setColor(Colors color);

}
