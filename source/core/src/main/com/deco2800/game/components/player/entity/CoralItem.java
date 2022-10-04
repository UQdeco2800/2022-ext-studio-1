package com.deco2800.game.components.player.entity;

public class CorelItem extends Item {
    private final String TOOL_ITEM = "Battery Item";

    public CorelItem() {
    }

    public CorelItem(int id, String name, String description, String textureLocation) {
        super(id, name, description, textureLocation);
    }

    @Override
    Type getType() {
        return Type.CONSUMABLE_ITEM;
    }
}
