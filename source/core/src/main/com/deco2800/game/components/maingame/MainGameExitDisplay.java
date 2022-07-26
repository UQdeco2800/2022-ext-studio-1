package com.deco2800.game.components.maingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.npcEvictionMenu.NpcEvictionMenuDisplayNew;
import com.deco2800.game.services.ServiceLocator;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.music.MusicStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Provider;

/**
 * Displays a button to exit the Main Game screen to the Main Menu screen.
 */
public class MainGameExitDisplay extends UIComponent {
  private static final Logger logger = LoggerFactory.getLogger(MainGameExitDisplay.class);
  private static final float Z_INDEX = 2f;
  private Table table;
  private static final int NPC_MENU_BUTTON_WIDTH = 200;
  private static final int NPC_MENU_BUTTON_HEIGHT = 150;
  private static final String buttonPath = "sounds/button.mp3";

  @Override
  public void create() {
    super.create();
    addActors();
  }

  private void addActors() {
    table = new Table();
    table.top().right();
    table.setFillParent(true);
    /** build new style eviction menu button */
    Button.ButtonStyle styleEvictionMenu = new Button.ButtonStyle();
    styleEvictionMenu.over = new TextureRegionDrawable(
            ServiceLocator.getResourceService()
            .getAsset("images/eviction_menu/menuIcon_black.png",Texture.class));
    //here is for button effect when you pressed on button
    styleEvictionMenu.up = new TextureRegionDrawable(
            ServiceLocator.getResourceService()
            .getAsset("images/eviction_menu/menuIcon_white.png",Texture.class));
    Button npcMenuBtn = new Button(styleEvictionMenu);

    TextButton mainMenuBtn = new TextButton("Exit", skin);

    TextButton inventoryButton = new TextButton("Inventory", skin);

    TextButton LevelButton = new TextButton("Level", skin);


    /**
     * Button to display player profile
     */

    TextButton profileButton = new TextButton("Player Profile", skin);

    profileButton.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent event, Actor actor) {
                logger.debug("profile button clicked");
                entity.getEvents().trigger("playerProfile");
              }
            }
    );

    /**
     * player profile button code block ends here.
     */

    // Triggers an event when the button is pressed.
    mainMenuBtn.addListener(
      new ChangeListener() {
        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
          logger.debug("Exit button clicked");
          entity.getEvents().trigger("exit");
        }
      });

      LevelButton.addListener(
              new ChangeListener() {
                  @Override
                  public void changed(ChangeEvent changeEvent, Actor actor) {
                      logger.debug("Exit button clicked");
                      entity.getEvents().trigger("level");
                  }
              });

    npcMenuBtn.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                  MusicStuff.playMusic("sounds/OpenEvictionMenu.wav", false);
             //in this component we call NpcEvictionMenuDisplayNew component by using its mother entity(i.e MainGameScrenn ui entity).
                  stage.addActor(entity.getComponent(NpcEvictionMenuDisplayNew.class).creatEvictionMenu());
              //NpcEvictionMenuDisplayNew npc = new NpcEvictionMenuDisplayNew(logger,ServiceLocator.getResourceService(),stage.getWidth(),stage.getHeight()
             //   stage.addActor(npc.creatEvictionMenu());
              }
            });



    inventoryButton.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent event, Actor actor) {
                logger.debug("Inventory button clicked");
                entity.getEvents().trigger("InventoryScreen");
              }
            });




    table.add(mainMenuBtn).padTop(10f).padRight(10f);
    table.row();
    table.add(npcMenuBtn).padTop(15f).width(NPC_MENU_BUTTON_WIDTH).height(NPC_MENU_BUTTON_HEIGHT);
    table.row();
    table.row();
    table.add(inventoryButton).padTop(10f).padRight(10f);
//
    table.row();
    table.add(profileButton).padTop(20f).padRight(10f);
    table.row();
      table.row();
      table.add(LevelButton).padTop(10f).padRight(10f);

    stage.addActor(table);
  }

  @Override
  public void draw(SpriteBatch batch) {
    // draw is handled by the stage
  }

  @Override
  public float getZIndex() {
    return Z_INDEX;
  }

  @Override
  public void dispose() {
    table.clear();
    super.dispose();
  }
}
