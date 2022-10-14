package com.deco2800.game.components.npcEvictionMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ParticleEffectActorScreen extends ScreenAdapter {
    private ParticleEffect effect;
    private SpriteBatch batch;

    private static final Logger logger = LoggerFactory.getLogger(ParticleEffectActorScreen.class);


    @Override
    public void show() {
        effect = new ParticleEffect();
        batch =new SpriteBatch();
        effect.load(Gdx.files.internal("data/test.p"), Gdx.files.internal("data/"));
        effect.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        effect.start();
    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(84/255f, 153/255f, 199/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        effect.draw(batch,delta);
        batch.end();
    }



}
