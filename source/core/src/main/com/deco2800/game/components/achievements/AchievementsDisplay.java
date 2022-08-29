package com.deco2800.game.components.achievements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AchievementsDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(AchievementsDisplay.class);
    private GdxGame game;
    private Table rootTable;
    private Table bgTable;
    private Texture backUpTexture;
    private Texture backOverTexture;
    private Button backBtn;
    private Texture lastPageUpTexture;
    private Texture lastPageOverTexture;
    private Button lastPageBtn;
    private Texture nextPageUpTexture;
    private Texture nextPageOverTexture;
    private Button nextPageBtn;
    private static final int BACK_BUTTON_LEFT_PADDING = 50;
    private static final int BACK_BUTTON_BOTTOM_PADDING = 50;
    private static final int PAGE_BUTTON_RIGHT_PADDING = 120;
    private static final int LAST_PAGE_BUTTON_TOP_PADDING = 420;
    private static final int NEXT_PAGE_BUTTON_TOP_PADDING = 490;

    public AchievementsDisplay(GdxGame game) {
        super();
        this.game = game;
    }

    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {
        Image achievementBackground =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/achievement/achievement_background.png",
                                        Texture.class));
        // Build button styles
        backUpTexture = new Texture(Gdx.files.internal("images/achievement/back_button.png"));
        backOverTexture = new Texture(Gdx.files.internal("images/achievement/back_button_pressed.png"));
        lastPageUpTexture = new Texture(Gdx.files.internal("images/achievement/last_page_button.png"));
        lastPageOverTexture = new Texture(Gdx.files.internal("images/achievement/last_page_button_pressed.png"));
        nextPageUpTexture = new Texture(Gdx.files.internal("images/achievement/next_page_button.png"));
        nextPageOverTexture = new Texture(Gdx.files.internal("images/achievement/next_page_button_pressed.png"));

        Button.ButtonStyle backStyle = new Button.ButtonStyle();
        Button.ButtonStyle lastStyle = new Button.ButtonStyle();
        Button.ButtonStyle nextStyle = new Button.ButtonStyle();

        backStyle.up = new TextureRegionDrawable(new TextureRegion(backUpTexture));
        backStyle.over = new TextureRegionDrawable(new TextureRegion(backOverTexture));
        lastStyle.up = new TextureRegionDrawable(new TextureRegion(lastPageUpTexture));
        lastStyle.over = new TextureRegionDrawable(new TextureRegion(lastPageOverTexture));
        nextStyle.up = new TextureRegionDrawable(new TextureRegion(nextPageUpTexture));
        nextStyle.over = new TextureRegionDrawable(new TextureRegion(nextPageOverTexture));

        backBtn = new Button(backStyle);
        lastPageBtn = new Button(lastStyle);
        nextPageBtn = new Button(nextStyle);

        backBtn.setPosition(BACK_BUTTON_LEFT_PADDING, BACK_BUTTON_BOTTOM_PADDING);
        lastPageBtn.setPosition(stage.getWidth() - PAGE_BUTTON_RIGHT_PADDING
                , stage.getHeight() - LAST_PAGE_BUTTON_TOP_PADDING);
        nextPageBtn.setPosition(stage.getWidth() - PAGE_BUTTON_RIGHT_PADDING
                , stage.getHeight() - NEXT_PAGE_BUTTON_TOP_PADDING);

        backBtn.setSize((float) (stage.getWidth()*0.1), (float) (stage.getHeight()*0.08));
        lastPageBtn.setSize((float) (stage.getWidth()*0.035), (float) (stage.getHeight()*0.06));
        nextPageBtn.setSize((float) (stage.getWidth()*0.035), (float) (stage.getHeight()*0.06));

        backBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Back button clicked");
                        exitMenu();
                    }
                });
        lastPageBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("LastPage button clicked");
                    }
                });
        nextPageBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("NextPage button clicked");
                    }
                });

        rootTable = new Table();
        rootTable.setFillParent(true);

        bgTable = new Table();
        bgTable.setFillParent(true);
        bgTable.add(achievementBackground)
                .height(Gdx.graphics.getHeight()).width(Gdx.graphics.getWidth());

        stage.addActor(bgTable);
        stage.addActor(backBtn);
        stage.addActor(lastPageBtn);
        stage.addActor(nextPageBtn);
        stage.addActor(rootTable);
    }

    private Table makeAchievements() {


        Table table = new Table();

        return table;
    }

    private void exitMenu() {
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {
        rootTable.clear();
        super.dispose();
    }
}
