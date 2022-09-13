package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class NpcAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("up", this::animateUp);
        entity.getEvents().addListener("left", this::animateLeft);
        entity.getEvents().addListener("down", this::animateDown);
        entity.getEvents().addListener("right", this::animateRight);

    }

    void animateUp() {
        animator.startAnimation("up");
    }

    void animateLeft() {
        animator.startAnimation("left");
    }

    void animateRight() {
        animator.startAnimation("down");
    }

    void animateDown() {
        animator.startAnimation("right");
    }
}


