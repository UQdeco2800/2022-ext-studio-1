package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

/**
 * This class listens to events relevant to a ghost entity's state and plays the animation when one
 * of the events is triggered.
 */
public class PlayerAnimationController extends Component {
    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String UP_ATTACK = "upAttack";
    public static final String DOWN_ATTACK = "downAttack";
    public static final String LEFT_ATTACK = "leftAttack";
    public static final String RIGHT_ATTACK = "rightAttack";

    AnimationRenderComponent animator;

    String animationName;

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
        entity.getEvents().addListener("stopUp", this::animateUp);
        entity.getEvents().addListener("stopLeft", this::animateLeft);
        entity.getEvents().addListener("stopRight", this::animateRight);
        entity.getEvents().addListener("stopDown", this::animateDown);
        entity.getEvents().addListener("stopPickup", this::animateStand);
        entity.getEvents().addListener("attack", this::animateAttack);
        entity.getEvents().addListener("stopAttack", this::animateStopAttack);
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

    private void animateAttack() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch(animationName) {
            case UP:
                animator.startAnimation("upAttack");
                break;
            case LEFT:
                animator.startAnimation("leftAttack");
                break;
            case RIGHT:
                animator.startAnimation("rightAttack");
                break;
            case DOWN:
            default:
                animator.startAnimation("downAttack");
                break;
        }
    }

    private void animateStopAttack() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch (animationName) {
            case UP_ATTACK:
                animator.startAnimation("up");
                break;
            case LEFT_ATTACK:
                animator.startAnimation("left");
                break;
            case RIGHT_ATTACK:
                animator.startAnimation("right");
                break;
            case DOWN_ATTACK:
            default:
                animator.startAnimation("down");
                break;
        }
    }

    private void animateStand() {
        preAnimationCleanUp();
        animator.startAnimation("down");
    }

    private void preAnimationCleanUp() {
        if(texturePresent) {
            animator.getEntity().getComponent(TextureRenderComponent.class).dispose();
            texturePresent = false;
        }
        animator.stopAnimation();
    }

}
