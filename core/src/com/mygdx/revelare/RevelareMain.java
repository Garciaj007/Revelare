package com.mygdx.revelare;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.revelare.Screens.GameScreen;
import com.mygdx.revelare.Screens.MainMenuScreen;
import com.mygdx.revelare.Utils.ActInfo;
import com.mygdx.revelare.Utils.Assets;
import com.mygdx.revelare.Utils.BackgroundActorInfo;
import com.mygdx.revelare.Utils.BlockActorInfo;
import com.mygdx.revelare.Utils.LevelInfo;

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
	public List<BackgroundActorInfo> backgroundActorInfoList = new ArrayList<BackgroundActorInfo>();
	public Music music;

	@Override
	public void create () {
        camera = new OrthographicCamera();
		batch = new SpriteBatch();
		font = new BitmapFont();
		random = new Random();

		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);

        Assets.load();

		while(true) {
            if (Assets.update()) {
            	populateBackgroundInfoList();

				ActInfo actInfo = new ActInfo(Assets.song1, 300, 200);

				List<BlockActorInfo> blockActorInfoListLevel1 = new ArrayList<BlockActorInfo>();
				List<BlockActorInfo> blockActorInfoListLevel2 = new ArrayList<BlockActorInfo>();
				List<BlockActorInfo> blockActorInfoListLevel3 = new ArrayList<BlockActorInfo>();
				List<BlockActorInfo> blockActorInfoListLevel4 = new ArrayList<BlockActorInfo>();
				List<BlockActorInfo> blockActorInfoListLevel5 = new ArrayList<BlockActorInfo>();
				List<BlockActorInfo> blockActorInfoListLevel6 = new ArrayList<BlockActorInfo>();

				for(int i = 0; i < 15; i++) {
					blockActorInfoListLevel1.add(new BlockActorInfo(random.nextInt(5), 0, 0, 0, random.nextInt(200)+100,  random.nextInt(100)+50, random.nextFloat() + 1f, false, Color.WHITE));
					blockActorInfoListLevel2.add(new BlockActorInfo(random.nextInt(5), 0, 0, 25, random.nextInt(200)+100,  random.nextInt(100)+50, random.nextFloat() + 0.5f, false, Color.WHITE));
					blockActorInfoListLevel3.add(new BlockActorInfo(random.nextInt(5), 0, 50, 50, random.nextInt(250)+100,  random.nextInt(100)+50, random.nextFloat() + 1f, false, Color.WHITE));
					blockActorInfoListLevel4.add(new BlockActorInfo(random.nextInt(5), 0, 50, 75, random.nextInt(250)+100,  random.nextInt(100)+50, random.nextFloat() + 0.5f, false, Color.WHITE));
					blockActorInfoListLevel5.add(new BlockActorInfo(random.nextInt(5), 0, 50, 100, random.nextInt(300)+150,  random.nextInt(200)+50, random.nextFloat() + 1f, false, Color.WHITE));
					blockActorInfoListLevel6.add(new BlockActorInfo(random.nextInt(5), 0, 50, 150, random.nextInt(300)+150,  random.nextInt(200)+50, random.nextFloat() + 0.5f, false, Color.WHITE));
				}

				actInfo.addLevel(new LevelInfo(blockActorInfoListLevel1, actInfo));
				actInfo.addLevel(new LevelInfo(blockActorInfoListLevel2, actInfo));
				actInfo.addLevel(new LevelInfo(blockActorInfoListLevel3, actInfo));
				actInfo.addLevel(new LevelInfo(blockActorInfoListLevel4, actInfo));
				actInfo.addLevel(new LevelInfo(blockActorInfoListLevel5, actInfo));
				actInfo.addLevel(new LevelInfo(blockActorInfoListLevel6, actInfo));

				for(int i = 4; i < actInfo.levelInfoList.size(); i++) {
					actInfo.levelInfoList.get(i).addColor(Color.CHARTREUSE);
					actInfo.levelInfoList.get(i).addColor(Color.BLUE);
					actInfo.levelInfoList.get(i).addColor(Color.MAROON);
					actInfo.levelInfoList.get(i).addColor(Color.ORANGE);
				}

            	//Set New Screen
                setScreen(new GameScreen(this, actInfo, 1));
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

	//Custom Methods
	private void populateBackgroundInfoList(){
		//Populate BackgroundActorInfoList of all possible BackgroundActorInfo's
		backgroundActorInfoList.add(new BackgroundActorInfo(3,3,0,Assets.get(Assets.popSheet, Texture.class), 0.025f));
		backgroundActorInfoList.add(new BackgroundActorInfo(3,3,1, Assets.get(Assets.tranceSheet, Texture.class), 0.025f));
		backgroundActorInfoList.add(new BackgroundActorInfo(4,4,2,Assets.get(Assets.zigzagSheet, Texture.class), 0.025f));
	}
}
