package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.map.NpcInteraction_Display;
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

public class NpcInteractionScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(NpcInteractionScreen.class);
    private final GdxGame game;
    private final Renderer renderer;

    private static final String[] mainGameScreenTestTextures = {
            "images/black.jpg", "images/map/LAB/whole lab blood.png", "images/black.jpg",
            "images/map/LAB/whole lab dark.png","images/eviction_menu/exitButton.png",
            "images/eviction_menu/exitButton_selected.png", "images/npc_interaction/dialog_box.png",
            "images/map/LAB/whole lab.png",
            "images/eviction_menu/transparentBg.png", "images/eviction_menu/evictionMenu_background.png",
            "images/eviction_menu/evictionCard_single.png", "images/eviction_menu/evictionCard_hover.png",
            "images/eviction_menu/selectButton_single.png", "images/eviction_menu/selectButton_selected.png",
            "images/eviction_menu/npcNereus.png", "images/eviction_menu/npcNereus_hover.png",
            "images/eviction_menu/npcHeph.png", "images/eviction_menu/npcHeph_hover.png",
            "images/eviction_menu/npcAres.png", "images/eviction_menu/npcAres_hover.png",
            "images/eviction_menu/npcDoris.png", "images/eviction_menu/npcDoris_hover.png",
            "images/eviction_menu/npcMetis.png", "images/eviction_menu/npcMetis_hover.png",
            "images/eviction_menu/npcOrpheus.png", "images/eviction_menu/npcOrpheus_hover.png",
            "images/eviction_menu/npcZoe.png", "images/eviction_menu/npcZoe_hover.png",
            "images/eviction_menu/confirmBox.png", "images/eviction_menu/confirmBtn_cancel.png",
            "images/eviction_menu/confirmBtn_cancel1.png", "images/eviction_menu/confirmBtn_ok.png",
            "images/eviction_menu/confirmBtn_ok1.png", "images/eviction_menu/infoWindow.png"
    };

    public NpcInteractionScreen(GdxGame game) {
        this.game = game;

        logger.debug("Initialising map screen services");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        renderer = RenderFactory.createRenderer();

        loadAssets();
        createUI();
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
        unloadAssets();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();

        ServiceLocator.clear();
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(mainGameScreenTestTextures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(mainGameScreenTestTextures);
    }

    /**
     * Creates the first lab's ui including components for rendering ui elements to the screen and
     * capturing and handling ui input.
     */

    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        ui.addComponent(new NpcInteraction_Display(game)).addComponent(new InputDecorator(stage, 10));
        ServiceLocator.getEntityService().register(ui);
    }
}
