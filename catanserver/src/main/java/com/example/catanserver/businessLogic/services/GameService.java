package com.example.catanserver.businessLogic.services;

import com.example.catanserver.businessLogic.model.Colors;
import com.example.catanserver.businessLogic.model.GameImpl;
import com.example.catanserver.businessLogic.model.PlayerImpl;

import java.util.ArrayList;

/**
 * @author Christina Senger
 * <p>
 * Der GameService verfügt zusätzlich über Methoden, um eine Spiel
 * über seine Spieler zu finden. Außerdem kann er
 * Spieler und deren Farben einander zuordnen und er hat eine Methode,
 * um die Id, die Namen aller Nutzer und deren Farben als Liste von
 * Stringelementen zurückzugeben.
 */
public interface GameService extends Service<Integer, GameImpl> {

    GameImpl findGameByPlayers(ArrayList<String> players);

    PlayerImpl findPlayerInGameByColor(Colors color, GameImpl game);

    ArrayList<String> getList(Integer id);
}
