package com.mygdx.revelare.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.revelare.Actors.BackgroundActor;
import com.mygdx.revelare.Actors.BlockActor;
import com.mygdx.revelare.Actors.PlayerActor;
import com.mygdx.revelare.RevelareMain;
import com.mygdx.revelare.Utils.Assets;
import com.mygdx.revelare.Utils.BackgroundActorInfo;
import com.mygdx.revelare.Utils.LevelInfo;

import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.ir.Block;

public class GameScreen implements Screen {

    private final RevelareMain game;
    private List<BackgroundActorInfo> backgroundActorInfos;
    private List<LevelInfo> levelInfos;
    private List<BlockActor> blocks;
    private PlayerActor playerActor;
    private BackgroundActor backgroundActor;
    private Stage stage;
    private boolean state;
    private float blockBaseVelocity, playerBaseVelocity;
    private int level;

    public GameScreen(final RevelareMain game, List<LevelInfo> levelInfos, int startingLevel, Music music, float blockBaseVelocity, float playerBaseVelocity){
        this.game = game;
        this.levelInfos = levelInfos;
        this.blockBaseVelocity = blockBaseVelocity;
        this.playerBaseVelocity = playerBaseVelocity;
        level = startingLevel;

        stage = new Stage(new ExtendViewport(RevelareMain.V_WIDTH, RevelareMain.V_HEIGHT, game.camera));
        Gdx.input.setInputProcessor(stage);

        backgroundActor = new BackgroundActor(backgroundActorInfos.get(level));
        playerActor = new PlayerActor(new Vector2(stage.getWidth() / 2, 200), 180, stage);

        stage.setDebugAll(true);
        stage.addActor(backgroundActor);
        stage.addActor(playerActor);

        if(this.game.music.isPlaying()){
            this.game.music.stop();
        }

        this.game.music = music;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        update(delta);
    }

    private void update(float delta){

    }

    @Override
    public void show() {


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
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

}
