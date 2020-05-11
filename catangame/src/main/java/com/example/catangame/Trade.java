package com.example.catangame;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trade implements Serializable {

    private int woodGive;
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

    private Player tradingPartner;
    private Map<Player, Boolean> answers = new HashMap<>();
    private List<Player> answeredPlayers;
    private String answerMessage;

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

    public void setMessage(String message) {
        this.message = message;
    }

    public GameSession getGame() {
        return game;
    }

    public void setGame(GameSession game) {
        this.game = game;
    }

    public Player getTradingPartner() {
        return tradingPartner;
    }

    public Map<Player, Boolean> getAnswers() {
        return answers;
    }

    public void addAnswers(Player player, Boolean answer) {
        this.answers.put(player, answer);
    }

    public List<Player> getAnsweredPlayers() {
        return answeredPlayers;
    }

    public void addAnsweredPlayer(Player player) {
        this.answeredPlayers.add(player);
    }

    public String getAnswerMessage() {
        return answerMessage;
    }

    public void setAnswerMessage(String answerMessage) {
        this.answerMessage = answerMessage;
    }
}


