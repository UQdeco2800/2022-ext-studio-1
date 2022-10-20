package com.deco2800.game.components.countDownClock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.player.PlayerProfileDisplay;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskWindow extends UIComponent {

    private static final int bgWidth = 700;
    private static final int bgHeight = 350;
    private static final Logger logger = LoggerFactory.getLogger(PlayerProfileDisplay.class);


    GdxGame game;

    Table background;
    Table buttons;
    Stack stack;

    private String imageName;

    public TaskWindow(GdxGame game, String imageName) {
        super();
        this.game = game;
        this.imageName = imageName;
    }

    @Override
    public void create(){
        super.create();
        addActors();
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }


    private void addActors() {
        Texture pauseWindowTexture = new Texture(Gdx.files.internal(this.imageName));
        TextureRegionDrawable pausedWindow = new TextureRegionDrawable(pauseWindowTexture);

        Texture resumeBtnTexture = new Texture(Gdx.files.internal("images/countdown_clock/resume.png"));
        TextureRegionDrawable resumeBtnDrawable = new TextureRegionDrawable(resumeBtnTexture);

//        Texture restartBtnTexture = new Texture(Gdx.files.internal("images/countdown_clock/replay1.png"));
//        TextureRegionDrawable restartBtnDrawable = new TextureRegionDrawable(restartBtnTexture);

        Button resumeButton = new ImageButton(resumeBtnDrawable);
//        Button restartButton = new ImageButton(restartBtnDrawable);

        Image pausedWindowImage = new Image(pausedWindow);
        
        background = new Table();
        background.setFillParent(true);
        background.add(pausedWindowImage).width(bgWidth).height(bgHeight);

//        buttons = new Table();
//        buttons.add(resumeButton).expandY().width(40).height(40);
//        buttons.add(restartButton).expandY().width(100).height(100);

        stack = new Stack();
        stack.add(background);
//        stack.add(buttons);

        stack.setFillParent(true);

        stage.addActor(stack);

        background.addListener(
                        new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                logger.info("Clicked on background");
                                dispose();
                            }
                        });
    }

    public void dispose() {
        super.dispose();
        stack.remove();
    }

}
