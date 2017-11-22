package com.pickoky.game.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pickoky.game.AssetManagerClass;

/**
 * Created by MickelPickel on 3/26/2017.
 */

public class Boost {
    private OrthographicCamera camera;
    private Texture boostImg;
    private Sprite boostSprite;
    private final int PPM = 10;
    public Boost(OrthographicCamera camera, AssetManagerClass assetManagerClass){
        this.camera = camera;
        boostSprite = new Sprite(assetManagerClass.boostRegion);
        boostSprite.setSize(assetManagerClass.boostRegion.getRegionWidth()/PPM,assetManagerClass.boostRegion.getRegionHeight()/PPM);
        boostSprite.setPosition(camera.viewportWidth/2-assetManagerClass.boostRegion.getRegionWidth()/2/PPM,camera.viewportHeight/7-150/PPM);//boost button

    }

}
