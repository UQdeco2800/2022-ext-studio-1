package com.deco2800.game.components.npc;

import java.util.ArrayList;

public class NPCClueLibrary {

    private String name;
    private ArrayList<String> clues;

    public NPCClueLibrary(String name) {
        this.name = name;
        this.clues = new ArrayList<>();
    }

    public void addClues(String clue) {
        this.clues.add(clue);
    }

    public ArrayList<String> getClues() {
        return this.clues;
    }

    public boolean removeClue(String clue) {
        return this.clues.remove(clue);
    }

}
