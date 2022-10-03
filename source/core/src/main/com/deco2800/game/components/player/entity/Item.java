package com.deco2800.game.components.player.entity;

/**
 * Item abstract class
 */
public abstract class Item {

    /**
     * item id
     */
    private int id;
    /**
     * item name
     */
    private String name;
    /**
     * item description
     */
    private String description;

    /**
     * texture path
     */
    private String textureLocation;

    /**
     * Get the current item type
     * @return the current item type
     */
    abstract Type getType();

    public Item() {
    }

    public Item(int id, String name, String description, String textureLocation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.textureLocation = textureLocation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTextureLocation() {
        return textureLocation;
    }

    enum Type {
        /**
         * Consumables, items that can be acquired repeatedly.
         */
        CONSUMABLE_ITEM,
        /**
         * Equipment items such as diving suits, weapons, etc. Can improve player attributes
         */
        EQUIPMENT_ITEM,
        /**
         * Clue items, usually only one can be obtained
         */
        CLUE_ITEM
    }

}
