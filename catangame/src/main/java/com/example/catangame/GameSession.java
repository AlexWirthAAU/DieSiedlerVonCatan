package com.example.catangame;

import com.example.catangame.gameboard.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Ein Game kennt seine GameId, das Gameboard inklusive
 * Roads, Settlements und Cities, sowie die zugehörigen Spieler
 * und enthält entsprechende Getter und Setter.
 */
public class GameSession implements Serializable {

    private static int gameCounter = 0; //fortlaufende Id
    private List<Player> players = new ArrayList<>(4);
    private int gameId;
    private Gameboard gameboard;
    private LinkedList<Edge> roads;
    private LinkedList<Knot> settlements;
    private LinkedList<Knot> cities;
    private int currPlayer;

    // private LinkedList<Entwicklungskarte> entwicklungskartenStapel;

    public GameSession() {

        this.gameId = ++gameCounter;
        gameboard = new Gameboard();
        roads = new LinkedList<>();
        settlements = new LinkedList<>();
        cities = new LinkedList<>();
        currPlayer = 0;
    }

    public int getGameId() {
        return gameId;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Player getPlayer(int userId){
        for (Player player:players) {
            if(player.getUserId() == userId){
                return player;
            }
        }
        return null;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setPlayer(Player player) {
        this.players.add(player);
    }

    public Gameboard getGameboard() {
        return gameboard;
    }

    public LinkedList<Edge> getRoads() {
        return roads;
    }

    public LinkedList<Knot> getSettlements() {
        return settlements;
    }

    public LinkedList<Knot> getCities() {
        return cities;
    }

    public void addRoad(Edge road) {
        roads.add(road);
    }

    public void addSettlement(Knot settlement) {
        settlements.add(settlement);
    }

    public void addCity(Knot settlement) {
        settlements.remove(settlement);
        cities.add(settlement);
    }

    public void nextPlayer() {
        if (currPlayer == players.size() - 1) {
            currPlayer = 0;
        }
        else{
            currPlayer++;
        }
    }

    public int getCurrPlayer() {
        return currPlayer;
    }
}
