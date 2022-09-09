package com.deco2800.game.components.maingame;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.InventoryDisplayComponent;
import com.deco2800.game.entities.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to the Main Game Screen and does something when one of the
 * events is triggered.
 */
public class MainGameActions extends Component {
  private static final Logger logger = LoggerFactory.getLogger(MainGameActions.class);
  private GdxGame game;

  private InventoryDisplayComponent playerInventory;

  public MainGameActions(GdxGame game) {
    this.game = game;
  }

  @Override
  public void create() {
    entity.getEvents().addListener("exit", this::onExit);
    entity.getEvents().addListener("InventoryScreen", this::onInventoryScreen);
    entity.getEvents().addListener("NpcMenu", this::onNpcMenu);
    entity.getEvents().addListener("CountdownScreen", this::onCountdownScreen);
  }

  /**
   * Intended for loading a saved game state.
   * load npc menu.
   */
  private void onNpcMenu() {
    logger.info("Load npc menu");
    game.setScreen(GdxGame.ScreenType.NPC_EVICTION_MENU);
  }
  /**
   * Swaps to the Main Menu screen.
   */
  private void onExit() {
    logger.info("Exiting main game screen");
    game.setScreen(GdxGame.ScreenType.MAIN_MENU);
  }

  private void onInventoryScreen() {
    logger.info("Opening Inventory");
    Entity player = entity;
    playerInventory = new InventoryDisplayComponent(player.getComponent(InventoryComponent.class).getInventory());
  }

  private void onCountdownScreen() {
    logger.info("Opening countdown screen");
    game.setScreen(GdxGame.ScreenType.COUNTDOWN_SCREEN);
  }

}