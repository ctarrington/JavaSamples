import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class PerfTest4Math extends PerfTest {
    private static final int WALK_LENGTH = 2000;
    private static final double WALK_STEP = 0.05;


    private class Results {
        public double mean;
        public double std;
        public double[] sortedResults;
    }

    @Override
    protected Object createData(int listSize) {
        return null;
    }

    @Override
    protected Results runTraditional(int sampleSize, Object ignored) {
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

    @Override
    protected Results runStreams(int sampleSize, Object ignored) {
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

    @Override
    protected boolean sanityCheck(Object traditional, Object streams) {
        Results traditionalResults = (Results) traditional;
        Results streamsResults = (Results) streams;

        if (traditionalResults.sortedResults.length != streamsResults.sortedResults.length) {
            System.out.println("lengths differ: " + traditionalResults.sortedResults.length + " " + streamsResults.sortedResults.length);
            return false;
        }

        if (traditionalResults.sortedResults.length > 100 && Math.abs(traditionalResults.mean - streamsResults.mean) > 0.25) {
            System.out.println("means differ: " + traditionalResults.mean + " " + streamsResults.mean);
            return false;
        }

        if (traditionalResults.sortedResults.length > 100 && Math.abs(traditionalResults.std - streamsResults.std) > 0.25) {
            System.out.println("standard deviations differ: " + traditionalResults.std + " " + streamsResults.std);
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        new PerfTest4Math().runTests(1, 1000, 1, new int[] {1, 10, 100, 1000, 10000, 100000});
    }
}
