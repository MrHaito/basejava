package ru.javawebinar.basejava;

import java.util.Arrays;

public class Streams {
    public static void main(String[] args) {
        int[] intArray = new int[]{3,2,3,3,2,1};

        for (Integer integer : intArray) {
            System.out.print(integer + " ");
        }
        System.out.println("\n");
        System.out.println("minValue: " + minValue(intArray));


    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> 10 * a + b);
    }

}
