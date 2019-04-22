package com.mygdx.revelare;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.revelare.Actors.BackgroundActor;
import com.mygdx.revelare.Screens.MainMenuScreen;
import com.mygdx.revelare.Utils.Assets;
import com.mygdx.revelare.Utils.BackgroundActorInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RevelareMain extends Game {

    /** Static Members */
    public static final String TITLE = "Revelare";
    public static final float VERSION = 0.1f;
    public static final int V_WIDTH = 720;
    public static final int V_HEIGHT = 1280;

    /** Members */
    public SpriteBatch batch;
	public OrthographicCamera camera;
	public BitmapFont font;
	public Random random;
	public List<BackgroundActorInfo> backgroundActorInfos = new ArrayList<BackgroundActorInfo>();
	public Music music;

	@Override
	public void create () {
        camera = new OrthographicCamera();
		batch = new SpriteBatch();
		font = new BitmapFont();
		random = new Random();

		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);

		music.setLooping(true);

        Assets.load();

		while(true) {
            if (Assets.update()) {
                setScreen(new MainMenuScreen(this));
                break;
            } else {
                System.out.println("Waiting on Assets: " + Assets.progress() + "%");
            }
        }
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		Assets.dispose();
		getScreen().dispose();
	}
}
