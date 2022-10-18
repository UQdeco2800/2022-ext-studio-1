package com.deco2800.game.components.endingmenu;


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

public class timeOverDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(timeOverDisplay.class);
    private final GdxGame game;

    private Table rootTable;

    public timeOverDisplay(GdxGame game) {
        super();
        this.game = game;
    }

    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {

        // Table table = makeSettingsTable();
        Label title = new Label("You ran out of time. Game is over.You lose", skin, "title");

        title.setPosition(260,450);
        stage.addActor(title);
        //stage.addActor(table);

        Table exitTabkle = makeMenuBtns();
        exitTabkle.setPosition(340,350);
        stage.addActor(exitTabkle);

        Table restartButton = makeRestartBtns();
        restartButton.setPosition(600,350);
        stage.addActor(restartButton);


        Table exitButton = makeExitBtns();
        exitButton.setPosition(860,350);
        stage.addActor(exitButton);



    }





    private void exitMenu() {
        game.setScreen(ScreenType.MAIN_MENU);
    }

    private void restartGame(){game.setScreen(ScreenType.MAIN_GAME);}



    @Override
    protected void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    private Table makeMenuBtns() {
        TextButton exitBtn = new TextButton("Menu", skin);

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
    private Table makeRestartBtns() {
        TextButton restartBtn = new TextButton("Restart", skin);

        restartBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("restart button clicked");
                        game.setGameTime(10);
                        restartGame();
                    }
                });

        Table table = new Table();
        table.add(restartBtn).expandX().left().pad(0f, 100f, 100f, 0f);
        return table;
    }

    private Table makeExitBtns() {
        TextButton ExitBtn = new TextButton("Exit Game", skin);

        ExitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("exit game button clicked");
                        game.exit();
                    }
                });

        Table table = new Table();
        table.add(ExitBtn).expandX().left().pad(0f, 100f, 100f, 0f);
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

