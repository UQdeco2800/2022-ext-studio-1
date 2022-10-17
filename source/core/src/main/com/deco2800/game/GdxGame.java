package com.deco2800.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.deco2800.game.files.UserSettings;
import com.deco2800.game.screens.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.badlogic.gdx.Gdx.app;

/**
 * Entry point of the non-platform-specific game logic. Controls which screen is currently running.
 * The current screen triggers transitions to other screens. This works similarly to a finite state
 * machine (See the State Pattern).
 */
public class GdxGame extends Game {
  private static final Logger logger = LoggerFactory.getLogger(GdxGame.class);

  public boolean stopGame = false;

  public MainGameScreen theGameScreen;

  public float gameTimeVar =7260;

  @Override
  public void create() {
    logger.info("Creating game");
    loadSettings();

    // Sets background to light blue
    Gdx.gl.glClearColor(84/255f, 153/255f, 199/255f, 1);

    setScreen(ScreenType.MAIN_MENU);
  }

  /**
   * Loads the game's settings.
   */
  private void loadSettings() {
    logger.debug("Loading game settings");
    UserSettings.Settings settings = UserSettings.get();
    UserSettings.applySettings(settings);
  }

  /**
   * Sets the game's screen to a new screen of the provided type.
   * @param screenType screen type
   */
  public void setScreen(ScreenType screenType) {
    logger.info("Setting game screen to {}", screenType);
    Screen currentScreen = getScreen();
    if (currentScreen != null) {
      currentScreen.dispose();
    }
    setScreen(newScreen(screenType));
  }

  @Override
  public void dispose() {
    logger.debug("Disposing of current screen");
    getScreen().dispose();
  }

  /**
   * Create a new screen of the provided type.
   * @param screenType screen type
   * @return new screen
   */
  private Screen newScreen(ScreenType screenType) {
    switch (screenType) {
      case LAB_1:
        return new LabScreen_1(this);
      case LAB_2:
        return new LabScreen_2(this);
      case LAB_3:
        return new LabScreen_3(this);
      case LAB_4:
        return new LabScreen_4(this);
      case LAB_5:
        return new LabScreen_5(this);
      case LAB_HOUSE:
        return new LabHouseScreen(this);
      case MAIN_MENU:
        return new MainMenuScreen(this);
      case MAIN_GAME:
        theGameScreen = new MainGameScreen(this, stopGame,gameTimeVar);
        return theGameScreen;
      case LOAD_GAME:
        return new LoadSaveScreen(this);
      case ACHIEVEMENTS:
        return new AchievementsScreen(this);
      case SETTINGS:
        return new SettingsScreen(this);
      case STORYLINE:
        return new StorylineScreen(this);
      case TUTORIAL:
        return new TutorialScreen(this);
      case MAP:
        return new MapScreen(this);
      case COUNTDOWN_SCREEN:
        return new CountdownScreen(this);
      case ENDING:
        return new EndingMenuScreen(this);
      default:
        return null;
    }
  }

  public enum ScreenType {

    MAIN_MENU, MAIN_GAME, LOAD_GAME, ACHIEVEMENTS, STORYLINE, TUTORIAL, SETTINGS, MAP, COUNTDOWN_SCREEN, LAB_1, LAB_2,
    LAB_3, LAB_4, LAB_5, LAB_HOUSE, MAIN_GAME_Test, ENDING,

  }

  /**
   * Exit the game.
   */
  public void exit() {
    app.exit();
  }

  public void setGameTime(float theTime) {
    this.gameTimeVar = theTime;
  }

}
