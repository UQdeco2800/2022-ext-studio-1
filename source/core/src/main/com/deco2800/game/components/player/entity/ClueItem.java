package com.deco2800.game.components.player.entity;

public class ClueItem extends Item{
    private final String MERMAID_SCALE1 = "Mermaid Scale1";
    private final String MERMAID_SCALE2 = "Mermaid Scale2";
    private final String MERMAID_SCALE3 = "Mermaid Scale3";
    private final String MERMAID_SCALE4 = "Mermaid Scale4";
    private final String MERMAID_SCALE5 = "Mermaid Scale5";

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
