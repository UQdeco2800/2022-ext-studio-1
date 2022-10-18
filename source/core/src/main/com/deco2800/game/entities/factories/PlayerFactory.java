package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.achievements.AchievementStatsComponent;
import com.deco2800.game.components.achievements.pojo.AchievementStatus;
import com.deco2800.game.components.player.*;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.PlayerConfig;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.AchievementService;
import com.deco2800.game.services.ServiceLocator;

import java.util.Map;

/**
 * Factory to create a player entity.
 *
 * <p>Predefined player properties are loaded from a config stored as a json file and should have
 * the properties stores in 'PlayerConfig'.
 */
public class PlayerFactory {
  private static final PlayerConfig stats =
      FileLoader.readClass(PlayerConfig.class, "configs/player.json");

  /**
   * Create a player entity.
   * @return entity
   */
  public static Entity createPlayer(GdxGame game) {
    InputComponent inputComponent =
        ServiceLocator.getInputService().getInputFactory().createForPlayer();

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/player.atlas", TextureAtlas.class));
    animator.addAnimation("up", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("down", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("left", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("right", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("pickUp", 0.1f, Animation.PlayMode.NORMAL);
    animator.addAnimation("attack",0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("downAttack",0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("upAttack",0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("leftAttack",0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("rightAttack",0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("gunRightAttack",0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("gunLeftAttack",0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("gunUpAttack",0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("gunDownAttack",0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("knifeRightAttack",0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("knifeLeftAttack",0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("knifeDownAttack",0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("knifeUpAttack",0.1f, Animation.PlayMode.LOOP);



    AchievementService achievementService = ServiceLocator.getAchievementService();
    Map<String, AchievementStatus> achievementStatusMap = achievementService.getAchievementStatusMap();

    Entity player =
        new Entity()
            .addComponent(new TextureRenderComponent("images/player_front.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.PLAYER))
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
            .addComponent(new PlayerActions(game))
            .addComponent(new InventoryComponent())
            .addComponent(new CombatStatsComponent(stats.health, stats.health))
            .addComponent(inputComponent)
            .addComponent(animator)
            .addComponent(new PlayerAnimationController())
            .addComponent(new AchievementStatsComponent(achievementStatusMap))
            .addComponent(new FistAttackComponent(6f))
            .addComponent(new KnifeAttackComponent())
            .addComponent(new GunAttackComponent(10f));

    PhysicsUtils.setScaledCollider(player, 0.9f, 0.4f);
    player.getComponent(ColliderComponent.class).setDensity(1.5f);
    player.getComponent(TextureRenderComponent.class).scaleEntity();
    return player;
  }

  private PlayerFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
