package com.deco2800.game.components.npc;

import org.junit.Test;

import static org.junit.Assert.*;

public class NPCClueLibraryTest {

    @Test
    public void getInstance() throws Exception {
        NPCClueLibrary npcClueLibrary = NPCClueLibrary.getInstance();

        String[] clues = npcClueLibrary.getUnlockClues("Metis");
        assertEquals("A man who was close to her", clues[0]);
        assertEquals("Has deep voice", clues[1]);
        assertEquals("A tall, thin person", clues[2]);
    }
}