package com.mygdx.revelare.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class Assets {


    /** Wrapper Class for AssetManager */

    //AssetManager
    public static final AssetManager assetManager = new AssetManager();

    //Assets
    public static final String rectangle = "data/rect.png";
    public static final String circle = "data/circle.png";
    public static final String circleBorder = "data/circleborder.png";
    public static final String popSheet = "data/pop_sheet.png";
    public static final String tranceSheet = "data/trance_sheet.png";
    public static final String zigzagSheet = "data/zigzag_sheet.png";
    public static final String circleSheet = "data/circleanim_sheet.png";
    public static final MusicInfo song1 = new MusicInfo("data/awake.mp3", 124);

    //Wrapper Methods

    public static void load(){
        song1.load(assetManager);
        assetManager.load(rectangle, Texture.class);
        assetManager.load(circle, Texture.class);
        assetManager.load(circleBorder, Texture.class);
        assetManager.load(circleSheet, Texture.class);
        assetManager.load(popSheet, Texture.class);
        assetManager.load(tranceSheet, Texture.class);
        assetManager.load(zigzagSheet, Texture.class);
    }

    public static void dispose(){
        assetManager.dispose();
    }

    public static <T> T get(String path, Class<T> classType){
        return assetManager.get(path, classType);
    }

    public static boolean update(){
        return assetManager.update();
    }

    public static float progress(){
        return assetManager.getProgress();
    }

    public static boolean isFinished() { return assetManager.isFinished(); }
}
