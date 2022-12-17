import silly.cache.SillyCache;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SillyCache cache = new SillyCache(1000000);

        int REPS = 100;
        long totalTime = 0;
        long minTime = 100000;
        long maxTime = 0;

        cache.sorted(); // throw away the first one
        for (int repCtr = 0; repCtr < REPS; repCtr++) {
            long start = System.currentTimeMillis();
            List<Integer> indexes = cache.sorted();
            long delta = System.currentTimeMillis() - start;
            minTime = Long.min(minTime, delta);
            maxTime = Long.max(maxTime, delta);
            totalTime += delta;
            System.out.println("head:\n"+cache.show(indexes.subList(0, 8)));
        }

        System.out.println("average: "+totalTime/REPS+", min: " +minTime+ ", max: " + maxTime);
    }
}