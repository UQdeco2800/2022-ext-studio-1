package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

/**
 * This class listens to events relevant to a ghost entity's state and plays the animation when one
 * of the events is triggered.
 */
public class PlayerAnimationController extends Component {
    AnimationRenderComponent animator;

    private boolean texturePresent = true;
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("up", this::animateUp);
        entity.getEvents().addListener("down", this::animateDown);
        entity.getEvents().addListener("left", this::animateLeft);
        entity.getEvents().addListener("right", this::animateRight);
        entity.getEvents().addListener("pickUp", this::animatePickup);
        entity.getEvents().addListener("stopUp", this::animateStand);
        entity.getEvents().addListener("stopLeft", this::animateStand);
        entity.getEvents().addListener("stopRight", this::animateStand);
        entity.getEvents().addListener("stopDown", this::animateStand);
        entity.getEvents().addListener("stopPickup", this::animateStand);
    }

    private void animateUp() {
        preAnimationCleanUp();
        animator.startAnimation("up");
    }

    private void animateDown() {
        preAnimationCleanUp();
        animator.startAnimation("down");
    }

    private void animateLeft() {
        preAnimationCleanUp();
        animator.startAnimation("left");
    }

    private void animateRight() {
        preAnimationCleanUp();
        animator.startAnimation("right");
    }

    private void animatePickup() {
        preAnimationCleanUp();
        animator.startAnimation("pickUp");
    }

    private void animateStand() {
        preAnimationCleanUp();
        animator.startAnimation("stand");
    }

    private void preAnimationCleanUp() {
        if(texturePresent) {
            animator.getEntity().getComponent(TextureRenderComponent.class).dispose();
            texturePresent = false;
        }
        animator.stopAnimation();
    }

}
