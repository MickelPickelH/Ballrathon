package com.pickoky.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.pickoky.game.AssetManagerClass;

/**
 * Created by MickelPickel on 3/26/2017.
 */

public class Connection {
    private OrthographicCamera camera;
    private Sprite curveSprite[] = new Sprite[4];
    private  float curveOrderIndexCorX[];
    private  float curveOrderIndexCorY[];




    private final int PPM = 10;
    public AssetManagerClass assetManagerClass;
    public Connection(OrthographicCamera camera, World world, AssetManagerClass assetManagerClass){
        this.assetManagerClass = assetManagerClass;
        this.camera = camera;

        for(int i = 0; i < 4; i++){
            curveSprite[i] = new Sprite(assetManagerClass.pathRegion[i]);
            curveSprite[i].setSize(assetManagerClass.pathRegion[i].getRegionWidth()/PPM,assetManagerClass.pathRegion[i].getRegionHeight()/PPM);
        }

        curveOrderIndexCorX = new float[10];//storeing the x and y of the curves
        curveOrderIndexCorY = new float[10];
    }


    public Sprite[] getCurveSprite() {
        return curveSprite;
    }

    public float[] getCurveOrderIndexCorX() {
        return curveOrderIndexCorX;
    }

    public float[] getCurveOrderIndexCorY() {
        return curveOrderIndexCorY;
    }

    public void setCurveOrderIndexCorX(int orderCount, float newCurveOrderCountCorX) {
        this.curveOrderIndexCorX[orderCount] = newCurveOrderCountCorX;
    }

    public void setCurveOrderIndexCorY(int orderCount, float newCurveOrderCountCorY) {
        this.curveOrderIndexCorY[orderCount] = newCurveOrderCountCorY;
    }


    public void addCurveOrderIndexCorX(int orderCount, float shiftCurveOrderCountCorX) {
        this.curveOrderIndexCorX[orderCount] += shiftCurveOrderCountCorX;
    }


    public TextureRegion[] getCurve() {
        return assetManagerClass.curveRegion;
    }


}
