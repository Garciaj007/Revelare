package com.mygdx.revelare.Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.revelare.Utils.Assets;
import com.mygdx.revelare.Utils.BlockActorInfo;

public class BlockActor extends Actor {

    //Block Actor's Members
    private BlockActorInfo info;
    private TextureRegion textureRegion;
    private Polygon polygon;
    private float x, y, scale, rotation, stageHeight, spawnInterval;
    private boolean active = false;

    //Getters
    public Polygon getCollisionPolygon(){
        return polygon;
    }
    public boolean isActive() { return active; }
    public float getY() { return y; }

    //Constructor
    public BlockActor(BlockActorInfo info, Stage stage){
        this.info = info;

        textureRegion = new TextureRegion(Assets.get(Assets.rectangle, Texture.class));

        rotation = info.spawnRotation;
        scale = 1;

        //Setting Blocks Initial Position
        stageHeight = stage.getHeight();
        y = stageHeight;

        switch(info.spawnPosition){
            case 1:
                x = 0;
                break;
            case 2:
                x = stage.getWidth() / 4;
                break;
            case 3:
                x = stage.getWidth() / 2;
                break;
            case 4:
                x = stage.getWidth() / 2 + stage.getWidth() / 4;
                break;
            case 5:
                x = stage.getWidth();
                break;
            default:
                x = 0;
        }

        //Setting up Polygon for Collision
        polygon = new Polygon(new float[]{0,0, info.spawnWidth, 0, info.spawnWidth, info.spawnHeight, info.spawnHeight, 0});
        polygon.setOrigin(info.spawnWidth/2, info.spawnHeight/2);
        polygon.setRotation(info.spawnRotation);
        polygon.setPosition(x, y);
    }

    //Updating Actor
    @Override
    public void act(float delta){
        if(active) {
            //Updating Simulated Physics
            y -= info.spawnSpeed * delta;
            rotation += info.spawnRotationSpeed * delta;

            //Updating Collision Polygon
            polygon.setPosition(x, y);
            polygon.setRotation(rotation);
        }
    }

    //Drawing Actor
    @Override
    public void draw(Batch batch, float parentAlpha){
        Color color = info.spawnColor;
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(textureRegion, x, y, info.spawnWidth / 2, info.spawnHeight / 2, info.spawnWidth, info.spawnHeight, scale, scale, rotation);
    }

    //Drawing Debug Info on Actor
    @Override
    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.polygon(polygon.getTransformedVertices());
    }

    public void start(){
        active = true;
    }

    public void reset(){
        active = false;

        //Setting Blocks Initial Position, Rotation & Scale
        y = stageHeight;
        rotation = info.spawnRotation;
        scale = 1;

        //Updating Collision Polygon
        polygon.setPosition(x, y);
        polygon.setRotation(rotation);
    }
}
