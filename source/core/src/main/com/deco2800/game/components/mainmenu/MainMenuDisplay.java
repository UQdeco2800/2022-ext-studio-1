package com.deco2800.game.components.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An ui component for displaying the Main menu.
 */
public class MainMenuDisplay extends UIComponent {
  private static final Logger logger = LoggerFactory.getLogger(MainMenuDisplay.class);
  private static final float Z_INDEX = 2f;
  private Table table;

  @Override
  public void create() {
    super.create();
    addActors();
  }

  private void addActors() {
    table = new Table();
    table.setFillParent(true);
    Image title =
        new Image(
            ServiceLocator.getResourceService()
                .getAsset("images/game logo.png", Texture.class));

      Image background =
              new Image(
                      ServiceLocator.getResourceService()
                              .getAsset("images/main-background/main-background.png", Texture.class));

      background.setWidth(Gdx.graphics.getWidth());
      background.setHeight(Gdx.graphics.getHeight());

      stage.addActor(background);

    TextButton startBtn = new TextButton("start", skin);
    TextButton loadBtn = new TextButton("load", skin);
    TextButton storyline = new TextButton("storyline", skin);
    TextButton mapBtn = new TextButton("map", skin);
    TextButton tutorialBtn = new TextButton("tutorials", skin);
    TextButton achievementsBtn = new TextButton("achievements", skin);
    TextButton settingsBtn = new TextButton("settings", skin);
    TextButton exitBtn = new TextButton("exit", skin);


    // Triggers an event when the button is pressed
    startBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Start button clicked");
            entity.getEvents().trigger("start");
          }
        });

    loadBtn.addListener(
        new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("load game button clicked");
                entity.getEvents().trigger("load");
            }
        });



    storyline.addListener(
            new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    logger.debug("storyline button clicked");
                    entity.getEvents().trigger("storyline");
                }
            });

      mapBtn.addListener(
              new ChangeListener() {
                  @Override
                  public void changed(ChangeEvent changeEvent, Actor actor) {
                      logger.debug("map button clicked");
                      entity.getEvents().trigger("map");
                  }
              });

    tutorialBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Tutorial button clicked");
            entity.getEvents().trigger("tutorial");
          }
        });

    achievementsBtn.addListener(
            new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    logger.debug("Achievements button clicking");
                    entity.getEvents().trigger("achievements");
                }
            }
    );

    settingsBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Settings button clicked");
            entity.getEvents().trigger("settings");
          }
        });

    exitBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Exit button clicked");
            entity.getEvents().trigger("exit");
          }
        });

    table.add(title);
    table.row();
    table.add(startBtn).padTop(30f);
    table.row();
    table.add(loadBtn).padTop(15f);
    table.row();

    table.add(storyline).padTop(15f);
    table.row();
    table.add(mapBtn).padTop(15f);
    table.row();

    table.add(tutorialBtn).padTop(15f);
    table.row();
    table.add(achievementsBtn).padTop(15f);
    table.row();
    table.add(settingsBtn).padTop(15f);
    table.row();
    table.add(exitBtn).padTop(15f);
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
