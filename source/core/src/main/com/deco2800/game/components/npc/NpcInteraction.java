package com.deco2800.game.components.npc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NpcInteraction {

    private int playerId;
    private float distance;
    /** Trigger the conversation to specify the distance */
    private final int MIN_DISTANCE = 888;

    /**
     * Create a new instance of NpcInteractionn
     * @param playerId
     */
    public NpcInteraction(int playerId) {
        this.playerId = playerId;
        this.distance = 0;
    }

    /**
     * set new distance
     * @param distance
     */
    public void updateDistance(int distance) {
        this.distance = distance;
    }

    /**
     * get the current distance
     * @return current
     */
    public float getDistance() {
        return this.distance;
    }

    /**
     * what happen when the player meet a npc.
     * @param npcId
     * @param choice
     * @return null if wrong interaction or the corresponding response.
     */
    public String meetNpc(int npcId, int choice) {
        if (this.distance == MIN_DISTANCE) {
            try {
                ArrayList<String> content = readNpcFiles(npcId);
                if (choice == 1) {
                    return content.get(0);
                } else if (choice == 2) {
                    return content.get(1);
                } else if (choice == 3) {
                    return content.get(2);
                } else {
                    return null;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public static ArrayList<String> readNpcFiles(int chapterNum) throws IOException {
        BufferedReader br;
        ArrayList<String> texts = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("dialogs/Chapter " + chapterNum + ".txt"));
            String line;
            while ((line = br.readLine()) != null) {
                texts.add(line);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return texts;
    }
}
