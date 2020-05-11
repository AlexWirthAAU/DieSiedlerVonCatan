package com.example.catangame;

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
    private int victoryPoints;
    public int victoryPointsSettlement = 2;
    public int victoryPointsCity = 3;

    private LinkedList<Knot> cities = new LinkedList<>();
    private LinkedList<Knot> settlements = new LinkedList<>();
    private LinkedList<Edge> roads = new LinkedList<>();

    //Stores Knots, where the Player could add his next road
    private LinkedList<Knot> roadKnots = new LinkedList<>();

    // private ArrayList<Entwicklungskarte> entwicklungskarten = new ArrayList<>();

    public PlayerInventory() {
        this.wood = STARTVALUE;
        this.wool = STARTVALUE;
        this.wheat = STARTVALUE;
        this.ore = STARTVALUE;
        this.clay = STARTVALUE;
        this.victoryPoints = 0;
    }

    // displaying all resources and victory points
    public String getAllSupplies() {
        //TODO adding Entwicklungskarten (later)
        return "Wood: " + this.wood + "\nWool: " + this.wool + "\nWheat: " + this.wool + "\nOre: " + this.ore
                + "\nClay: " + this.clay + "\nVictoty points: " + this.victoryPoints;
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

    // get structures
    public LinkedList<Knot> getCities() {
        return cities;
    }

    public LinkedList<Knot> getSettlements() {
        return settlements;
    }

    public LinkedList<Edge> getRoads() {
        return roads;
    }

    // adding resources
    public void addWood(int amount) {
        this.wood += amount;
    }

    public void addWool(int amount) {
        this.wool += amount;
    }

    public void addWheat(int amount) {
        this.wheat += amount;
    }

    public void addOre(int amount) {
        this.ore += amount;
    }

    public void addClay(int amount) {
        this.clay += amount;
    }

    public void addVictoryPoints(int amount) {
        this.victoryPoints += amount;
    }


    // removing resources
    public void removeWood(int amount) {
        this.wood -= amount;
    }

    public void removeWool(int amount) {
        this.wool -= amount;
    }

    public void removeWheat(int amount) {
        this.wheat -= amount;
    }

    public void removeOre(int amount) {
        this.ore -= amount;
    }

    public void removeClay(int amount) {
        this.clay -= amount;
    }

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
}