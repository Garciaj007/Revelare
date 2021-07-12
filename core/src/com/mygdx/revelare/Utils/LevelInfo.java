package com.mygdx.revelare.Utils;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelInfo {
    public List<BlockActorInfo> blockActorInfoList;
    public List<Color> possibleColors;
    public ActInfo parentActInfo;

    public LevelInfo(List<BlockActorInfo> blockActorInfoList, ActInfo parentActInfo, List<Color> possibleColors) {
        this.blockActorInfoList = blockActorInfoList;
        this.possibleColors = possibleColors;
        this.parentActInfo = parentActInfo;
    }

    public LevelInfo(List<BlockActorInfo> blockActorInfoList, ActInfo parentActInfo) {
        this.blockActorInfoList = blockActorInfoList;
        this.parentActInfo = parentActInfo;
        possibleColors = new ArrayList<Color>();
    }

    public LevelInfo(ActInfo parentActInfo, List<Color> possibleColors) {
        this.blockActorInfoList = new ArrayList<BlockActorInfo>();
        this.possibleColors = possibleColors;
        this.parentActInfo = parentActInfo;
    }

    public LevelInfo(ActInfo parentActInfo) {
        this.blockActorInfoList = new ArrayList<BlockActorInfo>();
        this.parentActInfo = parentActInfo;
        possibleColors = new ArrayList<Color>();
    }

    public void addColor(Color c) {
        possibleColors.add(c);
    }

    public void addBlock(BlockActorInfo blockInfo) {
        blockActorInfoList.add(blockInfo);
    }

    public Color getColor() {
        if (!possibleColors.isEmpty()) {
            Random rand = new Random();
            return possibleColors.get(rand.nextInt(possibleColors.size()));
        } else
            return Color.WHITE;
    }
}
