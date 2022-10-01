package com.deco2800.game.components.npcEvictionMenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
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
            IMAGES_PATH + "rightBox.png",
            IMAGES_PATH + "rightBtn.png", IMAGES_PATH + "rightBtn_H.png",
            IMAGES_PATH + "wrongBox1.png", IMAGES_PATH + "wrongBox2.png",
            IMAGES_PATH + "chanceBtn.png", IMAGES_PATH + "chanceBtn_H.png",
            IMAGES_PATH + "chanceBtn2.png", IMAGES_PATH + "chanceBtn2_H.png"};
    @Test
    void healthTest(){
        ServiceLocator.registerResourceService(new ResourceService());
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(mainGameTextures);
        resourceService.loadTextures(npcEvictionMenuTextures);
        ServiceLocator.getResourceService().loadAll();


    }



}