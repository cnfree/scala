package java8.example.functional;

import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionAndConsumer {
    /**
     * 打印字符串
     */
    private static Consumer<String> println = System.out::println;
    private static Function<String, String> upperfier = String::toUpperCase;

    public static void main(String[] args) {
        println.accept(upperfier.apply("Hello"));
    }
}