package com.deco2800.game.components.player.entity;

public class CoralItem extends ClueItem {
    private final String TOOL_ITEM = "Coral Item";

    public CoralItem() {
    }

    public CoralItem(int id, String name, String description, String textureLocation) {
        super(id, name, description, textureLocation);
    }

    @Override
    Type getType() {
        return Type.CONSUMABLE_ITEM;
    }
}
