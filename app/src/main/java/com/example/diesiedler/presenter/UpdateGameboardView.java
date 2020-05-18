package com.example.diesiedler.presenter;

import android.graphics.Color;

import com.example.catangame.Colors;
import com.example.catangame.GameSession;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Gameboard;
import com.example.catangame.gameboard.Knot;
import com.example.catangame.gameboard.Tile;
import com.richpath.RichPath;
import com.richpath.RichPathView;

//TODO: Handle Colors!

/**
 * @author Alex Wirth
 * This class updates the gameboard depending on the state of the current gamesession object.
 * Each asset of the gameborad is checked for an owner. If a player own an asset, it will be colored in the player's game-appereance color.
 * Also the thief's position is checked to view him on the correct tile.
 */
public class UpdateGameboardView {

    public static void updateView(GameSession gs, RichPathView rpv) {
        Gameboard g = gs.getGameboard();
        RichPathView richPathView = rpv;

        for (Knot k : g.getKnots()
        ) {
            String city = "city_" + k.getRow() + "_" + k.getColumn();

            RichPath knotPath = richPathView.findRichPathByName(k.getId());
            RichPath cityPath = richPathView.findRichPathByName(city);

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
                knotPath.setFillColor(color);
                if (k.hasCity()) {
                    cityPath.setFillAlpha(1);
                }
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
