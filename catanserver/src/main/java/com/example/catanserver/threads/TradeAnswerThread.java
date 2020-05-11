package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.Trade;
import com.example.catanserver.User;

import java.util.ArrayList;
import java.util.List;

public class TradeAnswerThread extends GameThread {

    private Trade trade;
    private Player currPlayer;
    private Player tradingPartner;
    private String answerStr;

    public TradeAnswerThread(User user, GameSession game, String answerStr) {
        super(user, game);
        this.currPlayer = game.getPlayer(user.getUserId());
        this.answerStr = answerStr;
        this.trade = game.getTrade();
    }

    public void run() {
        if (answerStr.equals("accepted")) {
            trade.addAnsweredPlayer(currPlayer);
            trade.addAnswers(currPlayer, true);
        } else {
            trade.addAnsweredPlayer(currPlayer);
            trade.addAnswers(currPlayer, false);
        }

        if (trade.getAnsweredPlayers().size() == trade.getPotentialTradingPartners().size()) {

            for (Player p : trade.getAnsweredPlayers()) {
                if (trade.getAnswers().get(p)) {
                    this.tradingPartner = p;
                    break;
                }
            }

            setAnswerList();
        }
    }

    private void setAnswerList() {

        List<Player> toSend = new ArrayList<>();

        if (tradingPartner == null) {
            toSend.add(trade.getCurrPlayer());

            trade.setAnswerMessage("TRADEANSWER/Leider keine Handelspartner");
            distribute(toSend, "TRADEANSWER/Leider keine Handelspartner");

        } else {

            toSend.add(trade.getCurrPlayer());

            for (Player p : trade.getAnsweredPlayers()) {
                if (trade.getAnswers().get(p)) {
                    toSend.add(p);
                }
            }

            exchangeRessources();
            trade.setAnswerMessage("TRADEANSWER/Handel zwischen " + trade.getCurrPlayer().getDisplayName() + " und " + trade.getTradingPartner().getDisplayName() + " durchgeführt");
            distribute(toSend, "TRADEANSWER/Handel zwischen " + trade.getCurrPlayer().getDisplayName() + " und " + trade.getTradingPartner().getDisplayName() + " durchgeführt");
        }
    }

    private void exchangeRessources() {

        currPlayer.getInventory().addWood(trade.getWoodGet());
        currPlayer.getInventory().addWool(trade.getWoolGet());
        currPlayer.getInventory().addWheat(trade.getWheatGet());
        currPlayer.getInventory().addOre(trade.getOreGet());
        currPlayer.getInventory().addClay(trade.getClayGet());

        currPlayer.getInventory().removeWood(trade.getWoodGive());
        currPlayer.getInventory().removeWool(trade.getWoolGive());
        currPlayer.getInventory().removeWheat(trade.getWheatGive());
        currPlayer.getInventory().removeOre(trade.getOreGive());
        currPlayer.getInventory().removeClay(trade.getClayGive());

        tradingPartner.getInventory().addWood(trade.getWoodGive());
        tradingPartner.getInventory().addWool(trade.getWoolGive());
        tradingPartner.getInventory().addWheat(trade.getWheatGive());
        tradingPartner.getInventory().addOre(trade.getOreGive());
        tradingPartner.getInventory().addClay(trade.getClayGive());

        tradingPartner.getInventory().removeWood(trade.getWoodGet());
        tradingPartner.getInventory().removeWool(trade.getWoolGet());
        tradingPartner.getInventory().removeWheat(trade.getWheatGet());
        tradingPartner.getInventory().removeOre(trade.getOreGet());
        tradingPartner.getInventory().removeClay(trade.getClayGet());
    }

    private void distribute(List<Player> playersToSend, String mess) {
        game.setTrade(null);
        game.nextPlayer();
        SendToClient.sendTradeMessageBroadcast(playersToSend, mess);
        SendToClient.sendGameSessionBroadcast(game);
    }
}
