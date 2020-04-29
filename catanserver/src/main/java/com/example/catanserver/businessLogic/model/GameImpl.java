package com.example.catanserver.businessLogic.model;

import com.example.catanserver.businessLogic.model.devcards.DevCard;
import com.example.catanserver.businessLogic.model.devcards.DevCardStack;
import com.example.catanserver.businessLogic.model.gameboard.Edge;
import com.example.catanserver.businessLogic.model.gameboard.Gameboard;
import com.example.catanserver.businessLogic.model.gameboard.Knot;
import com.example.catanserver.businessLogic.model.player.PlayerImpl;
import com.example.catanserver.businessLogic.services.Trade;

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
    private int gameId;
    private Gameboard gameboard;
    private LinkedList<Edge> roads;
    private LinkedList<Knot> settlements;
    private LinkedList<Knot> cities;
    private int currPlayer;
    private ArrayList<DevCard> devCards;
    private Trade currTrade;

    // private LinkedList<Entwicklungskarte> entwicklungskartenStapel;

    public GameImpl() {

        this.gameId = ++currGameId;
        this.devCards = new DevCardStack().getDevCardStack();
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
        this.list.add(player);
    }

    public Trade getTrade() {
        return this.currTrade;
    }

    public void setTrade(Trade trade) {
        this.currTrade = trade;
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

    public ArrayList<DevCard> getDevCards() {
        return devCards;
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
        if (currPlayer == list.size() - 1) {
            currPlayer = 0;
        }
        else{
            currPlayer++;
        }
    }

}
