package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class MonsterAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("down", this::animateDown);
        entity.getEvents().addListener("up", this::animateUp);
        entity.getEvents().addListener("left", this::animateLeft);
        entity.getEvents().addListener("right", this::animateRight);
    }

    void animateDown() {
        animator.startAnimation("down");
    }

    void animateUp() {
        animator.startAnimation("up");
    }
    void animateLeft() {
        animator.startAnimation("left");
    }
    void animateRight() {
        animator.startAnimation("right");
    }
}
