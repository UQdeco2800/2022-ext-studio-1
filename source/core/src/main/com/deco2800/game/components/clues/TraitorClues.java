package com.deco2800.game.components.clues;

import java.util.ArrayList;
/**
 * Singleton class for Traitor Clue Library
 */
public class TraitorClues extends ClueLibrary{

    public TraitorClues(ArrayList<String> traitorClues){
        traitorClues.add("A man who has a deep voice.");
        traitorClues.add(" A tall, thin person.");
        traitorClues.add("A man who was close to Nereus.");
    }
}
