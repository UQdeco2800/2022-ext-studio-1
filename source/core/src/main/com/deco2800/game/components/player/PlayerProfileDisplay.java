package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.deco2800.game.entities.configs.PlayerProfileConfig;
import com.deco2800.game.entities.configs.PlayerProfileProperties;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.services.ResourceService;
import org.slf4j.Logger;
import com.badlogic.gdx.graphics.Texture;
import org.slf4j.LoggerFactory;
import java.util.List;


public class PlayerProfileDisplay extends UIComponent {

    private static final int bgWidth = 500;
    private static final int bgHeight = 350;

    private static final Logger logger = LoggerFactory.getLogger(PlayerProfileDisplay.class);

    Json json = new Json();

//    ArrayList<PlayerProfileConfig> statsList = json.fromJson(ArrayList.class, PlayerProfileConfig.class, Gdx.files.internal("configs/playerStatsInfo.json").readString());

//    public static final PlayerProfileConfig playerProfileConfigs = FileLoader.readClass(PlayerProfileConfig.class, "configs/playerStatsInfo.json");

    PlayerProfileConfig playerProfileConfigs = json.fromJson(PlayerProfileConfig.class, Gdx.files.internal("configs/playerStatsInfo.json"));
    Table root;
    Table background;
    Table title;
    Table content;

    Button backButton;


    @Override
    public void create() {
        logger.info(String.valueOf(playerProfileConfigs.playerStats));
//        loadAssets();
        super.create();
        addActors();
    }

    private List<PlayerProfileProperties> getPlayerProfile() {
        return playerProfileConfigs.playerStats;
    }

    public float calculateAvg(int sum, int numElements) {
        return (float )sum / numElements;
    }

    private void addActors() {
        ResourceService resourceService = ServiceLocator.getResourceService();

        Image bgImage = new Image(
                resourceService.getAsset(
                        "images/blank.png", Texture.class
                )
        );

        Texture backgroundTexture = new Texture(Gdx.files.internal("images/playerprofile/background.png"));
        TextureRegionDrawable ppBackground = new TextureRegionDrawable(backgroundTexture);

        Image backgroundImage = new Image(ppBackground);

        Texture exitBtnTexture = new Texture(Gdx.files.internal("images/exitbtn.png"));
        TextureRegionDrawable exitBtn = new TextureRegionDrawable(exitBtnTexture);

        Stack stack = new Stack();

        root = new Table();
        root.setFillParent(true);

        background = new Table();

        background.setFillParent(true);
        background.add(backgroundImage).height(Gdx.graphics.getHeight()-bgHeight).width(Gdx.graphics.getWidth()-bgWidth);

//        Labels (subheadings)
        Label attemptsLabel = new Label("Average attempts to win: ", skin);
        Label timeLabel = new Label ("Average time take to win: ", skin);
        Label lossesLabel = new Label("Number of losses: ", skin);
        Label winLabel = new Label("Number of wins: ", skin);

        List<PlayerProfileProperties> playerProfile = getPlayerProfile();

        int sumTimeRemaining = 0;
        int sumResult = 0;
        int sumAttempt = 0;

        for (PlayerProfileProperties stat : playerProfile) {

            int timeRemaining = 7260 - stat.timeRemaining;
            int result = stat.result;
            int attempt = stat.attempt;

            sumTimeRemaining += timeRemaining;
            sumResult += result;
            sumAttempt += attempt;

        }

        float avgTimeRemaining = calculateAvg(sumTimeRemaining, playerProfile.size());
        float avgResult = calculateAvg(sumResult, playerProfile.size());
        float avgAttempt = calculateAvg(sumAttempt, playerProfile.size());

//        Dummy Data
        Label attempts = new Label(String.valueOf(avgAttempt), skin);
        Label time = new Label(String.valueOf(avgTimeRemaining) + " s", skin);
        Label losses = new Label("1", skin);
        Label wins = new Label(String.valueOf(avgResult), skin);

        content = new Table();

        content.add(attemptsLabel).height(150).expandX(); //.expandX().expandY();
        content.add(timeLabel).expandX(); //.expandX().expandY();
        content.row();
        content.add(attempts); //.width(200);
        content.add(time); //.width(200);
        content.row();
        content.add(lossesLabel).height(150); //.expandX().expandY();
        content.add(winLabel); //.expandX().expandY();
        content.row();
        content.add(losses); //.width(300);
        content.add(wins); //.width(300);

//        backButton = new TextButton("Back", skin);
        backButton = new ImageButton(exitBtn);
        backButton.setSize((float) (bgWidth * 0.2), (float) (bgHeight * 0.2));
        backButton.setPosition((float) (bgWidth * 2.27), (float) (bgHeight * 1.9));

        backButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        closeWindow();
                    }
                }
        );

        stack.add(background);
        stack.add(content);

        root.add(stack);

        this.stage.addActor(root);
        this.stage.addActor(backButton);
//        this.stage.addActor(exitButton);
    }

    @Override
    public void draw(SpriteBatch batch)  {
        // draw is handled by the stage
    }

    private void closeWindow() {
        super.dispose();
        root.remove();
        backButton.remove();
    }

}
