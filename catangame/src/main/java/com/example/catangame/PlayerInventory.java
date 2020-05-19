package com.example.catangame;

import com.example.catangame.devcards.BuildStreetCard;
import com.example.catangame.devcards.KnightCard;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Knot;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath
 * @author Alex Wirth
 * <p>
 * Representation of a Players Inventory
 */
public class PlayerInventory implements Serializable {

    private final int STARTVALUE = 1; // Every Player has 3 of every Ressource on Game-Start
    private final int victoryPointsSettlement = 2; // Victory Points for Building
    private int wool;
    private int wheat;
    private int ore;
    private int clay;
    private final int victoryPointsCity = 3;
    private int buildStreetCard;
    private int inventionCard;
    private int monopolCard;
    private int cards;
    public boolean canTrade; // states whether a Player can Trade
    public boolean hasPorts; // states whether a Player has (specific) Ports
    private int wood; // Values of Ressources
    private int knightCard; // Values of Cards
    public boolean canBankTrade;
    public boolean canPortTrade;
    private int victoryPoints; // Victory Points
    private boolean woodport;
    private boolean woolport;
    private boolean wheatport;
    private boolean oreport;
    private boolean clayport;
    private LinkedList<Knot> cities = new LinkedList<>(); // List of Players Structures
    private LinkedList<Knot> settlements = new LinkedList<>();
    private LinkedList<Edge> roads = new LinkedList<>();

    private LinkedList<Knot> roadKnots = new LinkedList<>(); // stores Knots, where the Player could add his next Road

    private LinkedList<KnightCard> knightCards = new LinkedList<>(); // List of Players Cards
    private LinkedList<BuildStreetCard> buildStreetCardLinkedList = new LinkedList<>();

    private int[] resValues = new int[5]; // Array of Ressource-Values
    private boolean[] portValues = new boolean[5]; // Array of Port-Values

    /**
     * Constructor - Set Values 0 or false
     */
    PlayerInventory() {
        this.wood = STARTVALUE;
        this.wool = STARTVALUE;
        this.wheat = STARTVALUE;
        this.ore = STARTVALUE;
        this.clay = STARTVALUE;

        this.buildStreetCard = STARTVALUE;
        this.inventionCard = STARTVALUE;
        this.knightCard = STARTVALUE;
        this.monopolCard = STARTVALUE;
        this.cards = 0;
        this.victoryPoints = 0;

        this.woodport = false;
        this.woolport = false;
        this.wheatport = false;
        this.oreport = false;
        this.clayport = false;

        this.resValues[0] = this.wood;
        this.resValues[1] = this.wool;
        this.resValues[2] = this.wheat;
        this.resValues[3] = this.ore;
        this.resValues[4] = this.clay;

        for (int i = 0; i < 5; i++) {
            this.portValues[i] = false;
        }
        this.hasPorts = false;

        this.canBankTrade = false;
        this.canPortTrade = false;
        this.canTrade = true;
    }


    /**
     * @return A String displaying all Resources, Cards and Victory Points
     */
    public String getAllSupplies() {
        return "Wood: " + this.wood + "\nWool: " + this.wool + "\nWheat: " + this.wool + "\nOre: " + this.ore
                + "\nClay: " + this.clay + "\nVictoty points: " + this.victoryPoints + "\nKnightCard: " + this.knightCard
                + "\nInventionCard: " + this.inventionCard + "\nBuildStreetCard: " + this.buildStreetCard
                + "\nMonopolCard: " + this.monopolCard + "\nVictoty points: " + this.victoryPoints;
    }

    /**
     * @return A String displaying all Resources
     */
    public String getAllRessources() {
        return "Wood: " + this.wood + "\nWool: " + this.wool + "\nWheat: " + this.wool + "\nOre: " + this.ore
                + "\nClay: " + this.clay;
    }

    // adding Resources
    public void addWood(int amount) {
        this.wood += amount;
        this.resValues[0] = this.wood;
        checkPlayerOptions();
    }

    public void addWool(int amount) {
        this.wool += amount;
        this.resValues[1] = this.wool;
        checkPlayerOptions();
    }

    public void addWheat(int amount) {
        this.wheat += amount;
        this.resValues[2] = this.wheat;
        checkPlayerOptions();
    }

    public void addOre(int amount) {
        this.ore += amount;
        this.resValues[3] = this.ore;
        checkPlayerOptions();
    }

    public void addClay(int amount) {
        this.clay += amount;
        this.resValues[4] = this.clay;
        checkPlayerOptions();
    }

    // removing Resources
    public void removeWood(int amount) {
        this.wood -= amount;
        this.resValues[0] = this.wood;
        checkPlayerOptions();
    }

    public int removeAllWood() {
        int num = this.wood;
        this.wood = 0;
        this.resValues[0] = this.wood;
        checkPlayerOptions();
        return num;
    }

    public void removeWool(int amount) {
        this.wool -= amount;
        this.resValues[1] = this.wool;
        checkPlayerOptions();
    }

    public int removeAllWool() {
        int num = this.wool;
        this.wool = 0;
        this.resValues[1] = this.wool;
        checkPlayerOptions();
        return num;
    }

    public void removeWheat(int amount) {
        this.wheat -= amount;
        this.resValues[2] = this.wheat;
        checkPlayerOptions();
    }

    public int removeAllWheat() {
        int num = this.wheat;
        this.wheat = 0;
        this.resValues[2] = this.wheat;
        checkPlayerOptions();
        return num;
    }

    public void removeOre(int amount) {
        this.ore -= amount;
        this.resValues[3] = this.ore;
        checkPlayerOptions();
    }

    public int removeAllOre() {
        int num = this.ore;
        this.ore = 0;
        this.resValues[3] = this.ore;
        checkPlayerOptions();
        return num;
    }

    public void removeClay(int amount) {
        this.clay -= amount;
        this.resValues[4] = this.clay;
        checkPlayerOptions();
    }

    public int removeAllClay() {
        int num = this.clay;
        this.clay = 0;
        this.resValues[4] = this.clay;
        checkPlayerOptions();
        return num;
    }

    // adding Structures
    public void addRoad(Edge roadEdge) {
        this.roads.add(roadEdge);
    }

    public void addSettlement(Knot settlementKnot) {
        this.settlements.add(settlementKnot);
        this.victoryPoints += this.victoryPointsSettlement;
    }

    public void addCity(Knot cityKnot) {
        this.cities.add(cityKnot);
        this.victoryPoints += this.victoryPointsCity;
    }

    public void addRoadKnots(Knot k) {
        this.roadKnots.add(k);
    }


    // removing Structures
    public void removeRoad(Edge roadEdge) {
        this.roads.remove(roadEdge);
    }

    public void removeSettlement(Knot settlementKnot) {
        this.settlements.remove(settlementKnot);
        this.victoryPoints -= this.victoryPointsSettlement;
    }

    public void removeCity(Knot cityKnot) {
        this.cities.remove(cityKnot);
        this.victoryPoints -= this.victoryPointsCity;
    }


    // add and remove Victory Points
    public void addVictoryPoints(int amount) {
        this.victoryPoints += amount;
    }

    public void removeVictoryPoints(int amount) {
        this.victoryPoints -= amount;
    }


    // add Cards
    public void addKnightCard(int amount) {
        this.knightCard += amount;
        this.cards += amount;
    }

    public void addBuildStreetCard(int amount) {
        this.buildStreetCard += amount;
        this.cards += amount;
    }

    public void addInventianCard(int amount) {
        this.inventionCard += amount;
        this.cards += amount;
    }

    public void addMonopolCard(int amount) {
        this.monopolCard += amount;
        this.cards += amount;
    }

    public void addVictoryCard() {
        this.victoryPoints++;
    }

    // remove cards
    public void removeKnightCard(int amount) {
        this.knightCard -= amount;
        this.cards -= amount;
    }

    public void removeBuildStreetCard(int amount) {
        this.buildStreetCard -= amount;
        this.cards -= amount;
    }

    public void removeInventianCard(int amount) {
        this.inventionCard -= amount;
        this.cards -= amount;
    }

    public void removeMonopolCard(int amount) {
        this.monopolCard -= amount;
        this.cards -= amount;
    }


    // Get and Set Amount of Resources
    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public int getWool() {
        return wool;
    }

    public void setWool(int wool) {
        this.wool = wool;
    }

    public int getWheat() {
        return wheat;
    }

    public void setWheat(int wheat) {
        this.wheat = wheat;
    }

    public int getOre() {
        return ore;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }

    public int getClay() {
        return clay;
    }

    public void setClay(int clay) {
        this.clay = clay;
    }

    // Get and Set Victory Points
    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }


    // Get and Set Cards
    public int getKnightCard() {
        return knightCard;
    }

    public void setKnightCard(int knightCard) {
        this.knightCard = knightCard;
    }

    public int getBuildStreetCard() {
        return buildStreetCard;
    }

    public void setBuildStreetCard(int buildStreetCard) {
        this.buildStreetCard = buildStreetCard;
    }

    public int getInventionCard() {
        return inventionCard;
    }

    public void setInventionCard(int inventionCard) {
        this.inventionCard = inventionCard;
    }

    public int getMonopolCard() {
        return monopolCard;
    }

    public void setMonopolCard(int monopolCard) {
        this.monopolCard = monopolCard;
    }

    public int getCards() {
        return this.cards;
    }

    public LinkedList<BuildStreetCard> getBuildStreetCardLinkedList() {
        return buildStreetCardLinkedList;
    }

    public void setBuildStreetCardLinkedList(LinkedList<BuildStreetCard> buildStreetCardLinkedList) {
        this.buildStreetCardLinkedList = buildStreetCardLinkedList;
    }


    //Get and Set Ports
    public boolean isWoodport() {
        return woodport;
    }

    public void setWoodport(boolean woodport) {
        this.woodport = woodport;
    }

    public boolean isWoolport() {
        return woolport;
    }

    public void setWoolport(boolean woolport) {
        this.woolport = woolport;
    }

    public boolean isWheatport() {
        return wheatport;
    }

    public void setWheatport(boolean wheatport) {
        this.wheatport = wheatport;
    }

    public boolean isOreport() {
        return oreport;
    }

    public void setOreport(boolean oreport) {
        this.oreport = oreport;
    }

    public boolean isClayport() {
        return clayport;
    }

    public void setClayport(boolean clayport) {
        this.clayport = clayport;
    }


    //Get Structures
    public LinkedList getSettlements() {
        return this.settlements;
    }

    public Collection getRoads() {
        return this.roads;
    }

    public LinkedList<Knot> getRoadKnots() {
        return roadKnots;
    }

    public LinkedList<Knot> getCities() {
        return cities;
    }


    public void checkPlayerOptions() {
        for (Integer i : resValues) {

            if (i >= 4) {
                canTrade = true;
                canBankTrade = true;
                System.out.println("can Bank Trade");
            }

            if ((isWoodport() || isWoolport() || isWheatport() || isOreport() || isClayport())) {
                hasPorts = true;
                System.out.println("has Ports");
            }

            if (i >= 3) {
                canTrade = true;
                canPortTrade = true;
                System.out.println("can Port Trade");
            }

            if (i >= 1) {
                canTrade = true;
                System.out.println("can Trade");
            }
        }
    }
}
