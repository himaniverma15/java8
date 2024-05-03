import java.util.Arrays;
import java.util.List;

public class lamdaExpressionEffectiveFinal {
    public static void main(String[] args) {

        List<Integer> integers = Arrays.asList(1,2,3,4);

        int k = 5;
        integers.stream().forEach(i->{

            //k =9;
            // we cannot modify the variable inside of lambda expression even though they are not final this
            // condition is called as effective final

        });


    }
}
