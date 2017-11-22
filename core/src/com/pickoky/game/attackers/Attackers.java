package com.pickoky.game.attackers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pickoky.game.AssetManagerClass;

/**
 * Created by super on 4/6/2017.
 */
public abstract class Attackers {
    protected World world;
    protected Body body;
    protected BodyDef bodyDef;
    protected Fixture fixture;
    protected FixtureDef fixtureDef;
    protected Vector2 vertices[];
    protected  AssetManager assetManager;
    protected AssetManagerClass assetManagerClass;
    protected  Attackers(World world, AssetManagerClass assetManagerClass){
        this.world = world;
        this.bodyDef = new BodyDef();
        this.fixtureDef = new FixtureDef();
        this.assetManagerClass = assetManagerClass;
    }
    protected void addBody(){
        this.body = world.createBody(bodyDef);
        this.fixture = this.body.createFixture(fixtureDef);
        this.fixture.setUserData(this);
    }
    public void deleteFromWorld(){
        world.destroyBody(body);
    }

    public Body getBody() {
        return body;
    }

}
