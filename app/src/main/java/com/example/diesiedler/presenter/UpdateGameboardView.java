package com.example.diesiedler.presenter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.example.catangame.Colors;
import com.example.catangame.GameSession;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Gameboard;
import com.example.catangame.gameboard.Knot;
import com.example.catangame.gameboard.Tile;
import com.richpath.RichPath;
import com.richpath.RichPathView;

//TODO: Handle Colors!

public class UpdateGameboardView {

    public static void updateView(GameSession gs, RichPathView rpv) {
        Gameboard g = gs.getGameboard();
        RichPathView richPathView = rpv;

        for (Knot k : g.getKnots()
        ) {
            RichPath richPath = richPathView.findRichPathByName(k.getId());
            if (k.getPlayer() != null) {
                //Log.d("DEBUG", "Color is: "+k.getPlayer().getColor().toString());
                //Log.d("DEBUG", "Color is: "+k.getPlayer().getColor().hashCode());
                Colors c = k.getPlayer().getColor();
                Log.d("DEBUG", c.toString());
                int color = 0;
                if (c.toString().equals("GREEN")) {
                    color = Color.GREEN;
                } else if (k.getPlayer().getColor().toString().equals("LIGHTBLUE")) {
                    color = Color.BLUE;
                }
                richPath.setFillColor(color);
            }

        }

        for (Edge e : g.getEdges()
        ) {
            RichPath richPath = richPathView.findRichPathByName(e.getId());
            if (e.getPlayer() != null) {
                richPath.setFillColor(Color.GREEN);
            }

        }

        for (Tile t : g.getTiles()
        ) {
            RichPath richPath = richPathView.findRichPathByName("thief_" + t.getId());
            if (t.isThief() == true) {
                richPath.setFillAlpha(1);
            }
        }
    }
}
