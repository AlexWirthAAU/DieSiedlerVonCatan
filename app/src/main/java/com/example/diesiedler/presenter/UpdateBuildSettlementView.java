package com.example.diesiedler.presenter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.gameboard.Knot;
import com.richpath.RichPath;
import com.richpath.RichPathView;

import java.util.LinkedList;


public class UpdateBuildSettlementView {

    public static int updateView(GameSession gs, RichPathView rpv) {
        LinkedList<Knot> possibleKnots = possibleKnots(gs);

        if (possibleKnots != null) {
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
        Player currentP = gs.getPlayer(1);

        if (currentP.getInventory().getSettlements().size() >= 2 && hasResources(currentP) == false) {
            //Player does not have enough resources to build
            return null;

        } else if (currentP.getInventory().getSettlements().size() < 2) {
            for (Knot k : gs.getGameboard().getKnots()
            ) {
                if (k.getPlayer() == null && neighbors(k, gs) == 0) {
                    possibleKnots.add(k);
                }
            }
            return possibleKnots;

        } else {
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
        Log.d("DEBUG", "NEIGHBORS: " + settledNeighbors);
        return settledNeighbors;
    }

}
