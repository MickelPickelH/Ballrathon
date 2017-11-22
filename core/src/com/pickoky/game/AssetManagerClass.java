package com.pickoky.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by mickelpickel on 2017-04-27.
 */

public class AssetManagerClass {
    public AssetManager assetManager;
    public TextureAtlas ballrathonTextureAltas;
    public TextureRegion arrowLabelRegion;
    public TextureRegion backgroundRegion;
    public TextureRegion ballRegion;
    public TextureRegion circleAttackerRegion;
    public TextureRegion fireRegion;
    public TextureRegion gameOverTextRegion;
    public TextureRegion highestScoreTextRegion;
    public TextureRegion menuButtonRegion;
    public TextureRegion musicButtonRegion;
    public TextureRegion musicOffButtonRegion;
    public TextureRegion newRecordTextRegion;
    public TextureRegion pauseButtonRegion;
    public TextureRegion playWithGuidesRegion;
    public TextureRegion restartButtonRegion;
    public TextureRegion resumeButtonRegion;
    public TextureRegion scoreTextRegion;
    public TextureRegion squareAttackerRegion;
    public TextureRegion startRegion;
    public TextureRegion tapToBoostTextRegion;
    public TextureRegion tapToBuildTextRegion;
    public TextureRegion triangularAttackerRegion;
    public TextureRegion boostRegion;
    public TextureRegion curveRegion[];
    public TextureRegion pathRegion[];

    public final String ballrathonSpriteSheetLocation = "BallrathonSpriteSheet.txt";

    public AssetManagerClass(){
        assetManager = new AssetManager();
        assetManager.load(ballrathonSpriteSheetLocation,TextureAtlas.class);
        assetManager.finishLoading();
        ballrathonTextureAltas = assetManager.get(ballrathonSpriteSheetLocation,TextureAtlas.class);
        curveRegion = new TextureRegion[4];
        pathRegion = new TextureRegion[4];
        //load all the assets
        arrowLabelRegion = ballrathonTextureAltas.findRegion("arrowLabel");
        backgroundRegion = ballrathonTextureAltas.findRegion("background");
        ballRegion = ballrathonTextureAltas.findRegion("ball");
        fireRegion = ballrathonTextureAltas.findRegion(" fire");
        gameOverTextRegion = ballrathonTextureAltas.findRegion("gameOverText");
        highestScoreTextRegion = ballrathonTextureAltas.findRegion("highestScoreText");
        menuButtonRegion = ballrathonTextureAltas.findRegion("menuButton");
        musicButtonRegion = ballrathonTextureAltas.findRegion("musicButton");
        musicOffButtonRegion = ballrathonTextureAltas.findRegion("musicOffButton");
        newRecordTextRegion = ballrathonTextureAltas.findRegion("newRecordText");
        restartButtonRegion = ballrathonTextureAltas.findRegion("restartButton");
        resumeButtonRegion = ballrathonTextureAltas.findRegion("resumeButton");
        scoreTextRegion = ballrathonTextureAltas.findRegion("scoreText");
        squareAttackerRegion = ballrathonTextureAltas.findRegion("squareAttacker");
        startRegion = ballrathonTextureAltas.findRegion("start");
        tapToBoostTextRegion = ballrathonTextureAltas.findRegion(" tapToBoostText");
        tapToBuildTextRegion = ballrathonTextureAltas.findRegion("tapToBuildText");
        triangularAttackerRegion = ballrathonTextureAltas.findRegion("triangularAttacker");
        circleAttackerRegion = ballrathonTextureAltas.findRegion("circleAttacker");
        pauseButtonRegion = ballrathonTextureAltas.findRegion("pauseButton");
        playWithGuidesRegion = ballrathonTextureAltas.findRegion("playWithGuides");
        boostRegion = ballrathonTextureAltas.findRegion("boost");
        curveRegion[0] = ballrathonTextureAltas.findRegion("curve1");
        curveRegion[1] = ballrathonTextureAltas.findRegion("curve2");
        curveRegion[2] = ballrathonTextureAltas.findRegion("curve3");
        curveRegion[3] = ballrathonTextureAltas.findRegion("curve4");
        pathRegion[0] = ballrathonTextureAltas.findRegion("path1");
        pathRegion[1] = ballrathonTextureAltas.findRegion("path2");
        pathRegion[2] = ballrathonTextureAltas.findRegion("path3");
        pathRegion[3] = ballrathonTextureAltas.findRegion("path4");

    }

}
