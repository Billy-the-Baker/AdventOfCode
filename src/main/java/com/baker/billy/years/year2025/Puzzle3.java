package com.baker.billy.years.year2025;

import com.baker.billy.core.Puzzle;

public class Puzzle3 extends Puzzle {
    /**
     * Creates a new puzzle
     *
     * @param year The year
     */
    public Puzzle3(int year) {
        super(year);
    }

    @Override
    protected void task1() {
        long sum = 0L;
        for(String str : input) {
            sum += findLargestDigitOfLength(str, 2);
        }
        System.out.printf("The total output joltage is %d\n", sum);
    }

    @Override
    protected void task2() {
        long sum = 0L;
        for(String str : input) {
            sum += findLargestDigitOfLength(str, 12);
        }
        System.out.printf("The total output joltage is %d\n", sum);
    }

    /**
     * Finds the largest numerical value of a certain length from a string. Assumes all characters in the sequence are digits
     *
     * @param sequence the sequence of characters to search
     * @param length the number of digits required in the final number
     * @return the largest numerical value from the given string
     */
    private long findLargestDigitOfLength(String sequence, int length) {
        if(sequence.length() < length) {
            return 0;
        }

        if(sequence.length() == length) {
            return Long.parseLong(sequence);
        }

        int index = -1;
        int remainingLength = length;
        StringBuilder number = new StringBuilder();

        while(number.length() < length) {
            int max = -1;
            for(int i = index + 1; i <= sequence.length() - remainingLength; i++) {
                int val = sequence.charAt(i);
                if(val > max) {
                    max = val;
                    index = i;
                }
            }
            number.append((char)max);
            remainingLength--;
        }
        return Long.parseLong(number.substring(number.length() - length));
    }
}
