package com.example.catanserver.businessLogic.model;

import com.example.catanserver.businessLogic.model.gameboard.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private static int currGameId = 0;
    protected List<PlayerImpl> playerList = new ArrayList<>();
    private int gameId;
    private Gameboard gameboard;
    private LinkedList<Edge> roads;
    private LinkedList<Knot> settlements;
    private LinkedList<Knot> cities;
    private int currPlayer;

    // private LinkedList<Entwicklungskarte> entwicklungskartenStapel;

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
        return this.playerList;
    }

    public void setPlayers(List<PlayerImpl> players) {
        this.list = players;
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

    private void addRoad(Edge road){
        roads.add(road);
    }

    private void addSettlement(Knot settlement){
        settlements.add(settlement);
    }

    private void addCity(Knot settlement){
        settlements.remove(settlement);
        cities.add(settlement);
    }

    private void nextPlayer(){
        if(currPlayer == playerList.size()-1){
            currPlayer = 0;
        }
        else{
            currPlayer++;
        }
    }

}
