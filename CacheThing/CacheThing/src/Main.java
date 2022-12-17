import silly.cache.SillyCache;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SillyCache cache = new SillyCache(1000000);

        for (int repCtr = 0; repCtr < 20; repCtr++) {
            long start = System.currentTimeMillis();
            List<Integer> indexes = cache.sorted();
            long delta = System.currentTimeMillis() - start;
            System.out.println("delta: " + delta + " millis");
            //System.out.println("head:\n"+cache.show(indexes.subList(0, 8)));
        }
    }
}