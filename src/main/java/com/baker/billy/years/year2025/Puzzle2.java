package com.baker.billy.years.year2025;

import com.baker.billy.core.Puzzle;

public class Puzzle2 extends Puzzle {
    /**
     * Creates a new puzzle
     *
     * @param year The year
     */
    public Puzzle2(int year) {
        super(year);
    }

    @Override
    protected void task1() {
        String[] ranges = input[0].split(",");
        long sum = 0L;
        for(String range : ranges) {
            long start = Long.parseLong(range.split("-")[0]);
            long end = Long.parseLong(range.split("-")[1]);
            for(long i = start; i <= end; i++) {
                if(String.valueOf(i).matches("^([1-9]\\d*)\\1$")) {
                    sum += i;
                }
            }
        }
        System.out.printf("The sum of invalid IDs is %d\n", sum);
    }

    @Override
    protected void task2() {
        String[] ranges = input[0].split(",");
        long sum = 0L;
        for(String range : ranges) {
            long start = Long.parseLong(range.split("-")[0]);
            long end = Long.parseLong(range.split("-")[1]);
            for(long i = start; i <= end; i++) {
                if(String.valueOf(i).matches("^([1-9]\\d*)\\1+$")) {
                    sum += i;
                }
            }
        }
        System.out.printf("The sum of invalid IDs is %d\n", sum);
    }
}
