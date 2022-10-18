package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.npc.GhostAnimationController;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.npc.MonsterAnimationController;
import com.deco2800.game.components.tasks.ChaseTask;
import com.deco2800.game.components.tasks.MovingTask;
import com.deco2800.game.components.tasks.WanderTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.BaseEntityConfig;
import com.deco2800.game.entities.configs.GhostKingConfig;
import com.deco2800.game.entities.configs.NPCConfigs;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory to create non-playable character (NPC) entities with predefined components.
 *
 * <p>Each NPC entity type should have a creation method that returns a corresponding entity.
 * Predefined entity properties can be loaded from configs stored as json files which are defined in
 * "NPCConfigs".
 *
 * <p>If needed, this factory can be separated into more specific factories for entities with
 * similar characteristics.
 */
public class NPCFactory {
  private static final NPCConfigs configs =
      FileLoader.readClass(NPCConfigs.class, "configs/NPCs.json");

  private static final Logger logger = LoggerFactory.getLogger(NPCFactory.class);

  public static Entity createKnight(Entity target) {
    AITaskComponent aiComponent =
            new AITaskComponent()
                    .addTask(new MovingTask(new Vector2(2f, 2f), 2f))
                    .addTask(new ChaseTask(target, 10, 3f, 2f));

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/knight.atlas", TextureAtlas.class));
    animator.addAnimation("down", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("up", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("left", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("right", 0.1f, Animation.PlayMode.LOOP);

    BaseEntityConfig config = configs.ghost;

    Entity knight =
            new Entity()
                    .addComponent(new PhysicsComponent())
                    .addComponent(new PhysicsMovementComponent())
                    .addComponent(new ColliderComponent())
                    .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                    .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 4f))
                    .addComponent(aiComponent)
                    .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
                    .addComponent(animator)
                    .addComponent(new MonsterAnimationController());

    PhysicsUtils.setScaledCollider(knight, 0.9f, 0.4f);
    knight.setScale(1.2f,1.2f);
    logger.debug("Create a Knight");
    return knight;
  }

  public static Entity createRobot(Entity target) {
    Entity robot = createBaseNPC(target);
    BaseEntityConfig config = configs.ghost;

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/robot.atlas", TextureAtlas.class));
    animator.addAnimation("down", 0.1f, Animation.PlayMode.LOOP);

    robot
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
            .addComponent(animator)
            .addComponent(new MonsterAnimationController());

    robot.setScale(1.2f,1.2f);
    logger.debug("Create a Robot");
    return robot;
  }

  public static Entity createSlime(Entity target) {
    Entity slime = createBaseNPC(target);
    BaseEntityConfig config = configs.ghost;

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/slime.atlas", TextureAtlas.class));
    animator.addAnimation("down", 0.1f, Animation.PlayMode.LOOP);

    slime
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
            .addComponent(animator)
            .addComponent(new MonsterAnimationController());

    slime.setScale(1.2f,1.2f);
    logger.debug("Create a Slime");
    return slime;
  }

  public static Entity createBobo(Entity target) {
    Entity bobo = createBaseNPC(target);
    BaseEntityConfig config = configs.ghost;

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/bobo.atlas", TextureAtlas.class));
    animator.addAnimation("down", 0.1f, Animation.PlayMode.LOOP);

    bobo
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
            .addComponent(animator)
            .addComponent(new MonsterAnimationController());

    bobo.setScale(1.2f,1.2f);
    logger.debug("Create a Bobo");
    return bobo;
  }

  public static Entity createPiranha(Entity target) {
    Entity piranha = createBaseNPC(target);
    BaseEntityConfig config = configs.ghost;

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/Piranha.atlas",
                            TextureAtlas.class));
    animator.addAnimation("down", 0.1f, Animation.PlayMode.LOOP);

    piranha
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
            .addComponent(animator)
            .addComponent(new MonsterAnimationController());

    piranha.setScale(1.2f,1.2f);
    logger.debug("Create a Piranha");
    return piranha;
  }
  /**
   * Creates a ghost entity.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createGhost(Entity target) {
    Entity ghost = createBaseNPC(target);
    BaseEntityConfig config = configs.ghost;

    AnimationRenderComponent animator =
        new AnimationRenderComponent(
            ServiceLocator.getResourceService().getAsset("images/ghost.atlas", TextureAtlas.class));
    animator.addAnimation("angry_float", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("float", 0.1f, Animation.PlayMode.LOOP);

    ghost
        .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
        .addComponent(animator)
        .addComponent(new GhostAnimationController());

    ghost.getComponent(AnimationRenderComponent.class).scaleEntity();

    return ghost;
  }

  /**
   * Creates a ghost king entity.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createGhostKing(Entity target) {
    Entity ghostKing = createBaseNPC(target);
    GhostKingConfig config = configs.ghostKing;

    AnimationRenderComponent animator =
        new AnimationRenderComponent(
            ServiceLocator.getResourceService()
                .getAsset("images/ghostKing.atlas", TextureAtlas.class));
    animator.addAnimation("float", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("angry_float", 0.1f, Animation.PlayMode.LOOP);

    ghostKing
        .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
        .addComponent(animator)
        .addComponent(new GhostAnimationController());

    ghostKing.getComponent(AnimationRenderComponent.class).scaleEntity();
    return ghostKing;
  }

  public static Entity createNeutralLives(Entity target) {
    Entity NeutralLives = new Entity();

    AITaskComponent aiComponent =
            new AITaskComponent()
                    .addTask(new WanderTask(new Vector2(2f, 2f), 2f));

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/bobo.atlas",
                            TextureAtlas.class));
    animator.addAnimation("float", 0.1f, Animation.PlayMode.LOOP);

    NeutralLives
            .addComponent(new PhysicsComponent())
            .addComponent(new PhysicsMovementComponent())
            .addComponent(new ColliderComponent())
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
            .addComponent(animator)
            .addComponent(aiComponent);

    animator.startAnimation("float");
    NeutralLives.setScale(2.4f, 2.4f);
    logger.debug("Create a Neutral creature");
    return NeutralLives;
  }
  /**
   * Creates a generic NPC to be used as a base entity by more specific NPC creation methods.
   *
   * @return entity
   */
  private static Entity createBaseNPC(Entity target) {
    AITaskComponent aiComponent =
        new AITaskComponent()
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f))
            .addTask(new ChaseTask(target, 10, 3f, 4f));

    Entity npc =
        new Entity()
            .addComponent(new PhysicsComponent())
            .addComponent(new PhysicsMovementComponent())
            .addComponent(new ColliderComponent())
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
            //.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 4f))
            .addComponent(aiComponent);

    PhysicsUtils.setScaledCollider(npc, 0.9f, 0.4f);
    return npc;
  }




  private NPCFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
