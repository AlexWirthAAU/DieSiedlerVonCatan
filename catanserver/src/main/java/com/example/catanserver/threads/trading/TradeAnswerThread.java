package com.example.catanserver.threads.trading;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.Trade;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.trading.Answer;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

import java.util.List;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * This Thread handles the Answers of the Players to the current active Trade.
 */
public class TradeAnswerThread extends GameThread {

    private Trade trade; // Trade which was answered to
    private Player currPlayer; // current Player
    private Player tradingPartner; // Trading-Partner
    private Player tradingOfferer; // Player which started the Trade

    private String answerStr;

    /**
     * Constructor - Gets the current Player from the Game und sets the Trade-Offer.
     *
     * {@inheritDoc}
     *
     * @param answerStr Answer --> accept or dismiss
     */
    public TradeAnswerThread(User user, GameSession game, String answerStr) {
        super(user, game);
        this.currPlayer = game.getPlayer(user.getUserId());
        this.answerStr = answerStr;
        this.trade = game.getTrade();
        this.tradingOfferer = this.trade.getCurrPlayer();
    }

    /**
     * When there is a Answer, the Player is added to the List of the
     * answered Players and his Answer is added as Boolean to the Answers-Map.
     * When all Players have answered, the first one which answered with
     * accepted is set as Trading-Partner.
     */
    public void run() {

        Answer.addAnsweredPlayer(answerStr, trade, currPlayer);

        if (trade.getAnsweredPlayers().size() == trade.getPotentialTradingPartners().size()) {

            game.setTrade(null);
            game.setIsTradeOn(false);
            game.nextPlayer();

            if(!endTurn()) {

                SendToClient.sendGameSessionBroadcast(game);

                if (Answer.trade(this.tradingOfferer, trade)) {
                    tradingPartner = trade.getTradingPartner();
                    String mess = "Handel zwischen " + tradingOfferer.getDisplayName() + " und " + tradingPartner.getDisplayName() + " durchgefuehrt.";
                    SendToClient.sendStringMessage(Server.findUser(tradingOfferer.getUserId()), SendToClient.HEADER_ENDTURN + " " + mess);

                    if (game.getCurr().equals(tradingPartner)) {
                        User nextUser = Server.findUser(game.getCurr().getUserId());
                        if (nextUser != null) {
                            SendToClient.sendStringMessage(nextUser, SendToClient.HEADER_BEGINTURN + " " + mess);
                        }
                    } else {
                        User tradingPartnerUser = Server.findUser(tradingPartner.getUserId());
                        if (tradingPartnerUser != null) {
                            SendToClient.sendStringMessage(tradingPartnerUser, SendToClient.HEADER_TRADECOMPLETE + " " + mess);
                        }
                        User nextUser = Server.findUser(game.getCurr().getUserId());
                        if (nextUser != null) {
                            SendToClient.sendStringMessage(nextUser, SendToClient.HEADER_BEGINTURN);
                        }
                    }

                } else {
                    SendToClient.sendStringMessage(Server.findUser(tradingOfferer.getUserId()), SendToClient.HEADER_ENDTURN + " Leider keine Handelspartner.");
                    User nextUser = Server.findUser(game.getCurr().getUserId());
                    if (nextUser != null) {
                        SendToClient.sendStringMessage(nextUser, SendToClient.HEADER_BEGINTURN);
                    }
                }
            }
        }
    }
}
