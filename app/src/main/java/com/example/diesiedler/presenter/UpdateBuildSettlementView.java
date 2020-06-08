package com.example.diesiedler.presenter;

import android.graphics.Color;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.gameboard.Knot;
import com.richpath.RichPath;
import com.richpath.RichPathView;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Alex Wirth
 * The purpose of this class, is to update the view that is loaded when a player chooses to build a settlement. The gameboard-view is refreshed, highlighting the
 * knots on which the player may build a settlement on.
 */
public class UpdateBuildSettlementView {

    private static final Logger logger = Logger.getLogger(UpdateBuildSettlementView.class.getName());

    private UpdateBuildSettlementView() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * The delivered list contains all knots a player can possibly settle. These knots are highlighted in red.
     *
     * @param gs
     * @param rpv
     * @return: -1 -> player cant build, because there is no road that leads to a knot that can be settled.
     * 0 -> player cant build, because he does not have have enough resources.
     * 1 -> player can build. Possible knots are highlighted.
     */
    public static void updateView(GameSession gs, RichPathView rpv) {
        logger.log(Level.INFO, "Called Update Activity");

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


    private static LinkedList possibleKnots(GameSession gs) {
        LinkedList<Knot> possibleKnots = new LinkedList<>();
        Player currentP = gs.getCurr();

        if (currentP.getInventory().getSettlements().size() >= 2 && !(hasResources(currentP))) {
            /**
             * Player does not have enough resources to build
             */
            logger.log(Level.INFO, "Player cant build Settlement");
            return null;
        } else if (currentP.getInventory().getSettlements().size() < 2) {
            /**
             * Case for the first two turns, where each player can build to settlements for free
             */
            logger.log(Level.INFO, "Player can build Settlement INIT");
            for (Knot k : gs.getGameboard().getKnots()
            ) {
                if (k.getPlayer() == null && neighbors(k, gs) == 0) {
                    possibleKnots.add(k);
                }
            }
        } else {
            /**
             * Player has enough resources.
             * Though the list can still be empty because player has no roads that lead to a available knot.
             */
            logger.log(Level.INFO, "Player can build Settlement");
            for (Knot k : gs.getGameboard().getKnots()
            ) {
                if (k.getPlayer() == null && currentP.getInventory().getRoadKnots().contains(k) && neighbors(k, gs) == 0) {
                    possibleKnots.add(k);
                }
            }
        }
        return possibleKnots;
    }


    private static boolean hasResources(Player p) {
        boolean hasResources = false;
        if (p.getInventory().getWood() >= 1 && p.getInventory().getWheat() >= 1 && p.getInventory().getClay() >= 1 && p.getInventory().getWool() >= 1) {
            hasResources = true;
        }
        return hasResources;
    }

    public static int status(GameSession gs) {
        LinkedList<Knot> possibleKnots = possibleKnots(gs);

        if (possibleKnots == null) {
            return 0;
        } else if (possibleKnots.isEmpty()) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Checks if a knot has neighboor-knots that are alreddy settled -> if yes, the knot cant be settled anymore.
     * @param k
     * @param gs
     * @return: number of neighboors for a knot -> if > 0 the knot cant be settled
     */
    private static int neighbors(Knot k, GameSession gs) {
        LinkedList<Knot> neighbor = new LinkedList<>();
        int row = k.getRow();
        int column = k.getColumn();
        int sum = row + column;

        if (sum % 2 == 0) {
            for (Knot knot : gs.getGameboard().getKnots()
            ) {
                if (knot.getRow() == row && knot.getColumn() == (column - 1)) {
                    neighbor.add(knot);
                }
                if (knot.getRow() == row && knot.getColumn() == (column + 1)) {
                    neighbor.add(knot);
                }
                if (knot.getRow() == (row + 1) && knot.getColumn() == column) {
                    neighbor.add(knot);
                }
            }
        } else {
            for (Knot knot : gs.getGameboard().getKnots()
            ) {
                if (knot.getRow() == row && knot.getColumn() == (column - 1)) {
                    neighbor.add(knot);
                }
                if (knot.getRow() == row && knot.getColumn() == (column + 1)) {
                    neighbor.add(knot);
                }
                if (knot.getRow() == (row - 1) && knot.getColumn() == column) {
                    neighbor.add(knot);
                }
            }
        }

        int settledNeighbors = 0;
        for (Knot n : neighbor
        ) {
            if (n.getPlayer() != null) {
                settledNeighbors++;
            }
        }
        return settledNeighbors;
    }

}
