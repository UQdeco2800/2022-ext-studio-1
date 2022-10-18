package com.deco2800.game.components.npc;

import org.junit.Test;

import static org.junit.Assert.*;

public class NPCClueLibraryTest {

    @Test
    public void getInstance() throws Exception {
        NPCClueLibrary npcClueLibrary = NPCClueLibrary.getInstance();

        npcClueLibrary.addHephClue0();
        npcClueLibrary.addHephClue1();

        String[] clue0 = npcClueLibrary.getUnlockClues("Heph");
        assertEquals("Heph is good at repairing, and he can help repair scales.", clue0[0]);
        assertEquals("Heph, the bad-tempered restorer, was known for his superior skills and his weird personality.", clue0[1]);

    }
}
