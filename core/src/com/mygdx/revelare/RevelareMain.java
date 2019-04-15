package com.mygdx.revelare;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.revelare.Utils.BeatSequencer;
import com.mygdx.revelare.screens.MainMenuScreen;

public class RevelareMain extends Game {

    /** Static Members */
    public static final String TITLE = "Revelare";
    public static final float VERSION = 0.1f;
    public static final int V_WIDTH = 720;
    public static final int V_HEIGHT = 1280;

    /** Members */
    public SpriteBatch batch;
	public OrthographicCamera camera;
	public AssetManager assets;
	public BitmapFont font;
	public ShapeRenderer shapeRenderer;
	public ShapeRenderer shapeRendererBG;
	public ShapeRenderer shapeRendererFG;

	@Override
	public void create () {
	    assets = new AssetManager();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		batch = new SpriteBatch();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();
		shapeRendererFG = new ShapeRenderer();
		shapeRendererBG = new ShapeRenderer();

		//queueAssets();

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		assets.dispose();
		shapeRenderer.dispose();
		shapeRendererBG.dispose();
		shapeRendererFG.dispose();
		this.getScreen().dispose();
	}

	public void queueAssets(){
		assets.get("circle.png", Texture.class);
		assets.get("circleborder.png", Texture.class);
	}
}
