package com.pickoky.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pickoky.game.states.GameStateManager;
import com.pickoky.game.states.MenuState;
import com.pickoky.game.states.PlayState;

public class Curvy_Ball extends ApplicationAdapter {
	public static SpriteBatch batch;
	public static  float  HEIGHT = 1080;
	public static  float WIDTH = 1920;
	public static final int PPM = 10;

	public TextureAtlas ballrathonSpriteSheet;
	public static boolean initPlayState = false;
	public static Preferences preferences;
	public static int highscore;
	public static boolean firstGame;

	public AssetManagerClass assetManagerClass;



	public enum GameState{
		PAUSE,
		RUN,
		RESUME,
		GAME_OVER
	}
	public static GameState gameState;
	Texture img;

	private GameStateManager gsm;
	public static Viewport viewport;
	public static OrthographicCamera camera;
	private Controller controller;
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManagerClass = new AssetManagerClass();
		gsm = new GameStateManager(assetManagerClass);
		gameState = GameState.RUN;

		Gdx.gl.glClearColor(1, 1, 1, 1);
		gsm.push(new MenuState(gsm,assetManagerClass));
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(WIDTH/PPM,HEIGHT/PPM,camera);
		//camera.position.set(WIDTH/2/PPM,HEIGHT/2/PPM,0);
		//ballrathonSpriteSheet = new TextureAtlas("BallrathonSpriteSheet.txt");
		preferences = Gdx.app.getPreferences("My Highscore");
		if(!preferences.contains("firstgame")){
			preferences.putBoolean("firstgame",true);
			preferences.flush();
		}
		firstGame = preferences.getBoolean("firstgame");
		if(!preferences.contains("highscore")){
			preferences.putInteger("highscore",0);
			preferences.flush();
		}
		highscore = preferences.getInteger("highscore");
	}
	@Override
	public void resize(int width, int height){

		if(gsm.state() instanceof PlayState){
			PlayState.controller.resize(width,height);
		}
		viewport.update(width,height);
		camera.position.set(WIDTH/2/PPM,HEIGHT/2/PPM,0);
		camera.update();
//		if(isPlayState){
////			PlayState.controller.resize(width,height);
////			PlayState.controller.getCamera().update();
//		}
	}
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//camera.position.set(WIDTH/2/PPM,HEIGHT/2/PPM,0);
		batch.setProjectionMatrix(camera.combined);
		switch(gameState){
			case RUN:
				gsm.update(Gdx.graphics.getDeltaTime());
				gsm.render(batch);
				break;
			case PAUSE:
				//
				gsm.render(batch);

				break;
			case RESUME:
				gsm.update(Gdx.graphics.getDeltaTime());
				gsm.render(batch);
				break;
			case GAME_OVER:
				gsm.render(batch);
			default:
				break;
		}
		//gsm.update(Gdx.graphics.getDeltaTime());
		//gsm.render(batch);
//		batch.begin();
//		batch.draw(resumeButton,10,10);
//		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetManagerClass.assetManager.dispose();
		gsm.assetManagerClass.assetManager.dispose();
	}
}
