package com.deco2800.game.components.npcEvictionMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class ParticleActor extends Actor {
    private ParticleEffect particleEffect;

    public ParticleActor(String path) {
        super();
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal(path), Gdx.files.internal("data/"));
    }

    @Override
    public void setPosition(float x, float y) {
        particleEffect.setPosition(x, y);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        particleEffect.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        particleEffect.update(delta);

    }

    public void start(){
        particleEffect.start();
    }
}