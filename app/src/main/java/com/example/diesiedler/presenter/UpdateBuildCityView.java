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

public class UpdateBuildCityView {

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
        Player p = gs.getPlayer(1); //TODO: Update


        if (p.getInventory().getWheat() >= 2 && p.getInventory().getOre() >= 3) {
            for (Knot k : gs.getSettlements()
            ) {
                if (k.getPlayer().equals(p)) {
                    possibleKnots.add(k);
                }
            }
            return possibleKnots;
        }
        return null;
    }

}
