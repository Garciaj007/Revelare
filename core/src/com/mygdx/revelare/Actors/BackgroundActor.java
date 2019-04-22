package com.mygdx.revelare.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.revelare.Utils.BackgroundActorInfo;

import javax.xml.soap.Text;

public class BackgroundActor extends Actor {

    //AnimationState Enum
    public enum AnimationState {forward1, reverse1,forward2, reverse2, None};

    //Box Actor Members
    private AnimationState animationState = AnimationState.None;
    private Animation<TextureRegion> boxAnimation;
    private float stateTime = 0f;
    private float scale = 2, rotation = 0;
    private final float MARGIN = 120, SIZE = 96;
    private final Vector2 POSITION_OFFSET = new Vector2(14, -4 );

    //Getters & Setters
    public void setBoxAnimation(BackgroundActorInfo backgroundActorInfo){
        boxAnimation = setBoxAnimation(backgroundActorInfo.frameCols,
                backgroundActorInfo.frameRows, backgroundActorInfo.offset,
                backgroundActorInfo.sheet, backgroundActorInfo.animationSpeed);
    }

    //Constructor
    public BackgroundActor(BackgroundActorInfo backgroundActorInfo){
        setBoxAnimation(backgroundActorInfo);
    }

    private void resetAnimation(){
        stateTime = 0f; scale = 2; rotation = 0;
    }

    private Animation<TextureRegion> setBoxAnimation(int frameCols, int frameRows, int offset, Texture sheet, float animationsSpeed){
        TextureRegion[][] temp = TextureRegion.split(sheet,
                sheet.getWidth() / frameCols,
                sheet.getHeight() / frameRows);

        TextureRegion[] animation = new TextureRegion[frameCols*frameRows - offset];
        int index = 0;
        for(int i = 0; i < frameRows; i++){
            for(int j = 0; j < frameCols; j++){
                if(index < animation.length)
                    animation [index++] = temp[i][j];
            }
        }

        return new Animation<TextureRegion>(animationsSpeed, animation);
    }

    //Updating Actor
    @Override
    public void act(float delta) {
        //Comparing Animation States
        switch(animationState){
            case forward1:
                stateTime += Gdx.graphics.getDeltaTime();
                break;
            case reverse1:
                break;
            case forward2:
                scale -= 0.5f / boxAnimation.getKeyFrames().length;
                rotation += 90f / boxAnimation.getKeyFrames().length;

                if(scale <= 0.5f)
                    scale = 0.5f;

                if(rotation >= 90)
                    rotation = 90f;

                if(scale <= 0.5 && rotation >= 90)
                    reset2();
                break;
            case reverse2:
                scale += 0.5f / boxAnimation.getKeyFrames().length;
                if(scale >= 2)
                    scale = 2;

                rotation -= 90f / boxAnimation.getKeyFrames().length;

                if(rotation <= 0)
                    rotation = 0;
                break;
        }

        //Resetting Animation State
        if(boxAnimation.isAnimationFinished(stateTime)){
            reset1();
        }
    }

    //Dra
    @Override
    public void draw(Batch batch, float parentAlpha){
        Color c = getColor();

        TextureRegion currentFrame = boxAnimation.getKeyFrame(stateTime, true);

        c.a = 0.2f;

        batch.setColor(c.r,c.g,c.b,c.a * parentAlpha);

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 12; j++){
                batch.draw(currentFrame, MARGIN * i - POSITION_OFFSET.x, MARGIN * j + POSITION_OFFSET.y, SIZE / 2, SIZE / 2, SIZE, SIZE, scale, scale, rotation);
            }
        }

    }

    public void playAnim1(){
        animationState = AnimationState.forward1;
    }

    public void playAnim2(){
        animationState = AnimationState.forward2;
    }

    public void reset1(){
        stateTime = 0;
        animationState = AnimationState.reverse1;
    }

    public void reset2(){
        animationState = AnimationState.reverse2;
    }

}
