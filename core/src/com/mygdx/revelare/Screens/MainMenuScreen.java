package com.mygdx.revelare.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.revelare.Actors.BackgroundActor;
import com.mygdx.revelare.Actors.BlockActor;
import com.mygdx.revelare.Actors.PlayerActor;
import com.mygdx.revelare.RevelareMain;
import com.mygdx.revelare.Utils.Assets;
import com.mygdx.revelare.Utils.BackgroundActorInfo;
import com.mygdx.revelare.Utils.BeatSequencer;
import com.mygdx.revelare.Utils.BlockActorInfo;

public class MainMenuScreen implements Screen {

    private final RevelareMain game;
    private Stage stage;
    private boolean state;
    private BackgroundActor backgroundActor;
    private PlayerActor playerActor;

    private BlockActor block;

    public MainMenuScreen(final RevelareMain game){
        this.game = game;

        stage = new Stage(new ExtendViewport(RevelareMain.V_WIDTH, RevelareMain.V_HEIGHT, game.camera));
        Gdx.input.setInputProcessor(stage);

        backgroundActor = new BackgroundActor(game.backgroundActorInfos.get(this.game.random.nextInt(game.backgroundActorInfos.size())));
        block = new BlockActor(new BlockActorInfo(3, 0,100,50,200, 30, false, Color.WHITE), stage);
        playerActor = new PlayerActor(new Vector2(stage.getWidth() / 2, 200), 180, stage);

        stage.setDebugAll(true);
        stage.addActor(backgroundActor);
        stage.addActor(block);
        stage.addActor(playerActor);

        this.game.music = Assets.get(Assets.musicScore1, Music.class);
    }

    @Override
    public void render(float delta) {
        //Clear All Contents
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    /** Update All Actors & Behaviours */
    private void update(float delta){
        stage.act(delta);

        BeatSequencer.BeatAnalyser.update(delta);

        if(BeatSequencer.BeatAnalyser.beatHalf){
            if(state){
                backgroundActor.playAnim1();
            } else {
                backgroundActor.playAnim2();
            }
            state = !state;
        }
    }

    @Override
    public void show() {
        game.music.play();
        BeatSequencer.BeatAnalyser.bpm = 124;
        BeatSequencer.BeatAnalyser.Start();
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
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
