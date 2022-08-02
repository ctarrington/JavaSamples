import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class PerfTest4Math {
    private static final int WALK_LENGTH = 2000;
    private static final double WALK_STEP = 0.05;

    public static void main(String[] args) {
        new PerfTest4Math().runTests();
    }

    private class Results {
        public double mean;
        public double std;
        public double[] sortedResults;
    }

    private void runTests() {
        long repeatCount = 5;

        System.out.printf("create and describe normal distribution. all times in milliseconds\n");

        System.out.printf("each value is the average of %d repetitions of the loops\n", repeatCount);
        System.out.printf("%10s %10s %10s %10s %10s\n", "sample size", "imperative", "fp", "diff", "% diff");
        for (int sampleSizePower : new int[] {3,4,5}) {
            int sampleSize = (int) Math.pow(10, sampleSizePower);

            long imperativeDelta = 0;
            for (int sampleCtr = 0; sampleCtr < repeatCount; sampleCtr++) {
                long start = System.currentTimeMillis();
                Results results = imperativeBuildDistribution(sampleSize);
                long delta = System.currentTimeMillis() - start;
                imperativeDelta += delta;

                //System.out.println("Imperative mean: " + results.mean);
                //System.out.println("imperative std: " +results.std);
                //System.out.println("Imperative IQR "+results.sortedResults[(int) (sampleSize*0.25)] + " to " +results.sortedResults[(int) (sampleSize*0.75)]);
            }

            long fpDelta = 0;
            for (int sampleCtr = 0; sampleCtr < repeatCount; sampleCtr++) {
                long start = System.currentTimeMillis();
                Results results = fpBuildDistribution(sampleSize);
                long delta = System.currentTimeMillis() - start;
                fpDelta += delta;

                //System.out.println("FP mean: " + results.mean);
                //System.out.println("FP std: " +results.std);
                //System.out.println("FP IQR "+results.sortedResults[(int) (sampleSize*0.25)] + " to " +results.sortedResults[(int) (sampleSize*0.75)]);
            }

            imperativeDelta = imperativeDelta / repeatCount;
            fpDelta = fpDelta / repeatCount;

            long change = imperativeDelta-fpDelta;
            String msg = change<0?"slower":"faster";
            msg = change==0?"wash":msg;
            long abs = Math.abs(change);
            long percent = Math.round(100.0*abs/imperativeDelta);
            System.out.printf("%10d %10d %10d %10d %10d%% %s\n", sampleSize, imperativeDelta, fpDelta, abs, percent, msg);
        }
    }

    private Results imperativeBuildDistribution(int sampleSize) {
        Results results = new Results();
        results.sortedResults = new double[sampleSize];

        double sum = 0;
        for (int sampleCtr = 0; sampleCtr < sampleSize; sampleCtr++) {
            double sample = 0;
            for (int walkCtr = 0; walkCtr < WALK_LENGTH; walkCtr++) {
                double sign = Math.random() > 0.5 ? 1.0 : -1.0;
                sample += sign * WALK_STEP;
            }

            results.sortedResults[sampleCtr] = sample;
            sum += sample;
        }

        Arrays.sort(results.sortedResults);
        results.mean = sum / sampleSize;

        double sum_of_squared_deviation = 0;
        for (double value : results.sortedResults) {
            sum_of_squared_deviation += Math.pow(value - results.mean,2.0);
        }
        results.std = Math.sqrt(sum_of_squared_deviation / (sampleSize-1));
        return results;
    }

    private Results fpBuildDistribution(int sampleSize) {
        Results results = new Results();

        DoubleStream sampleStream = DoubleStream.generate(() -> {
            DoubleStream stepsStream = DoubleStream.generate(() -> {
                double sign = Math.random() > 0.5 ? 1.0 : -1.0;
                return sign * WALK_STEP;
            });

            return stepsStream.limit(WALK_LENGTH).sum();
        });

        results.sortedResults = sampleStream.
                limit(sampleSize).
                sorted().
                toArray();
        results.mean = Arrays.stream(results.sortedResults).sum() / sampleSize;

        double sum_of_squared_deviation = Arrays.stream(results.sortedResults).
                map((value) -> Math.pow(value - results.mean,2.0)).
                sum();
        results.std = Math.sqrt(sum_of_squared_deviation / (sampleSize-1));
        return results;
    }
}
