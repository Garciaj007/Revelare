package com.mygdx.revelare.Utils;

import com.badlogic.gdx.graphics.Texture;

public class BackgroundActorInfo {
    public int frameCols;
    public int frameRows;
    public int offset;
    public Texture sheet;
    public float animationSpeed;

    public BackgroundActorInfo(int frameCols, int frameRows, int offset, Texture sheet, float animationSpeed){
        this.frameCols = frameCols;
        this.frameRows = frameRows;
        this.offset = offset;
        this.sheet = sheet;
        this.animationSpeed = animationSpeed;
    }
}
