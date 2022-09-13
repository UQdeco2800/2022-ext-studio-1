package com.deco2800.game.components.npc;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class for NPC Clue Library
 */
public class NPCClueLibrary {

    /**
     * Static variable reference of single_instance
     */
    private static NPCClueLibrary clueLibrary = null;

    /**
     * Declaring a variable of clues
     */
    public Map<String, String[]> clues;

    /**
     * Constructor
     * Here we will be creating private constructor
     * restricted to this class itself
     */
    private NPCClueLibrary() {
        clues = new HashMap<>();

        clues.put("Metis", new String[]{"a man who was close to her", "has deep voice", "a tall, thin person"});

        // put more clues here later on.
    }

    /**
     * Static method to create instance of NPCClueLibrary class
     */
    public static NPCClueLibrary getInstance() {
        if (clueLibrary == null)
            clueLibrary = new NPCClueLibrary();

        return clueLibrary;
    }
}
