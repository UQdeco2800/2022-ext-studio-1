package com.deco2800.game.components.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.utils.math.Vector2Utils;
import com.deco2800.game.music.MusicStuff;

/**
 * Input handler for the player for keyboard and touch (mouse) input.
 * This input handler only uses keyboard input.
 */
public class KeyboardPlayerInputComponent extends InputComponent {
  private final Vector2 walkDirection = Vector2.Zero.cpy();
  private static final String movementPath = "sounds/Movement_sound.wav";

  private boolean gunFired;

  public KeyboardPlayerInputComponent() {
    super(5);
  }

  /**
   * Triggers player events on specific keycodes.
   *
   * @return whether the input was processed
   * @see InputProcessor#keyDown(int)
   */
  @Override
  public boolean keyDown(int keycode) {
    switch (keycode) {
      case Keys.W:
        walkDirection.add(Vector2Utils.UP);
        triggerWalkEvent();
        entity.getEvents().trigger("up");
        MusicStuff.playMusic(movementPath, false);
        return true;
      case Keys.A:
        walkDirection.add(Vector2Utils.LEFT);
        triggerWalkEvent();
        entity.getEvents().trigger("left");
        MusicStuff.playMusic(movementPath, false);
        return true;
      case Keys.S:
        walkDirection.add(Vector2Utils.DOWN);
        triggerWalkEvent();
        entity.getEvents().trigger("down");
        MusicStuff.playMusic(movementPath, false);
        return true;
      case Keys.D:
        walkDirection.add(Vector2Utils.RIGHT);
        triggerWalkEvent();
        entity.getEvents().trigger("right");
        MusicStuff.playMusic(movementPath, false);
        return true;
      case Keys.SPACE:
        entity.getEvents().trigger("attack");
        return true;
      case Keys.J:
        if (!gunFired){
          entity.getEvents().trigger("gunAttack");
          gunFired = true;
          return true;
        }
        return true;
      case Keys.H:
        entity.getEvents().trigger("knifeAttack");
        return true;
      case Keys.I:
        entity.getEvents().trigger("openInventory");
        return true;
      case Keys.K:
        entity.getEvents().trigger("pickUp");
        return true;
      default:
        return false;
    }
  }

  /**
   * Triggers player events on specific keycodes.
   *
   * @return whether the input was processed
   * @see InputProcessor#keyUp(int)
   */
  @Override
  public boolean keyUp(int keycode) {
    switch (keycode) {
      case Keys.W:
        walkDirection.sub(Vector2Utils.UP);
        triggerWalkEvent();
        entity.getEvents().trigger("stopUp");
        return true;
      case Keys.A:
        walkDirection.sub(Vector2Utils.LEFT);
        triggerWalkEvent();
        entity.getEvents().trigger("stopLeft");
        return true;
      case Keys.S:
        walkDirection.sub(Vector2Utils.DOWN);
        triggerWalkEvent();
        entity.getEvents().trigger("stopDown");
        return true;
      case Keys.D:
        walkDirection.sub(Vector2Utils.RIGHT);
        triggerWalkEvent();
        entity.getEvents().trigger("stopRight");
        return true;
      case Keys.K:
        entity.getEvents().trigger("stopPickup");
        return true;
      case Keys.SPACE:
        entity.getEvents().trigger("stopAttack");
        return true;
      case Keys.J:
        entity.getEvents().trigger("stopGunAttack");
        return true;
      case Keys.H:
        entity.getEvents().trigger("stopKnifeAttack");
        return true;
      default:
        return false;
    }
  }

  private void triggerWalkEvent() {
    if (walkDirection.epsilonEquals(Vector2.Zero)) {
      entity.getEvents().trigger("walkStop");
    } else {
      entity.getEvents().trigger("walk", walkDirection);
    }
  }
}
