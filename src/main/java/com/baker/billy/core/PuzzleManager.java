package com.baker.billy.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PuzzleManager {
    private static final int MIN_YEAR = 2015;
    private static final int CURRENT_YEAR = LocalDate.now().getYear();
    private static final int PIVOT_YEAR = 2025;
    private static final int MIN_DAY = 1;
    private static final int MAX_DAY_NEW = 12;
    private static final int MAX_DAY_OLD = 25;

    private int year;
    private int day;
    private int part;

    public PuzzleManager(int year, int day, int part) {
        if(year < MIN_YEAR || year > CURRENT_YEAR) {
            throw new IllegalArgumentException("[ERROR]: PuzzleManager.java : new() : Year falls outside of authorized range");
        }
        this.year = year;
        // Only -1 and a day value smaller than 12 or 25 is allowed depending on the year
        if(day == 0 || day < -1 || (this.year >= PIVOT_YEAR && day > MAX_DAY_NEW) || day > MAX_DAY_OLD) {
            throw new IllegalArgumentException("[ERROR]: PuzzleManager.java : new() : Day falls outside of authorized range");
        }
        this.day = day;
        if(part < 1 || part > 2) {
            throw new IllegalArgumentException("[ERROR]: PuzzleManager.java : new() : Part falls outside of authorized range");
        }
        this.part = part;
    }

    public void run() {
        List<Puzzle> puzzles = new ArrayList<>();
        if(day == -1) {
            int daysToRun = CURRENT_YEAR >= PIVOT_YEAR ? MAX_DAY_NEW : MAX_DAY_OLD;
            for(int i = MIN_DAY; i < daysToRun; i++) {
                // Add single day puzzle
                String className = "com.baker.billy.years.year" + year + ".Puzzle" + i;
                puzzles.add(Puzzle.getPuzzle(year, className));
            }
        } else {
            // Add single day puzzle
            String className = "com.baker.billy.years.year" + year + ".Puzzle" + day;
            puzzles.add(Puzzle.getPuzzle(year, className));
        }
        for(Puzzle puzz : puzzles) {
            puzz.doTasks();
        }
        //String url = "https://adventofcode.com/" + year + "/day/" + day + "/input";
    }
}
