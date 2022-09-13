package com.deco2800.game.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.achievements.AchievementsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.AchievementFactory;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.AchievementService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** The game screen containing the achievements. */
public class AchievementsScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AchievementsScreen.class);

    private final GdxGame game;
    private final Renderer renderer;

    private static final String[] UITextures = {
            "images/achievement/achievement_background.png",
            "images/achievement/back_button.png", "images/achievement/back_button_pressed.png",
            "images/achievement/last_page_button.png", "images/achievement/last_page_button_pressed.png",
            "images/achievement/next_page_button.png", "images/achievement/next_page_button_pressed.png"};

    private static final String[] achievementTextures = AchievementFactory.getTextures();

    public AchievementsScreen(GdxGame game) {
        this.game = game;

        logger.debug("Initialising achievements screen services");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        ServiceLocator.registerAchievementService(new AchievementService());
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
        resourceService.loadTextures(UITextures);
        resourceService.loadTextures(achievementTextures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(UITextures);
        resourceService.unloadAssets(achievementTextures);
    }

    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        ui.addComponent(new AchievementsDisplay(game)).addComponent(new InputDecorator(stage, 10));
        ServiceLocator.getEntityService().register(ui);
    }
}
