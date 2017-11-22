package com.pickoky.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pickoky.game.sprites.Connection;
import com.pickoky.game.states.PlayState;

import static com.pickoky.game.Curvy_Ball.batch;
import static com.pickoky.game.Curvy_Ball.highscore;

/**
 * Created by MickelPickel on 4/1/2017.
 */

public class Controller {
    private Viewport viewport;
    private Stage stage;
    private static OrthographicCamera camera;
    private final int PPM = 10;
    private final float WIDTH = 1980;
    private final float HEIGHT = 1080;
    private Vector3 v;
    private  int tappedIndex = -1;
    private boolean newGame = false;
    private boolean boostTapped = false;

    private Table curve;
    private Image curveImg[];
    private Image boostImage;

    private Image restartImg;
    private Image menuImg;
    private Image resumeImg;
    private Image musicImg;
    private Image musicOffImg;
    private Image scoreImg;
    private Image highestScoreImg;

    private Label scoreLabel;
    private Label meterLabelOver;
    private Label gameOverLabel;
    private Label distanceTraveledLabel;
    private Label meterWordLabel;
    private Label highscoreLabel;
    private Label howToJumpLabel;
    private Label howToBuildLabel;

    private boolean musicOn;
    private BitmapFont font;

    private AssetManagerClass assetManagerClass;
    public Controller(Connection connection,AssetManagerClass assetManagerClass){
        v = new Vector3();
        this.assetManagerClass = assetManagerClass;
        camera = new OrthographicCamera();

        viewport = new ExtendViewport(WIDTH/PPM,HEIGHT/PPM,camera);
        viewport.apply();
        camera.position.set(WIDTH/PPM/2,HEIGHT/PPM/2,0);
        camera.update();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.getInputProcessor().touchDown(0,0,0,0);

        curve = new Table();
        //Table boost = new Table();

        curveImg = new Image[4];
        Image boostImg = new Image(assetManagerClass.boostRegion);

        restartImg = new Image(assetManagerClass.restartButtonRegion);
        menuImg = new Image(assetManagerClass.menuButtonRegion);
        resumeImg = new Image(assetManagerClass.resumeButtonRegion);
        musicImg = new Image(assetManagerClass.musicButtonRegion);
        musicOffImg = new Image(assetManagerClass.musicOffButtonRegion);
       // scorTexture = new Texture("scoreText.png");
      //  highestScoreTexture = new Texture("highestScoreText.png");
      //  scorTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
       // highestScoreTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        scoreImg = new Image(assetManagerClass.scoreTextRegion);
        highestScoreImg = new Image(assetManagerClass.highestScoreTextRegion);


        //Text score = new Text("Distance: ");
         //distanceLabel = new Label("Distance",new Label.LabelStyle(new BitmapFont(),Color.BLACK));
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font,Color.BLACK);
        scoreLabel = new Label(Integer.toString(PlayState.score),labelStyle);
        meterLabelOver = new Label(Integer.toString(PlayState.score),labelStyle);
        gameOverLabel = new Label("Game Over",labelStyle);
        distanceTraveledLabel = new Label("You  Went",labelStyle);
        meterWordLabel = new Label(" Meters",labelStyle);
        howToJumpLabel = new Label(" Tap to Jump", labelStyle);
        howToBuildLabel = new Label(" Tap to Build Path", labelStyle);

        highscoreLabel = new Label(Integer.toString(Curvy_Ball.highscore),labelStyle);
        musicOn = true;
        //TextureRegionDrawable restartBtnDrawable = new
       // ImageButton restartBtn = new ImageButton(restartImg);



       // restartImg.draw(Curvy_Ball.batch,0.5f);
        //Actor boostImg = new Actor();
       // Button boostimg = new Button(boostImg);
       // boostImg.

        for(int i = 0;i < 4; i ++) {
            curveImg[i] = new Image(assetManagerClass.curveRegion[i]);
        }
        curveImg[0].addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Curvy_Ball.gameState == Curvy_Ball.GameState.RUN) {
                    tappedIndex = 0;

                }
                return true;
            }
        });
        curveImg[1].addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Curvy_Ball.gameState == Curvy_Ball.GameState.RUN) {
                    tappedIndex = 1;

                }
                return true;
            }
        });
        curveImg[2].addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Curvy_Ball.gameState == Curvy_Ball.GameState.RUN) {
                    tappedIndex = 2;

                }
                return true;
            }
        });
        curveImg[3].addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Curvy_Ball.gameState == Curvy_Ball.GameState.RUN) {
                    tappedIndex = 3;

                }
                return true;
            }
        });

        restartImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Curvy_Ball.gameState == Curvy_Ball.GameState.RUN) {
                    newGame = true;
                    Curvy_Ball.gameState = Curvy_Ball.GameState.RUN;
                }
                return true;
            }
        });
        menuImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Curvy_Ball.gameState == Curvy_Ball.GameState.RUN) {
                    Curvy_Ball.gameState = Curvy_Ball.GameState.PAUSE;
                }
                return true;
            }
        });
        resumeImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Curvy_Ball.gameState== Curvy_Ball.GameState.PAUSE){
                    stage.getActors().peek().remove();
                }
                Curvy_Ball.gameState = Curvy_Ball.GameState.RUN;
                return true;
            }
        });
        boostImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //boostTapped = true;
                if(Curvy_Ball.gameState == Curvy_Ball.GameState.RUN) {
                    PlayState.boostTapped = true;
                    PlayState.onSquareAttackers = true;

                }
//                Gdx.app.log("boost"," tapped");
                return true;
            }
        });

        musicImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                musicOn = false;
                musicOffImg.setVisible(true);
                return true;
            }
        });
        musicOffImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                musicOn = true;
                musicOffImg.setVisible(false);
                return true;
            }
        });
        curve.setPosition(Curvy_Ball.camera.viewportWidth-20,Curvy_Ball.camera.viewportHeight/7);

        curve.add(curveImg[1]).size(15,15).padRight(2.5f).padBottom(2.5f).padTop(2.5f);
        curve.add(curveImg[0]).size(15,15).padLeft(2.5f).padBottom(2.5f).padTop(2.5f);
        curve.row();
        curve.add(curveImg[2]).size(15,15).padRight(2.5f).padTop(2.5f).padBottom(10f);
        curve.add(curveImg[3]).size(15,15).padLeft(2.5f).padTop(2.5f).padBottom(10f);



//        boost.setPosition(Curvy_Ball.camera.viewportWidth/2-boostImg.getWidth()/2/PPM,Curvy_Ball.camera.viewportHeight/7);
//        boost.add(boostImg);

//        restartImg.setPosition(0,Curvy_Ball.camera.viewportHeight-restartImg.getHeight()/PPM/2);
//        restartImg.setSize(10f,10f);
//        menuImg.setPosition(Curvy_Ball.camera.viewportWidth-menuImg.getWidth()/PPM,Curvy_Ball.camera.viewportHeight-menuImg.getHeight()/PPM/2);
//        menuImg.setSize(10f,10f);
        restartImg.setSize(10f,10f);
        menuImg.setSize(10f,10f);
        musicImg.setSize(10f,10f);
        resumeImg.setSize(30f,30f);
        musicOffImg.setSize(10f,10f);
        //restartImg.scaleBy(0.01f);
        scoreLabel.setFontScale(.39f);
        highscoreLabel.setFontScale(0.39f);
        scoreImg.setSize(16,7.5f);
        highestScoreImg.setSize(32*1.238f,7.5f*1.238f);
        gameOverLabel.setFontScale(1);
        distanceTraveledLabel.setFontScale(0.5f);
        meterLabelOver.setFontScale(0.5f);
        meterWordLabel.setFontScale(0.5f);
        howToBuildLabel.setFontScale(.5f);
        howToJumpLabel.setFontScale(.5f);
        restartImg.setPosition(0,Curvy_Ball.camera.viewportHeight-restartImg.getHeight()/2-1.5f);
        menuImg.setPosition(Curvy_Ball.camera.viewportWidth-menuImg.getWidth()/2,Curvy_Ball.camera.viewportHeight-menuImg.getHeight()/2-1.5f);
        resumeImg.setPosition(Curvy_Ball.camera.viewportWidth/2-resumeImg.getWidth()/2,Curvy_Ball.camera.viewportHeight/2-resumeImg.getHeight()/2);
        musicImg.setPosition(Curvy_Ball.camera.viewportWidth-menuImg.getWidth()*1.5f,Curvy_Ball.camera.viewportHeight-menuImg.getHeight()/2-1.5f);
        musicOffImg.setPosition(Curvy_Ball.camera.viewportWidth-menuImg.getWidth()*1.5f,Curvy_Ball.camera.viewportHeight-menuImg.getHeight()/2-1.5f);
        scoreImg.setPosition(Curvy_Ball.camera.viewportWidth/2-scoreImg.getWidth()*3f,Curvy_Ball.camera.viewportHeight-scoreImg.getHeight()/1.5f);
        scoreLabel.setPosition(Curvy_Ball.camera.viewportWidth/2-scoreImg.getWidth()*3f+18f,Curvy_Ball.camera.viewportHeight- scoreImg.getHeight()/1.5f-5.55f);
        highestScoreImg.setPosition(Curvy_Ball.camera.viewportWidth/2+scoreImg.getWidth(),Curvy_Ball.camera.viewportHeight- scoreImg.getHeight()/1.5f);
        highscoreLabel.setPosition(Curvy_Ball.camera.viewportWidth/2+scoreImg.getWidth()+40f,Curvy_Ball.camera.viewportHeight- scoreImg.getHeight()/1.5f-5.55f);
        gameOverLabel.setPosition(Curvy_Ball.camera.viewportWidth/2-gameOverLabel.getWidth()/2,Curvy_Ball.camera.viewportHeight/2-gameOverLabel.getHeight()/2+50);
        distanceTraveledLabel.setPosition(Curvy_Ball.camera.viewportWidth/2-distanceTraveledLabel.getWidth()/2-meterLabelOver.getWidth(),Curvy_Ball.camera.viewportHeight/2-gameOverLabel.getHeight()/2+20);
        meterLabelOver.setPosition(Curvy_Ball.camera.viewportWidth/2-distanceTraveledLabel.getWidth()/2,Curvy_Ball.camera.viewportHeight/2-gameOverLabel.getHeight()/2+20);
        meterWordLabel.setPosition(Curvy_Ball.camera.viewportWidth/2,Curvy_Ball.camera.viewportHeight/2-gameOverLabel.getHeight()/2+20);
       // distanceImg.setPosition(50,50);

        // restartImg.setSize(10f,10f);
       // restartImg.setPosition(0,Gdx.graphics.getHeight()/PPM-restartImg.getHeight()/PPM/2);
        //restartImg.setSize(10f,10f);
      // distanceLabel.setFontScale(0.5f);
        boostImg.setPosition(Curvy_Ball.camera.viewportWidth-(Curvy_Ball.camera.viewportWidth-20)-15,Curvy_Ball.camera.viewportHeight/7-150/PPM);
        boostImg.setSize(boostImg.getWidth()/PPM*1.3f,boostImg.getHeight()/PPM*1.3f);

        howToJumpLabel.setPosition(Curvy_Ball.camera.viewportWidth-(Curvy_Ball.camera.viewportWidth-20)+10,Curvy_Ball.camera.viewportHeight/7-150/PPM);
        howToBuildLabel.setPosition(Curvy_Ball.camera.viewportWidth-(Curvy_Ball.camera.viewportWidth-20)+80,Curvy_Ball.camera.viewportHeight/7-150/PPM);

        musicOffImg.setVisible(false);
       // Gdx.app.log(Float.toString(Curvy_Ball.camera.viewportWidth),"  s");
        //Gdx.app.log("this is ","ran");
        stage.addActor(curve);
        stage.addActor(boostImg);
        stage.addActor(restartImg);
        stage.addActor(menuImg);
        stage.addActor(musicImg);
        stage.addActor(musicOffImg);
        stage.addActor(scoreImg);
        stage.addActor(scoreLabel);
        stage.addActor(highestScoreImg);
        stage.addActor(highscoreLabel);
//        stage.addActor(howToBuildLabel);
//        stage.addActor(howToJumpLabel);
    }

    public void draw(){

        stage.draw();
//        if(Gdx.input.justTouched()){
//            v.x = Gdx.input.getX();
//            v.y = Gdx.input.getY();
//            camera.unproject(v);
//            Gdx.app.log((Float.toString(v.x))," "+(Float.toString(v.y)));
//        }
    }
    public void resize(int width, int height){
        viewport.update(width,height);
        //camera.position.set(WIDTH/PPM/2,HEIGHT/PPM/2,0);
        //camera.update();
        //Gdx.app.log("this is ","ran");


    }
    public static OrthographicCamera getCamera(){
        return camera;
    }


    public int getTappedIndex() {
        return tappedIndex;
    }

    public void setTappedIndex(int tappedIndex) {
        this.tappedIndex = tappedIndex;
    }

    public boolean isNewGame() {
        return newGame;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }

    public boolean isBoostTapped() {
        return boostTapped;
    }

    public void setBoostTapped(boolean boostTapped) {
        this.boostTapped = boostTapped;
    }

    public Stage getStage() {
        return stage;
    }

    public Image getResumeImg() {
        return resumeImg;
    }
    public void dispose(){
        stage.dispose();
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public Image getRestartImg() {
        return restartImg;
    }

    public Label getGameOverLabel() {
        return gameOverLabel;
    }

    public Label getDistanceTraveledLabel() {
        return distanceTraveledLabel;
    }

    public Label getMeterLabelOver() {
        return meterLabelOver;
    }

    public Label getMeterWordLabel() {
        return meterWordLabel;
    }

    public Label getHighscoreLabel() {
        return highscoreLabel;
    }

    public BitmapFont getFont() {
        return font;
    }

    public Label getHowToJumpLabel() {
        return howToJumpLabel;
    }

    public Label getHowToBuildLabel() {
        return howToBuildLabel;
    }
}
