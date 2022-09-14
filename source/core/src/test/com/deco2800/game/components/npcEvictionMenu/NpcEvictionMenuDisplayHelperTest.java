package com.deco2800.game.components.npcEvictionMenu;

import com.deco2800.game.components.npc.NPCClueLibrary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NpcEvictionMenuDisplayHelperTest {
    @Test

    void testCreateLabelContext(){
        NpcEvictionMenuDisplayHelper helper = new NpcEvictionMenuDisplayHelper();
        NPCClueLibrary library = NPCClueLibrary.getInstance();
        //when npc card has no clues it will display : This npc card xxx has no clue yet.
        assertEquals("This npc card xxx has no clue yet.",helper.creatLabelContext("xxx",library));
        //when npc name is "Metis"
        assertEquals("A man who was close to her.\n" +
                "Has deep voice.\n" +
                "A tall, thin person.",helper.creatLabelContext("Metis",library));
    }

}