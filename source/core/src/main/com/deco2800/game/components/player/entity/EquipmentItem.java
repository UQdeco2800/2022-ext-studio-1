package com.deco2800.game.components.player.entity;

public class EquipmentItem extends Item {

    public EquipmentItem() {
    }

    public EquipmentItem(int id, String name, String description, String textureLocation) {
        super(id, name, description, textureLocation);
    }

    @Override
    Type getType() {
        return Type.EQUIPMENT_ITEM;
    }
}
