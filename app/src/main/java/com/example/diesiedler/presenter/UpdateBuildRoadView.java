package com.example.diesiedler.presenter;

import android.content.Context;
import android.graphics.Color;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Knot;
import com.richpath.RichPath;
import com.richpath.RichPathView;

import java.util.LinkedList;

public class UpdateBuildRoadView {

    public static int updateView(GameSession gs, RichPathView rpv) {
        LinkedList<Edge> possibleEdges = possibleEdges(gs);

        if (possibleEdges != null) {
            for (Edge e : possibleEdges
            ) {
                RichPath knot = rpv.findRichPathByName(e.getId());
                knot.setFillColor(Color.RED);
            }
            return 1;
        } else {
            return 0;
        }
    }

    private static LinkedList<Edge> possibleEdges(GameSession gs) {
        LinkedList<Edge> possibleEdges = new LinkedList<>();
        Player p = gs.getPlayer(gs.getCurrPlayer()); //TODO: Update


        if (p.getInventory().getWood() >= 1 && p.getInventory().getClay() >= 1 || p.getInventory().getRoads().size() < 2) {
            for (Edge e : gs.getGameboard().getEdges()
            ) {
                if (e.getPlayer() == null && (p.getInventory().getSettlements().contains(e.getOne()) || p.getInventory().getSettlements().contains(e.getTwo()) || p.getInventory().getRoadKnots().contains(e.getOne()) || p.getInventory().getRoadKnots().contains(e.getTwo()))) {
                    possibleEdges.add(e);
                }
            }
            return possibleEdges;
        }
        return null;
    }

}
