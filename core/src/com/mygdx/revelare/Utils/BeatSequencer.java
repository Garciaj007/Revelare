package com.mygdx.revelare.Utils;

public class BeatSequencer {

    public static class BeatAnalyser{
        //Class Members
        public static float bpm;
        public static boolean beatFull, beatD8;

        private static float beatInterval, beatTimer, beatIntervalD8, beatTimerD8;
        private static int beatCountFull, beatCountD8;
        private static boolean active;

        public static void update(float delta){

            //Full Beat Count
            beatFull = false;
            beatInterval = 60 / bpm;
            beatTimer += delta;

            if(beatTimer >= beatInterval && active){
                beatTimer -= beatInterval;
                beatFull = true;
                beatCountFull++;
                System.out.println("Full Beat Triggered");
            }

            beatD8 = false;
            beatIntervalD8 = beatInterval / 8;
            beatTimerD8 += delta;

            if(beatTimerD8 >= beatIntervalD8 && active){
                beatTimerD8 -= beatIntervalD8;
                beatD8 = true;
                beatCountD8++;
                System.out.println("8th Beat Triggered");
            }
        }

        public static void Start() { active = true; }
        public static void Stop(){ active = false; }
        public static boolean isActive(){ return active; }
    }
}
