package com.example.catangame;

import com.example.catangame.devcards.BuildStreetCard;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Knot;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath
 * @author Alex Wirth
 * <p>
 * Representation of a Players Inventory
 */
public class PlayerInventory implements Serializable {

    private static final int STARTVALUE = 0; // Every Player has 3 of every Resource on Game-Start
    private int wool;
    private int wheat;
    private int ore;
    private int clay;
    private int buildStreetCard;
    private int inventionCard;
    private int monopolCard;
    private int cards;
    private boolean canTrade; // states whether a Player can Trade
    private boolean hasPorts; // states whether a Player has (specific) Ports
    private int wood; // Values of Resources
    private int knightCard; // Values of Cards
    private boolean canBankTrade;
    private boolean canPortTrade;
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

    private LinkedList<BuildStreetCard> buildStreetCardLinkedList = new LinkedList<>(); // List of BuildStreetCards

    private int[] resValues = new int[5]; // Array of Resource-Values

    /**
     * Constructor - Set Values 0 or false
     */
    public PlayerInventory() {
        this.wood = STARTVALUE;
        this.wool = STARTVALUE;
        this.wheat = STARTVALUE;
        this.ore = STARTVALUE;
        this.clay = STARTVALUE;

        this.buildStreetCard = 0;
        this.inventionCard = 0;
        this.knightCard = 0;
        this.monopolCard = 0;
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

        this.hasPorts = false;

        this.canBankTrade = false;
        this.canPortTrade = false;
        this.canTrade = true;
    }


    /**
     * @return A String displaying all Resources, Cards and Victory Points
     */
    public String getAllSupplies() {
        return "Wood: " + this.wood + "\nWool: " + this.wool + "\nWheat: " + this.wheat + "\nOre: " + this.ore
                + "\nClay: " + this.clay + "\nVictory points: " + this.victoryPoints + "\nKnightCard: " + this.knightCard
                + "\nInventionCard: " + this.inventionCard + "\nBuildStreetCard: " + this.buildStreetCard
                + "\nMonopolCard: " + this.monopolCard;
    }

    /**
     * @return A String displaying all Resources
     */
    public String getAllResources() {
        return "Wood: " + this.wood + "\nWool: " + this.wool + "\nWheat: " + this.wheat + "\nOre: " + this.ore
                + "\nClay: " + this.clay;
    }

    // adding Resources
    public void addWood(int amount) {
        this.wood += amount;
        this.resValues[0] = this.wood;
        checkPlayerOptions(0);
    }

    public void addWool(int amount) {
        this.wool += amount;
        this.resValues[1] = this.wool;
        checkPlayerOptions(1);
    }

    public void addWheat(int amount) {
        this.wheat += amount;
        this.resValues[2] = this.wheat;
        checkPlayerOptions(2);
    }

    public void addOre(int amount) {
        this.ore += amount;
        this.resValues[3] = this.ore;
        checkPlayerOptions(3);
    }

    public void addClay(int amount) {
        this.clay += amount;
        this.resValues[4] = this.clay;
        checkPlayerOptions(4);
    }

    // removing Resources
    public void removeWood(int amount) {
        this.wood -= amount;
        this.resValues[0] = this.wood;
        checkPlayerOptions(0);
    }

    public int removeAllWood() {
        int num = this.wood;
        this.wood = 0;
        this.resValues[0] = this.wood;
        checkPlayerOptions(0);
        return num;
    }

    public void removeWool(int amount) {
        this.wool -= amount;
        this.resValues[1] = this.wool;
        checkPlayerOptions(1);
    }

    public int removeAllWool() {
        int num = this.wool;
        this.wool = 0;
        this.resValues[1] = this.wool;
        checkPlayerOptions(1);
        return num;
    }

    public void removeWheat(int amount) {
        this.wheat -= amount;
        this.resValues[2] = this.wheat;
        checkPlayerOptions(2);
    }

    public int removeAllWheat() {
        int num = this.wheat;
        this.wheat = 0;
        this.resValues[2] = this.wheat;
        checkPlayerOptions(2);
        return num;
    }

    public void removeOre(int amount) {
        this.ore -= amount;
        this.resValues[3] = this.ore;
        checkPlayerOptions(3);
    }

    public int removeAllOre() {
        int num = this.ore;
        this.ore = 0;
        this.resValues[3] = this.ore;
        checkPlayerOptions(3);
        return num;
    }

    public void removeClay(int amount) {
        this.clay -= amount;
        this.resValues[4] = this.clay;
        checkPlayerOptions(4);
    }

    public int removeAllClay() {
        int num = this.clay;
        this.clay = 0;
        this.resValues[4] = this.clay;
        checkPlayerOptions(4);
        return num;
    }

    // adding Structures
    public void addRoad(Edge roadEdge) {
        this.roads.add(roadEdge);
    }

    public void addSettlement(Knot settlementKnot) {
        this.settlements.add(settlementKnot);
        // Victory Points for Building
        int victoryPointsSettlement = 2;
        this.victoryPoints += victoryPointsSettlement;
    }

    public void addCity(Knot cityKnot) {
        this.cities.add(cityKnot);
        int victoryPointsCity = 3;
        this.victoryPoints += victoryPointsCity;
    }

    public void addRoadKnots(Knot k) {
        this.roadKnots.add(k);
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
        this.getBuildStreetCardLinkedList().add(new BuildStreetCard());
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
        this.getBuildStreetCardLinkedList().remove(this.getBuildStreetCardLinkedList().get(0));
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

    public int[] getResValues() {
        return this.resValues;
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

    public int getInventionCard() {
        return inventionCard;
    }

    public int getMonopolCard() {
        return monopolCard;
    }

    public int getCards() {
        return this.cards;
    }

    public List<BuildStreetCard> getBuildStreetCardLinkedList() {
        return buildStreetCardLinkedList;
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

    public boolean isHasPorts() {
        return hasPorts;
    }

    public void setHasPorts(boolean hasPorts) {
        this.hasPorts = hasPorts;
    }


    //Get Structures
    public List<Knot> getSettlements() {
        return this.settlements;
    }

    public List<Edge> getRoads() {
        return this.roads;
    }

    public List<Knot> getRoadKnots() {
        return roadKnots;
    }

    public List<Knot> getCities() {
        return cities;
    }


    //Get Player Options
    public boolean isCanTrade() {
        return this.canTrade;
    }

    public boolean isCanBankTrade() {
        return this.canBankTrade;
    }

    public boolean isCanPortTrade() {
        return this.canPortTrade;
    }


    //Set Player Options
    public void setCanBankTrade(boolean canBankTrade) {
        this.canBankTrade = canBankTrade;
    }


    public void checkPlayerOptions(int index) {
        int i = resValues[index];

        if (i >= 4) {
            canTrade = true;
            canBankTrade = true;
        } else {
            canBankTrade = false;
        }

        hasPorts = (isWoodport() || isWoolport() || isWheatport() || isOreport() || isClayport());

        if (i >= 3) {
            canTrade = true;
            canPortTrade = true;
        } else {
            canPortTrade = false;
        }

        canTrade = i >= 1;
    }

    public void checkPlayerOptions() {
        hasPorts = (isWoodport() || isWoolport() || isWheatport() || isOreport() || isClayport());
    }
}
