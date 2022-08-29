package com.deco2800.game.components.mainmenu;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
//import com.deco2800.game.music.musicStuff;
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
    //String filepath = "Theatre-BGM.wav";
    //musicStuff musicObject = new musicStuff();
    //musicObject.playMusic(filepath);

    entity.getEvents().addListener("start", this::onStart);

    entity.getEvents().addListener("achievements", this::onAchievements);
    entity.getEvents().addListener("exit", this::onExit);
    entity.getEvents().addListener("settings", this::onSettings);
  }

  /**
   * Swaps to the Main Game screen.
   */
  private void onStart() {
    logger.info("Start game");
    game.setScreen(GdxGame.ScreenType.MAIN_GAME);

  }

  /**
   * Swaps to the Achievements screen.
   */
  private void onAchievements() {
    logger.info("Launching achievements screen");
    game.setScreen(GdxGame.ScreenType.ACHIEVEMENTS);
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
}
