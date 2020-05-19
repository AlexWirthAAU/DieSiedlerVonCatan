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
 * Represantation of a GameSession
 */
public class GameSession implements Serializable {

    public static Trade currTrade; // current active Trade
    private static int gameCounter = 0; // consecutive ID
    private int gameId; // GameID
    private List<Player> players = new ArrayList<>(4); // List of all Players in the Game (max. 4)
    private Gameboard gameboard; // Gameboard
    private LinkedList<Knot> settlements;
    private LinkedList<Knot> cities;
    private LinkedList<Edge> roads; // Lists of Structures
    private Player curr;
    private int currPlayer; // current Player and his index
    private ArrayList<DevCard> devCards; // List of DevCards
    private String message; // optional Message to alert after an Action
    private boolean isCardBuild; // states whether a BuildRoad is started playing a Card
    private boolean isTradeOn; // states whether a Trade is active

    /**
     * Constructor - Gives the Game the next ID from GameCounter,
     * initialises empty Lists and give the Game a new DevCard-Stack.
     * The current Player (on Turn) is the one which started the Game.
     */
    public GameSession() {

        this.gameId = ++gameCounter;
        gameboard = new Gameboard();
        roads = new LinkedList<>();
        settlements = new LinkedList<>();
        cities = new LinkedList<>();
        currPlayer = 0;
        this.devCards = new DevCardStack().getDevCardStack();
    }

    // Getter
    public int getGameId() {
        return gameId;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    /**
     * Get a Player by its ID
     *
     * @param userId the Players ID
     * @return the Player with the userId, null if no Player is found
     */
    public Player getPlayer(int userId){
        for (Player player:players) {
            if(player.getUserId() == userId){
                return player;
            }
        }
        return null;
    }

    // Getters
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

    public Player getCurr() {
        return players.get(currPlayer);
    }

    public int getCurrPlayer() {
        return currPlayer;
    }

    public ArrayList<DevCard> getDevCards() {
        return devCards;
    }

    public Trade getTrade() {
        return this.currTrade;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCardBuild() {
        return this.isCardBuild;
    }

    public void setCardBuild(boolean isCardBuild) {
        this.isCardBuild = isCardBuild;
    }

    public void addSettlement(Knot settlement) {
        settlements.add(settlement);
    }

    public void addCity(Knot settlement) {
        settlements.remove(settlement);
        cities.add(settlement);
    }

    // Setters
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setPlayer(Player player) {
        this.players.add(player);
    }

    public boolean isTradeOn() {
        return this.isTradeOn;
    }

    // add Structures
    public void addRoad(Edge road) {
        roads.add(road);
    }

    public void setTrade(Trade trade) {
        this.currTrade = trade;
    }

    public void setIsTradeOn(boolean isTradeOn) {
        this.isTradeOn = isTradeOn;
    }

    /**
     * Make the next Player in the Row the current Player
     */
    public void nextPlayer() {
        if (currPlayer == players.size() - 1) {
            currPlayer = 0;
        } else{
            currPlayer++;
        }
        curr = players.get(currPlayer);
    }
}
