package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.Trade;
import com.example.catanserver.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TradeAnswerThread extends GameThread {

    private Trade trade;
    private Player currPlayer;
    private Player tradingPartner;
    private String answerStr;

    public TradeAnswerThread(Socket connection, User user, GameSession game, String answerStr) {
        super(connection, user, game);
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

        List<Player> toSendNeg = new ArrayList<>();
        List<Player> toSendPos = new ArrayList<>();

        if (tradingPartner == null) {
            toSendNeg.add(trade.getCurrPlayer());

            for (Player p : trade.getPotentialTradingPartners()) {
                toSendNeg.add(p);
            }

            distribute(toSendNeg, "TRADEANSWER/Leider keine Handelspartner");

        } else {

            toSendPos.add(trade.getCurrPlayer());

            for (Player p : trade.getPotentialTradingPartners()) {
                if (!p.equals(tradingPartner)) {
                    toSendNeg.add(p);
                }
                toSendPos.add(p);
            }

            exchangeRessources();
            distribute(toSendPos, "TRADEANSWER/Handel durchgeführt");
            distribute(toSendNeg, "TRADEANSWER/Kein Handel");
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
        SendToClient.sendTradeMessageBroadcast(connection, playersToSend, mess);
    }
}
