package com.heigvd.Tools;

import java.util.Random;


public class RandomNumber {
    /**
     * Generates a random number in the given range
     * @param min Minimum value
     * @param max Maximum value
     * @return Random value in range
     */
    public static int randomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
