package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.components.countDownClock.countdownDisplay;
import com.deco2800.game.components.maingame.MainGameActions;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.AchievementService;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.terminal.Terminal;
import com.deco2800.game.ui.terminal.TerminalDisplay;
import com.deco2800.game.components.maingame.MainGameExitDisplay;
import com.deco2800.game.components.gamearea.PerformanceDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The game screen containing the main game.
 *
 * <p>Details on libGDX screens: https://happycoding.io/tutorials/libgdx/game-screens
 */
public class MainGameScreen extends ScreenAdapter {
  private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
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
  private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 7.5f);

  private final GdxGame game;
  private final Renderer renderer;
  private final PhysicsEngine physicsEngine;

  private long timeSinceStart;

  private  boolean stopGame;

  public MainGameScreen(GdxGame game, boolean stop) {
    this.game = game;
    this.stopGame = stop;

    logger.debug("Initialising main game screen services");
    ServiceLocator.registerTimeSource(new GameTime());

    PhysicsService physicsService = new PhysicsService();
    ServiceLocator.registerPhysicsService(physicsService);
    physicsEngine = physicsService.getPhysics();

    ServiceLocator.registerInputService(new InputService());
    ServiceLocator.registerResourceService(new ResourceService());

    ServiceLocator.registerEntityService(new EntityService());
    ServiceLocator.registerRenderService(new RenderService());
    ServiceLocator.registerAchievementService(new AchievementService());

    renderer = RenderFactory.createRenderer();
    renderer.getCamera().getEntity().setPosition(CAMERA_POSITION);
    renderer.getDebug().renderPhysicsWorld(physicsEngine.getWorld());

    loadAssets();
    createUI();

    logger.debug("Initialising main game screen entities");
    TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());
    ForestGameArea forestGameArea = new ForestGameArea(terrainFactory);
    forestGameArea.create();
  }

  @Override
  public void render(float delta) {
    //physicsEngine.update();
    //ServiceLocator.getEntityService().update();
    //renderer.render();
    if (stopGame==true){
      renderer.render();
      ServiceLocator.getEntityService().update();





    }else {
      renderer.render();
      ServiceLocator.getEntityService().update();
      physicsEngine.update();




    }
  }

  @Override
  public void resize(int width, int height) {
    renderer.resize(width, height);
    logger.trace("Resized renderer: ({} x {})", width, height);
  }

  @Override
  public void pause() {
    logger.info("Game paused");
  }

  @Override
  public void resume() {
    logger.info("Game resumed");
  }

  @Override
  public void dispose() {
    logger.debug("Disposing main game screen");

    renderer.dispose();
    unloadAssets();

    ServiceLocator.getEntityService().dispose();
    ServiceLocator.getRenderService().dispose();
    ServiceLocator.getResourceService().dispose();
    ServiceLocator.getAchievementService().dispose();

    ServiceLocator.clear();
  }

  public GdxGame getGame() {
    return this.game;
  }

  private void loadAssets() {
    logger.debug("Loading assets");
    ResourceService resourceService = ServiceLocator.getResourceService();
    resourceService.loadTextures(mainGameTextures);
    resourceService.loadTextures(npcEvictionMenuTextures);
    ServiceLocator.getResourceService().loadAll();
  }

  private void unloadAssets() {
    logger.debug("Unloading assets");
    ResourceService resourceService = ServiceLocator.getResourceService();
    resourceService.unloadAssets(mainGameTextures);
    resourceService.unloadAssets(npcEvictionMenuTextures);
  }

  /**
   * Creates the main game's ui including components for rendering ui elements to the screen and
   * capturing and handling ui input.
   */
  private void createUI() {
    logger.debug("Creating ui");
    Stage stage = ServiceLocator.getRenderService().getStage();
    InputComponent inputComponent =
        ServiceLocator.getInputService().getInputFactory().createForTerminal();

    this.timeSinceStart = ServiceLocator.getTimeSource().getTime();
    logger.info("time passed since game started: {}", this.timeSinceStart);

    Entity ui = new Entity();
    ui.addComponent(new InputDecorator(stage, 10))
        .addComponent(new PerformanceDisplay())
        .addComponent(new MainGameActions(this.game))
        .addComponent(new MainGameExitDisplay())
            .addComponent(new countdownDisplay(this.game))
        .addComponent(new Terminal())
        .addComponent(inputComponent)
        .addComponent(new TerminalDisplay());

    ServiceLocator.getEntityService().register(ui);
  }
  public void changeStatus() {

    stopGame=true;

  }
  public void changeStatus2(){
    stopGame=false;

  }
}