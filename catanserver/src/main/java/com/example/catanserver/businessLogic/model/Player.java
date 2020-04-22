package com.example.catanserver.businessLogic.model;

/**
 * @author Christina Senger
 * <p>
 * Ein Player kennt seine PlayerId, die GameId
 * des Spiel in dem er ist und seine Farbe und
 * hat entsprechende Getter und Setter.
 */
public interface Player extends User {

    int getGameId();

    void setGameId(int gameId);

    Colors getColor();

    void setColor(Colors color);

}
