package com.deco2800.game.components.tasks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.GdxGame;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.ai.tasks.Task;
import com.deco2800.game.components.npc.NpcInteractionDisplay;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.InventoryDisplayComponent;
import com.deco2800.game.components.player.entity.ClueItem;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.SwitchFactory;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;

public class DistanceTask extends DefaultTask implements PriorityTask {
    private final Entity target;
    private Entity owner;

    public DistanceTask(Entity target) {
        this.target = target;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }
    /**
     * Start waiting from now until duration has passed.
     */
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void update() {
        Vector2 to = target.getCenterPosition();
        Vector2 from = owner.getCenterPosition();
        float distance = to.dst(from);

        if (NpcInteractionDisplay.chapterNum == 2 && distance <= 0.5f && !SwitchFactory.isCollected) {
            var playerInventory = target.getComponent(InventoryComponent.class);
            if (playerInventory.contains(ClueItem.MERMAID_SCALE1) &
                    playerInventory.contains(ClueItem.MERMAID_SCALE2) &
                    playerInventory.contains(ClueItem.MERMAID_SCALE3) &
                    playerInventory.contains(ClueItem.MERMAID_SCALE4) &
                    playerInventory.contains(ClueItem.MERMAID_SCALE5)){

                playerInventory.remove(ClueItem.MERMAID_SCALE1);
                playerInventory.remove(ClueItem.MERMAID_SCALE2);
                playerInventory.remove(ClueItem.MERMAID_SCALE3);
                playerInventory.remove(ClueItem.MERMAID_SCALE4);
                playerInventory.remove(ClueItem.MERMAID_SCALE5);

                playerInventory.add(ClueItem.LACK);
                SwitchFactory.isCollected = true;
                status = Status.FINISHED;
            }
        }

        if (NpcInteractionDisplay.chapterNum == 3 && distance <= 0.5f && !SwitchFactory.isCollected3) {
            var playerInventory = target.getComponent(InventoryComponent.class);
            if (playerInventory.contains(ClueItem.MERMAID_SCALE1) &
                    playerInventory.contains(ClueItem.MERMAID_SCALE2) &
                    playerInventory.contains(ClueItem.MERMAID_SCALE3) &
                    playerInventory.contains(ClueItem.MERMAID_SCALE4) &
                    playerInventory.contains(ClueItem.MERMAID_SCALE5)){

                playerInventory.remove(ClueItem.MERMAID_SCALE1);
                playerInventory.remove(ClueItem.MERMAID_SCALE2);
                playerInventory.remove(ClueItem.MERMAID_SCALE3);
                playerInventory.remove(ClueItem.MERMAID_SCALE4);
                playerInventory.remove(ClueItem.MERMAID_SCALE5);

                playerInventory.add(ClueItem.LACK);
                SwitchFactory.isCollected3 = true;
                status = Status.FINISHED;
            }
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
