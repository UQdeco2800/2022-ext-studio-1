package com.deco2800.game.components.npc;

import org.junit.Test;

import static org.junit.Assert.*;

public class NPCClueLibraryTest {

    @Test
    public void getInstance() throws Exception {
        NPCClueLibrary npcClueLibrary = NPCClueLibrary.getInstance();

        String[] clue1 = npcClueLibrary.getUnlockClues("Metis");
        assertEquals("A man who was close to her.", clue1[0]);
        assertEquals("Has deep voice.", clue1[1]);
        assertEquals("A tall, thin person.", clue1[2]);
        assertEquals("Metis can read the memory from the scale.", clue1[3]);

        String[] clue2 = npcClueLibrary.getUnlockClues("Zoe");
        assertEquals("Zoe is one of the researchers in the player's lab.", clue2[0]);

        String[] clue3 = npcClueLibrary.getUnlockClues("Doris");
        assertEquals("Doris has a dying scale, and she said it was a gift from Nereus a couple years ago.", clue3[0]);

        String[] clue4 = npcClueLibrary.getUnlockClues("Heph");
        assertEquals("Heph is good at repairing, and he can help repair scales.", clue4[0]);
        assertEquals("Heph, the bad-tempered restorer, was known for his superior skills and his weird personality.", clue4[1]);
        assertEquals("Heph acts violently with someone he has conflict with.", clue4[2]);
        assertEquals("Heph didn't quite agree with the political philosophy of the princess.", clue4[3]);

        String[] clue5 = npcClueLibrary.getUnlockClues("Ares");
        assertEquals("Ares has strong skills in swordsmanship, but wasn't good enough in other decisive aspects.", clue5[0]);

        String[] clue6 = npcClueLibrary.getUnlockClues("Orpheus");
        assertEquals("Orpheus is a singer who lives in theatre.", clue6[0]);
        assertEquals("Orpheus adores Nereus.", clue6[1]);
        assertEquals("Orpheus wanted to express his love to the princess by sending irises, but the flowers died before he sent them.", clue6[2]);

        String[] clue7 = npcClueLibrary.getUnlockClues("Nereus");
        assertEquals("The biggest wound is on her waist, and it looks like someone hurt her.", clue7[0]);
        assertEquals("Bloodstains are from outside, Nereus seemed to hobble here with all her strength.", clue7[1]);
        assertEquals("Nereus’ scale is shattered.", clue7[2]);
        assertEquals("Nereus was kind-hearted and treated everyone very well, also not having any grudge against someone.", clue7[3]);
    }
}
