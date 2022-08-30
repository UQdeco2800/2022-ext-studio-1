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

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("upStart", this::animateUp);
        entity.getEvents().addListener("downStart", this::animateDown);
        entity.getEvents().addListener("leftStart", this::animateLeft);
        entity.getEvents().addListener("rightStart", this::animateRight);
        entity.getEvents().addListener("stand", this::animateStand);
    }

    void animateUp() {
        animator.startAnimation("up");
    }

    void animateDown() {
        animator.startAnimation("down");
    }

    void animateLeft() {
        animator.startAnimation("left");
    }

    void animateRight() {
        animator.startAnimation("right");
    }

    void animateStand() {
        animator.stopAnimation();
        entity.addComponent(new TextureRenderComponent("images/player_front.png"));
    }
}
