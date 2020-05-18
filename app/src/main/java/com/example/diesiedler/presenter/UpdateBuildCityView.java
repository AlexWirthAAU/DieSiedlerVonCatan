package com.example.diesiedler.presenter;

import android.graphics.Color;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.gameboard.Knot;
import com.richpath.RichPath;
import com.richpath.RichPathView;

import java.util.LinkedList;

/**
 * @author Alex Wirth
 * The purpose of this class, is to update the view that is loaded when a player chooses to build a city. The gameboard is refreshed, highlighting the
 * knots on which the player may build a city on.
 */
public class UpdateBuildCityView {

    /**
     * Highlights the knots in red.
     *
     * @param gs
     * @param rpv
     * @return: 0 -> player does not have enough resources | 1 -> player can build
     */
    public static int updateView(GameSession gs, RichPathView rpv) {
        LinkedList<Knot> possibleKnots = possibleKnots(gs);

        if (possibleKnots != null) {
            for (Knot k : possibleKnots
            ) {
                RichPath knotPath = rpv.findRichPathByName(k.getId());
                knotPath.setFillColor(Color.RED);
            }
            return 1;
        } else {
            //not enough resources
            return 0;
        }
    }

    /**
     * This method checkes the current state of the gamesession to return a list of knots, the player can build on.
     * The knots in the returned list will be highlighted in red, in method "updateView".
     * @param gs
     * @return -> list of possible knots to build on
     */
    public static LinkedList<Knot> possibleKnots(GameSession gs) {
        LinkedList<Knot> possibleKnots = new LinkedList<>();
        Player p = gs.getPlayer(gs.getCurrPlayer());


        if (p.getInventory().getWheat() >= 2 && p.getInventory().getOre() >= 3) {
            for (Knot k : gs.getSettlements()
            ) {
                if (k.getPlayer().equals(p)) {
                    possibleKnots.add(k);
                }
            }
            return possibleKnots;
        } else {
            return null;
        }
    }

}
