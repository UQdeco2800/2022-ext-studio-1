package com.deco2800.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.raycast.RaycastHit;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.services.ServiceLocator;

/** Chases a target entity until they get too far away or line of sight is lost */
public class ChaseTask extends DefaultTask implements PriorityTask {
  private final Entity target;
  private final int priority;
  private final float viewDistance;
  private final float maxChaseDistance;
  private final PhysicsEngine physics;
  private final DebugRenderer debugRenderer;
  private final RaycastHit hit = new RaycastHit();
  private MovementTask movementTask;
  private Vector2 startPos;
  private float x;
  private float y;

  /**
   * @param target The entity to chase.
   * @param priority Task priority when chasing (0 when not chasing).
   * @param viewDistance Maximum distance from the entity at which chasing can start.
   * @param maxChaseDistance Maximum distance from the entity while chasing before giving up.
   */
  public ChaseTask(Entity target, int priority, float viewDistance, float maxChaseDistance) {
    this.target = target;
    this.priority = priority;
    this.viewDistance = viewDistance;
    this.maxChaseDistance = maxChaseDistance;
    physics = ServiceLocator.getPhysicsService().getPhysics();
    debugRenderer = ServiceLocator.getRenderService().getDebug();
  }

  @Override
  public void start() {
    super.start();
    startPos = owner.getEntity().getPosition();
    this.x = startPos.x;
    this.y = startPos.y;
    movementTask = new MovementTask(target.getPosition());
    movementTask.create(owner);
    movementTask.start();
    if (this.x < movementTask.returnX() && this.y < movementTask.returnY()){
      this.owner.getEntity().getEvents().trigger("up");
    } else if (this.x < movementTask.returnX() && this.y > movementTask.returnY()) {
      this.owner.getEntity().getEvents().trigger("down");
    } else if (this.x < movementTask.returnX() && this.y == movementTask.returnY()) {
      this.owner.getEntity().getEvents().trigger("right");
    }else if (this.x == movementTask.returnX() && this.y < movementTask.returnY()) {
      this.owner.getEntity().getEvents().trigger("up");
    }else if (this.x == movementTask.returnX() && this.y > movementTask.returnY()){
      this.owner.getEntity().getEvents().trigger("down");
    }else if (this.x > movementTask.returnX() && this.y == movementTask.returnY()){
      this.owner.getEntity().getEvents().trigger("left");
    }else if (this.x > movementTask.returnX() && this.y < movementTask.returnY()){
      this.owner.getEntity().getEvents().trigger("up");
    }else if (this.x > movementTask.returnX() && this.y > movementTask.returnY()){
      this.owner.getEntity().getEvents().trigger("down");
    } else {
      this.owner.getEntity().getEvents().trigger("down");
    }
  }

  @Override
  public void update() {
    movementTask.setTarget(target.getPosition());
    movementTask.update();
    if (movementTask.getStatus() != Status.ACTIVE) {
      movementTask.start();
    }
  }

  @Override
  public void stop() {
    super.stop();
    movementTask.stop();
  }

  @Override
  public int getPriority() {
    if (status == Status.ACTIVE) {
      return getActivePriority();
    }

    return getInactivePriority();
  }

  private float getDistanceToTarget() {
    return owner.getEntity().getPosition().dst(target.getPosition());
  }

  private int getActivePriority() {
    float dst = getDistanceToTarget();
    if (dst > maxChaseDistance || !isTargetVisible()) {
      return -1; // Too far, stop chasing
    }
    return priority;
  }

  private int getInactivePriority() {
    float dst = getDistanceToTarget();
    if (dst < viewDistance && isTargetVisible()) {
      return priority;
    }
    return -1;
  }

  private boolean isTargetVisible() {
    Vector2 from = owner.getEntity().getCenterPosition();
    Vector2 to = target.getCenterPosition();

    // If there is an obstacle in the path to the player, not visible.
    if (physics.raycast(from, to, PhysicsLayer.OBSTACLE, hit)) {
      debugRenderer.drawLine(from, hit.point);
      return false;
    }
    debugRenderer.drawLine(from, to);
    return true;
  }
}
