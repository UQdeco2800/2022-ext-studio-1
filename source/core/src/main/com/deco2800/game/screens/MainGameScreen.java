package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.countDownClock.countdownDisplay;
import com.deco2800.game.components.maingame.MainGameActions;
import com.deco2800.game.components.npc.NpcInteractionDisplay;
import com.deco2800.game.components.npcEvictionMenu.NpcEvictionMenuDisplayNew;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.components.countDownClock.PausedWindow;
import com.deco2800.game.components.endingmenu.EndingMenuDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.configs.PlayerConfig;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.files.FileLoader;
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
import com.badlogic.gdx.audio.Music;

/**
 * The game screen containing the main game.
 *
 * <p>Details on libGDX screens: https://happycoding.io/tutorials/libgdx/game-screens
 */
public class MainGameScreen extends ScreenAdapter {
  private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
  //for player health control.
  private static final PlayerConfig stats =
          FileLoader.readClass(PlayerConfig.class, "configs/player.json");
  private static final String[] mainGameTextures = {
          "images/heart.png","images/eviction_menu/menuIcon_black.png",
          "images/eviction_menu/menuIcon_white.png", "images/npc_interaction/dialog_box.png"};
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
          IMAGES_PATH + "nereusTrans1.png", IMAGES_PATH + "nereusTrans2.png", IMAGES_PATH + "nereusTrans3.png",
          IMAGES_PATH + "npcHeph.png", IMAGES_PATH + "npcHeph_hover.png",
          IMAGES_PATH + "hephTrans1.png", IMAGES_PATH + "hephTrans2.png", IMAGES_PATH + "hephTrans3.png",
          IMAGES_PATH + "npcMetis.png", IMAGES_PATH + "npcMetis_hover.png",
          IMAGES_PATH + "metisTrans1.png", IMAGES_PATH + "metisTrans2.png", IMAGES_PATH + "metisTrans3.png",
          IMAGES_PATH + "npcDoris.png", IMAGES_PATH + "npcDoris_hover.png",
          IMAGES_PATH + "dorisTrans1.png", IMAGES_PATH + "dorisTrans2.png", IMAGES_PATH + "dorisTrans3.png",
          IMAGES_PATH + "npcZoe.png", IMAGES_PATH + "npcZoe_hover.png",
          IMAGES_PATH + "zoeTrans1.png", IMAGES_PATH + "zoeTrans2.png", IMAGES_PATH + "zoeTrans3.png",
          IMAGES_PATH + "npcAres.png", IMAGES_PATH + "npcAres_hover.png",
          IMAGES_PATH + "aresTrans1.png", IMAGES_PATH + "aresTrans2.png", IMAGES_PATH + "aresTrans3.png",
          IMAGES_PATH + "npcOrpheus.png", IMAGES_PATH + "npcOrpheus_hover.png",
          IMAGES_PATH + "orpheusTrans1.png", IMAGES_PATH + "orpheusTrans2.png", IMAGES_PATH + "orpheusTrans3.png",
          IMAGES_PATH + "npcZeus.png", IMAGES_PATH + "npcZeus_hover.png",
          IMAGES_PATH + "zeusTrans1.png", IMAGES_PATH + "zeusTrans2.png", IMAGES_PATH + "zeusTrans3.png",
          IMAGES_PATH + "correctTrans.png", IMAGES_PATH + "wrongTrans.png",
          IMAGES_PATH + "rightBox.png",
          IMAGES_PATH + "rightBtn.png", IMAGES_PATH + "rightBtn_H.png",
          IMAGES_PATH + "wrongBox1.png", IMAGES_PATH + "wrongBox2.png",
          IMAGES_PATH + "chanceBtn.png", IMAGES_PATH + "chanceBtn_H.png",
          IMAGES_PATH + "chanceBtn2.png", IMAGES_PATH + "chanceBtn2_H.png",
          IMAGES_PATH + "saveMessage.png", IMAGES_PATH + "transBg.png" };
  private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 7.5f);

  private final GdxGame game;
  private final Renderer renderer;
  private final PhysicsEngine physicsEngine;

  private ForestGameArea forestGameArea;

  private long timeSinceStart;

  private boolean stopGame;

  private  float gameDuration;

  private static final String backgroundMusic = "sounds/new.mp3";

  private boolean timeTime=true;



  public MainGameScreen(GdxGame game, boolean stop,float gameTime) {
    this.game = game;
    this.stopGame = stop;
    this.gameDuration =gameTime;

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

    logger.debug("Initialising main game screen entities");
    TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());
    this.forestGameArea = new ForestGameArea(terrainFactory, game);
    forestGameArea.create();

    createUI();
  }

  @Override
  public void render(float delta) {
    if (stopGame==true){
      renderer.render();
      ServiceLocator.getEntityService().update();
    } else {
      renderer.render();
      ServiceLocator.getEntityService().update();
      physicsEngine.update();
    }
    if(timeTime==false){
      System.out.println("Testing:Game is over");
      EndingMenuDisplay.setLose();
      game.setScreen(GdxGame.ScreenType.ENDING);
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
            //for control health add combatstatscomponent
            //for display health add PlayerStatsDisplay()
            .addComponent(new CombatStatsComponent(stats.health,stats.baseAttack))
            .addComponent(new PlayerStatsDisplay())
            .addComponent(new NpcEvictionMenuDisplayNew(logger,ServiceLocator.getResourceService(),
                    stage.getWidth(),stage.getHeight(), this.forestGameArea, this.game))
        .addComponent(new Terminal())
        .addComponent(inputComponent)
        .addComponent(new TerminalDisplay());

    ServiceLocator.getEntityService().register(ui);
  }

  public void switchLevel(){
    forestGameArea.dispose();
    TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());
    this.forestGameArea = new ForestGameArea(terrainFactory, game);
    forestGameArea.nextLevel();
    forestGameArea.create();
  }

  public void changeStatus() {

    stopGame=true;
    //ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class).stop();
    ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class).stop();



  }
  public void changeStatus2(){
    stopGame=false;
    ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class).play();

  }
  public void setterForCountDown(){
    timeTime =false;

  }

  public boolean getStatus() {
    return stopGame;
  }
}