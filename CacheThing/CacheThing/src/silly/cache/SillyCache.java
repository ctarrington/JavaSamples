package silly.cache;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SillyCache {
    private List<Pair<Double, Integer>> aValues = new ArrayList<>();
    private List<Double> bValues = new ArrayList<>();
    private List<Integer> indexes = new ArrayList<>();


    public SillyCache(int size) {
        for (int ctr = 0; ctr < size; ctr++) {
            double aValue =  Math.random() * 1000;
            double bValue = aValue * 10 + Math.random();
            aValues.add(Pair.of(aValue, ctr));
            bValues.add(bValue);
            indexes.add(ctr);
        }
    }

    public List<Integer> sorted() {
        List<Integer> indexes = aValues.stream()
                .parallel()
                .sorted((pairA, pairB) -> Double.compare(pairA.getLeft(), pairB.getLeft()))
                .skip(475)
                .limit(50)
                .mapToInt(pair -> pair.getRight())
                .boxed()
                .collect(Collectors.toList());

        return indexes;
    }

    public String show(List<Integer> indexes) {
        StringBuilder builder = new StringBuilder();
        for (int ctr = 0; ctr < indexes.size(); ctr++) {
            int index = indexes.get(ctr);
            builder.append(this.indexes.get(index));
            builder.append(",");
            builder.append(this.aValues.get(index));
            builder.append(", ");
            builder.append(this.bValues.get(index));
            builder.append("\n");
        }

        return builder.toString();
    }

    public String head(int size) {
        List<Integer> indexes = IntStream.range(0, size).boxed().toList();
        return show(indexes);
    }
}
