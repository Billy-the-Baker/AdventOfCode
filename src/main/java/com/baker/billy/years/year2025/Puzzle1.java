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
        int location = 50;
        int numZero = 0;
        for(String str : input) {
            char direction = str.charAt(0);
            int magnitude = Integer.parseInt(str.substring(1));
            if(direction == 'L') {
                location = (location - magnitude + 100) % 100;
            } else {
                location = (location + magnitude) % 100;
            }
            if(location == 0) {
                numZero++;
            }
        }
        System.out.printf("The password is %d\n", numZero);
    }

    @Override
    protected void task2() {
        int location = 50;
        int numZero = 0;
        for(String str : input) {
            char direction = str.charAt(0);
            int magnitude = Integer.parseInt(str.substring(1));

            for(int i = 0; i < magnitude; i++) {
                if(direction == 'L') {
                    location = (location - 1 + 100) % 100;
                } else {
                    location = (location + 1) % 100;
                }
                if(location == 0) {
                    numZero++;
                }
            }

            /*int prev = location;
            numZero += magnitude / 100;
            int diff = magnitude % 100;
            if(direction == 'L') {
                location = (location - diff + 100) % 100;
                if(location > prev && prev != 0) {
                    numZero++;
                }
            } else {
                location = (location + diff) % 100;
                if(location < prev && location != 0) {
                    numZero++;
                }
            }
            if(prev != 0 && location == 0) {
                numZero++;
            }**/
        }
        System.out.printf("The password is %d\n", numZero);
    }
}
