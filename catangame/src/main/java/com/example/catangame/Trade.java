package com.example.catangame;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Christina Senger
 * <p>
 * Representation of a Trade
 */
public class Trade implements Serializable {

    private int woodGive; // Values of d´desired and offered Ressources
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
    private Map<String, Integer> want;

    private Player currPlayer;
    private List<Player> potentialTradingPartners;
    private String message;
    private GameSession game;

    private Player tradingPartner; // Player which first acceted the Trade
    private Map<Player, Boolean> answers = new HashMap<>(); // Map of all Answers
    private List<Player> answeredPlayers; // List of all Player, that have answered
    private String answerMessage; // Message to send after the Trade

    /**
     * Constructor - creates Trade with Data from Thread
     *
     * @param offer Map of offerd Ressources
     * @param want Map of desired Ressources
     * @param currPlayer current Player
     * @param potentialTradingPartners List of all Player with enough Ressources
     * @param mess Trade-Message
     * @param game current Game
     */
    public Trade(Map<String, Integer> offer, Map<String, Integer> want, Player currPlayer, List<Player> potentialTradingPartners, String mess, GameSession game) {

        this.woodGive = offer.get("WoodGive");
        this.woodGet = want.get("WoodGet");
        this.woolGive = offer.get("WoolGive");
        this.woolGet = want.get("WoolGet");
        this.wheatGive = offer.get("WheatGive");
        this.wheatGet = want.get("WheatGet");
        this.oreGive = offer.get("OreGive");
        this.oreGet = want.get("OreGet");
        this.clayGive = offer.get("ClayGive");
        this.clayGet = want.get("ClayGet");

        this.offer = offer;
        this.want = want;
        this.currPlayer = currPlayer;
        this.potentialTradingPartners = potentialTradingPartners;
        this.message = mess;
        this.game = game;
    }

    // Get Ressources
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

    public Map<String, Integer> getWant() {
        return want;
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

    public String getAnswerMessage() {
        return answerMessage;
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

    public void setAnswerMessage(String answerMessage) {
        this.answerMessage = answerMessage;
    }
}


