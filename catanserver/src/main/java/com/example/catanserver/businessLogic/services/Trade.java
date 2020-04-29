package com.example.catanserver.businessLogic.services;

import com.example.catanserver.businessLogic.model.GameImpl;
import com.example.catanserver.businessLogic.model.player.PlayerImpl;

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

    private List<String> potentialTradingPartners = new ArrayList<>();
    private Map<String, Boolean> answers = new HashMap<>();

    private Map<String, Integer> offer;
    private GameImpl game;
    private String host;
    private PlayerServiceImpl psi;
    private PlayerImpl currPlayer;
    private PlayerImpl tradingPartner;
    private StringBuilder message = new StringBuilder();

    public Trade(Map<String, Integer> offer, int gameId, GameServiceImpl gsi, String host, PlayerServiceImpl psi) {
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

    private void distribute(List<String> clientsToSend, String mess) {


    }

    private void checkAndSetTradingPartners() {

        List<PlayerImpl> players = game.getPlayers();

        for (PlayerImpl player : players) {

            if (player.getHost().equals(host)) {

                this.currPlayer = player;

            } else if (player.getInventory().getWood() >= woodGet
                    && player.getInventory().getWool() >= woolGet
                    && player.getInventory().getWheat() >= wheatGet
                    && player.getInventory().getOre() >= oreGet
                    && player.getInventory().getClay() >= clayGet) {

                potentialTradingPartners.add(player.getHost());
            }
        }

    }

    private String buildMessage() {

        int j = 0;

        message.append(currPlayer.getDisplayName()).append("bietet ");

        for (Map.Entry<String, Integer> entry : offer.entrySet()) {

            if (entry.getValue() > 0 && j < 5) {
                message.append(entry.getValue()).append(" ").append(entry.getKey()).append(" ");
            }
            j++;
        }

        message.append(" und möchte dafür ");

        for (Map.Entry<String, Integer> entry : offer.entrySet()) {

            if (entry.getValue() > 0 && j >= 5) {
                message.append(entry.getValue()).append(" ").append(entry.getKey()).append(" ");
            }
            j++;
        }

        return message.toString();
    }

    public void setAnswerList(String player, boolean answer) {

        answers.put(player, answer);
        boolean accepted = false;
        List<String> toSendNeg = new ArrayList<>();
        List<String> toSendPos = new ArrayList<>();

        if (answers.size() == potentialTradingPartners.size()) {

            for (Map.Entry<String, Boolean> entry : answers.entrySet()) {

                if (entry.getValue() && !accepted) {

                    accepted = true;
                    tradingPartner = psi.findPlayerByHost(entry.getKey());
                    toSendPos.add(entry.getKey());

                } else {
                    toSendNeg.add(entry.getKey());
                }
            }
        }

        if (!accepted) {

            updateRessources();
            toSendNeg.add(host);
            distribute(toSendNeg, "Leider keine Handelspartner");

        } else {

            toSendPos.add(host);
            distribute(toSendNeg, "Kein Handel");
            distribute(toSendPos, "Handel durchgeführt");
        }
    }

    private void updateRessources() {

        currPlayer.getInventory().addWood(woodGet);
        currPlayer.getInventory().addWool(woolGet);
        currPlayer.getInventory().addWheat(wheatGet);
        currPlayer.getInventory().addOre(oreGet);
        currPlayer.getInventory().addClay(clayGet);

        currPlayer.getInventory().removeWood(woodGive);
        currPlayer.getInventory().removeWool(woolGive);
        currPlayer.getInventory().removeWheat(wheatGive);
        currPlayer.getInventory().removeOre(oreGive);
        currPlayer.getInventory().removeClay(clayGive);

        tradingPartner.getInventory().addWood(woodGive);
        tradingPartner.getInventory().addWool(woolGive);
        tradingPartner.getInventory().addWheat(wheatGive);
        tradingPartner.getInventory().addOre(oreGive);
        tradingPartner.getInventory().addClay(clayGive);

        tradingPartner.getInventory().removeWood(woodGet);
        tradingPartner.getInventory().removeWool(woolGet);
        tradingPartner.getInventory().removeWheat(wheatGet);
        tradingPartner.getInventory().removeOre(oreGet);
        tradingPartner.getInventory().removeClay(clayGet);
    }
}
