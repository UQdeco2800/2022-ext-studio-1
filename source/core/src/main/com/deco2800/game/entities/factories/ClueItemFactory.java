package com.deco2800.game.entities.factories;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.player.AddToInventoryComponent;
import com.deco2800.game.components.player.ClueItemComponent;
import com.deco2800.game.components.player.ConsumeableItemComponent;
import com.deco2800.game.components.tasks.WanderTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

/**
 * Factory to create item entities with predefined components.
 *
 * <p>Each item entity type should have a creation method that returns a corresponding entity.
 * Predefined entity properties can be loaded from configs stored as json files which are defined in
 * "ItemConfigs".
 *
*/
@Deprecated
public class ClueItemFactory extends ItemFactory {

    private static int guiltyRating = 30;

    ClueItemFactory() {
        super();
    }

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
                        .addComponent(new ClueItemComponent(getGuiltyRating()));

        PhysicsUtils.setScaledCollider(item, 0.9f, 0.4f);
        return item;
    }
    public static int getGuiltyRating() {
        return guiltyRating;
    }
}

