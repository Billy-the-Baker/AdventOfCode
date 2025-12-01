package com.baker.billy.years.year2025;

import com.baker.billy.core.Puzzle;

public class Puzzle1 extends Puzzle {
    /**
     * Creates a new puzzle
     *
     * @param year The year
     */
    public Puzzle1(int year) {
        super(year);
    }

    @Override
    protected void task1() {
        System.out.println("Task 1");
        for(String str : input) {
            System.out.println(str);
        }
    }

    @Override
    protected void task2() {
        System.out.println("Task 2");
    }
}
