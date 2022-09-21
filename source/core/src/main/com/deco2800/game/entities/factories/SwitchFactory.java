package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.components.achievements.AchievementsUpdater;
import com.deco2800.game.components.player.*;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

public class SwitchFactory {

    public static Entity createSwitch() {
        Entity switchItem =
                new Entity()
                        .addComponent(new TextureRenderComponent("images/switch/Electric Switch Broken.png"))
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
                        .addComponent(new SwitchComponent());

        switchItem.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
        switchItem.getComponent(TextureRenderComponent.class).scaleEntity();
        switchItem.scaleHeight(0.5f);
        PhysicsUtils.setScaledCollider(switchItem, 0.5f, 0.2f);

        return switchItem;
    }

    public static Entity createBattery() {
        Entity battery =
                new Entity()
                        .addComponent(new TextureRenderComponent("images/switch/Battery.png"))
                        .addComponent(new PhysicsComponent())
                        .addComponent(new PhysicsMovementComponent())
                        .addComponent(new ColliderComponent())
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.Item))
                        .addComponent(new AddToInventoryComponent(PhysicsLayer.Item))
                        .addComponent(new BatteryComponent());

        battery.scaleHeight(0.5f);
        PhysicsUtils.setScaledCollider(battery, 0.9f, 0.4f);
        return battery;
    }

    public static Entity createTools() {
        Entity tool =
                new Entity()
                        .addComponent(new TextureRenderComponent("images/switch/Tools.png"))
                        .addComponent(new PhysicsComponent())
                        .addComponent(new PhysicsMovementComponent())
                        .addComponent(new ColliderComponent())
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.Item))
                        .addComponent(new AddToInventoryComponent(PhysicsLayer.Item))
                        .addComponent(new BatteryComponent());

        tool.scaleHeight(0.5f);
        PhysicsUtils.setScaledCollider(tool, 0.9f, 0.4f);
        return tool;
    }
}
