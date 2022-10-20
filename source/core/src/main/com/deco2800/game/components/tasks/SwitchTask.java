package com.deco2800.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.GdxGame;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.components.countDownClock.TaskWindow;
import com.deco2800.game.components.npc.NpcInteractionDisplay;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.SwitchFactory;

public class SwitchTask extends DefaultTask implements PriorityTask {
    private final Entity target;
    private Entity owner;
    private GdxGame game;
    private TaskWindow pausedWindow;

    public SwitchTask(Entity target, GdxGame game) {
        this.target = target;
        this.game = game;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void update() {
        Vector2 to = target.getCenterPosition();
        Vector2 from = owner.getCenterPosition();
        float distance = to.dst(from);

        if (NpcInteractionDisplay.chapterNum == 1 && distance <= 1f && !SwitchFactory.isWorking
            && !SwitchFactory.isSwitchProcessing) {
            SwitchFactory.isSwitchProcessing = true;
            pausedWindow = new TaskWindow(game, "images/test4.png");
            pausedWindow.create();
        }

        if (SwitchFactory.isSwitchProcessing && NpcInteractionDisplay.chapterNum == 1 && distance <= 1f) {
            var playerInventory = target.getComponent(InventoryComponent.class);

            if (playerInventory.contains(SwitchFactory.BATTERY_ID)
                    && playerInventory.count(SwitchFactory.BATTERY_ID) == 3
            && playerInventory.contains(SwitchFactory.TOOL_ID)){
                playerInventory.remove(SwitchFactory.TOOL_ID);
                playerInventory.remove(SwitchFactory.BATTERY_ID, 3);
                SwitchFactory.isWorking = true;
                SwitchFactory.isSwitchProcessing = false;

                pausedWindow = new TaskWindow(game, "images/test5.png");
                pausedWindow.create();
            }
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
