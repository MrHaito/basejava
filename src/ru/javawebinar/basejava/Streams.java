package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Streams {
    public static void main(String[] args) {
        int[] intArray = new int[]{3, 2, 3, 3, 2, 1};
        for (Integer integer : intArray) {
            System.out.print(integer + " ");
        }
        System.out.println("\n");
        System.out.println("minValue: " + minValue(intArray));

        List<Integer> integers = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            integers.add(i);
        }
        System.out.print("oddOrEven: " + oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (a, b) -> 10 * a + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum() % 2;
        return integers.stream()
                .filter(s -> s % 2 != sum)
                .collect(Collectors.toList());
    }
}
