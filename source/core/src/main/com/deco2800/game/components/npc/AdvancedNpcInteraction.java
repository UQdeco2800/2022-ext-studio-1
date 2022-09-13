package com.deco2800.game.components.npc;

import java.util.ArrayList;

public class AdvancedNpcInteraction extends NpcInteraction {

    /** player may get clus when interacting with npc */
    private ArrayList<String> playerClues;

    /**
     * Create a new instance of NpcInteractionn
     *
     * @param playerId
     */
    public AdvancedNpcInteraction(int playerId) {
        super(playerId);

        playerClues = new ArrayList<>();
    }

    public void meetNpcWithClue(int npcId, int choice) {
        String meet = super.meetNpc(npcId, choice);
        if (meet != null) {
            playerClues.add("needed to be confirmed");
        }
    }

    public ArrayList<String> getPlayerClues() {
        return playerClues;
    }
}
