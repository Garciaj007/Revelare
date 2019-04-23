package com.mygdx.revelare.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.revelare.Actors.BackgroundActor;
import com.mygdx.revelare.Actors.BlockActor;
import com.mygdx.revelare.Actors.PlayerActor;
import com.mygdx.revelare.RevelareMain;
import com.mygdx.revelare.Utils.Assets;
import com.mygdx.revelare.Utils.BeatSequencer;
import com.mygdx.revelare.Utils.BlockActorInfo;

public class MainMenuScreen implements Screen {

    private final RevelareMain game;
    private Stage stage;
    private boolean state;
    private BackgroundActor backgroundActor;
    private PlayerActor playerActor;

    public MainMenuScreen(final RevelareMain game){
        this.game = game;

        stage = new Stage(new ExtendViewport(RevelareMain.V_WIDTH, RevelareMain.V_HEIGHT, game.camera));
        Gdx.input.setInputProcessor(stage);

        backgroundActor = new BackgroundActor(game.backgroundActorInfoList.get(this.game.random.nextInt(game.backgroundActorInfoList.size())));
        playerActor = new PlayerActor(new Vector2(stage.getWidth() / 2, 200), 180, 150, stage, false);

        stage.addActor(backgroundActor);
        stage.addActor(playerActor);

        this.game.music = Assets.get(Assets.song1.path, Music.class);
        this.game.music.setLooping(true);
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
        BeatSequencer.BeatAnalyser.bpm = Assets.song1.bpm;
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
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
