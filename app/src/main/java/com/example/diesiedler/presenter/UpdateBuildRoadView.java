package com.example.diesiedler.presenter;

import android.graphics.Color;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.gameboard.Edge;
import com.richpath.RichPath;
import com.richpath.RichPathView;

import java.util.LinkedList;

/**
 * @author Alex Wirth
 * The purpose of this class, is to update the view that is loaded when a player chooses to build a road. The gameboard is refreshed, highlighting the
 * edges on which the player may build a road on.
 */

public class UpdateBuildRoadView {

    private UpdateBuildRoadView() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * The delivered list contains all edges a player can possibly build a road on. These edges are highlighted in red.
     *
     * @param gs
     * @param rpv
     * @param card
     * @return: 0 -> Player does not have enough resources | 1 -> player can build road
     */
    public static int updateView(GameSession gs, RichPathView rpv, String card) {
        LinkedList<Edge> possibleEdges = possibleEdges(gs, card);

        if (possibleEdges != null) {
            for (Edge e : possibleEdges
            ) {
                RichPath edge = rpv.findRichPathByName(e.getId());
                edge.setFillColor(Color.RED);
            }
            return 1;
        } else {
            return 0;
        }
    }

    public static LinkedList<Edge> possibleEdges(GameSession gs, String card) {
        LinkedList<Edge> possibleEdges = new LinkedList<>();
        Player p = gs.getPlayer(ClientData.userId);


        /**
         * Checks if the player has enough resources.
         * If the total amount of roads he has, it means it is still the beginning of the game and the player can build for free.
         */

        if (p.getInventory().getRoads().isEmpty()) {
            for (Edge e : gs.getGameboard().getEdges()) {
                if (e.getPlayer() == null && e.getOne().equals(p.getInventory().getSettlements().get(0)) || e.getTwo().equals(p.getInventory().getSettlements().get(0))) {
                    possibleEdges.add(e);
                }
            }
            return possibleEdges;
        }


        if (p.getInventory().getRoads().size() == 1) {
            for (Edge e : gs.getGameboard().getEdges()) {
                if (e.getPlayer() == null && e.getOne().equals(p.getInventory().getSettlements().get(1)) || e.getTwo().equals(p.getInventory().getSettlements().get(1))) {
                    possibleEdges.add(e);
                }
            }
            return possibleEdges;
        }

        if (p.getInventory().getWood() >= 1 && p.getInventory().getClay() >= 1 || card != null) {
            for (Edge e : gs.getGameboard().getEdges()) {
                /**
                 * A road can either be built next to a settlement or at the end of a road he already owns.
                 */
                if (e.getPlayer() == null && (p.getInventory().getSettlements().contains(e.getOne()) || p.getInventory().getSettlements().contains(e.getTwo()) || p.getInventory().getRoadKnots().contains(e.getOne()) || p.getInventory().getRoadKnots().contains(e.getTwo()))) {
                    possibleEdges.add(e);
                }
            }
            return possibleEdges;
        }

        return null;
    }

    public static int status(GameSession gs, String card) {
        LinkedList<Edge> possibleEdges = possibleEdges(gs, card);

        if (possibleEdges == null) {
            return 0;
        } else {
            return 1;
        }
    }
}
