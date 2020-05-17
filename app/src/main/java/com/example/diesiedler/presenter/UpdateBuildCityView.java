package com.example.diesiedler.presenter;

import android.graphics.Color;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.gameboard.Knot;
import com.richpath.RichPath;
import com.richpath.RichPathView;

import java.util.LinkedList;

// TODO: komentieren
public class UpdateBuildCityView {

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
