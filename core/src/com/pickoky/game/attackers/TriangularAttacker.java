package com.pickoky.game.attackers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pickoky.game.AssetManagerClass;
import com.pickoky.game.Curvy_Ball;
import com.pickoky.game.states.PlayState;

import java.util.Random;

/**
 * Created by super on 4/6/2017.
 */

public class TriangularAttacker extends Attackers {
//    private Body body;
//    private Fixture fixture;
    private float minY = 5/Curvy_Ball.PPM;
    private float maxY = Curvy_Ball.HEIGHT/Curvy_Ball.PPM;
    private float defaultx = Curvy_Ball.WIDTH/Curvy_Ball.PPM/2+70;
    private float triSideLen = 50/Curvy_Ball.PPM;
    private float inscribeRadius = triSideLen*(float)Math.sqrt(3)/6;
    private float factor = 1.1547f;
    private float centerFactor = triSideLen/inscribeRadius;
    private Random random;
    private int randomV;
    private Texture triangleAttackerImg;
    private TextureRegion triangleAttackerRegion;
    private Sprite triangleAttackerSprite;
    private double degrees;
    private Vector2 lowestYVertex;
    private Vector2 rightestXVertex;
    private Vector2 center;
    private PolygonShape polygonShape;
    public TriangularAttacker(World world, AssetManagerClass assetManagerClass){
        super(world,assetManagerClass);
        vertices = new Vector2[3];
        random = new Random();
        defaultx+=PlayState.getBodyPosX();
//        Gdx.app.log("Default x "+ Curvy_Ball.camera.position.x," <--");
        int randomY = random.nextInt(((int)maxY - (int)minY) + 1/Curvy_Ball.PPM) + (int)minY;
        randomY+=PlayState.getBodyPosY()-50;
       // Curvy_Ball.camera.update();
       // Gdx.app.log("Default x "+ PlayState.getBodyPosX()," <--");

        //randomY+=Curvy_Ball.camera.position.y;
        vertices[0] = new Vector2(defaultx,randomY);
        Float randomRadian = random.nextFloat()*3/2;
        vertices[1] = new Vector2(vertices[0].x+(float)Math.cos(randomRadian)*triSideLen,vertices[0].y+(float)Math.sin(randomRadian)*triSideLen);
        float midPointX = (vertices[1].x+vertices[0].x)/2;
        float midPointY = (vertices[1].y+vertices[0].y)/2;

        //determine which vertex is on the left, which one on the right in order t get the correct slope
        Vector2 temp;
        Vector2 temp2;
        temp = vertices[0].x>=vertices[1].x ? vertices[0] : vertices[1];
        temp2 = vertices[0].x!=temp.x ? vertices[0] : vertices[1];
//        float midPointX = (temp.x+temp2.x)/2;
//        float midPointY = (temp.y+temp2.y)/2;
        Vector2 slope = new Vector2((temp.y-temp2.y)*-1,temp.x-temp2.x);
        vertices[2] = new Vector2(midPointX+slope.x/factor,midPointY+slope.y/factor);//devide by factor b/c this game runs 1/60f

        this.randomV = random.nextInt(30)+10;
//        center = new Vector2(midPointX+slope.x/factor/2,midPointY+slope.y/factor/2);
        center = new Vector2(midPointX+slope.x/centerFactor,midPointY+slope.y/centerFactor);

//        CircleShape circle = new CircleShape();
//        circle.setRadius(5);
//        bodyDef.type = BodyDef.BodyType.KinematicBody;
//        bodyDef.position.set(center);
//        fixtureDef.shape = circle;
//        body = world.createBody(bodyDef);
//        body.createFixture(fixtureDef);
//        body.setLinearVelocity(-randomV,0);
//
        bodyDef.type = BodyDef.BodyType.KinematicBody ;
        bodyDef.position.set(center);
        for(int i = 0; i < 3; i ++){
            vertices[i].x -= center.x;
            vertices[i].y -= center.y;
        }
        //Gdx.app.log(Float.toString(midPointX+slope.x/factor/2)," "+Float.toString(midPointY+slope.y/factor/2));
        this.polygonShape = new PolygonShape();
        polygonShape.set(vertices);
        fixtureDef.shape = polygonShape;
        super.addBody();
        polygonShape.dispose();
//        this.body = world.createBody(bodyDef);
      // Gdx.app.log(Float.toString(body.getPosition().x)," "+Float.toString(body.getPosition().y));
//        this.fixture = this.body.createFixture(fixtureDef);
//        this.fixture.setUserData(this);
        body.setLinearVelocity(-randomV,0);
       // body.setLinearVelocity(-20,0);

        //get degrees
        lowestYVertex = vertices[0];
        rightestXVertex = vertices[0];
        for(int i = 1; i < 3;i++){
            if(lowestYVertex.y>vertices[i].y){
                lowestYVertex = vertices[i];
            }
            if(rightestXVertex.x<vertices[i].x){
                rightestXVertex = vertices[i];
            }
        }
        if(lowestYVertex.x==rightestXVertex.x){
            degrees=90;
        }
        else {
            degrees = 180/Math.PI*(Math.atan((rightestXVertex.y - lowestYVertex.y) / (rightestXVertex.x - lowestYVertex.x)));
        }
        //make image
       // triangleAttackerRegion = new TextureRegion(triangleAttackerImg,0,0,1,0.866f);
        triangleAttackerSprite = new Sprite(assetManagerClass.triangularAttackerRegion);
        triangleAttackerSprite.setSize(5,5);

        triangleAttackerSprite.rotate((float)degrees);
        triangleAttackerSprite.setOrigin(triangleAttackerSprite.getWidth()/2, triangleAttackerSprite.getHeight()/2);
        triangleAttackerSprite.setCenter(center.x,center.y);
        Gdx.app.log("x "+Float.toString(triangleAttackerSprite.getX())," y"+Float.toString(triangleAttackerSprite.getY()));



    }

    public Sprite getTriangleAttackerSprite() {
        return triangleAttackerSprite;
    }


}
