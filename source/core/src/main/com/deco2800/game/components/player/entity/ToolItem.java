package com.deco2800.game.components.player.entity;

public class ToolItem extends Item {
    private final String TOOL_ITEM = "Tool Item";

    public ToolItem() {
    }

    public ToolItem(int id, String name, String description, String textureLocation) {
        super(id, name, description, textureLocation);
    }

    @Override
    Type getType() {
        return Type.TOOL_ITEM;
    }
}
