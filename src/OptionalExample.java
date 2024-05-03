import java.util.Optional;

public class OptionalExample {
    public static void main(String[] args) {
        Integer[] integers = new Integer[5];
        integers[0] = 1;
        integers[1] = null;

       // int numb = integers[1];
        //will throw null pointer execption
        //System.out.println(numb);


        // Optional.ofNullable()
        Optional<Integer> optionalInteger = Optional.ofNullable(integers[1]);
        optionalInteger.ifPresent(integer -> System.out.println(integer));

        // Optional.of() will throw null pointer if value is null
       // Optional<Integer> i = Optional.of(integers[1]);

        //Optional.empty()
        Optional<String> emptyString = Optional.empty();

        //Optional.orElse()
        Optional<Integer> i = Optional.ofNullable(integers[1]);
        int result = i.orElse(-1);
        System.out.println(result);

        Optional<Integer> i1 = Optional.ofNullable(integers[0]);
        int result1 = i1.orElse(-1);
        System.out.println(result1);

        //Optional.orElseGet()
        Optional<Integer> i3 = Optional.ofNullable(integers[1]);
        int result3 = i3.orElseGet(()->-1);
        System.out.println(result3);

        //Optional.orElseThrow()
        Integer ex = Optional.of(integers[1]).orElseThrow(NullPointerException::new);
       // System.out.println("ex :: "+ ex);
    }
}
