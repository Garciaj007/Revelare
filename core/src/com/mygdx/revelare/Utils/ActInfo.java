package com.mygdx.revelare.Utils;

import java.util.ArrayList;
import java.util.List;

public class ActInfo {

    public List<LevelInfo> levelInfoList;
    public MusicInfo musicInfo;
    public float baseBlockVelocity;
    public float basePlayerVelocity;

    public ActInfo(List<LevelInfo> levelInfoList, MusicInfo musicInfo, float baseBlockVelocity, float basePlayerVelocity){
        this.levelInfoList = levelInfoList;
        this.musicInfo = musicInfo;
        this.baseBlockVelocity = baseBlockVelocity;
        this.basePlayerVelocity = basePlayerVelocity;
    }

    public ActInfo(MusicInfo musicInfo, float baseBlockVelocity, float basePlayerVelocity){
        this.levelInfoList = new ArrayList<LevelInfo>();
        this.musicInfo = musicInfo;
        this.baseBlockVelocity = baseBlockVelocity;
        this.basePlayerVelocity = basePlayerVelocity;
    }

    public void addLevel(LevelInfo levelInfo){
        levelInfoList.add(levelInfo);
    }
}
