package com.example.catanserver.businessLogic.services;

import com.example.catanserver.businessLogic.model.player.PlayerImpl;

import java.util.ArrayList;

/**
 * @author Christina Senger
 * <p>
 * Der PlayerService verfügt zusätzlich übe Methoden, um einen Player
 * über seinen Namen oder seinen Host zu finden. Außerdem
 * hat er eine Methode, um die Namen aller Spieler als Liste von
 * Stringelementen zurückzugeben.
 */
public interface PlayerService extends Service<Integer, PlayerImpl> {

    PlayerImpl findPlayerByUsername(String name);

    PlayerImpl findPlayerByHost(String host);

    ArrayList<String> getNameList();
}
