package com.deco2800.game.components.achievements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.achievements.pojo.AchievementStatus;
import com.deco2800.game.files.UserSettings;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AchievementsDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(AchievementsDisplay.class);
    private final GdxGame game;
    private Image achievementBackground;
    private Table table;
    private Texture backUpTexture;
    private Texture backDownTexture;
    private Button backBtn;
    private Texture lastPageUpTexture;
    private Texture lastPageDownTexture;
    private Button lastPageBtn;
    private Texture nextPageUpTexture;
    private Texture nextPageDownTexture;
    private Button nextPageBtn;
    private static final String[] gameProgressAchievementsPaths = {
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png",
            "images/achievement/gods_pocket_locked.png"};
    private static final String[] collectionsAchievementsPaths = {
            "images/achievement/treasurer_locked.png",
            "images/achievement/treasurer_locked.png",
            "images/achievement/treasurer_locked.png",
            "images/achievement/treasurer_locked.png",
            "images/achievement/treasurer_locked.png",
            "images/achievement/treasurer_locked.png",
            "images/achievement/treasurer_locked.png",
            "images/achievement/treasurer_locked.png",
            "images/achievement/treasurer_locked.png"};
    private static final String[] othersAchievementsPaths = {
            "images/achievement/nereus!_locked.png",
            "images/achievement/nereus!_locked.png",
            "images/achievement/nereus!_locked.png",
            "images/achievement/nereus!_locked.png"};
    private List<Image> curAchievements;
    private List<Image> gameProgressAchievements = new ArrayList<Image>();
    private List<Image> collectionsAchievements = new ArrayList<Image>();
    private List<Image> othersAchievements = new ArrayList<Image>();
    private int curPage = 0;
    private int minPage = 0;
    private int maxPage = 0;

    /**
     * Store achievement status information
     */
    private Map<String, AchievementStatus> achievementStatusMap;


    public AchievementsDisplay(GdxGame game) {
        super();
        this.game = game;
    }

    @Override
    public void create() {
        achievementStatusMap = ServiceLocator.getAchievementService().getAchievementStatusMap();
        super.create();
        addActors();
    }

    private void addActors() {
        // Background
        table = new Table();
        table.setFillParent(true);
        achievementBackground = new Image(ServiceLocator.getResourceService()
                .getAsset("images/achievement/achievement_background.png", Texture.class));
        table.add(achievementBackground)
                .height(Gdx.graphics.getHeight())
                .width(Gdx.graphics.getWidth());

        // Set button textures
        backUpTexture = new Texture(Gdx.files.internal("images/achievement/back_button.png"));
        backDownTexture = new Texture(Gdx.files.internal("images/achievement/back_button_pressed.png"));
        lastPageUpTexture = new Texture(Gdx.files.internal("images/achievement/last_page_button.png"));
        lastPageDownTexture = new Texture(Gdx.files.internal("images/achievement/last_page_button_pressed.png"));
        nextPageUpTexture = new Texture(Gdx.files.internal("images/achievement/next_page_button.png"));
        nextPageDownTexture = new Texture(Gdx.files.internal("images/achievement/next_page_button_pressed.png"));

        // Create button styles
        Button.ButtonStyle backStyle = new Button.ButtonStyle();
        Button.ButtonStyle lastStyle = new Button.ButtonStyle();
        Button.ButtonStyle nextStyle = new Button.ButtonStyle();

        // Set up and down texture of buttons
        backStyle.up = new TextureRegionDrawable(new TextureRegion(backUpTexture));
        backStyle.down = new TextureRegionDrawable(new TextureRegion(backDownTexture));
        lastStyle.up = new TextureRegionDrawable(new TextureRegion(lastPageUpTexture));
        lastStyle.down = new TextureRegionDrawable(new TextureRegion(lastPageDownTexture));
        nextStyle.up = new TextureRegionDrawable(new TextureRegion(nextPageUpTexture));
        nextStyle.down = new TextureRegionDrawable(new TextureRegion(nextPageDownTexture));

        // Create buttons
        backBtn = new Button(backStyle);
        lastPageBtn = new Button(lastStyle);
        nextPageBtn = new Button(nextStyle);

        // Set sizes of buttons
        backBtn.setSize(relativeWidth(120), relativeHeight(60));
        lastPageBtn.setSize(relativeWidth(45), relativeHeight(55));
        nextPageBtn.setSize(relativeWidth(45), relativeHeight(55));

        // Set positions of buttons
        backBtn.setPosition(relativeWidth(50), relativeHeight(50));
        lastPageBtn.setPosition(stage.getWidth() - relativeWidth(120), stage.getHeight() - relativeHeight(420));
        nextPageBtn.setPosition(stage.getWidth() - relativeWidth(120), stage.getHeight() - relativeHeight(490));

        // Set listeners of buttons
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
                        // Page up
                        if (curPage > 0) {
                            curPage--;
                            showAchievements(curPage * 8, Math.min((curPage + 1) * 8, curAchievements.size()));
                        }
                    }
                });
        nextPageBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("NextPage button clicked");
                        // Page down
                        if (curPage < maxPage) {
                            curPage++;
                            showAchievements(curPage * 8, Math.min((curPage + 1) * 8, curAchievements.size()));
                        }
                    }
                });

        // Set achievement category labels
        Label progressLabel = new Label("Game Progress", skin);
        Label collectionLabel = new Label("Collection", skin);
        Label othersLabel = new Label("Others", skin);

        // Set positions of labels
        progressLabel.setPosition(relativeWidth(138), relativeHeight(592));
        collectionLabel.setPosition(relativeWidth(160), relativeHeight(482));
        othersLabel.setPosition(relativeWidth(180), relativeHeight(372));

        progressLabel.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        logger.debug("progress Label clicked");
                        curAchievements = gameProgressAchievements;
                        curPage = 0;
                        minPage = 0;
                        maxPage = (curAchievements.size() - 1) / 8;
                        showAchievements(0, Math.min(8, curAchievements.size()));
                    }
                }
        );

        collectionLabel.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        logger.debug("collection Label clicked");
                        curAchievements = collectionsAchievements;
                        curPage = 0;
                        minPage = 0;
                        maxPage = (curAchievements.size() - 1) / 8;
                        showAchievements(0, Math.min(8, curAchievements.size()));
                    }
                }
        );

        othersLabel.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        logger.debug("others Label clicked");
                        curAchievements = othersAchievements;
                        showAchievements(0, Math.min(8, curAchievements.size()));
                    }
                }
        );

        stage.addActor(table);
        stage.addActor(backBtn);
        stage.addActor(lastPageBtn);
        stage.addActor(nextPageBtn);
        stage.addActor(progressLabel);
        stage.addActor(collectionLabel);
        stage.addActor(othersLabel);

        // Show achievements(default game progress)
        for (String path : gameProgressAchievementsPaths) {
            Image image = new Image(ServiceLocator.getResourceService().getAsset(path, Texture.class));
            image.setPosition(-10000, -10000);
            gameProgressAchievements.add(image);
            stage.addActor(image);
        }
        for (String path : collectionsAchievementsPaths) {
            Image image = new Image(ServiceLocator.getResourceService().getAsset(path, Texture.class));
            image.setPosition(-10000, -10000);
            collectionsAchievements.add(image);
            stage.addActor(image);
        }
        for (String path : othersAchievementsPaths) {
            Image image = new Image(ServiceLocator.getResourceService().getAsset(path, Texture.class));
            image.setPosition(-10000, -10000);
            othersAchievements.add(image);
            stage.addActor(image);
        }
        curAchievements = gameProgressAchievements;
        curPage = 0;
        minPage = 0;
        maxPage = (curAchievements.size() - 1) / 8;
        showAchievements(0, Math.min(8, curAchievements.size()));
    }

    private void removeAchievementsFromStage() {
        for (Image gameProgressAchievement : gameProgressAchievements) {
            gameProgressAchievement.setPosition(-10000, -10000);
        }
        for (Image collectionsAchievement : collectionsAchievements) {
            collectionsAchievement.setPosition(-10000, -10000);
        }
        for (Image othersAchievement : othersAchievements) {
            othersAchievement.setPosition(-10000, -10000);
        }
    }

    private void showAchievements(int start, int end) {
        removeAchievementsFromStage();
        float offsetX = relativeWidth(333);
        float offsetY = relativeHeight(151);
        for (int i = start; i < end; i++) {
            curAchievements.get(i).setSize(relativeWidth(292), relativeHeight(111));
            if (i % 2 == 0) {
                curAchievements.get(i).setPosition(relativeWidth(477),
                        relativeHeight(533) - offsetY * (i % 8) / 2);
            } else {
                curAchievements.get(i).setPosition(relativeWidth(477) + offsetX,
                        relativeHeight(533) - offsetY * ((i - 1) % 8) / 2);
            }
        }
    }

    /**
     * Relative width for dealing with windows of different sizes
     */
    private float relativeWidth(int width) {
        return stage.getWidth() * width / 1280;
    }

    /**
     * Relative height for dealing with windows of different sizes
     */
    private float relativeHeight(int height) {
        return stage.getHeight() * height / 800;
    }

    private void exitMenu() {
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {
        table.clear();
        super.dispose();
    }
}
