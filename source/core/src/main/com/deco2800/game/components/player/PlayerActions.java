package com.deco2800.game.components.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.ServiceLocator;

/**
 * Action component for interacting with the player. Player events should be initialised in create()
 * and when triggered should call methods within this class.
 */
public class PlayerActions extends Component {
  private static final Vector2 MAX_SPEED = new Vector2(3f, 3f); // Metres per second
  private FistAttackComponent fistAttackComponent;
  private KnifeAttackComponent knifeAttackComponent;
  private GunAttackComponent gunAttackComponent;
  private PhysicsComponent physicsComponent;
  private Vector2 walkDirection = Vector2.Zero.cpy();
  private boolean moving = false;

  private boolean inventoryOpen = false;

  private boolean saveDisplayOpen = false;

  private GdxGame game;

  private InventoryDisplayComponent playerInventory;

  private SaveGameDisplay saveGameDisplay;

  private boolean isAttacked;


  public PlayerActions(GdxGame game) {
    this.game = game;
  }

  @Override
  public void create() {
    fistAttackComponent = entity.getComponent(FistAttackComponent.class);
    knifeAttackComponent = entity.getComponent(KnifeAttackComponent.class);
    gunAttackComponent = entity.getComponent(GunAttackComponent.class);
    physicsComponent = entity.getComponent(PhysicsComponent.class);
    entity.getEvents().addListener("walk", this::walk);
    entity.getEvents().addListener("walkStop", this::stopWalking);
    entity.getEvents().addListener("attack", this::attack);
    entity.getEvents().addListener("stopAttack", this::stopAttack);
    entity.getEvents().addListener("knifeAttack", this::KnifeAttack);
    entity.getEvents().addListener("stopKnifeAttack", this::StopKnifeAttack);
    entity.getEvents().addListener("gunAttack", this::GunAttack);
    entity.getEvents().addListener("stopGunAttack", this::StopGunAttack);
    entity.getEvents().addListener("openInventory", this::openInventory);
    entity.getEvents().addListener("openSaveDisplay", this::openSaveDisplay);
  }

  @Override
  public void update() {
    if (moving) {
      updateSpeed();
    }
  }

  private void updateSpeed() {
    Body body = physicsComponent.getBody();
    Vector2 velocity = body.getLinearVelocity();
    Vector2 desiredVelocity = walkDirection.cpy().scl(MAX_SPEED);
    // impulse = (desiredVel - currentVel) * mass
    Vector2 impulse = desiredVelocity.sub(velocity).scl(body.getMass());
    body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
  }

  /**
   * Moves the player towards a given direction.
   *
   * @param direction direction to move in
   */
  void walk(Vector2 direction) {
    this.walkDirection = direction;
    moving = true;
  }

  /**
   * Stops the player from walking.
   */
  void stopWalking() {
    this.walkDirection = Vector2.Zero.cpy();
    updateSpeed();
    moving = false;
  }

  /**
   * Makes the player attack.
   */
  private void attack() {
    Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/Impact4.ogg", Sound.class);
    attackSound.play();
    fistAttackComponent.attackStatus(true);

  }

  private void stopAttack() {
    fistAttackComponent.attackStatus(false);

  }

  private void KnifeAttack() {
    knifeAttackComponent.attackStatus(true);
  }

  private void StopKnifeAttack() {
    knifeAttackComponent.attackStatus(false);
  }

  private void GunAttack() {
    gunAttackComponent.attackStatus(true);

  }

  private void StopGunAttack() {
    gunAttackComponent.attackStatus(false);
  }

  /**
   * Opens and closes the inventory.
   */
  void openInventory() {
    if (inventoryOpen == true) {
      inventoryOpen = false;
      playerInventory.destroyInventory();
    } else {
      inventoryOpen = true;

      // logic to locate the player and get inventory
      Array<Entity> entities = ServiceLocator.getEntityService().getEntities();
      Entity player = null;
      for (Entity i: entities)
      {
        if (i.getComponent(InventoryComponent.class) != null)
        {
          // Assign entity to player
          player = i;
        }
      }
      playerInventory = new InventoryDisplayComponent(player.getComponent(InventoryComponent.class), game);
    }
  }

  void openSaveDisplay() {
    if (saveDisplayOpen == true) {
      saveDisplayOpen = false;
    } else {
      saveDisplayOpen = true;
      saveGameDisplay = new SaveGameDisplay();
    }
  }

  }

