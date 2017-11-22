package com.pickoky.game.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pickoky.game.AssetManagerClass;

import java.util.Stack;

/**
 * Created by MickelPickel on 3/25/2017.
 */

public class GameStateManager {
    private Stack<State> states;
    public AssetManagerClass assetManagerClass;
    public GameStateManager(AssetManagerClass assetManagerClass){
        states = new Stack<State>();
        this.assetManagerClass = assetManagerClass;
    }

    public void push (State state){
        states.push(state);
    }

    public void pop(){
        states.pop();
    }

    public void set(State state){
        states.pop();
        states.push(state);
    }

    public void update(float dt){
        states.peek().update(dt);

    }

    public void render(SpriteBatch batch){
        states.peek().render(batch);
    }
    public State state(){
        return states.peek();
    }




}
