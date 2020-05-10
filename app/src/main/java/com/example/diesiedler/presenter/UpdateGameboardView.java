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
                Colors c = k.getPlayer().getColor();
                int color = 0;
                switch (c) {
                    case GREEN:
                        color = Color.parseColor("#6AE626");
                        break;
                    case LIGHTBLUE:
                        color = Color.parseColor("#07B9FF");
                        break;
                    case ORANGE:
                        color = Color.parseColor("#FF8E07");
                        break;
                    case VIOLETT:
                        color = Color.parseColor("#A30FFF");
                        break;
                    default:
                        break;
                }
                richPath.setFillColor(color);
            }

        }

        for (Edge e : g.getEdges()
        ) {
            RichPath richPath = richPathView.findRichPathByName(e.getId());
            if (e.getPlayer() != null) {
                Colors c = e.getPlayer().getColor();
                int color = 0;
                switch (c) {
                    case GREEN:
                        color = Color.parseColor("#6AE626");
                        break;
                    case LIGHTBLUE:
                        color = Color.parseColor("#07B9FF");
                        break;
                    case ORANGE:
                        color = Color.parseColor("#FF8E07");
                        break;
                    case VIOLETT:
                        color = Color.parseColor("#A30FFF");
                        break;
                    default:
                        break;
                }
                richPath.setFillColor(color);
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
