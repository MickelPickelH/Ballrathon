package com.pickoky.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.pickoky.game.AssetManagerClass;
import com.pickoky.game.Curvy_Ball;

import static com.pickoky.game.Curvy_Ball.camera;

/**
 * Created by MickelPickel on 3/25/2017.
 */

public class MenuState extends State {
    private Texture background;
    private Texture startBtn;
    private Sprite backgroundSprite;
    private Sprite startBtnSprite;
    private Vector3 touchPos = new Vector3();
    private Rectangle startRegion;
    private final int PPM = 10;
    public MenuState(GameStateManager gsm, AssetManagerClass assetManagerClass) {
        super(gsm,assetManagerClass);
//        Curvy_Ball.camera.position.set(Curvy_Ball.WIDTH/2/PPM,Curvy_Ball.HEIGHT/2/PPM,0);
//        Curvy_Ball.camera.update();
       // background = new Texture("background.png");
       // startBtn = new Texture("start.png");

       // background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
      //  startBtn.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backgroundSprite = new Sprite(assetManagerClass.backgroundRegion);
        startBtnSprite = new Sprite(assetManagerClass.startRegion);
        backgroundSprite.setSize( Curvy_Ball.WIDTH/PPM,Curvy_Ball.HEIGHT/PPM);
      //  backgroundSprite.setScale(Curvy_Ball.WIDTH/PPM,Curvy_Ball.HEIGHT/PPM);
        startBtnSprite.setSize(assetManagerClass.startRegion.getRegionWidth()/PPM,assetManagerClass.startRegion.getRegionHeight()/PPM);
       // startBtnSprite.setScale(startBtn.getWidth()/PPM,startBtn.getHeight()/PPM);
        backgroundSprite.setPosition(0,0);
        startBtnSprite.setCenter(95.85f,40.89f);
        startRegion = new Rectangle((Curvy_Ball.WIDTH/2)/PPM-assetManagerClass.startRegion.getRegionWidth()/2/PPM,(Curvy_Ball.HEIGHT/2)/PPM-assetManagerClass.startRegion.getRegionHeight()/2/PPM-(67.15f-40.89f)/2,240/PPM,240/PPM);


    }


    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            touchPos.x = Gdx.input.getX();
            touchPos.y = Gdx.input.getY();
            camera.unproject(touchPos);
            Gdx.app.log(" ",Float.toString(touchPos.x)+" "+ Float.toString(touchPos.y));
            if(startRegion.contains(touchPos.x,touchPos.y)) {
                gsm.set(new PlayState(gsm,assetManagerClass));
                Curvy_Ball.initPlayState = true;
                dispose();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch batch) {
        //camera.update();
        batch.begin();
//        batch.draw(background,0,0, Curvy_Ball.WIDTH/PPM,Curvy_Ball.HEIGHT/PPM);
//        batch.draw(startBtn,(Curvy_Ball.WIDTH/2)/PPM-startBtn.getWidth()/2/PPM,(Curvy_Ball.HEIGHT/2)/PPM-startBtn.getHeight()/2/PPM);
        backgroundSprite.draw(batch);
        startBtnSprite.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
    }
}
