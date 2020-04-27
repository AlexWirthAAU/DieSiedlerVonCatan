package com.example.catanserver.businessLogic.model;

import com.example.catanserver.businessLogic.model.player.PlayerImpl;

import java.util.List;

/**
 * @author Christina Senger
 * <p>
 * Ein Game kennt seine GameId und seine
 * Spieler und deren Id und hat entsprechende
 * Getter und Setter.
 */
public interface Game {

    int getGameId();

    void setGameId(int gameId);

    List<PlayerImpl> getPlayers();

    void setPlayers(List<PlayerImpl> players);

    void setPlayer(PlayerImpl player);
}
