package com.mygdx.revelare.Utils;

public class BeatSequencer {

    public static class BeatAnalyser{
        //Class Members
        public static float bpm;
        public static boolean beatFull, beatHalf;

        private static float beatInterval, beatTimer, beatIntervalHalf, beatTimerHalf;
        private static boolean active = false;

        public static void update(float delta){

            //Full Beat Count
            beatFull = false;
            beatInterval = 60 / bpm;
            beatTimer += delta;

            if(beatTimer >= beatInterval && active){
                beatTimer -= beatInterval;
                beatFull = true;
            }

            beatHalf = false;
            beatIntervalHalf = beatInterval / 2;
            beatTimerHalf += delta;

            if(beatTimerHalf >= beatIntervalHalf && active){
                beatTimerHalf -= beatIntervalHalf;
                beatHalf = true;
            }
        }

        public static void start() { active = true; }
        public static void stop(){ active = false; }
        public static boolean isActive(){ return active; }
        public static void reset(){ beatTimer = 0f; beatTimerHalf = 0f; }
    }
}
