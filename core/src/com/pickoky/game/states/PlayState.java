package com.pickoky.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Queue;
import com.pickoky.game.AssetManagerClass;
import com.pickoky.game.Controller;
import com.pickoky.game.Curvy_Ball;
import com.pickoky.game.WorldContactListener;
import com.pickoky.game.attackers.CircleAttacker;
import com.pickoky.game.attackers.KillAttacker;
import com.pickoky.game.attackers.SquareAttacker;
import com.pickoky.game.attackers.TriangularAttacker;
import com.pickoky.game.bodies.ConnectionBodyVertices;
import com.pickoky.game.bodies.connectionBody;
import com.pickoky.game.sprites.Ball;
import com.pickoky.game.sprites.Boost;
import com.pickoky.game.sprites.Connection;

/**
 * Created by MickelPickel on 3/25/2017.
 */

public class PlayState extends State {
    public static int maxOrderCount;
    public int orderIndex[];
    private Ball ball;
    private static Connection connection;
    private Boost boost;
    public int preCurveOrJoint[];
    private Vector3 touchPos;
    public static boolean firstTap;
    public static boolean secondTap;
    private int orderCount;
    private int preOrderCount;
    private int curIndex;
    private float preCorX,preCorY;
    private World world;
    public static  Body body;
    private Body ground;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private Fixture fixture;
    private CircleShape circleShape;
    private EdgeShape edgeShape;
    private Box2DDebugRenderer box2DDebugRenderer;
    private final int PPM = 10;
    private int counter = 0;

    private ConnectionBodyVertices innerCurveVertices;
    private ConnectionBodyVertices outterCurveVertices;
    private ConnectionBodyVertices innerJointVertices;
    private ConnectionBodyVertices outterJointVertices;

    private float connectionBodyX;
    private float connectionBodyY;

    private float oriBallX;

    public static boolean inContact;
    public static boolean boostTapped;
    private boolean startDisposeBody = false;

    private Queue<TriangularAttacker> triangularAttackers;
    private Queue<SquareAttacker> squareAttackers;
    private Queue<CircleAttacker> circleAttackers;
    private Queue<KillAttacker>killAttackers;
    public static boolean onSquareAttackers;
    private int squareAttackersNum;
    private int triangularAttackersNum;
    private int circleAttackersNum;
    private int killAttackersNum;
    private int numOfCircleAttackerRemoved;
    //private connectionBody connectionbody[];
    private Queue<connectionBody>connectionbody;
    private WorldContactListener worldContactListener;
    private float originalBallSize;

    public static int countDown;

    public static Controller controller;

    public static int score;
    private int score2;

    public static boolean hitFire;
    private int gone;

    private final int PATHDIMENSION = 200;
    public PlayState (GameStateManager gsm, AssetManagerClass assetManagerClass) {
        super(gsm,assetManagerClass);
        gone = 0;
        world = new World((new Vector2(0,-100f)),true);
        ball = new Ball(Curvy_Ball.camera,world,assetManagerClass);
        connection = new Connection((Curvy_Ball.camera),world,assetManagerClass);
        boost = new Boost(Curvy_Ball.camera,assetManagerClass);
        controller = new Controller(connection,assetManagerClass);

        box2DDebugRenderer = new Box2DDebugRenderer();
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Curvy_Ball.WIDTH/PPM/8-ball.getBallSprite().getWidth()/2,Curvy_Ball.HEIGHT/PPM/2-ball.getBallSprite().getWidth()/2);
        circleShape = new CircleShape();
        circleShape.setRadius(2.5f);
        fixtureDef.shape = circleShape;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        fixtureDef.density = 2;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("ball");
        originalBallSize = circleShape.getRadius()*2;
        circleShape.dispose();
        oriBallX = bodyDef.position.x;
        Curvy_Ball.camera.position.set(body.getPosition().x+Curvy_Ball.WIDTH/2/PPM-oriBallX,body.getPosition().y,0);
        Curvy_Ball.camera.update();

//        edgeShape = new EdgeShape();
//        bodyDef.type = BodyDef.BodyType.KinematicBody;
//        edgeShape.set(0,0,200,0);
//        bodyDef.position.set(0,10);
//        fixtureDef.shape = edgeShape;
//        ground = world.createBody(bodyDef);
//        ground.createFixture(fixtureDef);
//        edgeShape.dispose();
        maxOrderCount= 10;
       // maxOrderCount = (int)(((Curvy_Ball.camera.viewportWidth-ball.getBallSprite().getX())/(connection.loopImg.getWidth()/2))*2)*100;
        orderIndex = new int[maxOrderCount];//for storing the index of the curves, from 0~3
        preCurveOrJoint = new int[maxOrderCount];
       // connectionbody = new connectionBody[maxOrderCount];
        connectionbody = new Queue<connectionBody>();
        touchPos = new Vector3();
        firstTap = true;
        secondTap = false;
        orderCount = -1;
        preOrderCount = -1;

        innerCurveVertices = new ConnectionBodyVertices(connection.getCurveSprite()[0].getHeight()-2.5f);
        outterCurveVertices = new ConnectionBodyVertices(connection.getCurveSprite()[0].getHeight());
        Gdx.app.log(Float.toString(connection.getCurveSprite()[0].getHeight())," meters");
        worldContactListener = new WorldContactListener(this);
        world.setContactListener(worldContactListener);

        //connectionBody
        triangularAttackers = new Queue<TriangularAttacker>();
        squareAttackers = new Queue<SquareAttacker>();
        circleAttackers = new Queue<CircleAttacker>();
        killAttackers = new Queue<KillAttacker>();
        onSquareAttackers = false;

        squareAttackersNum = 0;
        triangularAttackersNum = 0;
        circleAttackersNum = 0;
        killAttackersNum = 0;
        numOfCircleAttackerRemoved = 0;

        countDown = 0;

        score = 0;
        score2 = 0;
        hitFire = false;
    }


    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        if(Gdx.input.justTouched()){
            touchPos.x = Gdx.input.getX();
            touchPos.y = Gdx.input.getY();
            Curvy_Ball.camera.unproject(touchPos);
          //  Gdx.app.log(" ",Float.toString(touchPos.x)+" "+ Float.toString(touchPos.y));
        }
        if(controller.getTappedIndex()!=-1){
            connectionClicked(controller.getTappedIndex());
            controller.setTappedIndex(-1);
            //Gdx.app.log("this is ","ran");

            //Curvy_Ball.camera.position.set(body.getPosition().x,body.getPosition().y,0);
            //Curvy_Ball.camera.update();
//            for (int i = 0; i <= orderCount; i++) {
//                if (connection.getCurveOrderIndexCorX()[i] != 0 && connection.getCurveOrderIndexCorY()[i] != 0) {//if it is not (0,0), it means that it was tapped.
//                    connection.setCurveBody(connection.getCurveBody()[orderIndex[i]],connection.getJointOrderIndexCorX()[i],connection.getCurveOrderIndexCorY()[i]);
//                } else if (connection.getJointOrderIndexCorX()[i] != 0 && connection.getJointOrderIndexCorY()[i] != 0) {//if it is not (0,0), it means that it was tapped.
//                }
//            }
        }
//        controller.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //onBoost(controller,connectionBody.connectVertices);
        if(inContact&&boostTapped){
            onBoost();
        }
       // Gdx.app.log("isContact ",Boolean.toString(inContact));

        if(controller.isNewGame()){
            controller.setNewGame(false);
            controller.dispose();
            gsm.set(new PlayState(gsm,assetManagerClass));
            dispose();
        }
       if(!firstTap&& triangularAttackersNum >=1){
           if(triangularAttackers.last().getBody().getPosition().x<=ball.getBallSprite().getX()-30){
              // triangularAttackers.last().getTriangleAttackerSprite().getTexture().dispose();
               triangularAttackers.last().deleteFromWorld();
               triangularAttackers.removeLast();
               triangularAttackersNum -=1;
           }

       }
       if(!firstTap&&squareAttackersNum>=1){
           if(squareAttackers.last().getBody().getPosition().x<=ball.getBallSprite().getX()-30){
               //squareAttackers.last().getSqauareAttackerSprite().getTexture().dispose();
               squareAttackers.last().deleteFromWorld();
               squareAttackers.removeLast();
               squareAttackersNum-=1;
           }
       }
        if(!firstTap&&killAttackersNum>=1){
            if(killAttackers.last().getBody().getPosition().x<=ball.getBallSprite().getX()-30){
                //squareAttackers.last().getSqauareAttackerSprite().getTexture().dispose();
                killAttackers.last().deleteFromWorld();
                killAttackers.removeLast();
                killAttackersNum-=1;
            }
        }
//        if(!firstTap&&circleAttackersNum>=1){
//            if(circleAttackers.last().getBody().getPosition().x<=ball.getBallSprite().getX()-30){
//                //squareAttackers.last().getSqauareAttackerSprite().getTexture().dispose();
//                circleAttackers.last().deleteFromWorld();
//                circleAttackers.removeLast();
//                circleAttackersNum-=1;
////                numOfCircleAttackerRemoved+=1;
//            }
//        }
       if(onSquareAttackers){
           squareAttackers.addFirst(new SquareAttacker(world,assetManagerClass));
           onSquareAttackers = false;
           squareAttackersNum+=1;
       }
       if(!firstTap){
           world.step(1 / 60f, 8, 3);
           countDown+=1;


       }


       if(worldContactListener.isRadiusChanged()&&!firstTap) {
           //Gdx.app.log(Float.toString(worldContactListener.getFixA().getShape().getRadius())," meters");
//           ball.setBallSize(worldContactListener.getFixA().getShape().getRadius());
           ball.setBallSize(worldContactListener.getNewBallRadius());

           ball.setBallSprite(body.getPosition().x,body.getPosition().y);
           worldContactListener.setRadiusChanged(false);
       }
        if(score >Curvy_Ball.highscore){
            Curvy_Ball.preferences.putInteger("highscore", score);
            Curvy_Ball.preferences.flush();
            Curvy_Ball.highscore = Curvy_Ball.preferences.getInteger("highscore");
        }
        controller.getScoreLabel().setText(Integer.toString(score));
        controller.getHighscoreLabel().setText(Integer.toString(Curvy_Ball.highscore));
        controller.getMeterLabelOver().setText(Integer.toString(score));

        if(body.getPosition().y <= Curvy_Ball.camera.position.y-Curvy_Ball.camera.viewportHeight){
            // Curvy_Ball.gameState = Curvy_Ball.GameState.PAUSE;  //****need to make this GAME_OVER later****
            controller.setNewGame(true);
            ////////////Curvy_Ball.gameState = Curvy_Ball.GameState.GAME_OVER;
            countDown = 0;
        }
        if(hitFire){
            countDown = 180;
            body.applyLinearImpulse(0,1000,body.getWorldCenter().x,body.getWorldCenter().y,true);
            while(connectionbody.iterator().hasNext()){
                connectionbody.last().dispose();
                connectionbody.removeLast();
            }
            while(circleAttackers.iterator().hasNext()){
                circleAttackers.last().deleteFromWorld();
                circleAttackers.removeLast();
                circleAttackersNum-=1;
            }
            while(squareAttackers.iterator().hasNext()){
                squareAttackers.last().deleteFromWorld();
                squareAttackers.removeLast();
                squareAttackersNum-=1;
            }
            while(triangularAttackers.iterator().hasNext()){
                triangularAttackers.last().deleteFromWorld();
                triangularAttackers.removeLast();
                triangularAttackersNum -=1;
            }


            hitFire = false;
        }
        if(circleAttackersNum%2==0&&score2!=circleAttackersNum){
            killAttackers.addFirst(new KillAttacker(world,assetManagerClass));
            killAttackersNum += 1;
            score2=circleAttackersNum;
        }


    }

    public void pauseUpdate(){
        controller.getStage().addActor(controller.getResumeImg());
    }
    public void gameoverUpdate(){
        controller.getStage().addActor(controller.getGameOverLabel());
        controller.getStage().addActor(controller.getDistanceTraveledLabel());
        controller.getStage().addActor(controller.getMeterLabelOver());
        controller.getStage().addActor(controller.getMeterWordLabel());
    }

    private boolean isCollided(CircleAttacker item){
        if(item.isCollided()){
            return true;
        }
        return false;
    }
    @Override
    public void render(SpriteBatch batch) {

        batch.begin();

        if(!firstTap) {
//            world.step(1 / 60f, 8, 3);
            ball.setBallSprite(body.getPosition().x, body.getPosition().y);

            for (int i = 0; i < maxOrderCount&& i < counter; i++) {
                if (connection.getCurveOrderIndexCorX()[i] != 0 && connection.getCurveOrderIndexCorY()[i] != 0) {//if it is not (0,0), it means that it was tapped.
                    batch.draw(connection.getCurve()[orderIndex[i]], connection.getCurveOrderIndexCorX()[i], connection.getCurveOrderIndexCorY()[i],10,10);//get the index of each curve and draw them
                }
            }
            for(TriangularAttacker item: triangularAttackers){
                //item.getTriangleAttackerSprite().setPosition(item.getCenter().x-10f,item.getCenter().y+20f);
                item.getTriangleAttackerSprite().draw(batch);
                item.getTriangleAttackerSprite().setCenter(item.getBody().getPosition().x-0.5f,item.getBody().getPosition().y+0.65f);

            }
            for(SquareAttacker item: squareAttackers){
                item.getSqauareAttackerSprite().setCenter(item.getBody().getPosition().x,item.getBody().getPosition().y);
                item.getSqauareAttackerSprite().draw(batch);
            }
            for(CircleAttacker item: circleAttackers){
                if(!isCollided(item)) {
                    item.getCircleAttackerSprite().setCenter(item.getBody().getPosition().x, item.getBody().getPosition().y);
                    if(item.getCircleAttackerSprite().getX()>ball.getBallSprite().getX()-30) {
                        item.getCircleAttackerSprite().draw(batch);
                    }

                }
                else{
                    item.deleteFromWorld();
                    circleAttackers.removeValue(item,true);
                    circleAttackersNum-=1;
                    gone+=1;
                }
            }
//            Queue<CircleAttacker> item;
//            item = circleAttackers;
//            while(item.iterator().hasNext()){
//                if(!isCollided(item.iterator())) {
//                    item.iterator().next().getCircleAttackerSprite().setCenter(item.iterator().next().getBody().getPosition().x, item.iterator().next().getBody().getPosition().y);
//                    item.iterator().next().getCircleAttackerSprite().draw(batch);
//
//                }
//                else{
//                    item.iterator().next().deleteFromWorld();
//                    circleAttackers.removeValue(item.iterator().next(),true);
//                    circleAttackersNum-=1;
//                    gone+=1;
//                    Gdx.app.log(Integer.toString(gone), " ones gone");
//
//                }
//            }
            if(!firstTap&&circleAttackersNum>=1){
                if(circleAttackers.last().getBody().getPosition().x<=ball.getBallSprite().getX()-30-Curvy_Ball.WIDTH){
                    //squareAttackers.last().getSqauareAttackerSprite().getTexture().dispose();
                    circleAttackers.last().deleteFromWorld();
                    circleAttackers.removeLast();
                    circleAttackersNum-=1;
                    gone+=1;
                    Gdx.app.log(Integer.toString(gone), " ones gone");
//                numOfCircleAttackerRemoved+=1;
                }
            }


        }

        //boost.getBoostSprite().draw(batch);
        ball.getBallSprite().draw(batch);
        batch.end();
        box2DDebugRenderer.render(world, Curvy_Ball.camera.combined);

        if(countDown/60 < 3&&Curvy_Ball.gameState== Curvy_Ball.GameState.RUN){
            Curvy_Ball.camera.position.set(body.getPosition().x+Curvy_Ball.WIDTH/2/PPM-oriBallX,body.getPosition().y,0);
            Curvy_Ball.camera.update();
        }
        //Gdx.app.log(Integer.toString(countDown/60)," seconds");

        if(Curvy_Ball.gameState == Curvy_Ball.GameState.PAUSE){
            pauseUpdate();
        }
        if(Curvy_Ball.gameState== Curvy_Ball.GameState.GAME_OVER){
            gameoverUpdate();
        }
        controller.draw();

//        if(!Curvy_Ball.preferences.getBoolean("firstgame")){
//            controller.getHowToBuildLabel().setVisible(false);
//            controller.getHowToJumpLabel().setVisible(false);
//        }
    }

    @Override
    public void dispose() {
    }
    private float shiftConnectionCorX(int index, Sprite sprite){
        switch (index){
            case 0:
                return 0;
            case 1:
                return sprite.getWidth();
            case 2:
                return sprite.getWidth();
            case 3:
                return 0;
            default:
                return 0;
        }
    }
    private float shiftConnectionCorY(int index, Sprite sprite){
        switch (index){
            case 0:
                return 0;
            case 1:
                return 0;
            case 2:
                return sprite.getWidth();
            case 3:
                return sprite.getWidth();
            default:
                return 0;
        }
    }
    private void setNewCor(int tappedIndex){
        connection.setCurveOrderIndexCorY(orderCount,preCorY + shiftPreviousY(orderIndex[preOrderCount]) + shiftNewY(tappedIndex)); //it equals to the starting point of the previous connection+the shift of the previous img's starting to ending+shift of new img from bottom to it's starting point.E.g curve[0]'s new shift=-100+25 ,curve[1]'s is 0
        connection.setCurveOrderIndexCorX(orderCount,preCorX + shiftPreviousX(orderIndex[preOrderCount]));//equals to the previous connection's starting x + the shift to it's ending, either 100 or 75
        preCurveOrJoint[orderCount] = 0;//the current connection is a curve, therefore, its set to 0. curve = 0, joint = 1

    }
    private void getPreCor(){
//        if (preCurveOrJoint[orderCount - 1] == 0) {//it the last connection tapped is a curve, which is 0
//            preCorX = connection.getCurveOrderIndexCorX()[orderCount - 1];//set the starting point of the current img the starting point of the previous img
//            preCorY = connection.getCurveOrderIndexCorY()[orderCount - 1];//both x and y
//
//        } else if (preCurveOrJoint[orderCount - 1] == 1) {//same logic as above but for joints
//            preCorX = connection.getJointOrderIndexCorX()[orderCount - 1];
//            preCorY = connection.getJointOrderIndexCorY()[orderCount - 1];
//        }
        if (preCurveOrJoint[preOrderCount] == 0) {//it the last connection tapped is a curve, which is 0
            preCorX = connection.getCurveOrderIndexCorX()[preOrderCount];//set the starting point of the current img the starting point of the previous img
            preCorY = connection.getCurveOrderIndexCorY()[preOrderCount];//both x and y

        }
    }
    private void executeFirstTap(int tappedIndex){
        connection.setCurveOrderIndexCorX(orderCount,firstTapMethodX(tappedIndex));
        connection.setCurveOrderIndexCorY(orderCount,firstTapMethodY(tappedIndex));
        preCurveOrJoint[orderCount] = 0;

        firstTap = false;
        curIndex = 0;//it was set as -1 in the beginning
    }
    private float shiftNewY(int index){
        switch (index){
            case 0:
                return -PATHDIMENSION/2/PPM+originalBallSize/2;

            case 1:
                return 0;

            case 2:
                return -PATHDIMENSION/2/PPM;

            case 3:
                return 0;

            default:
                return 0;

        }
    }
    private float shiftPreviousY(int index){
        switch (index){
            case 0:
                return 0;

            case 1:
                return PATHDIMENSION/2/PPM-originalBallSize/2;//25 is the width of the curve, loop.getHeight() returns 200

            case 2:
                return 0;

            case 3:
                return PATHDIMENSION/2/PPM;

            default:
                return 0;

        }
    }
    private float shiftPreviousX(int index){
        switch (index){
            case 0:
                return PATHDIMENSION/2/PPM-originalBallSize/2;

            case 1:
                return PATHDIMENSION/2/PPM;//25 is the width of the curve, loop.getHeight() returns 200

            case 2:
                return PATHDIMENSION/2/PPM;

            case 3:
                return PATHDIMENSION/2/PPM-originalBallSize/2;

            default:
                return 0;

        }
    }


    private float firstTapMethodX(int tappedIndex){
        if(tappedIndex ==0) {
            return ball.getCurveCorX()+ball.getBallSprite().getWidth()/2-2f;
        }
        else if(tappedIndex==1){
            return ball.getCurveCorX()+ball.getBallSprite().getWidth();
        }
        else if(tappedIndex==2){
            return ball.getCurveCorX()-ball.getBallSprite().getWidth()+3;

        }
        else{
            return ball.getCurveCorX()+ball.getBallSprite().getWidth()/2;

        }
    }
    private float firstTapMethodY(int tappedIndex){
        if(tappedIndex ==0) {
            return ball.getCurveCorY()-PATHDIMENSION/2/PPM ;//curve 0 to curve 3
        }
        else if(tappedIndex==1){
            return ball.getCurveCorY() + ball.getBallSprite().getHeight()/2;
        }
        else if(tappedIndex==2){
            return ball.getCurveCorY()-PATHDIMENSION/2/PPM+ball.getBallSprite().getHeight()/2-2;

        }
        else{
            return ball.getCurveCorY()-ball.getBallSprite().getHeight()/2;

        }
        //  Gdx.app.log("X POSITION IS  ",Float.toString(curveOrderIndexCorX[orderCount]));

    }

private void connectionAdjust(int orderCount, int preOrderCount){
    if((orderIndex[orderCount]==2&&orderIndex[preOrderCount]==2)||(orderIndex[orderCount]==2&&orderIndex[preOrderCount]==1)){
        preCorY+=25/PPM;
        preCorX-=25/PPM;
    }
    else if((orderIndex[orderCount]==0&&orderIndex[preOrderCount]==0)||(orderIndex[orderCount]==3&&orderIndex[preOrderCount]==0)
                ||(orderIndex[orderCount]==3&&orderIndex[preOrderCount]==3)){
        preCorY-=25/PPM;
    }
}

public  void connectionClicked(int tappedIndex){

    //check the which connection is tapped
    //getCurImg(tappedIndex);
    triangularAttackers.addFirst(new TriangularAttacker(world,assetManagerClass));
    triangularAttackersNum +=1;
    circleAttackers.addFirst(new CircleAttacker(world,assetManagerClass));
    circleAttackersNum +=1;
    counter+=1;
//    orderCount += 1;//its original value was -1; now it is 0.
//    orderIndex[orderCount] = tappedIndex;//tappedIndex is b/t 0~3, this records the order of the ball that will be draw as well as the setting the position of the curve or joint
    if (firstTap) {//this is for when the ball is first tapped.
        orderCount += 1;
        orderIndex[orderCount] = tappedIndex;
        executeFirstTap(tappedIndex);
            connectionBodyX = shiftConnectionCorX(tappedIndex, connection.getCurveSprite()[tappedIndex]);
            connectionBodyY = shiftConnectionCorY(tappedIndex, connection.getCurveSprite()[tappedIndex]);
        if (tappedIndex <= 1) {
                //connectionbody[orderCount] = new connectionBody(world, outterCurveVertices.getConnectionBodyVertices(orderIndex[orderCount]), connection.getCurveOrderIndexCorX()[orderCount]+connectionBodyX, connection.getCurveOrderIndexCorY()[orderCount]+connectionBodyY);
            connectionbody.addFirst(new connectionBody(world, outterCurveVertices.getConnectionBodyVertices(orderIndex[orderCount]), connection.getCurveOrderIndexCorX()[orderCount]+connectionBodyX, connection.getCurveOrderIndexCorY()[orderCount]+connectionBodyY));
        }
        else {
               // connectionbody[orderCount] = new connectionBody(world, innerCurveVertices.getConnectionBodyVertices(orderIndex[orderCount]), connection.getCurveOrderIndexCorX()[orderCount]+connectionBodyX, connection.getCurveOrderIndexCorY()[orderCount]+connectionBodyY);
            connectionbody.addFirst(new connectionBody(world, innerCurveVertices.getConnectionBodyVertices(orderIndex[orderCount]), connection.getCurveOrderIndexCorX()[orderCount]+connectionBodyX, connection.getCurveOrderIndexCorY()[orderCount]+connectionBodyY));

        }

        firstTap = false;


    }
    else {

        if(orderCount == maxOrderCount -1){
           startDisposeBody = true;
            orderCount = 0;
            preOrderCount = maxOrderCount-1;
        }

        else{
            orderCount += 1;
            preOrderCount = orderCount -1;
        }
      //  orderCount += 1;
        orderIndex[orderCount] = tappedIndex;
        getPreCor();
        connectionAdjust(orderCount,preOrderCount);
        setNewCor(tappedIndex);
            connectionBodyX = shiftConnectionCorX(tappedIndex, connection.getCurveSprite()[tappedIndex]);
            connectionBodyY = shiftConnectionCorY(tappedIndex, connection.getCurveSprite()[tappedIndex]);
            if (tappedIndex <= 1) {
//                connectionbody[orderCount] = new connectionBody(world, outterCurveVertices.getConnectionBodyVertices(orderIndex[orderCount]), connection.getCurveOrderIndexCorX()[orderCount] + connectionBodyX, connection.getCurveOrderIndexCorY()[orderCount] + connectionBodyY);
                connectionbody.addFirst(new connectionBody(world, outterCurveVertices.getConnectionBodyVertices(orderIndex[orderCount]), connection.getCurveOrderIndexCorX()[orderCount]+connectionBodyX, connection.getCurveOrderIndexCorY()[orderCount]+connectionBodyY));

            } else {
                connectionbody.addFirst(new connectionBody(world, innerCurveVertices.getConnectionBodyVertices(orderIndex[orderCount]), connection.getCurveOrderIndexCorX()[orderCount]+connectionBodyX, connection.getCurveOrderIndexCorY()[orderCount]+connectionBodyY));

//   connectionbody[orderCount] = new connectionBody(world, innerCurveVertices.getConnectionBodyVertices(orderIndex[orderCount]), connection.getCurveOrderIndexCorX()[orderCount] + connectionBodyX, connection.getCurveOrderIndexCorY()[orderCount] + connectionBodyY);

            }

        //curveCorY = curveOrderIndexCorY[orderCount];
        //curveCorX =  curveOrderIndexCorX[orderCount];
        if(startDisposeBody){
            connectionbody.last().dispose();
            connectionbody.removeLast();

        }
    }
//    if (orderCount >= maxOrderCount) {//reset orderCount it hits the max number.
//        orderCount = -1;//i still need to make a method so that when the max is reached. I get the position of the curve, and all the info for the curve and ball prior to the max is reached.
//    }


}

    public static Connection getConnection() {
        return connection;
    }

    public Controller getController() {
        return controller;
    }
    private void onBoost(){
        if(body.getLinearVelocity().x<=0){
            body.setLinearVelocity(new Vector2(10,75));
        }
        else{
            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x,75));
        }
        inContact = false;
        boostTapped = false;
    }
//    private void onBoost(Controller controller,Vector2 connectVertices ){
//        if(controller.isBoostTapped()&&connectionBody.isOnContact){
//            //float deltaX = Math.abs(connectVertices.x-body.getPosition().x);
//            //float deltaY = Math.abs(connectVertices.y-body.getPosition().y);
//            //Vector2 ballMovement = new Vector2(deltaY*300,deltaX*300);
//            body.setLinearVelocity(new Vector2(5,60));
//            controller.setBoostTapped(false);
//            connectionBody.isOnContact = false;
//            Gdx.app.log("this is  ","asdasdasdasdasdasdasdran ");
//
//
//        }

//    public Body getBody() {
//        return body;
//    }
    public static float getBodyPosX(){
        return body.getPosition().x;
    }
    public static float getBodyPosY(){
        return body.getPosition().y;
    }
//    }
}
