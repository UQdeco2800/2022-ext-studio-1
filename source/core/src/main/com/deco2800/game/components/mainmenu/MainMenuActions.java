package com.deco2800.game.components.mainmenu;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to the Main Menu Screen and does something when one of the
 * events is triggered.
 */
public class MainMenuActions extends Component {
  private static final Logger logger = LoggerFactory.getLogger(MainMenuActions.class);
  private GdxGame game;

  public MainMenuActions(GdxGame game) {
    this.game = game;
  }

  @Override
  public void create() {
    entity.getEvents().addListener("start", this::onStart);
    entity.getEvents().addListener("load", this::onLoad);
    entity.getEvents().addListener("tutorial", this::onTutorial);
    entity.getEvents().addListener("storyline", this::onStoryline);
    entity.getEvents().addListener("map", this::onMap);
    entity.getEvents().addListener("achievements", this::onAchievements);
    entity.getEvents().addListener("settings", this::onSettings);
    entity.getEvents().addListener("exit", this::onExit);

    entity.getEvents().addListener("ending", this::onEnding);
  }

  /**
   * Swaps to the Main Game screen.
   */
  private void onStart() {
    logger.info("Start game");
    //game.setGameTime(7260);
    game.setScreen(GdxGame.ScreenType.MAIN_GAME);
  }

  /**
   * Display a menu showing saved game states that can be loaded
   */
  private void onLoad() {
    logger.info("Load Game");
    game.setScreen(GdxGame.ScreenType.LOAD_GAME);
  }
  /**
   * Swaps to the Achievements screen.
   */
  private void onAchievements() {
    logger.info("Launching achievements screen");
    game.setScreen(GdxGame.ScreenType.ACHIEVEMENTS);
  }

  private void onStoryline() {
    logger.info("Start game");
    game.setScreen(GdxGame.ScreenType.STORYLINE);
  }

  private void onMap() {
    logger.info("Launching map screen");
    game.setScreen(GdxGame.ScreenType.MAP);
  }


  /**
   * Intended for displaying the games tutorials
   */
  private void onTutorial() {
    logger.info("Open Tutorial");
    game.setScreen(GdxGame.ScreenType.TUTORIAL);
  }

  /**
   * Exits the game.
   */
  private void onExit() {
    logger.info("Exit game");
    game.exit();
  }

  /**
   * Swaps to the Settings screen.
   */
  private void onSettings() {
    logger.info("Launching settings screen");
    game.setScreen(GdxGame.ScreenType.SETTINGS);
  }

  private void onEnding() {
    logger.info("Launching ending screen");
    game.setScreen(GdxGame.ScreenType.ENDING);
  }
}
