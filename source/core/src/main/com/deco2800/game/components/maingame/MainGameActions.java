package com.deco2800.game.components.maingame;

import com.badlogic.gdx.utils.Array;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.countDownClock.countdownDisplay;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.InventoryDisplayComponent;
import com.deco2800.game.components.endingmenu.*;
import com.deco2800.game.components.player.PlayerProfileDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.music.MusicStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to the Main Game Screen and does something when one of the
 * events is triggered.
 */
public class MainGameActions extends Component {
  private static final Logger logger = LoggerFactory.getLogger(MainGameActions.class);
  private GdxGame game;

  public InventoryDisplayComponent playerInventory;

  private static final String buttonPath = "sounds/button.mp3";

  public MainGameActions(GdxGame game) {
    this.game = game;
  }

  @Override
  public void create() {
    entity.getEvents().addListener("exit", this::onExit);
    entity.getEvents().addListener("level", this::onLevel);
    entity.getEvents().addListener("InventoryScreen", this::onInventoryScreen);
    entity.getEvents().addListener("ending", this::onEnding);
    entity.getEvents().addListener("playerProfile", this::onPlayerProfile);
//    entity.getEvents().addListener("NpcMenu", this::onNpcMenu); [Deprecated]
  }

//  /**
//   * Intended for loading a saved game state.
//   * load npc menu.
//   */
//  private void onNpcMenu() { [Deprecated]
//    logger.info("Load npc menu");
//    game.setScreen(GdxGame.ScreenType.NPC_EVICTION_MENU);
//  }
  /**
   * Swaps to the Main Menu screen.
   */
  private void onExit() {
    MusicStuff.playMusic(buttonPath, false);

    game.setScreen(GdxGame.ScreenType.MAIN_MENU);
  }

  private void onLevel() {
    game.theGameScreen.switchLevel();
  }

  private void onInventoryScreen() {
    MusicStuff.playMusic(buttonPath, false);
    logger.info("Opening Inventory");

    // logic to locate the player and get inventory
    Array<Entity> entities = ServiceLocator.getEntityService().getEntities();
    logger.info(String.valueOf(entities));
    Entity player = null;
    for (Entity i: entities)
    {
      if (i.getComponent(InventoryComponent.class) != null)
      {
        // Assign entity to player
        player = i;
      }
//      else if (i.getComponent(countdownDisplay.class) != null) {
//        i.getComponent(countdownDisplay.class).increaseTime((float) 30.33);
//      }
    }
    playerInventory = new InventoryDisplayComponent(player.getComponent(InventoryComponent.class), game);

  }

  private void onPlayerProfile() {
    MusicStuff.playMusic(buttonPath, false);
    logger.info("Opening player profile");
    new PlayerProfileDisplay().create();
  }

  private void onEnding() {
    logger.info("Launching ending screen");
    game.setScreen(GdxGame.ScreenType.ENDING);
  }

}