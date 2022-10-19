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
    public static final String UP_GUN_ATTACK = "gunUpAttack";
    public static final String DOWN_GUN_ATTACK = "gunDownAttack";
    public static final String LEFT_GUN_ATTACK = "gunLeftAttack";
    public static final String RIGHT_GUN_ATTACK = "gunRightAttack";
    public static final String UP_KNIFE_ATTACK = "knifeUpAttack";
    public static final String DOWN_KNIFE_ATTACK = "knifeDownAttack";
    public static final String LEFT_KNIFE_ATTACK = "knifeLeftAttack";
    public static final String RIGHT_KNIFE_ATTACK = "knifeRightAttack";

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
        entity.getEvents().addListener("gunAttack", this::animateGunAttack);
        entity.getEvents().addListener("stopGunAttack", this::animateStopGunAttack);
        entity.getEvents().addListener("knifeAttack", this::animateKnifeAttack);
        entity.getEvents().addListener("stopKnifeAttack", this::animateStopKnifeAttack);
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

    public void animateAttack() {
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

    private void animateGunAttack() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch(animationName) {
            case UP:
                animator.startAnimation("gunUpAttack");
                break;
            case LEFT:
                animator.startAnimation("gunLeftAttack");
                break;
            case RIGHT:
                animator.startAnimation("gunRightAttack");
                break;
            case DOWN:
            default:
                animator.startAnimation("gunDownAttack");
                break;
        }
    }

    private void animateStopGunAttack() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch (animationName) {
            case UP_GUN_ATTACK:
                animator.startAnimation("up");
                break;
            case LEFT_GUN_ATTACK:
                animator.startAnimation("left");
                break;
            case RIGHT_GUN_ATTACK:
                animator.startAnimation("right");
                break;
            case DOWN_GUN_ATTACK:
            default:
                animator.startAnimation("down");
                break;
        }
    }

    private void animateKnifeAttack() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch(animationName) {
            case UP:
                animator.startAnimation("knifeUpAttack");
                break;
            case LEFT:
                animator.startAnimation("knifeLeftAttack");
                break;
            case RIGHT:
                animator.startAnimation("knifeRightAttack");
                break;
            case DOWN:
            default:
                animator.startAnimation("knifeDownAttack");
                break;
        }
    }

    private void animateStopKnifeAttack() {
        animationName = animator.getCurrentAnimation();
        preAnimationCleanUp();
        switch (animationName) {
            case UP_KNIFE_ATTACK:
                animator.startAnimation("up");
                break;
            case LEFT_KNIFE_ATTACK:
                animator.startAnimation("left");
                break;
            case RIGHT_KNIFE_ATTACK:
                animator.startAnimation("right");
                break;
            case DOWN_KNIFE_ATTACK:
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
