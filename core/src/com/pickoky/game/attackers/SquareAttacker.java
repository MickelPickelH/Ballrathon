package com.pickoky.game.attackers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pickoky.game.AssetManagerClass;
import com.pickoky.game.Curvy_Ball;
import com.pickoky.game.states.PlayState;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by MickelPickel on 4/8/2017.
 */

public class SquareAttacker extends Attackers {
    private float minY = 5/ Curvy_Ball.PPM;
    private float maxY = Curvy_Ball.HEIGHT/Curvy_Ball.PPM;
    private float defaultx = Curvy_Ball.WIDTH/Curvy_Ball.PPM/2+70;
    private int randomV;
    private Sprite sqauareAttackerSprite;
    private PolygonShape polygonShape;
    public SquareAttacker(World world, AssetManagerClass assetManagerClass) {
        super(world,assetManagerClass);
        defaultx+= PlayState.getBodyPosX();
//        Gdx.app.log("Default x "+ Curvy_Ball.camera.position.x," <--");
        int randomY = random.nextInt(((int)maxY - (int)minY) + 1/Curvy_Ball.PPM) + (int)minY;
        randomY+=PlayState.getBodyPosY()-50;
        int randomDegree = random.nextInt(6);
        this.randomV = random.nextInt(30)+10;

        this.polygonShape = new PolygonShape();
        polygonShape.setAsBox(2.5f,2.5f);
        bodyDef.type = BodyDef.BodyType.KinematicBody ;
        bodyDef.position.set(defaultx,randomY);
        fixtureDef.shape = polygonShape;
        super.addBody();
        body.setLinearVelocity(-randomV,0);
        body.setTransform(body.getPosition(),randomDegree);
        sqauareAttackerSprite = new Sprite(assetManagerClass.squareAttackerRegion);
        sqauareAttackerSprite.setSize(5,5);
        sqauareAttackerSprite.setOrigin(sqauareAttackerSprite.getWidth()/2,sqauareAttackerSprite.getHeight()/2);
        sqauareAttackerSprite.rotate(body.getAngle()*180/(float)Math.PI);
        polygonShape.dispose();
        // sqauareAttackerSprite.setPosition(100,100);
        //


    }

    public Sprite getSqauareAttackerSprite() {
        return sqauareAttackerSprite;
    }

}
