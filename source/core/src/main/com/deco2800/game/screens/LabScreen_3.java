package com.deco2800.game.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.map.Lab_2_Display;
import com.deco2800.game.components.map.Lab_3_Display;
import com.deco2800.game.components.map.MapDisplay;
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

/** The game screen containing the map */
public class LabScreen_3 extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LabScreen_3.class);

    private final GdxGame game;
    private final Renderer renderer;

    private static final String[] Lab_3_Textures = {
            "images/map/LAB/lab 3.png", "images/eviction_menu/exitButton.png","images/eviction_menu/exitButton_selected.png"

    };

    public LabScreen_3(GdxGame game) {
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
        resourceService.loadTextures(Lab_3_Textures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(Lab_3_Textures);
    }

    /**
     * Creates the first lab's ui including components for rendering ui elements to the screen and
     * capturing and handling ui input.
     */

    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        ui.addComponent(new Lab_3_Display(game)).addComponent(new InputDecorator(stage, 10));
        ServiceLocator.getEntityService().register(ui);
    }
}