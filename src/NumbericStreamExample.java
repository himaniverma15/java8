import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumbericStreamExample {
    public static void main(String[] args) {

        int[] arr = new int[]{1, 2, 3, 4};
//        Arrays.stream(arr).boxed().collect(Collectors.toList());
        // boxing
        List<Integer> integerList = IntStream.range(1, 5).boxed().collect(Collectors.toList());
        System.out.println(integerList);

        // unboxing
        List<Integer> numbs = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbs.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);

        int sum1 = numbs.stream().mapToInt(Integer::intValue).reduce((a, b) -> a + b).orElse(-1);
        System.out.println(sum1);
    }
}
