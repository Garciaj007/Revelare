package com.mygdx.revelare.Utils;

import com.badlogic.gdx.graphics.Color;

//Block Actor Info Class
public class BlockActorInfo{
    public int spawnPosition;
    public float spawnRotation;
    public float spawnRotationSpeed;
    public float spawnSpeed;
    public int spawnWidth;
    public int spawnHeight;
    public boolean invisible;
    public Color spawnColor;

    public BlockActorInfo(int spawnPosition, float spawnRotation, float spawnRotationSpeed, float spawnSpeed, int spawnWidth, int spawnHeight, boolean invisible, Color spawnColor) {
        this.spawnPosition = spawnPosition;
        this.spawnRotation = spawnRotation;
        this.spawnRotationSpeed = spawnRotationSpeed;
        this.spawnSpeed = spawnSpeed;
        this.spawnWidth = spawnWidth;
        this.spawnHeight = spawnHeight;
        this.invisible = invisible;
        this.spawnColor = spawnColor;
    }
}
