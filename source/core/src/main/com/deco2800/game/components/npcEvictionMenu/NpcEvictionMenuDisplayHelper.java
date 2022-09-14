package com.deco2800.game.components.npcEvictionMenu;

import com.deco2800.game.components.npc.NPCClueLibrary;

/**
 * NpcEvictionMenuDisplay.java is a subclass of UIComponent<br/>
 * Mockito cannot mock this class: class com.deco2800.game.ui.UIComponent. <br/>
 * It means we have no way to write test for some function in NpcEvictionMenuDisplay.java <br/>
 * As a result, Some static functions will be moved here to support unit test
 *
 * @author 2022-ext-studio-1-Team7 Yingxin Liu
 */
class NpcEvictionMenuDisplayHelper {

    /**
     * Creat a label with the given set of clues
     *
     * @param name    the index of card to find the name of npc
     * @param library the class from team 9 to get the clues for given name of npc
     * @return A string of npc clues of given name of npc, each clue will end as \n <br/>
     * if there is no clue, it will return "This npc card npcName has no clue yet."
     *
     * @author Team7 Yingxin Liu Shaohui Wang
     */
    public  String creatLabelContext(String name, NPCClueLibrary library) {
        String[] clues = {};
        try {
            clues = library.getUnlockClues(name);
        } catch (Exception ignored) {
        }

        StringBuilder message = new StringBuilder();
        if (clues == null || clues.length == 0) {
            message = new StringBuilder("This npc card " + name + " has no clue yet.");
        } else {
            for (int i = 0; i < clues.length; i++) {
                message.append(clues[i]);
                if (i != (clues.length - 1)) {
                    message.append("\n");
                }
            }
        }
        return message.toString();
    }
}
