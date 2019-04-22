package com.mygdx.revelare.Utils;

import com.badlogic.gdx.audio.Music;
import com.mygdx.revelare.Actors.BackgroundActor;

import java.util.ArrayList;
import java.util.List;

public class LevelInfo {
    public List<BlockActorInfo> blockActorInfos = new ArrayList<BlockActorInfo>();
    public List<Float> spawnIntervals = new ArrayList<Float>();

    public LevelInfo(List<BlockActorInfo> blockActorInfos, List<Float> spawnIntervals,
                     BackgroundActorInfo backgroundActorInfo, float blockBaseVelocity,
                     float playerBaseVelocity, Music music){
        this.blockActorInfos = blockActorInfos;
        this.spawnIntervals = spawnIntervals;
    }
}
