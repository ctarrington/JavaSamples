import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PerfTest2MapLookup extends PerfTest{
    private String[] keys = new String[] {null, "notfound", "aaa"};

    private Map<String, String> mapFile = new HashMap<>();
    @Override
    protected List<Integer> createData(int listSize) {
        return null;
    }

    @Override
    protected Object runStreams(int sampleSize, Object data) {
        // intentionally not streaming over the keys to replicate a size of 1 on each call
        boolean[] results = new boolean[keys.length];
        for (int ctr = 0; ctr < keys.length; ctr++) {
            results[ctr] = Optional.ofNullable(keys[ctr]).
                    map( str -> mapFile.containsKey(str)).
                    orElse(false);
        }

        return results;
    }

    @Override
    protected Object runTraditional(int sampleSize, Object data) {
        boolean[] results = new boolean[keys.length];
        for (int ctr = 0; ctr < keys.length; ctr++) {
            results[ctr] = keys[ctr] == null ? false : mapFile.containsKey(keys[ctr]);
        }

        return results;
    }

    @Override
    protected boolean sanityCheck(Object traditional, Object streams) {
        boolean[] traditionalResults = (boolean[]) traditional;
        boolean[] streamsResults = (boolean[]) streams;

        if (traditionalResults.length != keys.length || traditionalResults.length != streamsResults.length) {
            System.out.println("lengths do not match "+ traditionalResults.length +" "+streamsResults.length);
            return false;
        }

        for (int ctr = 0; ctr < keys.length; ctr++) {
            if (traditionalResults[ctr] != streamsResults[ctr]) {
                System.out.printf("values do not match for ctr: %d \n"+ ctr);
                return false;
            }
        }


        return true;
    }

    PerfTest2MapLookup() {
        mapFile.put("aaa", "abc");
        mapFile.put("bbb", "def");
        mapFile.put("ddd", "efg");

    }
    public static void main(String[] args) {
        new PerfTest2MapLookup().runTests(1, 5000, 1, new int[] {1});
    }


}

