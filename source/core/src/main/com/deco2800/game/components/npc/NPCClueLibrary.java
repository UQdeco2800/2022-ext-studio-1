package com.deco2800.game.components.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private Map<Integer, String> ZoeClues;
    private Map<Integer, String> MetisClues;
    private Map<Integer, String> DorisClues;
    private Map<Integer, String> HephClues;
    private Map<Integer, String> AresClues;
    private Map<Integer, String> OrpheusClues;
    private Map<Integer, String> NereusClues;

    List<String> ZoeGot;
    List<String> MetisGot;
    List<String> DorisGot;
    List<String> HephGot;
    List<String> AresGot;
    List<String> OrpheusGot;
    List<String> NereusGot;

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

    public void addZoeClue0() {
        ZoeGot.add(ZoeClues.get(0));
    }

    public void addMetisClue0() {
        MetisGot.add(MetisClues.get(0));
    }

    public void addMetisClue1() {
        MetisGot.add(MetisClues.get(1));
    }

    public void addMetisClue2() {
        MetisGot.add(MetisClues.get(2));
    }

    public void addMetisClue3() {
        MetisGot.add(MetisClues.get(3));
    }

    public void addDorisClue0() {
        DorisGot.add(DorisClues.get(0));
    }

    public void addHephClue0() {
        HephGot.add(HephClues.get(0));
    }

    public void addHephClue1() {
        HephGot.add(HephClues.get(1));
    }

    public void addHephClue2() {
        HephGot.add(HephClues.get(2));
    }

    public void addHephClue3() {
        HephGot.add(HephClues.get(3));
    }

    public void addAresClue0() {
        AresGot.add(AresClues.get(0));
    }

    public void addOrpheusClue0() {
        OrpheusGot.add(OrpheusClues.get(0));
    }

    public void addOrpheusClue1() {
        OrpheusGot.add(OrpheusClues.get(1));
    }

    public void addOrpheusClue2() {
        OrpheusGot.add(OrpheusClues.get(2));
    }

    public void addNereusClue0() {
        NereusGot.add(NereusClues.get(0));
    }

    public void addNereusClue1() {
        NereusGot.add(NereusClues.get(1));
    }

    public void addNereusClue2() {
        NereusGot.add(NereusClues.get(2));
    }

    public void addNereusClue3() {
        NereusGot.add(NereusClues.get(3));
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
                "Nereus's scale is shattered.",
                "Nereus was kind-hearted and treated everyone very well, also not having any grudge against someone."});

        // put more clues here later on.

        ZoeClues = new HashMap<>();
        MetisClues = new HashMap<>();
        DorisClues = new HashMap<>();
        HephClues = new HashMap<>();
        AresClues = new HashMap<>();
        OrpheusClues = new HashMap<>();
        NereusClues = new HashMap<>();

        ZoeGot = new ArrayList<>();
        MetisGot = new ArrayList<>();
        DorisGot = new ArrayList<>();
        HephGot = new ArrayList<>();
        AresGot = new ArrayList<>();
        OrpheusGot = new ArrayList<>();
        NereusGot = new ArrayList<>();

        ZoeClues.put(0, "Zoe is one of the researchers in the player's lab.");

        MetisClues.put(0, "A man who was close to her.");
        MetisClues.put(1, "Has deep voice.");
        MetisClues.put(2, "A tall, thin person.");
        MetisClues.put(3, "Metis can read the memory from the scale.");

        DorisClues.put(0, "Doris has a dying scale, and she said it was a gift from Nereus a couple years ago.");

        HephClues.put(0, "Heph is good at repairing, and he can help repair scales.");
        HephClues.put(1, "Heph, the bad-tempered restorer, was known for his superior skills and his weird personality.");
        HephClues.put(2, "Heph acts violently with someone he has conflict with.");
        HephClues.put(3, "Heph didn't quite agree with the political philosophy of the princess.");

        AresClues.put(0, "Ares has strong skills in swordsmanship, but wasn't good enough in other decisive aspects.");

        OrpheusClues.put(0, "Orpheus is a singer who lives in theatre.");
        OrpheusClues.put(1, "Orpheus adores Nereus.");
        OrpheusClues.put(2, "Orpheus wanted to express his love to the princess by sending irises, but the flowers died before he sent them.");

        NereusClues.put(0, "The biggest wound is on her waist, and it looks like someone hurt her.");
        NereusClues.put(1, "Bloodstains are from outside, Nereus seemed to hobble here with all her strength.");
        NereusClues.put(2, "Nereus's scale is shattered.");
        NereusClues.put(3, "Nereus was kind-hearted and treated everyone very well, also not having any grudge against someone.");




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
