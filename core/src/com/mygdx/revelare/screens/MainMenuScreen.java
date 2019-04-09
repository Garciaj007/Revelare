package com.mygdx.revelare.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.revelare.RevelareMain;

public class MainMenuScreen implements Screen {

    private final RevelareMain game;
    private Stage stage;
    private Skin skin;

    public MainMenuScreen(final RevelareMain game){
        this.game = game;
        this.stage = new Stage(new StretchViewport(RevelareMain.V_WIDTH, RevelareMain.V_HEIGHT, game.camera));

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(.2f, .2f, .2f, 1f);
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 20; j++){
                game.shapeRenderer.rect(i * 80 - 20, j * 80 - 20, 50, 50);
            }
        }
        game.shapeRenderer.setColor(1,1,1,1);
        game.shapeRenderer.circle(RevelareMain.V_WIDTH / 2, RevelareMain.V_HEIGHT / 8, 20);
        game.shapeRenderer.end();

        Gdx.gl.glLineWidth(4);

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.circle(RevelareMain.V_WIDTH / 2, RevelareMain.V_HEIGHT / 8, 120);
        game.shapeRenderer.end();

    }

    public void update(float delta){
        stage.act(delta);

        //if(game.assets.update()){
        //    game.setScreen(new GameScreen(game));
        //}
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
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void queueAssets(){

    }
}
