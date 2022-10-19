package com.deco2800.game.components.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.npc.GhostAnimationController;
import com.deco2800.game.components.npc.MonsterAnimationController;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.NPCFactory;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class FistAttackComponent extends Component {
    private static final Logger logger = LoggerFactory.getLogger(FistAttackComponent.class);
    private short targetLayer;
    private float knockbackForce;
    private CombatStatsComponent combatStats;
    private HitboxComponent hitboxComponent;
    private boolean isAttacked = false;
    public FistAttackComponent(float knockbackForce){
        this.targetLayer = PhysicsLayer.NPC;
        this.knockbackForce = knockbackForce;
    }

    public void create() {
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
        combatStats = entity.getComponent(CombatStatsComponent.class);
        hitboxComponent = entity.getComponent(HitboxComponent.class);
    }

    public void attackStatus(boolean status) {
        this.isAttacked = status;
    }

    private void onCollisionStart(Fixture me, Fixture other) {
        if (!isAttacked){
            return;
        }
        logger.info("can attack");
        if (hitboxComponent.getFixture() != me) {
            // Not triggered by hitbox, ignore
            return;
        }
        logger.info("triggered by hitbox");
        if (!PhysicsLayer.contains(targetLayer, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }
        logger.info("match target layer");
        // Try to attack target.
        Entity target = ((BodyUserData) other.getBody().getUserData()).entity;
        CombatStatsComponent targetStats = target.getComponent(CombatStatsComponent.class);
        if (targetStats != null) {
            targetStats.hit(combatStats);
            logger.info("attack target");
        }
        // Apply knockback
        PhysicsComponent physicsComponent = target.getComponent(PhysicsComponent.class);
        if (physicsComponent != null && knockbackForce > 0f) {
            Body targetBody = physicsComponent.getBody();
            Vector2 direction = target.getCenterPosition().sub(entity.getCenterPosition());
            Vector2 impulse = direction.setLength(knockbackForce);
            targetBody.applyLinearImpulse(impulse, targetBody.getWorldCenter(), true);
            logger.info("target knockback");
        }
    }
}
