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

import java.util.List;

public class BlockActor extends Actor {

    //Block Actor's Members
    private BlockActorInfo info;
    private TextureRegion textureRegion;
    private Polygon polygon;
    private float x, y, scale, rotation, stageHeight, speed;
    private boolean active = false, animating = false;
    private final float SCALE_AMOUNT = 0.10f, ANIMATION_DURATION = 10.0f;

    //Getters
    public Polygon getCollisionPolygon(){
        return polygon;
    }
    public boolean isActive() { return active; }
    public float getY() { return y; }
    public float getHeight(){ return info.spawnHeight * scale; }
    public Color getColor() { return info.spawnColor; }
    public void setVelocity(float velocity) { speed = info.spawnSpeed + velocity; }

    //Constructor
    public BlockActor(BlockActorInfo info, Stage stage){
        this.info = info;

        textureRegion = new TextureRegion(Assets.get(Assets.rectangle, Texture.class));

        rotation = info.spawnRotation;
        speed = info.spawnSpeed;
        scale = 1;

        //Setting Blocks Initial Position
        stageHeight = stage.getHeight();
        y = stageHeight;

        switch(info.spawnPosition){
            case 1:
                x = info.spawnWidth / 2;
                break;
            case 2:
                x = stage.getWidth() / 4 + info.spawnWidth / 2;
                break;
            case 3:
                x = stage.getWidth() / 2 + info.spawnWidth / 2;
                break;
            case 4:
                x = stage.getWidth() / 2 + stage.getWidth() / 4 - info.spawnWidth/2;
                break;
            case 5:
                x = stage.getWidth() - info.spawnWidth / 2;
                break;
            default:
                x = info.spawnWidth/2;
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
            y -= speed * delta;
            rotation += info.spawnRotationSpeed * delta;

            if(animating){
                scale += SCALE_AMOUNT / ANIMATION_DURATION;
                if(scale >= 1.3f) {
                    scale = 1.3f;
                    animating = false;
                }
            } else {
                scale -= SCALE_AMOUNT / ANIMATION_DURATION;
                if(scale <= 1f)
                    scale = 1f;
            }

            //Updating Collision Polygon
            polygon.setPosition(x, y);
            polygon.setRotation(rotation);
            polygon.setScale(scale, scale);
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
        animating = false;

        //Setting Blocks Initial Position, Rotation & Scale
        y = stageHeight;
        rotation = info.spawnRotation;
        scale = 1;

        //Updating Collision Polygon
        polygon.setPosition(x, y);
        polygon.setRotation(rotation);
        polygon.setScale(scale, scale);
    }

    public void animate(){
        animating = true;
    }
}
