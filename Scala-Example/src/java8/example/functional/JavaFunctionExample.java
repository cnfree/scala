package java8.example.functional;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public class JavaFunctionExample {

    private static Function<Integer, Integer> add1 = x -> x + 1;
    private static Function<Integer, Integer> mul3 = x -> x * 3;

    private static BinaryOperator<Function<Integer, Integer>> compose = (f, g) -> x -> g
            .apply(f.apply(x));

    public static void main(String[] args) {
        Function<Integer, Integer> composeFunc = compose.apply(add1, mul3);
        System.out.println(composeFunc.apply(10));
    }
}

