package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class MonsterAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("wanderStart", this::animateWander);
        entity.getEvents().addListener("chaseStart", this::animateChase);
    }

    void animateChase() {
        animator.startAnimation("down");
    }

    void animateWander() {
        animator.startAnimation("down");
    }
}
