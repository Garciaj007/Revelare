package com.mygdx.revelare.Utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

public class MusicInfo{
    public String path;
    public float bpm;

    public MusicInfo(String path, float bpm){
        this.path = path;
        this.bpm = bpm;
    }

    public void load(final AssetManager assetManager){
        assetManager.load(path, Music.class);
    }
}

