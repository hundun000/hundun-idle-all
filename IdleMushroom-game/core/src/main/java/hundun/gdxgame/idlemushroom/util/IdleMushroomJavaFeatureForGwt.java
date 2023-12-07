package hundun.gdxgame.idlemushroom.util;

import java.util.Random;

public class IdleMushroomJavaFeatureForGwt {
    static Random random = new Random();
    public static String uuid() {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<10;i++) {
            sb.append('a'+random.nextInt(26));
        }
        return sb.toString();
    };
}
