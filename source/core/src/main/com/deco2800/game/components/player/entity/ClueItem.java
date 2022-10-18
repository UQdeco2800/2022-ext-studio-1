package com.deco2800.game.components.player.entity;

public class ClueItem extends Item{
    public static int MERMAID_SCALE1 = 4;
    public static int MERMAID_SCALE2 = 5;
    public static int MERMAID_SCALE3 = 6;
    public static int MERMAID_SCALE4 = 7;
    public static int MERMAID_SCALE5 = 8;
    public static int LACK = 10;

    public ClueItem() {
    }

    public ClueItem(int id, String name, String description, String textureLocation) {
        super(id, name, description, textureLocation);
    }

    @Override
    Type getType() {
        return Type.CLUE_ITEM;
    }
}
