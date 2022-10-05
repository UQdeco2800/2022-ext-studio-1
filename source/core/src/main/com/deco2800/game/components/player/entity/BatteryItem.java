package com.deco2800.game.components.player.entity;

public class BatteryItem extends Item {
    private final String TOOL_ITEM = "Battery Item";

    public BatteryItem() {
    }

    public BatteryItem(int id, String name, String description, String textureLocation) {
        super(id, name, description, textureLocation);
    }

    @Override
    Type getType() {
        return Type.CONSUMABLE_ITEM;
    }
}
