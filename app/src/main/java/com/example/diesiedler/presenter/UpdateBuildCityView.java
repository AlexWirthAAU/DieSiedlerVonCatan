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


    private UpdateBuildCityView() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Highlights the knots in red.
     *
     * @param gs
     * @param rpv
     * @return: 0 -> player does not have enough resources | 1 -> player can build
     */
    public static void updateView(GameSession gs, RichPathView rpv) {
        LinkedList<Knot> possibleKnots = possibleKnots(gs);
        int status = status(gs);

        if (status == 1) {
            for (Knot k : possibleKnots
            ) {
                RichPath knot = rpv.findRichPathByName(k.getId());
                knot.setFillColor(Color.RED);
            }
        }
    }

    /**
     * This method checkes the current state of the gamesession to return a list of knots, the player can build on.
     * The knots in the returned list will be highlighted in red, in method "updateView".
     * @param gs
     * @return -> list of possible knots to build on
     */
    private static LinkedList<Knot> possibleKnots(GameSession gs) {
        LinkedList<Knot> possibleKnots = new LinkedList<>();
        Player p = gs.getCurr();


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

    public static int status(GameSession gs) {
        LinkedList<Knot> possibleKnots = possibleKnots(gs);

        if (possibleKnots == null) {
            return 0;
        } else {
            return 1;
        }
    }

}
