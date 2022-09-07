package com.deco2800.game.components.countDownClock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.deco2800.game.GdxGame;
import com.deco2800.game.ui.UIComponent;

public class countdownDisplay extends UIComponent {
    private final GdxGame game;
    private int timeRemaining;
    private float timeCount;
    public countdownDisplay(GdxGame game) {
        super();
        this.game = game;
        timeRemaining = 300;
        timeCount = 0;
    }
    Label counterLabel;

    @Override
    public void create() {
        super.create();
        addActors();
    }

    @Override
    public void update() {
        super.update();
        timeCount = Gdx.graphics.getDeltaTime();
        if (timeCount >= 1) {
            timeRemaining--;
            counterLabel.setText(String.valueOf(timeRemaining));
            timeCount = 0;
        }

    }

    private void addActors() {

        Label.LabelStyle counterLabelStyle = new Label.LabelStyle();
//        BitmapFont myFont = new BitmapFont(Gdx.files.internal());
//        counterLabelStyle.font = myFont;
//        counterLabelStyle.fontColor = Color.YELLOW;
//
        counterLabel = new Label(String.valueOf(timeRemaining), counterLabelStyle);
        counterLabel.setPosition((float) (stage.getWidth() * 0.9), (float) (stage.getHeight() * 0.1));
        counterLabel.setSize((float) (stage.getWidth() * 0.05), (float) (stage.getHeight() * 0.03));

        stage.addActor(counterLabel);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }



}
