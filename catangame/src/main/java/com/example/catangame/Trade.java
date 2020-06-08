package com.example.catangame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Christina Senger
 * <p>
 * Representation of a Trade
 */
public class Trade implements Serializable {

    private int woodGive; // Values of desired and offered Resources
    private int woodGet;
    private int woolGive;
    private int woolGet;
    private int wheatGive;
    private int wheatGet;
    private int oreGive;
    private int oreGet;
    private int clayGive;
    private int clayGet;

    private Map<String, Integer> offer;

    private Player currPlayer;
    private List<Player> potentialTradingPartners;
    private String message;
    private GameSession game;

    private Player tradingPartner; // Player which first accepted the Trade
    private Map<Player, Boolean> answers = new HashMap<>(); // Map of all Answers
    private List<Player> answeredPlayers = new ArrayList<>(); // List of all Player, that have answered

    /**
     * Constructor - creates Trade with Data from Thread
     *
     * @param offer Map of offered Resources
     * @param want Map of desired Resources
     * @param currPlayer current Player
     * @param potentialTradingPartners List of all Player with enough Resources
     * @param mess Trade-Message
     * @param game current Game
     */
    public Trade(Map<String, Integer> offer, Map<String, Integer> want, Player currPlayer, List<Player> potentialTradingPartners, String mess, GameSession game) {

        this.woodGive = offer.get("Holz");
        this.woodGet = want.get("Holz");
        this.woolGive = offer.get("Wolle");
        this.woolGet = want.get("Wolle");
        this.wheatGive = offer.get("Weizen");
        this.wheatGet = want.get("Weizen");
        this.oreGive = offer.get("Erz");
        this.oreGet = want.get("Erz");
        this.clayGive = offer.get("Lehm");
        this.clayGet = want.get("Lehm");

        this.offer = offer;
        this.currPlayer = currPlayer;
        this.potentialTradingPartners = potentialTradingPartners;
        this.message = mess;
        this.game = game;
    }

    // Get Resources
    public int getWoodGive() {
        return woodGive;
    }

    public int getWoodGet() {
        return woodGet;
    }

    public int getWoolGive() {
        return woolGive;
    }

    public int getWoolGet() {
        return woolGet;
    }

    public int getWheatGive() {
        return wheatGive;
    }

    public int getWheatGet() {
        return wheatGet;
    }

    public int getOreGive() {
        return oreGive;
    }

    public int getOreGet() {
        return oreGet;
    }

    public int getClayGive() {
        return clayGive;
    }

    public int getClayGet() {
        return clayGet;
    }


    // More Getter
    public Map<String, Integer> getOffer() {
        return offer;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public List<Player> getPotentialTradingPartners() {
        return potentialTradingPartners;
    }

    public String getMessage() {
        return message;
    }

    public GameSession getGame() {
        return game;
    }

    public Player getTradingPartner() {
        return tradingPartner;
    }

    public Map<Player, Boolean> getAnswers() {
        return answers;
    }

    public List<Player> getAnsweredPlayers() {
        return answeredPlayers;
    }


    // Setter
    public void setMessage(String message) {
        this.message = message;
    }

    public void setGame(GameSession game) {
        this.game = game;
    }

    public void addAnswers(Player player, Boolean answer) {
        this.answers.put(player, answer);
    }

    public void addAnsweredPlayer(Player player) {
        this.answeredPlayers.add(player);
    }

    public void setTradingPartner(Player tradingPartner) {
        this.tradingPartner = tradingPartner;
    }
}


