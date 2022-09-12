package com.deco2800.game.components.achievements;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.player.AddToInventoryComponent;
import com.deco2800.game.components.player.ConsumeableItemComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.achievementConfigs.AchievementPropertyConfig;
import com.deco2800.game.entities.factories.AchievementFactory;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

import java.util.List;

public class AchievementsUpdater extends Component {
//    private static final List<AchievementPropertyConfig> achievements = AchievementFactory.getAchievements();

    private HitboxComponent hitboxComponent;

    @Override
    public void create() {
        hitboxComponent = entity.getComponent(HitboxComponent.class);
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
    }

    private void onCollisionStart(Fixture me, Fixture other) {
        if (hitboxComponent.getFixture() != me) {
            // Not triggered by hitbox, ignore
            return;
        }

        Entity player = ((BodyUserData) other.getBody().getUserData()).entity;
        AchievementStatsComponent achvStatsComponent = player.getComponent(AchievementStatsComponent.class);
        if (isConsumableItem(entity)) {
            achvStatsComponent.updateConsumableCollectionAchievement(entity);
        }
        if (isClueItem(entity)) {
            achvStatsComponent.updateClueCollectionAchievement(entity);
        }



    }

    public boolean isConsumableItem(Entity entity) {
        return entity.getComponent(ConsumeableItemComponent.class) != null;
    }

    public boolean isClueItem(Entity entity) {
        // TODO At present, the eighth group of students does not complete #ClueItemFactory,
        //  so they can only make judgments based on this logic.
        //  Need to be modified later
        return entity.getComponent(ConsumeableItemComponent.class) == null
                && entity.getComponent(AddToInventoryComponent.class) != null;
    }

    public boolean isPickUp(Entity entity) {
        return isClueItem(entity) || isConsumableItem(entity);
    }


}
