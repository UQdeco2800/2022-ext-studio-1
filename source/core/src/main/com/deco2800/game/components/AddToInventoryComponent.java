package com.deco2800.game.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;

/**
 * When this entity touches a valid objects's hitbox, the item dissappears and is added to the inventory hashmap
 */
public class AddToInventoryComponent extends Component {
  private short targetLayer;
  private HitboxComponent hitboxComponent;

  /**
   * Create a component which attacks entities on collision, with knockback.
   * @param targetLayer The physics layer of the target's collider.
   */
  public AddToInventoryComponent(short targetLayer) {
    this.targetLayer = targetLayer;
  }

  @Override
  public void create() {
    entity.getEvents().addListener("collisionStart", this::onCollisionStart);
    hitboxComponent = entity.getComponent(HitboxComponent.class);
  }

  private void onCollisionStart(Fixture me, Fixture other) {
    if (hitboxComponent.getFixture() != me || !PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits) ) {
      // Not triggered by hitbox, ignore or If interacting with anything that is not the player return
      return;
    }

    // Add to inventory
    Entity target = ((BodyUserData) other.getBody().getUserData()).entity;
    InventoryComponent playerInventory = target.getComponent(InventoryComponent.class);

    int inventoryCount = playerInventory.inventoryHashMap.size();

    // the inventory item and number + the entityID so it can be interacted with later
    playerInventory.inventoryHashMap.put(inventoryCount+1, entity.getId());

    // now that this has been added remove the item from the screen but have it still accessible to call on it
    Entity self = ((BodyUserData) me.getBody().getUserData()).entity;
    self.setEnabled(false);
    self.getComponent(TextureRenderComponent.class).dispose();
  }
}
