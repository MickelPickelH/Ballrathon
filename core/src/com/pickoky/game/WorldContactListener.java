package com.pickoky.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pickoky.game.attackers.CircleAttacker;
import com.pickoky.game.attackers.KillAttacker;
import com.pickoky.game.bodies.connectionBody;
import com.pickoky.game.states.PlayState;

/**
 * Created by super on 4/4/2017.
 */

public class WorldContactListener implements ContactListener {
    private PlayState playState;
    private Fixture fixA;
    private Fixture fixB;
    private boolean radiusChanged;
    private float newBallRadius;
    public WorldContactListener(PlayState playState){
        this.playState = playState;
    }
    @Override
    public void beginContact(Contact contact) {
        //Gdx.app.log("contact"," conntect");
        fixA = contact.getFixtureA();
        fixB = contact.getFixtureB();
        if(fixA.getUserData()=="ball"||fixB.getUserData()=="ball"){
//            Fixture ball = fixA.getUserData()=="ball" ? fixA : fixB;
//            Fixture connection = ball == fixA ? fixB : fixA;
//
//            if(connection.getUserData() instanceof connectionBody && playState.getController().isBoostTapped()){
//                ((connectionBody)connection.getUserData()).onContactConnection((connectionBody) connection.getUserData());
//
//            }
            if(fixA.getUserData() instanceof CircleAttacker||fixB.getUserData() instanceof  CircleAttacker){
//                Fixture toBeDestroyed  = (fixA.getUserData()=="ball")? fixA : fixB ;
                PlayState.score += 1;
                if(fixA.getUserData() == "ball"){
                    fixA.getShape().setRadius(fixA.getShape().getRadius()+0.05f);
                    newBallRadius = fixA.getShape().getRadius();
                    ((CircleAttacker)fixB.getUserData()).setCollided(true);
                    Gdx.app.log(Float.toString(fixA.getShape().getRadius())," meters");

                }
                else{
                    fixB.getShape().setRadius(fixB.getShape().getRadius()+0.05f);
                    newBallRadius = fixB.getShape().getRadius();
                    ((CircleAttacker)fixA.getUserData()).setCollided(true);

//                    ((CircleAttacker)fixA.getUserData()).deleteFromWorld();

                    Gdx.app.log(Float.toString(fixB.getShape().getRadius())," meters");


                }
                radiusChanged = true;

            }
            if(fixA.getUserData() instanceof KillAttacker||fixB.getUserData() instanceof  KillAttacker){
                PlayState.hitFire = true;
            }
            PlayState.boostTapped = false;
            PlayState.inContact = true;
            PlayState.countDown = 0;
        }

    }

    @Override
    public void endContact(Contact contact) {


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public float getNewBallRadius() {
        return newBallRadius;
    }
//    public Fixture getFixA() {
//        return fixA;
//    }
//
//    public Fixture getFixB() {
//        return fixB;
//    }


    public boolean isRadiusChanged() {
        return radiusChanged;
    }

    public void setRadiusChanged(boolean radiusChanged) {
        this.radiusChanged = radiusChanged;
    }
}
