package com.deco2800.game.components.mainmenu.storyline;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame;
import com.deco2800.game.GdxGame.ScreenType;
import com.deco2800.game.files.UserSettings;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialDisplay extends UIComponent {
  private static final Logger logger = LoggerFactory.getLogger(TutorialDisplay.class);
  private final GdxGame game;

  private Table rootTable;

  public TutorialDisplay(GdxGame game) {
    super();
    this.game = game;
  }

  @Override
  public void create() {
    super.create();
    addActors();
  }

  private void addActors() {

    Table table = makeSettingsTable();
    Label title = new Label("tutorial", skin, "title");
    title.setPosition((Gdx.graphics.getWidth()-title.getWidth())/2f,Gdx.graphics.getHeight()-title.getHeight()-15);
    stage.addActor(title);
    stage.addActor(table);

    Table exitTabkle = makeMenuBtns();
    stage.addActor(exitTabkle);

  }

  private Table makeSettingsTable() {

    UserSettings.Settings settings = UserSettings.get();
    int width = Gdx.graphics.getWidth();

    Table mainTable = new Table();




    mainTable.setFillParent(true);
    mainTable.setWidth(500);
    Table table = new Table();
    TextButton oneBt = new TextButton("inventory", skin);
    TextButton twoBt = new TextButton("controls", skin);
    TextButton threeBt = new TextButton("combat", skin);
    TextButton fourBt = new TextButton("evictions/winning", skin);

    table.row();
    table.add(oneBt).padTop(30f).left();
    table.row();

    table.add(twoBt).padTop(15f).left();
    table.row();
    table.add(threeBt).padTop(15f).left();
    table.row();
    table.add(fourBt).padTop(15f).left();
    table.row();
    mainTable.add(table).width(300);
    Label label = new Label("", skin, "title");
    label.setWrap(true);
    label.setFontScale(0.5f);
    label.setPosition(0, 0);

    oneBt.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                label.setText(
                        "The inventory is the key method used by the player to interact with items they pick up on their adventure! It is used to determine the guilt of a player as well as increase the game time \n" +
                        "\n" +
                        "To open the inventory you have two different options 'I' key or the 'Inventory' icon on the left hand side both open up the inventory screen\n" +
                        "\n" +
                        "The main item the player will encounter is the Time item. By clicking on this item, you will increase the time you have to save the island!\n"
                        );
              }
            });
    twoBt.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                label.setText("The main controls in this game are as follows\n" +
                        "\n" +
                        "Movement: W (Move forward) A (Move left) S (Move down) D (Move Right) \n" +
                        "\n" +
                        "Inventory: I key");
              }
            });
    threeBt.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                label.setText("COMBAT\n" +
                        "\n" +
                        "There is currently no combat implemented\n");
              }
            });
    fourBt.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                label.setText("The eviction menu is where the removal of suspected traitors will occur\n" +
                        "\n" +
                        "Once a player is quite sure of another characters guilt, they can vote on them to be evicted.\n" +
                        "\n" +
                        "If that player was the traitor, the game will end in a victor!\n" +
                        "\n" +
                        "If that player was not a traitor. The game will continue until the time has run out.\n" +
                        "\n");
              }
            });

    mainTable.add(label).top().padTop(30).width(width/2);



    return mainTable;
  }


  private void exitMenu() {
    game.setScreen(ScreenType.MAIN_MENU);
  }

  private Integer parseOrNull(String num) {
    try {
      return Integer.parseInt(num, 10);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  @Override
  protected void draw(SpriteBatch batch) {
    // draw is handled by the stage
  }

  private Table makeMenuBtns() {
    TextButton exitBtn = new TextButton("Exit", skin);

    exitBtn.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("Exit button clicked");
                exitMenu();
              }
            });

    Table table = new Table();
    table.add(exitBtn).expandX().left().pad(0f, 100f, 100f, 0f);
    return table;
  }

  @Override
  public void update() {
    stage.act(ServiceLocator.getTimeSource().getDeltaTime());
  }

  @Override
  public void dispose() {
    rootTable.clear();
    super.dispose();
  }
}
