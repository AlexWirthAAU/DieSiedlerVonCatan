package com.example.catanserver.threads.trading;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.Trade;
import com.example.catanserver.User;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

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

    public TradeThread(User user, GameSession game, String tradeStr) {
        super(user, game);
        this.currPlayer = game.getPlayer(user.getUserId());
        this.tradeStr = tradeStr;
    }

    public void run() {

        setTradeData(tradeStr);

        if (checkTrade(offer)) {

            checkAndSetTradingPartners(game, want);
            String mess = buildMessage();
            this.trade = new Trade(offer, want, currPlayer, potentialTradingPartners, mess, game);
            game.setTrade(this.trade);

            distribute(potentialTradingPartners, mess);

        } else {
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Nicht genug Rohstoffe um zu handeln");
            errThread.run();
        }
    }

    private void distribute(List<Player> playersToSend, String mess) {
        SendToClient.sendTradeMessageBroadcast(playersToSend, mess);
    }

    private boolean checkTrade(Map<String, Integer> offer) {

        if (currPlayer.getInventory().canTrade) {
            return false;
        } else return currPlayer.getInventory().getWood() >= offer.get("WoodGive")
                && currPlayer.getInventory().getWool() >= offer.get("WoolGive")
                && currPlayer.getInventory().getWheat() >= offer.get("WheatGive")
                && currPlayer.getInventory().getOre() >= offer.get("OreGive")
                && currPlayer.getInventory().getClay() >= offer.get("ClayGive");
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

    private void setTradeData(String tradeStr) {

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
