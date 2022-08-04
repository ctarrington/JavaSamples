import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PerfTest3FilterSortMap extends PerfTest{
    @Override
    protected List<Integer> createData(int listSize) {
        Random random = new Random();
        List<Integer> numbers = new ArrayList<>();
        for (int ctr=0; ctr<listSize; ctr++) {
            numbers.add(random.nextInt(100));
        }
        return numbers;
    }

    @Override
    protected Object runStreams(int sampleSize, Object data) {
        List<Integer> numbers = (List<Integer>) data;
        return numbers
                .stream()
                .filter(number -> number % 2 == 0)
                .sorted()
                .map(number -> new Thing(number))
                .collect(Collectors.toList());
    }

    @Override
    protected Object runTraditional(int sampleSize, Object data) {
        List<Integer> numbers = (List<Integer>) data;

        List<Thing> things = new ArrayList<>();
        List<Integer> evens = new ArrayList<>();

        for (int number : numbers) {
            if (number % 2 == 0) {
                evens.add(number);
            }
        }

        evens.sort(Integer::compareTo);

        for (int even : evens) {
            things.add(new Thing(even));
        }

        return things;
    }

    @Override
    protected boolean sanityCheck(Object traditionalResults, Object streamsResults) {
        List<Thing> traditionalThings = (List<Thing>) traditionalResults;
        List<Thing> streamsThings = (List<Thing>) streamsResults;

        if (traditionalThings.size() != streamsThings.size()) {
            System.out.println("sizes do not match");
            return false;
        }

        if (traditionalThings.size() > 0 && traditionalThings.get(0).getValue() != streamsThings.get(0).getValue()) {
            System.out.println("values do not match");
            return false;
        }

        return true;
    }

    class Thing {
        private Integer value;

        public Thing(Integer value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    PerfTest3FilterSortMap() {
    }
    public static void main(String[] args) {
        new PerfTest3FilterSortMap().runTests(1, 1000, 1, new int[] {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000});
    }


}

