package com.deco2800.game.components.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;

public class GunAttackComponent extends Component {
    private short targetLayer;
    private float knockbackForce;
    private CombatStatsComponent combatStats;
    private HitboxComponent hitboxComponent;
    private boolean isAttacked = false;

    public GunAttackComponent(float knockbackForce){
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
        if (hitboxComponent.getFixture() != me) {
            // Not triggered by hitbox, ignore
            return;
        }
        System.out.println("trigger by hitbox");

        if (!PhysicsLayer.contains(targetLayer, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }
        System.out.println("match the target layer");

        // Try to attack target.
        Entity target = ((BodyUserData) other.getBody().getUserData()).entity;
        CombatStatsComponent targetStats = target.getComponent(CombatStatsComponent.class);
        if (targetStats != null) {
            targetStats.hit(combatStats);
            System.out.println("target touched");
        }
        // Apply knockback
        PhysicsComponent physicsComponent = target.getComponent(PhysicsComponent.class);
        if (physicsComponent != null && knockbackForce > 0f) {
            Body targetBody = physicsComponent.getBody();
            Vector2 direction = target.getCenterPosition().sub(entity.getCenterPosition());
            Vector2 impulse = direction.setLength(knockbackForce);
            targetBody.applyLinearImpulse(impulse, targetBody.getWorldCenter(), true);
            System.out.println("target back");
        }
    }
}
