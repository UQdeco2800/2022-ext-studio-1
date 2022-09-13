package com.deco2800.game.components.npc;

import org.junit.Test;

import static org.junit.Assert.*;

public class NPCClueLibraryTest {

    @Test
    public void getInstance() throws Exception {
        NPCClueLibrary npcClueLibrary = NPCClueLibrary.getInstance();

        String[] clues = npcClueLibrary.getUnlockClues("Metis");
        assertEquals("a man who was close to her", clues[0]);
        assertEquals("has deep voice", clues[1]);
        assertEquals("a tall, thin person", clues[2]);
    }
}