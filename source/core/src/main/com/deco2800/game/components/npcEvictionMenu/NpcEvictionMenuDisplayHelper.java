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
    public  String createClueContext(String name, NPCClueLibrary library) {
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
                message.append("\n");
                message.append(clues[i]);
                if (i != (clues.length - 1)) {
                    message.append("\n");
                }
            }
        }
        return message.toString();
    }
    public String createTraitorMessageForSaveAtlantis(String name, NpcEvictionMenuDisplayNew.NpcResultDialogType type){
        StringBuilder message = new StringBuilder();
        if (type == NpcEvictionMenuDisplayNew.NpcResultDialogType.RIGHT_BOX) {
            message.append("You found the traitor "+name+" who destroyed Atlantis.");
            message.append("\n");
            message.append("Maybe "+name+" can give you some useful information:).");
            message.append("\n");
            message.append("Go and expel him and try to save Atlantis!");
            message.append("\n");
        } else if (type == NpcEvictionMenuDisplayNew.NpcResultDialogType.WRONG_BOX1) {
            message.append(name+" does not seem to be the traitor :(");
            message.append("\n");
            message.append("Don't worry, you still have two chances to try.");
            message.append("\n");
            message.append("Combine the clues and try again!");

        } else {
            message.append(name+" does not seem to be a traitor either :(");
            message.append("\n");
            message.append("Be careful, you only have one chance left to try.");
            message.append("\n");
            message.append("Go and see whether there are any missing clues!");

        }

        return  message.toString();
    }

    public String createTraitorMessageInterjection(NpcEvictionMenuDisplayNew.NpcResultDialogType type){
        StringBuilder message = new StringBuilder();
        if (type == NpcEvictionMenuDisplayNew.NpcResultDialogType.RIGHT_BOX) {
            message.append("That's great!!!");
        } else if (type == NpcEvictionMenuDisplayNew.NpcResultDialogType.WRONG_BOX1) {
            message.append("Unfortunately");
        } else {
            message.append("Oops");
        }

        return  message.toString();
    }

    public String createInformationFromTraitor(String name){
        return name + " said ....";
    }
}
