package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.components.player.entity.Backpack;
import com.deco2800.game.components.player.entity.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;

/**
 * A component intended to be used by the player to track their inventory.
 *
 * Currently only stores the gold amount but can be extended for more advanced functionality such as storing items.
 * Can also be used as a more generic component for other entities.
 */
public class InventoryComponent extends Component implements Inventory {
  private static final Logger logger = LoggerFactory.getLogger(InventoryComponent.class);

  private Backpack backpack;
  public InventoryComponent() {
    backpack = new Backpack();
  }

  @Override
  public void create() {
    super.create();
  }

  @Override
  public boolean contains(int id) {
    return backpack.contains(id);
  }

  @Override
  public boolean contains(String itemName) {
    return backpack.contains(itemName);
  }

  @Override
  public int count(int id) {
    return backpack.count(id);
  }

  @Override
  public int count(String itemName) {
    return backpack.count(itemName);
  }

  @Override
  public boolean add(int id) {
    boolean ret = backpack.add(id);
    logger.info(backpack.toString());
    return ret;
  }

  @Override
  public boolean add(int id, int num) {
    return backpack.add(id, num);
  }

  @Override
  public boolean add(String itemName) {
    return backpack.add(itemName);
  }

  @Override
  public boolean add(String itemName, int num) {
    return backpack.add(itemName, num);
  }

  @Override
  public boolean remove(int id) {
    return backpack.remove(id);
  }

  @Override
  public boolean remove(String itemName) {
    return backpack.remove(itemName);
  }

  @Override
  public boolean remove(int id, int num) {
    return backpack.remove(id, num);
  }

  @Override
  public boolean remove(String itemName, int num) {
    return backpack.remove(itemName, num);
  }



  @Deprecated
  private int gold;

  @Deprecated
  public HashMap<Integer, Integer> inventoryHashMap = new HashMap<Integer, Integer>();

  @Deprecated
  public InventoryComponent(int gold) {
    setGold(gold);
  }

  @Deprecated
  public HashMap<Integer, Integer> getInventory() {
    return inventoryHashMap;
  }

  /**
   * Returns the player's gold.
   *
   * @return entity's health
   */
  @Deprecated
  public int getGold() {
    return this.gold;
  }

  /**
   * Returns if the player has a certain amount of gold.
   * @param gold required amount of gold
   * @return player has greater than or equal to the required amount of gold
   */
  @Deprecated
  public Boolean hasGold(int gold) {
    return this.gold >= gold;
  }

  /**
   * Sets the player's gold. Gold has a minimum bound of 0.
   *
   * @param gold gold
   */
  @Deprecated
  public void setGold(int gold) {
    if (gold >= 0) {
      this.gold = gold;
    } else {
      this.gold = 0;
    }
    logger.debug("Setting gold to {}", this.gold);
  }

  /**
   * Adds to the player's gold. The amount added can be negative.
   * @param gold gold to add
   */

  @Deprecated
  public void addGold(int gold) {
    setGold(this.gold + gold);
  }
}


