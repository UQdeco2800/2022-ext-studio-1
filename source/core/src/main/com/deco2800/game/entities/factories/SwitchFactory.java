package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.components.achievements.AchievementsUpdater;
import com.deco2800.game.components.player.*;
import com.deco2800.game.components.player.entity.Backpack;
import com.deco2800.game.components.player.entity.Item;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

import java.util.Map;

public class SwitchFactory {
    private static final Map<Integer, Item> idItemMap = Backpack.ID_ITEM_MAP;
    public static final int TOOL_ID = 2;
    public static final int BATTERY_ID = 3;
    public static boolean isWorking = false;

    public static Entity createSwitch() {
        Entity switchItem =
                new Entity()
                        .addComponent(new TextureRenderComponent(SwitchFactory.isWorking ? "images/switch/Electric Switch.png": "images/switch/Electric Switch Broken.png"))
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
                        .addComponent(new SwitchComponent());

        switchItem.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
        switchItem.getComponent(TextureRenderComponent.class).scaleEntity();
        switchItem.scaleHeight(1f);
        PhysicsUtils.setScaledCollider(switchItem, 0.8f, 0.4f);

        return switchItem;
    }

    public static Entity createTool() {
        Item item = idItemMap.get(SwitchFactory.TOOL_ID);
        if (item == null) {
            throw new IllegalArgumentException("unknown id " + SwitchFactory.TOOL_ID);
        }
        Entity itemEntity = new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new PhysicsMovementComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.Item))
                .addComponent(new AddToInventoryComponent(PhysicsLayer.Item, SwitchFactory.TOOL_ID))
                .addComponent(new AchievementsUpdater())
                .addComponent(new ToolComponent())
                .addComponent(new TextureRenderComponent(item.getTextureLocation()));
        PhysicsUtils.setScaledCollider(itemEntity, 0.9f, 0.4f);
        return itemEntity;
    }
}