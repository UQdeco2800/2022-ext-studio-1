package com.deco2800.game.components.player;

import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(GameExtension.class)
class InventoryComponentTest {
  @Test
  void shouldSetGetGold() {
    InventoryComponent inventory = new InventoryComponent(100);
    assertEquals(100, inventory.getGold());

    inventory.setGold(150);
    assertEquals(150, inventory.getGold());

    inventory.setGold(-50);
    assertEquals(0, inventory.getGold());
  }

  @Test
  void shouldCheckHasGold() {
    InventoryComponent inventory = new InventoryComponent(150);
    assertTrue(inventory.hasGold(100));
    assertFalse(inventory.hasGold(200));
  }

  @Test
  void shouldAddGold() {
    InventoryComponent inventory = new InventoryComponent(100);
    inventory.addGold(-500);
    assertEquals(0, inventory.getGold());

    inventory.addGold(100);
    inventory.addGold(-20);
    assertEquals(80, inventory.getGold());
  }
  @Test
  void shouldAddItemToInventory() {
    InventoryComponent inventory = new InventoryComponent(0);
    inventory.inventoryHashMap.put(1,1);
    assertEquals(1, inventory.inventoryHashMap.size());
  }

  @Test
  void shouldAddGuilt() {


  }

  @Test
  void shouldIncreaseTime() {
    ConsumeableItemComponent consumable = new ConsumeableItemComponent(30);
    assertEquals(30, consumable.increaseTime());

  }
  /*
  This test should show that another item is not added to the inventory system, otherwise will fail over
   */
  @Test
  void shouldLimitInventoryCapacity() {
    InventoryComponent inventory = new InventoryComponent(0);

    short Item = (1 << 2);

    inventory.inventoryHashMap.put(1,1);
    inventory.inventoryHashMap.put(2,1);
    inventory.inventoryHashMap.put(3,1);
    inventory.inventoryHashMap.put(4,1);
    inventory.inventoryHashMap.put(5,1);
    inventory.inventoryHashMap.put(6,1);
    inventory.inventoryHashMap.put(7,1);
    inventory.inventoryHashMap.put(8,1);
    inventory.inventoryHashMap.put(9,1);
    inventory.inventoryHashMap.put(10,1);

    // Desirable parameters
    AddToInventoryComponent addToInventoryComponent = new AddToInventoryComponent(Item);

    addToInventoryComponent.addToInventory(inventory.inventoryHashMap.size(), inventory, null);
    assertEquals(30, inventory.inventoryHashMap.size());


  }

}
