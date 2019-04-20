package com.mygdx.revelare.Utils;

import com.mygdx.revelare.RevelareMain;

public class BeatSequencer {

    public static class BeatAnalyser{
        //Class Members
        public static float bpm;
        public static boolean beatFull, beatHalf, beatD8;

        private static float beatInterval, beatTimer, beatIntervalHalf, beatTimerHalf, beatIntervalD8, beatTimerD8;
        private static int beatCountFull, beatCountD8, beatCountHalf;
        private static boolean active = false;

        public static void update(float delta){

            //Full Beat Count
            beatFull = false;
            beatInterval = 60 / bpm;
            beatTimer += delta;

            if(beatTimer >= beatInterval && active){
                beatTimer -= beatInterval;
                beatFull = true;
                beatCountFull++;
            }

            beatHalf = false;
            beatIntervalHalf = beatInterval / 2;
            beatTimerHalf += delta;

            if(beatTimerHalf >= beatIntervalHalf && active){
                beatTimerHalf -= beatIntervalHalf;
                beatHalf = true;
                beatCountHalf++;
            }

            beatD8 = false;
            beatIntervalD8 = beatInterval / 8;
            beatTimerD8 += delta;

            if(beatTimerD8 >= beatIntervalD8 && active){
                beatTimerD8 -= beatIntervalD8;
                beatD8 = true;
                beatCountD8++;
            }
        }

        public static void Start() { active = true; }
        public static void Stop(){ active = false; }
        public static boolean isActive(){ return active; }
    }
}
