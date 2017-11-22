package com.pickoky.game.bodies;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by MickelPickel on 3/31/2017.
 */

public  class connectionBody {
    private World world;
    private Body body;
    private BodyDef bodyDef;
    private Fixture fixture;
    private FixtureDef fixtureDef;
    private ChainShape chainShape;
    public  static Vector2 connectVertices;
    public static boolean isOnContact;

    private Vector2 vertices[];

    public connectionBody(World world, Vector2 vertices[], float posX, float posY) {
        //initialization
        this.world = world;
        this.vertices = vertices;
        bodyDef = new BodyDef();
        chainShape = new ChainShape();
        fixtureDef = new FixtureDef();
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        //create the body
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(posX, posY);
        chainShape.createChain(vertices);
        fixtureDef.shape = chainShape;
        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        chainShape.dispose();

    }

    public void onContactConnection(connectionBody connectionbody){
         connectVertices = new Vector2(connectionbody.body.getPosition());
         isOnContact = true;
    }

    public void dispose(){
        world.destroyBody(body);
    }
}