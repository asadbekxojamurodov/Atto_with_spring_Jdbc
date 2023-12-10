package code.uz.RandomUtils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
    public static long getRandomNumber(long smallest,long biggest){
        return ThreadLocalRandom.current().nextLong(smallest, biggest+1);
    }

}
