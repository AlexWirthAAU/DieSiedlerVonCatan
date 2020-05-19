package com.example.diesiedler.presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fabian Schaffenrath
 * @author Christina Senger (edit)
 * @author Alex Wirth (edit)
 *
 * Class has the direct Server-Call and all Methods for the Creating of Request-String.
 */
public class ServerQueries {

    private static final Logger logger = Logger.getLogger(ServerQueries.class.getName()); // Logger

    /**
     * Sends a precast String-Request to the Server and flushes the Stream.
     *
     * @param query to send Request
     */
    public static void sendStringQuery(String query) {
        try {
            logger.log(Level.INFO, "MSG: " + query);
            ClientData.SEND_TO_SERVER.writeUTF(query);
            ClientData.SEND_TO_SERVER.flush();
        } catch (IOException ex) {
            logger.log(Level.INFO, "ERROR: Couldn't send query to server.");
        }
    }

    /**
     * Creates Login-Query
     *
     * @param name to send Username
     * @return Query with Identifier and Name
     */
    public static String createStringQueryLogin(String name) {
        return "LOGIN " + name.trim();
    }

    /**
     * Creates Apply-Query
     *
     * @return Query with Identifier
     */
    public static String createStringQueryApply() {
        return "APPLY";
    }

    /**
     * Creates Stop-Query
     *
     * @return Query with Identifier
     */
    public static String createStringQueryStop() {
        return "STOP";
    }

    /**
     * Creates Create-Query
     *
     * @param users List with IDs of all selected Users
     * @return Query with Identifier and all User-IDs
     */
    public static String createStringQueryCreate(ArrayList<Integer> users) {
        String query = "CREATE " + ClientData.userId;
        for (Integer user : users) {
            query += " " + user;
        }
        return query;
    }

    /**
     * Creates Color-Query
     *
     * @param color selected Color
     * @return Query with Identifier and selected Color
     */
    public static String createStringQueryColor(String color) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " COLOR " + color;
    }

    /**
     * Creates Start-Query
     *
     * @return Query with Identifier
     */
    public static String createStringQueryStart() {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " START";
    }

    /**
     * Creates Trade-Query
     *
     * @param offer String with offered and wanted Ressources
     * @return Query with Identifier and offer
     */
    public static String createStringQueryTrade(String offer) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " TRADE " + offer;
    }

    /**
     * Creates Trade-Query
     *
     * @param answer String with answer
     * @return Query with Identifier and answer
     */
    public static String createStringQueryTradeAnswer(String answer) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " TRADEANSWER " + answer;
    }

    /**
     * Creates PortChange-Query
     *
     * @param offer String with offered and wanted Ressources
     * @return Query with Identifier and offer
     */
    public static String createStringQueryPortChange(String offer) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " PORT " + offer;
    }

    /**
     * Creates BankChange-Query
     *
     * @param offer String with offered and wanted Ressources
     * @return Query with Identifier and offer
     */
    public static String createStringQueryBankChange(String offer) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " BANK " + offer;
    }

    /**
     * Creates BuyCard-Query
     *
     * @return Query with Identifier
     */
    public static String createStringQueryBuyCard() {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " BUYCARD";
    }

    /**
     * Creates PlayKnightCard-Query
     *
     * @return Query with Identifier
     */
    public static String createStringQueryPlayKnightCard() {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " PLAYKNIGHT";
    }

    /**
     * Creates PlayMonopolCard-Query
     *
     * @param res wanted Ressource
     * @return Query with Identifier and Ressource
     */
    public static String createStringQueryPlayMonopolCard(String res) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " PLAYMONOPOL " + res;
    }

    /**
     * Creates PlayInventionCard-Query
     *
     * @param res wanted Ressource
     * @return Query with Identifier and Ressource
     */
    public static String createStringQueryPlayInventionCard(String res) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " PLAYINVENTION " + res;
    }

    /**
     * Creates PlayBuildStreetCard-Query
     *
     * @param structure Asset-ID
     * @return Query with Identifier and Asset-ID
     */
    public static String createStringQueryPlayBuildStreetCard(String structure) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " PLAYBUILDSTREET " + structure;
    }

    /**
     * Creates BuildSettlement-Query
     *
     * @param structure Asset-ID
     * @return Query with Identifier and Asset-ID
     */
    public static String createStringQueryBuildSettlement(String structure) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " BUILDSETTLEMENT " + structure;
    }

    /**
     * Creates BuildRoad-Query
     *
     * @param structure Asset-ID
     * @return Query with Identifier and Asset-ID
     */
    public static String createStringQueryBuildRoad(String structure) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " BUILDROAD " + structure;
    }

    /**
     * Creates BuildCity-Query
     *
     * @param structure Asset-ID
     * @return Query with Identifier and Asset-ID
     */
    public static String createStringQueryBuildCity(String structure) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " BUILDCITY " + structure;
    }

    /**
     * Creates Dice-Query
     *
     * @param value Dice-Value
     * @return Query with Identifier and Value
     */
    public static String createStringRolledDice(String value) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " DICEVALUE " + value;
    }

    /**
     * Creates Next-Query
     *
     * @return Query with Identifier
     */
    public static String createStringQueryNext() {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " NEXT";
    }
}
