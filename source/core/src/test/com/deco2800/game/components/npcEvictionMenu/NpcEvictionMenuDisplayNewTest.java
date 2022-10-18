package com.deco2800.game.components.npcEvictionMenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.countDownClock.countdownDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.extensions.GameExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(GameExtension.class)
public class NpcEvictionMenuDisplayNewTest {
    private static final Logger logger = LoggerFactory.getLogger(NpcEvictionMenuDisplayNewTest.class);
    private GdxGame game;

    private ForestGameArea forestGameArea;
    private static final String[] mainGameTextures = {
            "images/heart.png","images/eviction_menu/menuIcon_black.png",
            "images/eviction_menu/menuIcon_white.png"};
    private static final String IMAGES_PATH = "images/eviction_menu/";  //path of team7 images
    private static final String[] npcEvictionMenuTextures = { //path of team7 images
            IMAGES_PATH + "evictionMenu_background.png",IMAGES_PATH + "transparentBg.png",
            IMAGES_PATH + "exitButton.png", IMAGES_PATH + "exitButton_selected.png",
            IMAGES_PATH + "selectButton_single.png", IMAGES_PATH + "selectButton_selected.png",
            IMAGES_PATH + "evictionCard_single.png", IMAGES_PATH + "evictionCard_hover.png",
            IMAGES_PATH + "confirmBox.png",
            IMAGES_PATH + "confirmBtn_ok.png", IMAGES_PATH + "confirmBtn_ok1.png",
            IMAGES_PATH + "confirmBtn_cancel.png", IMAGES_PATH + "confirmBtn_cancel1.png",
            IMAGES_PATH + "infoWindow.png",
            IMAGES_PATH + "npcNereus.png", IMAGES_PATH + "npcNereus_hover.png",
            IMAGES_PATH + "npcHeph.png", IMAGES_PATH + "npcHeph_hover.png",
            IMAGES_PATH + "npcMetis.png", IMAGES_PATH + "npcMetis_hover.png",
            IMAGES_PATH + "npcDoris.png", IMAGES_PATH + "npcDoris_hover.png",
            IMAGES_PATH + "npcZoe.png", IMAGES_PATH + "npcZoe_hover.png",
            IMAGES_PATH + "npcAres.png", IMAGES_PATH + "npcAres_hover.png",
            IMAGES_PATH + "npcOrpheus.png", IMAGES_PATH + "npcOrpheus_hover.png",
            IMAGES_PATH + "npcZeus.png", IMAGES_PATH + "npcZeus_hover.png",
            IMAGES_PATH + "rightBox.png",
            IMAGES_PATH + "rightBtn.png", IMAGES_PATH + "rightBtn_H.png",
            IMAGES_PATH + "wrongBox1.png", IMAGES_PATH + "wrongBox2.png",
            IMAGES_PATH + "chanceBtn.png", IMAGES_PATH + "chanceBtn_H.png",
            IMAGES_PATH + "chanceBtn2.png", IMAGES_PATH + "chanceBtn2_H.png",
            IMAGES_PATH + "saveMessage.png"};

    public NpcEvictionMenuDisplayNewTest() {
    }

    @Test
    void healthTest(){
        ServiceLocator.registerResourceService(new ResourceService());
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(mainGameTextures);
        resourceService.loadTextures(npcEvictionMenuTextures);
        ServiceLocator.getResourceService().loadAll();
        Entity test_entity=new Entity();
        test_entity.addComponent(new NpcEvictionMenuDisplayNew(logger,ServiceLocator.getResourceService(),1000,500,forestGameArea,game))
                .addComponent(new CombatStatsComponent(100,0))
                .addComponent(new countdownDisplay(game));

        assertEquals(NpcEvictionMenuDisplayNew.NpcResultDialogType.RIGHT_BOX,
                test_entity.getComponent(NpcEvictionMenuDisplayNew.class).handleLogic("Ares"));
        assertEquals(0,test_entity.getComponent(NpcEvictionMenuDisplayNew.class).getErrorNum());
        //If the handleLogic function is called with the wrong name,
        // according to the logic we designed,
        // the errorNum will be increased by one, and the health and time will be reduced to 90% of the current value.
        //and finally it will return NpcResultDialogType.WRONG_BOX1
        int health = test_entity.getComponent(CombatStatsComponent.class).getHealth();
        float remainingTime =test_entity.getComponent(countdownDisplay.class).getRemainingTime();
        assertEquals(NpcEvictionMenuDisplayNew.NpcResultDialogType.WRONG_BOX1,
                test_entity.getComponent(NpcEvictionMenuDisplayNew.class).handleLogic("xxx"));
        assertEquals(health*0.9,test_entity.getComponent(CombatStatsComponent.class).getHealth());
        assertEquals(remainingTime*0.9f,test_entity.getComponent(countdownDisplay.class).getRemainingTime());
        assertEquals(1,test_entity.getComponent(NpcEvictionMenuDisplayNew.class).getErrorNum());

        //If the handleLogic function is called second time with the wrong name,
        // according to the logic we designed,
        // the errorNum will be increased by one, and the health and time will be reduced to 80% of the current value.
        //and finally it will return NpcResultDialogType.WRONG_BOX2
        health = test_entity.getComponent(CombatStatsComponent.class).getHealth();
        remainingTime =test_entity.getComponent(countdownDisplay.class).getRemainingTime();
        assertEquals(NpcEvictionMenuDisplayNew.NpcResultDialogType.WRONG_BOX2,
                test_entity.getComponent(NpcEvictionMenuDisplayNew.class).handleLogic("xxx"));
        assertEquals(health*0.8,test_entity.getComponent(CombatStatsComponent.class).getHealth());
        assertEquals(remainingTime*0.8f,test_entity.getComponent(countdownDisplay.class).getRemainingTime());
        assertEquals(2,test_entity.getComponent(NpcEvictionMenuDisplayNew.class).getErrorNum());

        //If the handleLogic function is called third time with the wrong name,
        // according to the logic we designed,
        // it will return NpcResultDialogType.LOSE and errorNum is 0.

        assertEquals(NpcEvictionMenuDisplayNew.NpcResultDialogType.LOSE,
                test_entity.getComponent(NpcEvictionMenuDisplayNew.class).handleLogic("xxx"));
        assertEquals(0,test_entity.getComponent(NpcEvictionMenuDisplayNew.class).getErrorNum());

        //If the handleLogic function is called when findKey is true
        // it will return NpcResultDialogType.WIN .
        test_entity.getComponent(NpcEvictionMenuDisplayNew.class).setFindKey(true);
        assertEquals(NpcEvictionMenuDisplayNew.NpcResultDialogType.WIN,
                test_entity.getComponent(NpcEvictionMenuDisplayNew.class).handleLogic("xxx"));

    }



}