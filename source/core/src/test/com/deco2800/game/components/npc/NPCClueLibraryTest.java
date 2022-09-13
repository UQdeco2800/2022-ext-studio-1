package com.deco2800.game.components.npc;

import org.junit.Test;

import static org.junit.Assert.*;

public class NPCClueLibraryTest {

    @Test
    public void getInstance() {
        NPCClueLibrary npcClueLibrary = NPCClueLibrary.getInstance();

        assertTrue(npcClueLibrary.clues.containsKey("Metis"));
        assertFalse(npcClueLibrary.clues.containsKey("Meti"));
        assertFalse(npcClueLibrary.clues.containsKey("metis"));

        String[] clues = npcClueLibrary.clues.get("Metis");
        assertEquals("a man who was close to her", clues[0]);
        assertEquals("has deep voice", clues[1]);
        assertEquals("a tall, thin person", clues[2]);
    }
}