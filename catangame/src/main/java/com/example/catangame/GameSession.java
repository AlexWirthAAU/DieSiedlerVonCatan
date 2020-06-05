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

    private static int gameCounter = 0; // consecutive ID
    private int gameId; // GameID
    private List<Player> players = new ArrayList<>(4); // List of all Players in the Game (max. 4)
    private Gameboard gameboard; // Gameboard
    private LinkedList<Knot> settlements;
    private LinkedList<Knot> cities;
    private LinkedList<Edge> roads; // Lists of Structures
    private int currPlayer; // current Player and his index
    private ArrayList<DevCard> devCards; // List of DevCards
    private String message; // optional Message to alert after an Action
    private boolean isCardBuild; // states whether a BuildRoad is started playing a Card
    private boolean isTradeOn; // states whether a Trade is active
    private Trade currTrade; // current active Trade
    private int knightPowerCount; // Number of Knight-Cards the Player with the greatest Knightpower has
    private Player knightPowerOwner; // Player which has the greatest Knightpower
    private LinkedList<Grab> grabs; // Running Cheating requests
    private boolean isInitialized;

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
        this.grabs = new LinkedList<>();
        isInitialized = false;
        this.knightPowerCount = 0;
        this.setKnightPowerOwner(null);
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

    public ArrayList<DevCard> getDevCards() {
        return devCards;
    }

    public Trade getTrade() {
        return this.currTrade;
    }

    public boolean isTradeOn() {
        return this.isTradeOn;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isCardBuild() {
        return this.isCardBuild;
    }

    public int getKnightPowerCount() {
        return this.knightPowerCount;
    }

    public void setKnightPowerCount(int knightPowerCount) {
        this.knightPowerCount = knightPowerCount;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    // Setters
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setPlayer(Player player) {
        this.players.add(player);
    }

    public void setTrade(Trade trade) {
        this.currTrade = trade;
    }

    public void setIsTradeOn(boolean isTradeOn) {
        this.isTradeOn = isTradeOn;
    }

    public Player getKnightPowerOwner() {
        return this.knightPowerOwner;
    }

    public void setKnightPowerOwner(Player knightPowerOwner) {
        this.knightPowerOwner = knightPowerOwner;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCardBuild(boolean isCardBuild) {
        this.isCardBuild = isCardBuild;
    }

    public void setCurrPlayer(int index) {
        this.currPlayer = index;
    }

    public void setDevCards(ArrayList<DevCard> devCards) {
        this.devCards = devCards;
    }

    ;

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    // add Structures
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

    public LinkedList<Grab> getGrabs() {
        return grabs;
    }

    public Grab getGrabOf(int userId){
        for (Grab grab:this.grabs) {
            if(grab.getGrabbed().getUserId() == userId){
                return grab;
            }
        }
        return null;
    }

    public Grab getGrabFrom(int userId){
        for (Grab grab:this.grabs) {
            if(grab.getGrabber().getUserId() == userId){
                return grab;
            }
        }
        return null;
    }

    public void addGrab(Grab grab){
        grabs.add(grab);
    }

    public void removeGrab(Grab grab){
        grabs.remove(grab);
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
    }

    public void previousPlayer() {
        if (currPlayer == 0) {
            currPlayer = players.size() - 1;
        } else {
            currPlayer--;
        }
    }
}
