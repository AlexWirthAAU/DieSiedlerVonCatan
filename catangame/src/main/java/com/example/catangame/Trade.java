package com.example.catangame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trade {

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

    public void setWoodGive(int woodGive) {
        this.woodGive = woodGive;
    }

    public int getWoodGet() {
        return woodGet;
    }

    public void setWoodGet(int woodGet) {
        this.woodGet = woodGet;
    }

    public int getWoolGive() {
        return woolGive;
    }

    public void setWoolGive(int woolGive) {
        this.woolGive = woolGive;
    }

    public int getWoolGet() {
        return woolGet;
    }

    public void setWoolGet(int woolGet) {
        this.woolGet = woolGet;
    }

    public int getWheatGive() {
        return wheatGive;
    }

    public void setWheatGive(int wheatGive) {
        this.wheatGive = wheatGive;
    }

    public int getWheatGet() {
        return wheatGet;
    }

    public void setWheatGet(int wheatGet) {
        this.wheatGet = wheatGet;
    }

    public int getOreGive() {
        return oreGive;
    }

    public void setOreGive(int oreGive) {
        this.oreGive = oreGive;
    }

    public int getOreGet() {
        return oreGet;
    }

    public void setOreGet(int oreGet) {
        this.oreGet = oreGet;
    }

    public int getClayGive() {
        return clayGive;
    }

    public void setClayGive(int clayGive) {
        this.clayGive = clayGive;
    }

    public int getClayGet() {
        return clayGet;
    }

    public void setClayGet(int clayGet) {
        this.clayGet = clayGet;
    }

    public Map<String, Integer> getOffer() {
        return offer;
    }

    public void setOffer(Map<String, Integer> offer) {
        this.offer = offer;
    }

    public Map<String, Integer> getWant() {
        return want;
    }

    public void setWant(Map<String, Integer> want) {
        this.want = want;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public List<Player> getPotentialTradingPartners() {
        return potentialTradingPartners;
    }

    public void setPotentialTradingPartners(List<Player> potentialTradingPartners) {
        this.potentialTradingPartners = potentialTradingPartners;
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

    public void setTradingPartner(Player tradingPartner) {
        this.tradingPartner = tradingPartner;
    }

    public Map<Player, Boolean> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Player, Boolean> answers) {
        this.answers = answers;
    }

    public void addAnswers(Player player, Boolean answer) {
        this.answers.put(player, answer);
    }

    public List<Player> getAnsweredPlayers() {
        return answeredPlayers;
    }

    public void setAnsweredPlayers(List<Player> answeredPlayers) {
        this.answeredPlayers = answeredPlayers;
    }

    public void addAnsweredPlayer(Player player) {
        this.answeredPlayers.add(player);
    }
}


