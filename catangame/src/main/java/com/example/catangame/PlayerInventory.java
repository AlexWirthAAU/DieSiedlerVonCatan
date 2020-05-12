package com.example.catangame;

import com.example.catangame.devcards.BuildStreetCard;
import com.example.catangame.devcards.InventionCard;
import com.example.catangame.devcards.KnightCard;
import com.example.catangame.devcards.MonopolCard;

import java.io.Serializable;
import com.example.catangame.gameboard.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayerInventory implements Serializable {

    private final int STARTVALUE = 3;
    private int wood;
    private int wool;
    private int wheat;
    private int ore;
    private int clay;

    private int knightCard;
    private int buildStreetCard;
    private int inventionCard;
    private int monopolCard;

    private int victoryPoints;
    private int victoryPointsSettlement = 2;
    private int victoryPointsCity = 3;

    public boolean canTrade;
    public boolean canBankTrade;
    public boolean canPortTrade;
    private boolean woodport;
    private boolean woolport;
    private boolean wheatport;
    private boolean oreport;
    private boolean clayport;

    private LinkedList<Knot> cities = new LinkedList<>();
    private LinkedList<Knot> settlements = new LinkedList<>();
    private LinkedList<Edge> roads = new LinkedList<>();
    private LinkedList<KnightCard> knightCards = new LinkedList<>();
    private LinkedList<BuildStreetCard> buildStreetCards = new LinkedList<>();
    private LinkedList<InventionCard> inventionCards = new LinkedList<>();
    private LinkedList<MonopolCard> monopolCards = new LinkedList<>();
    private int[] resValues = new int[5];
    private boolean[] portValues = new boolean[5];

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

        this.canBankTrade = false;
        this.canPortTrade = false;
        this.canTrade = false;
    }


    // displaying all resources and victory points
    public String getAllSupplies() {
        return "Wood: " + this.wood + "\nWool: " + this.wool + "\nWheat: " + this.wool + "\nOre: " + this.ore
                + "\nClay: " + this.clay + "\nVictoty points: " + this.victoryPoints;
    }

    public String getAllRessources() {
        return "Wood: " + this.wood + "\nWool: " + this.wool + "\nWheat: " + this.wool + "\nOre: " + this.ore
                + "\nClay: " + this.clay;
    }

    // adding structures
    public void addRoad(Edge roadEdge){
        this.roads.add(roadEdge);
    }
    public void addSettlement(Knot settlementKnot){
        this.settlements.add(settlementKnot);
        this.victoryPoints += this.victoryPointsSettlement;
    }

    public void addCity(Knot cityKnot){
        this.cities.add(cityKnot);
        this.victoryPoints += this.victoryPointsCity;
    }

    // removing structures
    public void removeRoad(Edge roadEdge){
        this.roads.remove(roadEdge);
    }

    public void removeSettlement(Knot settlementKnot){
        this.settlements.remove(settlementKnot);
        this.victoryPoints -= this.victoryPointsSettlement;
    }

    public void removeCity(Knot cityKnot){
        this.cities.remove(cityKnot);
        this.victoryPoints -= this.victoryPointsCity;
    }

    // adding resources
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

    // add Cards
    public void addVictoryPoints(int amount) {
        this.victoryPoints += amount;
    }

    public void addKnightCard(int amount) {
        this.knightCard += amount;
    }

    public void addBuildStreetCard(int amount) {
        this.buildStreetCard += amount;
    }

    public void addInventianCard(int amount) {
        this.inventionCard += amount;
    }

    public void addMonopolCard(int amount) {
        this.monopolCard += amount;
    }

    public void addVictoryCard() {
        this.victoryPoints++;
    }


    // removing resources
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

    // remove Cards
    public void removeVictoryPoints(int amount) {
        this.victoryPoints -= amount;
    }

    //getting the amount of resources
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

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    /*******/

    public LinkedList<Knot> getRoadKnots() {
        return roadKnots;
    }

    public void addRoadKnots(Knot k) {
        this.roadKnots.add(k);
    }

    public int getSTARTVALUE() {
        return STARTVALUE;
    }

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

    private void checkPlayerOptions() {
        for (Integer i : resValues) {

            if (i >= 4) {
                canTrade = true;
                canBankTrade = true;
            }

            if (i >= 3 && (isWoodport() || isWoolport() || isWheatport() || isOreport() || isClayport())) {
                canTrade = true;
                canPortTrade = true;
            }

            if (i >= 1) {
                canTrade = true;
            }
        }
    }
}
