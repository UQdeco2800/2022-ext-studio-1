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
    private Map<String, String[]> clues;

    /**
     * Return unlock clues
     * @param name
     * @return unlock clues
     * @throws Exception if name not exists
     */
    public String[] getUnlockClues(String name) throws Exception {
        if (clues.containsKey(name)) {
            return clues.get(name);
        }

        throw new Exception("name not found");
    }

    /**
     * Constructor
     * Here we will be creating private constructor
     * restricted to this class itself
     */
    private NPCClueLibrary() {
        clues = new HashMap<>();

        clues.put("Metis", new String[]{"A man who was close to her.", "Has deep voice.", "A tall, thin person.",
                "Metis can read the memory from the scale."});
        clues.put("Zoe", new String[]{"Zoe is one of the researchers in the player's lab."});
        clues.put("Doris", new String[]{"Doris has a dying scale, and she said it was a gift" +
                " from Nereus a couple years ago."});
        clues.put("Heph", new String[]{"Heph is good at repairing, and he can help repair scales.",
                "Heph, the bad-tempered restorer, was known for his superior skills and his weird personality.",
                "Heph acts violently with someone he has conflict with.",
                "Heph didn't quite agree with the political philosophy of the princess."});
        clues.put("Ares", new String[]{"Ares has strong skills in swordsmanship, " +
                "but wasn't good enough in other decisive aspects."});
        clues.put("Orpheus", new String[]{"Orpheus is a singer who lives in theatre.",
                "Orpheus adores Nereus.",
                "Orpheus wanted to express his love to the princess by sending irises, " +
                        "but the flowers died before he sent them."});
        clues.put("Nereus", new String[]{"The biggest wound is on her waist, and it looks like someone hurt her.",
                "Bloodstains are from outside, Nereus seemed to hobble here with all her strength.",
                "Nereus’ scale is shattered.",
                "Nereus was kind-hearted and treated everyone very well, also not having any grudge against someone."});

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
