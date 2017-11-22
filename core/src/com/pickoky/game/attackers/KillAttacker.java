package com.pickoky.game.attackers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pickoky.game.AssetManagerClass;
import com.pickoky.game.Curvy_Ball;
import com.pickoky.game.states.PlayState;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by super on 4/25/2017.
 */

public class KillAttacker extends Attackers{
    private float minY = 5/ Curvy_Ball.PPM;
    private float maxY = Curvy_Ball.HEIGHT/Curvy_Ball.PPM;
    private float defaultx = Curvy_Ball.WIDTH/Curvy_Ball.PPM/2+70;
    private int randomV;
    private CircleShape circleShape;
    private Sprite circleAttackerSprite;
    public KillAttacker(World world, AssetManagerClass assetManagerClass) {
        super(world,assetManagerClass);
        defaultx+= PlayState.getBodyPosX();
//        Gdx.app.log("Default x "+ Curvy_Ball.camera.position.x," <--");
        int randomY = random.nextInt(((int)maxY - (int)minY) + 1/Curvy_Ball.PPM) + (int)minY;
        randomY+=PlayState.getBodyPosY()-50;
        int randomDegree = random.nextInt(6);
        this.randomV = random.nextInt(30)+10;

        circleShape = new CircleShape();
        circleShape.setRadius(2.5f);
        bodyDef.type = BodyDef.BodyType.KinematicBody ;
        bodyDef.position.set(defaultx,randomY);
        fixtureDef.shape = circleShape;
        super.addBody();
        body.setLinearVelocity(-15,0);
        body.setTransform(body.getPosition(),randomDegree);
        circleAttackerSprite = new Sprite(assetManagerClass.circleAttackerRegion);
        circleAttackerSprite.setSize(5,5);
        circleAttackerSprite.setOrigin(circleAttackerSprite.getWidth()/2,circleAttackerSprite.getHeight()/2);
        circleAttackerSprite.rotate(body.getAngle()*180/(float)Math.PI);
        circleShape.dispose();

    }

    public Sprite getCircleAttackerSprite() {
        return circleAttackerSprite;
    }

}
