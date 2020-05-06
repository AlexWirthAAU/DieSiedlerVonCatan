package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.Trade;
import com.example.catanserver.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradeThread extends GameThread {

    private List<Player> potentialTradingPartners = new ArrayList<>();

    private Map<String, Integer> offer;
    private Map<String, Integer> want;
    private Trade trade;
    private Player currPlayer;
    private StringBuilder message = new StringBuilder();
    private String tradeStr;

    public TradeThread(Socket connection, User user, GameSession game, String tradeStr) {
        super(connection, user, game);
        this.currPlayer = game.getPlayer(user.getUserId());
        this.tradeStr = tradeStr;
    }

    public void run() {
        setTradeData(tradeStr, game);
    }

    private void distribute(List<Player> playersToSend, String mess) {
        SendToClient.sendTradeMessageBroadcast(connection, playersToSend, mess);
    }

    private void checkAndSetTradingPartners(GameSession game, Map<String, Integer> want) {

        List<Player> players = game.getPlayers();

        for (Player player : players) {

            if (!player.equals(currPlayer)) {

                if (player.getInventory().getWood() >= want.get("WoodGet")
                        && player.getInventory().getWool() >= want.get("WoolGet")
                        && player.getInventory().getWheat() >= want.get("WheatGet")
                        && player.getInventory().getOre() >= want.get("OreGet")
                        && player.getInventory().getClay() >= want.get("ClayGet")) {

                    potentialTradingPartners.add(player);
                }
            }
        }
    }

    private void setTradeData(String tradeStr, GameSession game) {

        String[] trd = tradeStr.split("/");
        int i = 0;

        while (i < tradeStr.length() - 1) {
            int num = Integer.parseInt(trd[i + 1]);
            if (num == -1) {
                break;
            }
            offer.put(trd[i], num);
            i += 2;
        }

        while (i < tradeStr.length() - 1) {
            int num = Integer.parseInt(trd[i + 1]);
            want.put(trd[i], num);
            i += 2;
        }

        checkAndSetTradingPartners(game, want);
        String mess = buildMessage();
        this.trade = new Trade(offer, want, currPlayer, potentialTradingPartners, mess, game);
        game.setTrade(this.trade);

        distribute(potentialTradingPartners, mess);
    }

        private String buildMessage () {

            message.append("TRADEMESSAGE/");
            message.append(currPlayer.getDisplayName()).append("bietet ");

            for (Map.Entry<String, Integer> entry : offer.entrySet()) {
                if (entry.getValue() > 0) {
                    message.append(entry.getValue()).append(" ").append(entry.getKey()).append(" ");
                }
            }

            message.append(" und möchte dafür ");

            for (Map.Entry<String, Integer> entry : want.entrySet()) {
                if (entry.getValue() > 0) {
                    message.append(entry.getValue()).append(" ").append(entry.getKey()).append(" ");
                }
            }

            return message.toString();
        }
    }
