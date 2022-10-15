package com.deco2800.game.components.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.factories.NPCFactory;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TriggerDialogComponent extends Component {

    private static final Logger logger = LoggerFactory.getLogger(TriggerDialogComponent.class);
    private short targetLayer;

    private NpcInteractionDisplay npcInteractionDisplay;

    private InputListener listener;

    private NPCFactory.CommunicativeNpcName name;



    public TriggerDialogComponent(short targetLayer, NpcInteractionDisplay npcInteractionDisplay, NPCFactory.CommunicativeNpcName name) {
        this.targetLayer = targetLayer;
        this.npcInteractionDisplay = npcInteractionDisplay;
        this.name = name;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
        entity.getEvents().addListener("collisionEnd", this::onCollisionEnd);
    }

    private void onCollisionEnd(Fixture me, Fixture other) {

        if (!PhysicsLayer.contains(targetLayer, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }
        Stage stage = ServiceLocator.getRenderService().getStage();
        if (listener != null) {
            stage.removeListener(listener);
        }

    }

    private void onCollisionStart(Fixture me, Fixture other) {
        if (!PhysicsLayer.contains(targetLayer, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }
        Stage stage = ServiceLocator.getRenderService().getStage();
        listener = new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.B) {
                    // TODO add trigger dialog
                    switch (name) {
                        
                    }
                    logger.info("trigger dialog");
                }
                return false;
            }
        };
        stage.addListener(listener);
    }
}