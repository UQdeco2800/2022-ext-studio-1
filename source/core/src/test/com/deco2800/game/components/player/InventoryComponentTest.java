package com.deco2800.game.components.player;

import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.ClueItemFactory;
import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;

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
  void shouldAddItemToInventoy() {
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
  @Test
  void shouldLimitInventoryCapacity() {

  }

}
