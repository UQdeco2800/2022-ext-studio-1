package com.deco2800.game.entities.factories;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.achievements.AchievementsUpdater;
import com.deco2800.game.components.player.AddToInventoryComponent;
import com.deco2800.game.components.player.entity.Backpack;
import com.deco2800.game.components.player.entity.Item;
import com.deco2800.game.components.tasks.WanderTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

import java.util.Map;


/**
 * Factory to create item entities with predefined components.
 *
 * <p>Each item entity type should have a creation method that returns a corresponding entity.
 * Predefined entity properties can be loaded from configs stored as json files which are defined in
 * "ItemConfigs".
 */
public class ItemFactory {

  private static final Map<Integer, Item> idItemMap = Backpack.ID_ITEM_MAP;
  private static final Map<String, Integer> NAME_ID_MAP = Backpack.NAME_ID_MAP;

  /**
   * Create an item with the specified id.
   * @param id item id
   * @return the item entity
   */
  public static Entity createItem(int id) {
    Item item = idItemMap.get(id);
    if (item == null) {
      throw new IllegalArgumentException("unknown id " + id);
    }
    Entity itemEntity = new Entity()
            .addComponent(new PhysicsComponent())
            .addComponent(new PhysicsMovementComponent())
            .addComponent(new ColliderComponent())
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.Item))
            .addComponent(new AddToInventoryComponent(PhysicsLayer.Item, id))
            .addComponent(new AchievementsUpdater())
            .addComponent(new TextureRenderComponent(item.getTextureLocation()));;
    PhysicsUtils.setScaledCollider(itemEntity, 0.3f, 0.3f);
    return itemEntity;
  }

  /**
   * Create an item with the specified name.
   * @param name item name
   * @return item entity
   */
  public static Entity createItem(String name) {
    Integer id = NAME_ID_MAP.get(name);
    if (id == null) {
      throw new IllegalArgumentException("unknown item name " + name);
    }
    return createItem(id);
  }

  /**
   * Creates an item entity.
   *
   * @param target entity to chase
   * @return entity
   */
  @Deprecated
  public static Entity createItem(Entity target, String texturePath) {
    Entity item = createBaseItem(target);

    item
        .addComponent(new TextureRenderComponent(texturePath))
        .addComponent(new ColliderComponent());
    return item;
  }
  /**
   * Creates a generic item to be used as a base entity by more specific item creation methods.
   *
   * @return entity
   */
  @Deprecated
  private static Entity createBaseItem(Entity target) {
    AITaskComponent aiComponent =
        new AITaskComponent()
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f));
    Entity item =
        new Entity()
            .addComponent(new PhysicsComponent())
            .addComponent(new PhysicsMovementComponent())
            .addComponent(new ColliderComponent())
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.Item))
            .addComponent(aiComponent)
            .addComponent(new AddToInventoryComponent(PhysicsLayer.Item))
            .addComponent(new AchievementsUpdater());

    PhysicsUtils.setScaledCollider(item, 0.9f, 0.4f);
    return item;
  }

  public ItemFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }


}
