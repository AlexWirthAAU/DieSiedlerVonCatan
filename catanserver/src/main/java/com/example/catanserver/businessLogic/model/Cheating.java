package com.example.catanserver.businessLogic.model;

import com.example.catangame.GameSession;
import com.example.catangame.Grab;
import com.example.catangame.Player;
import com.example.catanserver.User;

import java.util.LinkedList;

public class Cheating {

    /**
     * Tries to create a Grab for the specified players in the GameSession.
     * This is only possible, if the Player to be stolen from does not have an open Grab yet.
     * @param game The GameSession in which to operate
     * @param grabberId The UserId of the cheating Player
     * @param grabbedId The UserId of the Player to be stolen from
     * @param resource A String containing the Resource Identifier
     * @return Return true if a the Grab was created, false if not
     */
    public static boolean requestGrab(GameSession game, int grabberId, int grabbedId, String resource){
        Player grabber = game.getPlayer(grabberId);
        Player grabbed = game.getPlayer(grabbedId);
        if(grabber != null && grabbed != null) {
            for (Grab grab : game.getGrabs()) {
                if (grab.getGrabbed().equals(grabbed)) {
                    return false;
                }
            }
            game.addGrab(new Grab(grabber,grabbed,resource));
            return true;
        }
        return false;
    }

    /**
     * Processes the Grabs for a specific Player (to be stolen from). Is called after each turn.
     * @param game The GameSession in which to operate
     * @param grabbed The User to be stolen from
     */
    public static void processGrabs(GameSession game, User grabbed){
        LinkedList<Grab> removeGrabs = new LinkedList<>();
        for (Grab grab:game.getGrabs()) {
            if(grab.getGrabbed().getUserId() == grabbed.getUserId() && !grab.getRevealed()){
                if(grab(grab)){
                    System.out.println("Transfered " + grab.getResource() + " from " + grab.getGrabbed() + " to " + grab.getGrabber());
                }
                removeGrabs.add(grab);
            }
        }
        for (Grab grab:removeGrabs) {
            game.removeGrab(grab);
        }
    }

    /**
     * Tries to transfer the specified resource from the player to be stolen from to the cheating player.
     * @param grab The Grab containing the involved Players and the Resource
     * @return Return true if the transfer was executed, false if not
     */
    private static boolean grab(Grab grab){
        return transfer(grab.getGrabbed(),grab.getGrabber(),grab.getResource());
    }

    /**
     * Tries to transfer the specified resource from the cheating player to the player to be stolen from.
     * @param grab The Grab containing the involved Players
     * @param resource String Identifier for the Resource
     * @return Returns true if the transfer was executed, false if not
     */
    public static boolean counter(Grab grab, String resource){
        return transfer(grab.getGrabber(),grab.getGrabbed(),resource);
    }

    /**
     * Tries to transfer 1 of the given Resource from one player to another
     * @param taken The Player losing the resource
     * @param given The Player gaining the resource
     * @param resource String Identifier of the resource to be transferred
     * @return Returns true if the transfer was executed, false if not
     */
    private static boolean transfer(Player taken, Player given, String resource){
        switch (resource) {
            case "WOOD":
                if(taken.getInventory().getWood() > 0){
                    taken.getInventory().removeWood(1);
                    given.getInventory().addWood(1);
                    return true;
                }
                break;
            case "WOOL":
                if(taken.getInventory().getWool() > 0){
                    taken.getInventory().removeWool(1);
                    given.getInventory().addWool(1);
                    return true;
                }
                break;
            case "CLAY":
                if(taken.getInventory().getClay() > 0){
                    taken.getInventory().removeClay(1);
                    given.getInventory().addClay(1);
                    return true;
                }
                break;
            case "ORE":
                if(taken.getInventory().getOre() > 0){
                    taken.getInventory().removeOre(1);
                    given.getInventory().addOre(1);
                    return true;
                }
                break;
            case "WHEAT":
                if(taken.getInventory().getWheat() > 0){
                    taken.getInventory().removeWheat(1);
                    given.getInventory().addWheat(1);
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

}
