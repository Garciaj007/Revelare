package com.mygdx.revelare.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.revelare.Actors.BackgroundActor;
import com.mygdx.revelare.Actors.BlockActor;
import com.mygdx.revelare.Actors.PlayerActor;
import com.mygdx.revelare.RevelareMain;
import com.mygdx.revelare.Utils.ActInfo;
import com.mygdx.revelare.Utils.Assets;
import com.mygdx.revelare.Utils.BeatSequencer;
import com.mygdx.revelare.Utils.BlockActorInfo;
import com.mygdx.revelare.Utils.LevelInfo;
import com.mygdx.revelare.Utils.PlayerCollision;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    //GameScreen Members
    private final RevelareMain game;
    private ActInfo actInfo;
    private List<BlockActor> blockList;
    private PlayerActor playerActor;
    private BackgroundActor backgroundActor;
    private Stage stage;
    private boolean state;//levelDoneSpawning;
    private int level; //Level is not 0 index Based
    private LevelInfo levelInfo;
    private float currentSpawnInterval, currentTime;

    //Constructor
    public GameScreen(final RevelareMain game, ActInfo actInfo, int startingLevel) {
        this.game = game;
        this.actInfo = actInfo;
        this.level = startingLevel;

        //Creating new Stage and Attaching Input
        stage = new Stage(new ExtendViewport(RevelareMain.V_WIDTH, RevelareMain.V_HEIGHT, game.camera));
        Gdx.input.setInputProcessor(stage);

        //Creating Background & Player Actor
        backgroundActor = new BackgroundActor(game.backgroundActorInfoList.get(this.game.random.nextInt(game.backgroundActorInfoList.size())));
        playerActor = new PlayerActor(new Vector2(stage.getWidth() / 2, 200), 180, this.actInfo.basePlayerVelocity, stage, true);

        //Display Debug Information
        stage.setDebugAll(true);

        //Adding Actors to Stage
        stage.addActor(backgroundActor);
        stage.addActor(playerActor);

        //Checking if music is playing
        if (this.game.music != null && this.game.music.isPlaying()) {
            this.game.music.stop();
        }
        this.game.music = Assets.get(this.actInfo.musicInfo.path, Music.class);
        this.game.music.setLooping(true);

        //New List of Active Blocks
        blockList = new ArrayList<BlockActor>();

        //
        startLevel();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    private void update(float delta) {
        stage.act(delta);

        currentTime += delta;

        if (currentTime > currentSpawnInterval) {
            for (int i = 0; i < blockList.size(); i++) {
                if (!blockList.get(i).isActive()) {
                    System.out.println("Block " + (i + 1) + " Spawned");
                    blockList.get(i).start();
                    if (i + 1 < blockList.size()) {
                        currentSpawnInterval = levelInfo.blockActorInfoList.get(i + 1).spawnInterval;
                        currentTime = 0;
                    }
                    break;
                }
            }
        }

        if (!blockList.isEmpty()) {
            BlockActor lastBlock = blockList.get(blockList.size() - 1);
            if (lastBlock.isActive()) {
                if (lastBlock.getY() + lastBlock.getHeight() < 0)
                    onLevelCompleted();
            }
        }

        for (BlockActor b : blockList) {
            if (PlayerCollision.overlaps(b.getCollisionPolygon(), playerActor.getCollisionCircle())) {
                System.out.println("Block Color " + b.getColor() + " | Player Color " + playerActor.getColor());
                if (b.getColor() != playerActor.getColor())
                    resetLevel();
                else
                    break;
            }
        }

        BeatSequencer.BeatAnalyser.update(delta);

        if (BeatSequencer.BeatAnalyser.beatHalf) {
            if (state) {
                backgroundActor.playAnim1();
            } else {
                backgroundActor.playAnim2();
            }
            state = !state;
        }

        if (BeatSequencer.BeatAnalyser.beatFull) {
            for (BlockActor b : blockList) {
                if (b.isActive())
                    b.animate();
                else
                    break;
            }
        }
    }

    @Override
    public void show() {
        game.music.play();
        BeatSequencer.BeatAnalyser.bpm = actInfo.musicInfo.bpm;
        BeatSequencer.BeatAnalyser.start();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        BeatSequencer.BeatAnalyser.stop();
        BeatSequencer.BeatAnalyser.reset();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    private void startLevel() {
        if (backgroundActor != null) {
            backgroundActor.remove();
            playerActor.remove();
        }

        backgroundActor = new BackgroundActor(game.backgroundActorInfoList.get(this.game.random.nextInt(game.backgroundActorInfoList.size())));
        stage.addActor(backgroundActor);
        stage.addActor(playerActor);

        currentTime = 0;
        //Spawn Blocks and Begin GamePlay
        levelInfo = actInfo.levelInfoList.get(level - 1);

        playerActor.setPossibleColors(levelInfo.possibleColors);
        playerActor.setPlayerVelocity(actInfo.basePlayerVelocity);

        for (BlockActorInfo blockInfo : levelInfo.blockActorInfoList) {
            blockInfo.SetRandSpawnColor(levelInfo);
            BlockActor b = new BlockActor(blockInfo, stage);
            b.setVelocity(actInfo.baseBlockVelocity);
            stage.addActor(b);
            blockList.add(b);
        }
        currentSpawnInterval = levelInfo.blockActorInfoList.get(0).spawnInterval;

        //levelDoneSpawning = false;
    }

    private void resetLevel() {
        //When Player Dies, Clear Blocks List and
        for (BlockActor b : blockList) {
            b.reset();
        }

        currentSpawnInterval = levelInfo.blockActorInfoList.get(0).spawnInterval;
        currentTime = 0;
    }

    private void onLevelCompleted() {
        System.out.println("Level " + level + " Completed");
        //When a Level is completed increase Level Counter and check if Act is completed
        ++level;

        System.out.println("Level " + level);

        //Remove Current Blocks
        for (BlockActor b : blockList) {
            b.remove();
        }

        blockList.clear();

        if (level > actInfo.levelInfoList.size()) {
            System.out.println("Act Completed");
            onCompleted();
        } else {
            System.out.println("Starting New Level");
            startLevel();
        }
    }

    private void onCompleted() {
        //Switch Screen into a new Act

        //In This case since there is no new Act switch to Menu Screen
        game.setScreen(new MainMenuScreen(game));
    }

}
