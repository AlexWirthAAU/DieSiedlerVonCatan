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

// TODO: komentieren
public class UpdateBuildSettlementView {

    private static final Logger logger = Logger.getLogger(UpdateBuildSettlementView.class.getName());

    public static int updateView(GameSession gs, RichPathView rpv) {
        logger.log(Level.INFO, "Called Update Activity");

        LinkedList<Knot> possibleKnots = possibleKnots(gs);

        if (possibleKnots != null) {
            if (possibleKnots.size() == 0) {
                return -1;
            }
            for (Knot k : possibleKnots
            ) {
                RichPath knot = rpv.findRichPathByName(k.getId());
                knot.setFillColor(Color.RED);
            }
            return 1;
        } else {
            return 0;
        }
    }


    private static LinkedList<Knot> possibleKnots(GameSession gs) {
        LinkedList<Knot> possibleKnots = new LinkedList<>();
        Player currentP = gs.getPlayer(gs.getCurrPlayer());

        if (currentP.getInventory().getSettlements().size() >= 2 && hasResources(currentP) == false) {
            //Player does not have enough resources to build
            logger.log(Level.INFO, "Player cant build Settlement");
            return null;

        } else if (currentP.getInventory().getSettlements().size() < 2) {
            logger.log(Level.INFO, "Player can build Settlement INIT");
            for (Knot k : gs.getGameboard().getKnots()
            ) {
                if (k.getPlayer() == null && neighbors(k, gs) == 0) {
                    possibleKnots.add(k);
                }
            }
            return possibleKnots;

        } else {
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