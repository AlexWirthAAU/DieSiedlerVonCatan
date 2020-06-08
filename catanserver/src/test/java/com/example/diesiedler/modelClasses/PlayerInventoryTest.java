package com.example.diesiedler.modelClasses;

import com.example.catangame.PlayerInventory;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerInventoryTest {

    private PlayerInventory playerInventory;

    @Before
    public void setUp() {
        playerInventory = new PlayerInventory();
        playerInventory.setWood(1);
        playerInventory.setWool(1);
        playerInventory.setWheat(1);
        playerInventory.setOre(1);
        playerInventory.setClay(1);
    }

    @After
    public void tearDown() {
        playerInventory = null;
    }

    @Test
    public void getAllSupplies() {
        Assert.assertEquals("Wood: " + 1 + "\nWool: " + 1 + "\nWheat: " + 1 + "\nOre: " + 1
                + "\nClay: " + 1 + "\nVictory points: " + 0 + "\nKnightCard: " + 0
                + "\nInventionCard: " + 0 + "\nBuildStreetCard: " + 0
                + "\nMonopolCard: " + 0, playerInventory.getAllSupplies());
    }

    @Test
    public void getAllRessources() {
        Assert.assertEquals("Wood: " + 1 + "\nWool: " + 1 + "\nWheat: " + 1 + "\nOre: " + 1
                + "\nClay: " + 1, playerInventory.getAllResources());
    }

    @Test
    public void addWood() {
        playerInventory.addWood(1);
        Assert.assertEquals(2, playerInventory.getWood());
        Assert.assertEquals(2, playerInventory.getResValues()[0]);
    }

    @Test
    public void addWool() {
        playerInventory.addWool(1);
        Assert.assertEquals(2, playerInventory.getWool());
        Assert.assertEquals(2, playerInventory.getResValues()[1]);
    }

    @Test
    public void addMoreWool() {
        playerInventory.addWool(3);
        Assert.assertEquals(4, playerInventory.getWool());
        Assert.assertEquals(4, playerInventory.getResValues()[1]);
        Assert.assertTrue(playerInventory.isCanBankTrade());
    }

    @Test
    public void addWheat() {
        playerInventory.addWheat(1);
        Assert.assertEquals(2, playerInventory.getWheat());
        Assert.assertEquals(2, playerInventory.getResValues()[2]);
    }

    @Test
    public void addOre() {
        playerInventory.addOre(1);
        Assert.assertEquals(2, playerInventory.getOre());
        Assert.assertEquals(2, playerInventory.getResValues()[3]);
    }

    @Test
    public void addClay() {
        playerInventory.addClay(1);
        Assert.assertEquals(2, playerInventory.getClay());
        Assert.assertEquals(2, playerInventory.getResValues()[4]);
    }

    @Test
    public void removeWood() {
        playerInventory.removeWood(1);
        Assert.assertEquals(0, playerInventory.getWood());
        Assert.assertEquals(0, playerInventory.getResValues()[0]);
    }

    @Test
    public void removeAllWood() {
        Assert.assertEquals(1, playerInventory.removeAllWood());
        Assert.assertEquals(0, playerInventory.getWood());
        Assert.assertEquals(0, playerInventory.getResValues()[0]);
    }

    @Test
    public void removeWool() {
        playerInventory.removeWool(1);
        Assert.assertEquals(0, playerInventory.getWool());
        Assert.assertEquals(0, playerInventory.getResValues()[1]);
    }

    @Test
    public void removeAllWool() {
        Assert.assertEquals(1, playerInventory.removeAllWool());
        Assert.assertEquals(0, playerInventory.getWool());
        Assert.assertEquals(0, playerInventory.getResValues()[1]);
    }

    @Test
    public void removeWheat() {
        playerInventory.removeWheat(1);
        Assert.assertEquals(0, playerInventory.getWheat());
        Assert.assertEquals(0, playerInventory.getResValues()[2]);
    }

    @Test
    public void removeAllWheat() {
        Assert.assertEquals(1, playerInventory.removeAllWheat());
        Assert.assertEquals(0, playerInventory.getWheat());
        Assert.assertEquals(0, playerInventory.getResValues()[2]);
    }

    @Test
    public void removeOre() {
        playerInventory.removeOre(1);
        Assert.assertEquals(0, playerInventory.getOre());
        Assert.assertEquals(0, playerInventory.getResValues()[3]);
    }

    @Test
    public void removeAllOre() {
        Assert.assertEquals(1, playerInventory.removeAllOre());
        Assert.assertEquals(0, playerInventory.getOre());
        Assert.assertEquals(0, playerInventory.getResValues()[3]);
    }

    @Test
    public void removeClay() {
        playerInventory.removeClay(1);
        Assert.assertEquals(0, playerInventory.getClay());
        Assert.assertEquals(0, playerInventory.getResValues()[4]);
    }

    @Test
    public void removeAllClay() {
        Assert.assertEquals(1, playerInventory.removeAllClay());
        Assert.assertEquals(0, playerInventory.getClay());
        Assert.assertEquals(0, playerInventory.getResValues()[4]);
    }

    @Test
    public void addVictoryPoints() {
        playerInventory.addVictoryPoints(1);
        Assert.assertEquals(1, playerInventory.getVictoryPoints());
    }

    @Test
    public void removeVictoryPoints() {
        playerInventory.addVictoryPoints(2);
        playerInventory.removeVictoryPoints(1);
        Assert.assertEquals(1, playerInventory.getVictoryPoints());
    }

    @Test
    public void addMonopolCard() {
        playerInventory.addMonopolCard(1);
        Assert.assertEquals(1, playerInventory.getMonopolCard());
        Assert.assertEquals(1, playerInventory.getCards());
    }

    @Test
    public void addInventionCard() {
        playerInventory.addInventianCard(1);
        Assert.assertEquals(1, playerInventory.getInventionCard());
        Assert.assertEquals(1, playerInventory.getCards());
    }

    @Test
    public void addBuildStreetCard() {
        playerInventory.addBuildStreetCard(1);
        Assert.assertEquals(1, playerInventory.getBuildStreetCard());
        Assert.assertEquals(1, playerInventory.getCards());
        Assert.assertEquals(1, playerInventory.getBuildStreetCardLinkedList().size());
    }

    @Test
    public void addVictoryCard() {
        playerInventory.addVictoryCard();
        Assert.assertEquals(1, playerInventory.getVictoryPoints());
    }

    @Test
    public void removeKnightCard() {
        playerInventory.addKnightCard(2);
        playerInventory.removeKnightCard(1);
        Assert.assertEquals(1, playerInventory.getKnightCard());
        Assert.assertEquals(1, playerInventory.getCards());
    }

    @Test
    public void removeMonopolCard() {
        playerInventory.addMonopolCard(2);
        playerInventory.removeMonopolCard(1);
        Assert.assertEquals(1, playerInventory.getMonopolCard());
        Assert.assertEquals(1, playerInventory.getCards());
    }

    @Test
    public void removeInventionCard() {
        playerInventory.addInventianCard(2);
        playerInventory.removeInventianCard(1);
        Assert.assertEquals(1, playerInventory.getInventionCard());
        Assert.assertEquals(1, playerInventory.getCards());
    }

    @Test
    public void removeBuildStreetCard() {
        playerInventory.addBuildStreetCard(1);
        playerInventory.removeBuildStreetCard(1);
        Assert.assertEquals(0, playerInventory.getBuildStreetCard());
        Assert.assertEquals(0, playerInventory.getCards());
        Assert.assertEquals(0, playerInventory.getBuildStreetCardLinkedList().size());
    }

    @Test
    public void hasNoPortOption() {
        playerInventory.checkPlayerOptions();
        Assert.assertFalse(playerInventory.isHasPorts());
    }

    @Test
    public void hasPortOption() {
        playerInventory.setWoolport(true);
        playerInventory.checkPlayerOptions();
        Assert.assertTrue(playerInventory.isHasPorts());
    }

    @Test
    public void canTradeOption() {
        playerInventory.addWood(1);
        playerInventory.checkPlayerOptions(0);
        Assert.assertTrue(playerInventory.isCanTrade());
    }

    @Test
    public void cannotBankTradeOption() {
        playerInventory.checkPlayerOptions(0);
        Assert.assertFalse(playerInventory.isCanBankTrade());
    }

    @Test
    public void canBankTradeOption() {
        playerInventory.addWood(4);
        Assert.assertTrue(playerInventory.isCanBankTrade());
    }

    @Test
    public void cannotMoreBankTrade() {
        playerInventory.addWool(3);
        playerInventory.removeWool(1);
        Assert.assertEquals(3, playerInventory.getWool());
        Assert.assertEquals(3, playerInventory.getResValues()[1]);
        Assert.assertFalse(playerInventory.isCanBankTrade());
    }

    @Test
    public void cannotPortTradeOption() {
        playerInventory.checkPlayerOptions(0);
        Assert.assertFalse(playerInventory.isCanBankTrade());
    }

    @Test
    public void canPortTradeOption() {
        playerInventory.addWood(3);
        playerInventory.checkPlayerOptions(0);
        Assert.assertTrue(playerInventory.isCanPortTrade());
    }

}
