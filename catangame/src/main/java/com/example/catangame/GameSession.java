package com.example.catangame;

import com.example.catangame.devcards.DevCard;
import com.example.catangame.devcards.DevCardStack;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Gameboard;
import com.example.catangame.gameboard.Knot;

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
    private Player curr;
    private ArrayList<DevCard> devCards;
    public static Trade currTrade;

    public GameSession() {

        this.gameId = ++gameCounter;
        gameboard = new Gameboard();
        roads = new LinkedList<>();
        settlements = new LinkedList<>();
        cities = new LinkedList<>();
        currPlayer = 0;
        this.devCards = new DevCardStack().getDevCardStack();
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

    public Player getCurr() {
        return players.get(currPlayer);
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

    public Trade getTrade() {
        return this.currTrade;
    }

    public void setTrade(Trade trade) {
        this.currTrade = trade;
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

    public void nextPlayer() {
        if (currPlayer == players.size() - 1) {
            currPlayer = 0;
        }
        else{
            currPlayer++;
        }
        curr = players.get(currPlayer);
    }

}
