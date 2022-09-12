package com.deco2800.game.components.player;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

/**
 * Used to determine if object is consumable
 */
public class ConsumeableItemComponent extends Component {
  private int timeIncrease;

  /**
   * Create a component which is used to increase the time
   * @param timeIncrease The physics layer of the target's collider.
   */
  public ConsumeableItemComponent(int timeIncrease) {
    this.timeIncrease = timeIncrease;
  }

  @Override
  public void create() {
  }

  public int increaseTime()
  {
    return this.timeIncrease;
  }
}

