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
        assertEquals("This npc card xxx has no clue yet.",helper.createClueContext("xxx",library));
        //when npc name is "Metis"
        assertEquals("A man who was close to her.\n" +
                "Has deep voice.\n" +
                "A tall, thin person.\n" +
                "Metis can read the memory from the scale.",helper.createClueContext("Metis",library));
    }
    @Test
    void testCreateInterjection(){
        NpcEvictionMenuDisplayHelper helper = new NpcEvictionMenuDisplayHelper();
        assertEquals("That's great!!!\n",helper.createTraitorMessageInterjection(NpcEvictionMenuDisplayNew.NpcResultDialogType.RIGHT_BOX));
        assertEquals("Unfortunately\n",helper.createTraitorMessageInterjection(NpcEvictionMenuDisplayNew.NpcResultDialogType.WRONG_BOX1));
        assertEquals("Oops\n",helper.createTraitorMessageInterjection(NpcEvictionMenuDisplayNew.NpcResultDialogType.WRONG_BOX2));
    }
    @Test
    void testCreateTraitorMessage(){
        NpcEvictionMenuDisplayHelper helper = new NpcEvictionMenuDisplayHelper();
        assertEquals("You found the traitor xxx who destroyed Atlantis.\n" +
                "Maybe xxx can give you some useful information:).\n" +
                "Go and expel him and try to save Atlantis!\n",helper.createTraitorMessageForSaveAtlantis("xxx",NpcEvictionMenuDisplayNew.NpcResultDialogType.RIGHT_BOX));
        assertEquals("xxx does not seem to be the traitor :(\n" +
                "Don't worry, you still have two chances to try.\n" +
                "Combine the clues and try again!" ,helper.createTraitorMessageForSaveAtlantis("xxx",NpcEvictionMenuDisplayNew.NpcResultDialogType.WRONG_BOX1));
        assertEquals("xxx does not seem to be a traitor either :(\n" +
                "Be careful, you only have one chance left to try.\n" +
                "Go and see whether there are any missing clues!",helper.createTraitorMessageForSaveAtlantis("xxx",NpcEvictionMenuDisplayNew.NpcResultDialogType.WRONG_BOX2));

    }

}