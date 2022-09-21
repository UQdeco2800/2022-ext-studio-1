package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.services.ServiceLocator;

import java.util.HashMap;

public class SwitchComponent extends Component {

//    private HitboxComponent hitboxComponent;

    @Override
    public void create() {
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
    }

    private void onCollisionStart(Fixture me, Fixture other) {
//        if (hitboxComponent.getFixture() != me || !PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits) ) {
//            // Not triggered by hitbox, ignore or If interacting with anything that is not the player return
//            return;
//        }
//        Entity target = ((BodyUserData) other.getBody().getUserData()).entity;
//        InventoryComponent playerInventory = target.getComponent(InventoryComponent.class);
//
//
//        HashMap<Integer, Integer> inventoryHashMap = playerInventory.inventoryHashMap;
        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        Dialog dialog = new Dialog("Task", uiSkin, "dialog") {
            public void result(Object obj) {
                System.out.println("result "+ obj);
            }
        };
        dialog.text("You need to find three batteries and one tool");
        dialog.button("Ok", true); //sends "true" as the result
        dialog.show(ServiceLocator.getRenderService().getStage());
    }
}
