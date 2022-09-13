package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.npcEvictionMenu.NpcEvictionMenuDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NpcEvictionMenu extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SettingsScreen.class);

    private final GdxGame game;
    private final Renderer renderer;
    private static final String IMAGES_PATH = "images/eviction_menu/";
    private static final String[] npcEvictionMenuTextures = {
            IMAGES_PATH + "evictionMenu_background.png",
            IMAGES_PATH + "exitButton.png", IMAGES_PATH + "exitButton_selected.png",
            IMAGES_PATH + "selectButton_single.png", IMAGES_PATH + "selectButton_selected.png",
            IMAGES_PATH + "evictionCard_single.png", IMAGES_PATH + "evictionCard_hover.png",
            IMAGES_PATH + "confirmBox.png",
            IMAGES_PATH + "confirmBtn_ok.png", IMAGES_PATH + "confirmBtn_ok1.png",
            IMAGES_PATH + "confirmBtn_cancel.png", IMAGES_PATH + "confirmBtn_cancel1.png",
            IMAGES_PATH + "infoWindow.png",
            IMAGES_PATH + "npcNereus.png", IMAGES_PATH + "npcNereus_hover.png",
            IMAGES_PATH + "npcHeph.png", IMAGES_PATH + "npcHeph_hover.png",
            IMAGES_PATH + "npcMetis.png", IMAGES_PATH + "npcMetis_hover.png"};


    public NpcEvictionMenu(GdxGame game) {
        this.game = game;

        logger.debug("Initialising npc menu screen services");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        renderer = RenderFactory.createRenderer();

        loadAssets();
        createUI();
    }

    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        ui.addComponent(new NpcEvictionMenuDisplay(game)).addComponent(new InputDecorator(stage, 10));
        ServiceLocator.getEntityService().register(ui);
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(npcEvictionMenuTextures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(npcEvictionMenuTextures);
    }

    @Override
    public void render(float delta) {
        ServiceLocator.getEntityService().update();
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void dispose() {

        renderer.dispose();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();
        ServiceLocator.clear();
    }
}
