package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.deco2800.game.entities.configs.PlayerProfileConfig;
import com.deco2800.game.entities.configs.PlayerProfileProperties;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.services.ResourceService;
import org.slf4j.Logger;
import com.badlogic.gdx.graphics.Texture;
import org.slf4j.LoggerFactory;
import java.util.List;


/**
 * This class creates the player profile window.
 *
 * Description of the feature:
 * This feature displays the player's profile for the current player of the game.
 * The profile includes useful and perhaps interesting statistics about the player's performance in the game,
 * including the average time they have taken to win the game,
 * the average number of attempts taken to guess the traitor until they win,
 * how many times they have lost the game, and how many times they have won the game.
 */
public class PlayerProfileDisplay extends UIComponent {

    private static final int bgWidth = 500;
    private static final int bgHeight = 350;

    private static final Logger logger = LoggerFactory.getLogger(PlayerProfileDisplay.class);

    // Reading from json file created to store player's profile (performance stats).
    Json json = new Json();

//    JsonValue jsonValue = new JsonReader().parse(Gdx.files.internal("configs/playerStatsInfo.json"));
    PlayerProfileConfig playerProfileConfigs = json.fromJson(PlayerProfileConfig.class, Gdx.files.internal("configs/playerStatsInfo.json"));

    // initialising ui elements.
    Table root;
    Table background;
    Table title;
    Image cluesIconImg;
    Image timeIconImg;
    Table content;

    Button backButton;


    @Override
    public void create() {
        logger.info(String.valueOf(playerProfileConfigs.playerStats));
        super.create();
        addActors();
    }

    /**
     * Gets the list of performance stats of the player (player profile) in the json file.
     * @return list of performance stats
     */
    public List<PlayerProfileProperties> getPlayerProfile() {
        return playerProfileConfigs.playerStats;
    }

    /**
     * Calculates the average value.
     * @param sum the sum of all elements.
     * @param numElements the number of elements.
     * @return the average of the elements.
     */
    public float calculateAvg(int sum, int numElements) {
        return (float )sum / numElements;
    }


    public float calculateAveTi(int timeLeft,int timeWins){
        float average = timeLeft/timeWins;

        return average;

    }
    public float calculateAvgAtm(int numberOfAttempt, int timeItWin){

        float average = numberOfAttempt/timeItWin;

        return average;


    }

    /**
     * Creates the player profile window.
     */
    private void addActors() {
        ResourceService resourceService = ServiceLocator.getResourceService();

        // loading and creating background image.
        Texture backgroundTexture = new Texture(Gdx.files.internal("images/playerprofile/background.png"));
        TextureRegionDrawable ppBackground = new TextureRegionDrawable(backgroundTexture);

        Image backgroundImage = new Image(ppBackground);

        // loading exit button image.
        Texture exitBtnTexture = new Texture(Gdx.files.internal("images/exitbtn.png"));
        TextureRegionDrawable exitBtn = new TextureRegionDrawable(exitBtnTexture);

        // loading and creating icon images
        Texture cluesTexture = new Texture(Gdx.files.internal("images/playerprofile/clues.png"));
        TextureRegionDrawable cluesIcon = new TextureRegionDrawable(cluesTexture);
        cluesIconImg = new Image(cluesIcon);
        cluesIconImg.setSize((float) (bgWidth * 0.3), (float) (bgHeight * 0.3));
        cluesIconImg.setPosition((float) (bgWidth * 1.3), (float) (bgHeight * 0.8));

        Texture timeTexture = new Texture(Gdx.files.internal("images/playerprofile/time.png"));
        TextureRegionDrawable timeIcon = new TextureRegionDrawable(timeTexture);
        timeIconImg = new Image(timeIcon);
        timeIconImg.setSize((float) (bgWidth * 0.3), (float) (bgHeight * 0.3));
        timeIconImg.setPosition((float) (bgWidth * 1.3), (float) (bgHeight * 1.4));

        // create a Stack object to allow elements to be placed over the background image.
        Stack stack = new Stack();

        // Setting parent table where all element exist.
        root = new Table();
        root.setFillParent(true);

        // creating table for background where background image will be placed.
        background = new Table();

        background.setFillParent(true);
        background.add(backgroundImage).height(Gdx.graphics.getHeight()-bgHeight).width(Gdx.graphics.getWidth()-bgWidth);

//        Labels (subheadings) - create labels for each performance stat
        Label attemptsLabel = new Label("Average attempts to win: ", skin);
        Label timeLabel = new Label ("Average time take to win: ", skin);
        Label lossesLabel = new Label("Number of losses: ", skin);
        Label winLabel = new Label("Number of wins: ", skin);

        // Processing the json data (calculating and storing average values)
        List<PlayerProfileProperties> playerProfile = getPlayerProfile();

        int sumTimeRemaining = 0;
        int sumResult = 0;
        int sumAttempt = 0;
        int numberOfWin =0;
        int numberOfLose =0;


        for (PlayerProfileProperties stat : playerProfile) {

            int timeRemaining = 7260 - stat.timeRemaining;
            int result = stat.result;
            int attempt = stat.attempt;
           if(stat.result==1) {
               sumTimeRemaining += timeRemaining;
           }
            sumResult += result;
           if(stat.result==1) {
               sumAttempt += attempt;
           }
            if(stat.result==2){
                numberOfLose+=1;

            }
            if(stat.result==1){
                numberOfWin+=1;
            }

        }

        float avgTimeRemaining = calculateAveTi(sumTimeRemaining, numberOfWin);
        float avgResult = calculateAvg(sumResult, playerProfile.size());
        float avgAttempt = calculateAvgAtm(sumAttempt, numberOfWin);
        //

        // creating labels to display the data retrieved from the json file.
        Label attempts = new Label(String.valueOf(avgAttempt), skin);
        Label time = new Label(String.valueOf(avgTimeRemaining) + " s", skin);
        Label losses = new Label(String.valueOf(numberOfLose), skin);
        Label wins = new Label(String.valueOf(numberOfWin), skin);


        // creating table to place and position all the labels in.
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

        // creating and positioning the exit button which returns to the main game screen.
        backButton = new ImageButton(exitBtn);
        backButton.setSize((float) (bgWidth * 0.2), (float) (bgHeight * 0.2));
        backButton.setPosition((float) (bgWidth * 2.27), (float) (bgHeight * 1.9));

        // add event listener to the exit button.
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
        this.stage.addActor(cluesIconImg);
        this.stage.addActor(timeIconImg);
    }

    @Override
    public void draw(SpriteBatch batch)  {
        // draw is handled by the stage
    }

    /**
     * Method to close the player profile window and return to the main game screen.
     */
    private void closeWindow() {
        super.dispose();
        root.remove();
        backButton.remove();
        cluesIconImg.remove();
        timeIconImg.remove();
    }

}
