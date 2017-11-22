package com.pickoky.game.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.pickoky.game.AssetManagerClass;
import com.pickoky.game.Curvy_Ball;
import com.pickoky.game.states.PlayState;

/**
 * Created by MickelPickel on 3/26/2017.
 */

public class Ball {
    private OrthographicCamera camera;
    private Sprite ballSprite;

    private float ballCorX;
    private float ballCorY;
    private float curveCorX;
    private float curveCorY;

    private World world;
    public final int PPM = 10;
    public Ball(OrthographicCamera camera, World world, AssetManagerClass assetManagerClass){
        this.camera = camera;
        ballSprite = new Sprite(assetManagerClass.ballRegion);
        ballSprite.setSize(50/PPM,50/PPM);
        ballSprite.setPosition(Curvy_Ball.WIDTH/PPM/8-ballSprite.getWidth(),Curvy_Ball.HEIGHT/PPM/2-ballSprite.getWidth());
       // ballSprite.setPosition(PlayState.body.getPosition().x,PlayState.body.getPosition().y);
        ballCorX = ballSprite.getX();
        ballCorY = ballSprite.getY();

        curveCorX = ballSprite.getX();//set original curve position, right below the ball
        curveCorY = ballSprite.getY() ;

    }


    public float getCurveCorX() {
        return curveCorX;
    }

    public float getCurveCorY() {
        return curveCorY;
    }

    public Sprite getBallSprite() {
        return ballSprite;
    }

    public void setBallSprite(float setCenterX, float setCenterY) {
        this.ballSprite.setCenter(setCenterX,setCenterY);
    }

    public void setBallCorY(float ballCorY) {
        this.ballCorY = ballCorY;
    }

    public void setBallSize(float radius){
        ballSprite.setSize(radius*2,radius*2);
    }
}
