package com.mygdx.revelare.Utils;

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

}
