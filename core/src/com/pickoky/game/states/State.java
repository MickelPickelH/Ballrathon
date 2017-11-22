package com.pickoky.game.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.pickoky.game.AssetManagerClass;
import com.pickoky.game.Curvy_Ball;

/**
 * Created by MickelPickel on 3/25/2017.
 */

public abstract class State {

    //protected Vector3 touch;
    protected GameStateManager gsm;
    protected AssetManagerClass assetManagerClass;
    protected State(GameStateManager gsm, AssetManagerClass assetManagerClass){
        this.gsm = gsm;
        this.assetManagerClass = assetManagerClass;
    }

    protected abstract void handleInput();
    protected abstract void update(float dt);
    public abstract void render (SpriteBatch batch);

    public abstract void dispose();


}
