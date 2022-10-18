package com.deco2800.game.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.endingmenu.timeOverDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deco2800.game.GdxGame;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TimeIsOverScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TimeIsOverScreen.class);

    private final GdxGame game;
    private final Renderer renderer;

    public TimeIsOverScreen(GdxGame game) {
        this.game = game;

        logger.debug("Initialising settings screen services");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        ServiceLocator.registerTimeSource(new GameTime());

        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(5f, 5f);

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
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();

        ServiceLocator.clear();
    }

    /**
     * Creates the setting screen's ui including components for rendering ui elements to the screen
     * and capturing and handling ui input.
     */
    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        ui.addComponent(new timeOverDisplay(game)).addComponent(new InputDecorator(stage, 10));
        ServiceLocator.getEntityService().register(ui);
    }
}

