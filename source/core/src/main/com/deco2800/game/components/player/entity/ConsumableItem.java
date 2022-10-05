package com.deco2800.game.components.player.entity;

public class ConsumableItem extends Item {
    private final String TIME_ITEM = "Time Item";

    public ConsumableItem() {
    }

    public ConsumableItem(int id, String name, String description, String textureLocation) {
        super(id, name, description, textureLocation);
    }

    @Override
    Type getType() {
        return Type.CONSUMABLE_ITEM;
    }

}
