public abstract class PerfTest {
    protected void runTests(int runCount, int minimumTime, int repsPerCycle, int[] sampleSizes) {
        System.out.printf("For each sample size, the number of repetitions is selected to ensure that the traditional method requires at least %d milliseconds.\n", minimumTime);
        System.out.printf("%10s %10s %10s %10s %10s %10s\n", "sample size", "reps", "traditional", "streams", "traditional", "streams");

        for (int runCtr = 0; runCtr < runCount; runCtr++) {
            for (int sampleSize : sampleSizes) {
                long traditionalDelta = 0;
                long fpDelta = 0;
                int reps = 0;

                while (traditionalDelta < minimumTime) {
                    Object data = createData(sampleSize);

                    Object traditionalResult = null;
                    Object streamsResult = null;

                    for (int sampleCtr = 0; sampleCtr < repsPerCycle; sampleCtr++) {
                        reps++;
                        long start = System.currentTimeMillis();
                        traditionalResult = runTraditional(sampleSize, data);
                        traditionalDelta += (System.currentTimeMillis() - start);
                    }

                    for (int sampleCtr = 0; sampleCtr < repsPerCycle; sampleCtr++) {
                        long start = System.currentTimeMillis();
                        streamsResult = runStreams(sampleSize, data);
                        fpDelta += (System.currentTimeMillis() - start);
                    }

                    if (!sanityCheck(traditionalResult, streamsResult)) {
                        System.exit(-1);
                    }
                }

                float totalDelta = traditionalDelta + fpDelta;
                float traditionalRatio = (float) traditionalDelta / totalDelta;
                float fpRatio = (float) fpDelta / totalDelta;
                System.out.printf("%10d %10d %10d %10d %10.3f %10.3f \n", sampleSize, reps, traditionalDelta, fpDelta, traditionalRatio, fpRatio);
            }
        }
    }

    protected abstract Object createData(int sampleSize);

    protected abstract Object runStreams(int sampleSize, Object data);

    protected abstract Object runTraditional(int sampleSize, Object data);

    protected abstract boolean sanityCheck(Object traditionalResults, Object streamsResults);
}
