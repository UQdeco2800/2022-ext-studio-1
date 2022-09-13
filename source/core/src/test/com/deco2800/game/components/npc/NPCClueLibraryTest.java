package com.deco2800.game.components.npc;

import static org.junit.Assert.*;

public class NPCClueLibraryTest {

    @org.junit.Test
    public void getInstance() {
        NPCClueLibrary npcClueLibrary = NPCClueLibrary.getInstance();
        assertTrue(npcClueLibrary.clues.containsKey("Metis"));
        assertTrue(npcClueLibrary.clues.containsKey("Jack"));
    }
}