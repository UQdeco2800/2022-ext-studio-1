package com.deco2800.game.components.player.entity;

import java.util.List;
import java.util.Map;

/**
 * Inventory interface, limiting some basic functions of inventory
 */
public interface Inventory {

    /**
     * Check if a specific item is present in the backpack
     * @param id item id
     * @return true if the quantity of the item in stock is greater than zero
     */
    boolean contains(int id);

    /**
     * Check if a specific item is present in the backpack
     * @param itemName item name
     * @return true if the quantity of the item in stock is greater than zero
     */
    boolean contains(String itemName);

    /**
     * Quantity of a specific item
     * @param id item id
     * @return the quantity of the item or -1 if the item id is not exist.
     */
    int count(int id);

    /**
     * Quantity of a specific item
     * @param itemName item name
     * @return the quantity of the item or -1 if the item name is not exist.
     */
    int count(String itemName);

    /**
     * Add an item with a specified id
     * @param id item id
     * @return true if add successfully
     */
    boolean add(int id);

    /**
     * Add several of this item
     * @param id item id
     * @param num the number of the added item
     * @return
     */
    boolean add(int id, int num);

    /**
     * Add an item with a specified id
     * @param itemName item name
     * @return true if add successfully
     */
    boolean add(String itemName);

    /**
     * Add several of this item
     * @param itemName item name
     * @param num the number of the added item
     * @return
     */
    boolean add(String itemName, int num);

    /**
     * remove a specified item
     * @param id item id
     * @return true if remove successfully
     */
    boolean remove(int id);

    /**
     * remove a specified item
     * @param itemName item name
     * @return true if remove successfully
     */
    boolean remove(String itemName);

    boolean remove(int id, int num);

    boolean remove(String itemName, int num);
    List<Integer> getInStockItemIds();

}
