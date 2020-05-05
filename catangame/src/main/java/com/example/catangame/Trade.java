package com.example.catangame;

import java.util.ArrayList;
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

    private List<Player> potentialTradingPartners = new ArrayList<>();
    private Map<String, Boolean> answers = new HashMap<>();

    private Map<String, Integer> offer;
    private GameSession game;
    private String host;
    private PlayerServiceImpl psi;
    private Player currPlayer;
    private Player tradingPartner;
    private StringBuilder message = new StringBuilder();

    public Trade(Map<String, Integer> offer, Player currPlayer, List<Player> potentialTradingPartners) {
        this.woodGive = offer.get("WoodGive");
        this.woodGet = offer.get("WoodGet");
        this.woolGive = offer.get("WoolGive");
        this.woolGet = offer.get("WoolGet");
        this.wheatGive = offer.get("WheatGive");
        this.wheatGet = offer.get("WheatGet");
        this.oreGive = offer.get("OreGive");
        this.oreGet = offer.get("OreGet");
        this.clayGive = offer.get("ClayGive");
        this.clayGet = offer.get("ClayGet");

        this.offer = offer;
        this.game = gsi.findObject(gameId);
        this.host = host;
        this.psi = psi;
        game.setTrade(this);

        checkAndSetTradingPartners();
        distribute(potentialTradingPartners, buildMessage());
    }
}


