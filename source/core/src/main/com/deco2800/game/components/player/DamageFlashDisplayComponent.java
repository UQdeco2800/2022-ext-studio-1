package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.player.InventoryDisplayComponent;
import com.deco2800.game.screens.CountdownScreen;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DamageFlashDisplayComponent extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(InventoryComponent.class);
    private GdxGame game = new GdxGame();

    private Table flashTable;


    public DamageFlashDisplayComponent() {
        super();
        create();
    }

    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {

        Image damageFlash =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/damageFlash.png", Texture.class));


        flashTable = new Table();
        flashTable.setFillParent(true);
        flashTable.add(damageFlash).height(Gdx.graphics.getHeight()).width(Gdx.graphics.getWidth());
        stage.addActor(flashTable);
    }

    public void removeDamageFlash() {
        super.dispose();
        flashTable.remove();

    }

    public void waitTime() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        removeDamageFlash();
                    }
                },
                500
        );

    }

    @Override
    protected void draw(SpriteBatch batch) {
    }
}
