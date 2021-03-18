package com.deco2800.game.ecs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.deco2800.game.services.ServiceLocator;

/**
 * Core entity class. Entities exist in the game and are updated each frame. All entities have a
 * position and scale, but have no default behaviour. Components should be added to an entity to
 * give it specific behaviour. This class should not be inherited or modified directly.
 *
 * Example use:
 * <pre>
 * Entity player = new Entity()
 *   .addComponent(new RenderComponent())
 *   .addComponent(new PlayerControllerComponent());
 * ServiceLocator.getEntityService().register(player);
 * </pre>
 */
public class Entity {
  private static int nextId = 0;

  private final int id;
  private final IntMap<Component> components;

  private boolean enabled = true;
  private boolean created = false;
  private Vector2 position = Vector2.Zero;
  private Vector2 scale = new Vector2(1, 1);
  private Array<Component> createdComponents;

  public Entity() {
    components = new IntMap<>(4);
    id = nextId;
    nextId++;
  }

  /**
   * Enable or disable an entity. Disabled entities do not run update() or earlyUpdate() on their
   * components, but can still be disposed.
   * @param enabled true for enable, false for disable.
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Get the entity's game position.
   * @return position
   */
  public Vector2 getPosition() {
    return position;
  }

  /**
   * Set the entity's game position. Avoid calling this when using physics.
   * @param position new position.
   */
  public void setPosition(Vector2 position) {
    this.position = position;
  }

  /**
   * Set the entity's game position. Avoid calling this when using physics.
   * @param x new x position
   * @param y new y position
   */
  public void setPosition(float x, float y) {
    this.position.x = x;
    this.position.y = y;
  }

  /**
   * Get the entity's scale. Used for rendering and physics bounding box calculations.
   * @return Scale in x and y directions. 1 = 1 metre.
   */
  public Vector2 getScale() {
    return scale;
  }

  /**
   * Set the entity's scale.
   * @param scale new scale in metres
   */
  public void setScale(Vector2 scale) {
    this.scale = scale;
  }

  /**
   * Set the entity's scale.
   * @param x width in metres
   * @param y height in metres
   */
  public void setScale(float x, float y) {
    this.scale.x = x;
    this.scale.y = y;
  }

  /**
   * Get a component of type T on the entity.
   * @param type The component class, e.g. RenderComponent.class
   * @param <T> The component type, e.g. RenderComponent
   * @return The entity component, or null if nonexistent.
   */
  @SuppressWarnings("unchecked")
  public <T extends Component> T getComponent(Class<T> type) {
    ComponentType componentType = ComponentType.getFrom(type);
    return (T) components.get(componentType.getId());
  }

  /**
   * Add a component to the entity. Can only be called before the entity is registered in the world.
   * @param component The component to add. Only one component of a type can be added to an entity.
   * @return Itself
   */
  public Entity addComponent(Component component) {
    if (created) {
      throw new RuntimeException("Attempted to add component after entity creation");
    }
    ComponentType componentType = ComponentType.getFrom(component.getClass());
    if (components.containsKey(componentType.getId())) {
      throw new RuntimeException("Attempted to add two components of the same type to entity");
    }
    components.put(componentType.getId(), component);

    return this;
  }

  /**
   * Dispose of the entity. This will dispose of all components on this entity.
   */
  public void dispose() {
    for (Component component : createdComponents) {
      component.dispose();
    }
    ServiceLocator.getEntityService().unregister(this);
  }

  /**
   * Create the entity and start running. This is called when the entity is registered in the world,
   * and should not be called manually.
   */
  public void create() {
    if (created) {
      throw new RuntimeException("Entity was created twice");
    }
    createdComponents = components.values().toArray();
    for (Component component : createdComponents) {
      component.setEntity(this);
      component.create();
    }
    created = true;
  }

  /**
   * Perform an early update on all components. This is called by the entity service and should not
   * be called manually.
   */
  public void earlyUpdate() {
    if (!enabled) {
      return;
    }
    for (Component component : createdComponents) {
      component.triggerEarlyUpdate();
    }
  }

  /**
   * Perform an update on all components. This is called by the entity service and should not
   * be called manually.
   */
  public void update() {
    if (!enabled) {
      return;
    }
    for (Component component : createdComponents) {
      component.triggerUpdate();
    }
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Entity && ((Entity) obj).id == this.id);
  }
}
