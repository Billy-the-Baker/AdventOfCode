package com.baker.billy;

import com.baker.billy.core.PuzzleManager;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Runs the main program
 */
public class Driver {
    private static final Set<String> FLAGS = Set.of("--all");

    /**
     * Entry-point for the main program
     *
     * @param args the list of command line arguments passed to the program
     */
    public static void main(String[] args) {
        ParameterSet params = new ParameterSet(args);

        // Determine the correct day and year to process. Default behavior is to run all available puzzles for the current year
        LocalDate date = LocalDate.now();
        boolean all = false;
        if(!params.contains("--year")) {
            throw new IllegalStateException("The parameter --year is a required program parameter");
        }

        if(params.contains("--all")) {
            all = true;
        } else {
            if (params.contains("--day")) { // How to ensure I get a proper default date? What year is the default?
                date = updateDate(date, params.getParameter("--year"), params.getParameter("--day"));
            }
        }

        // Determine if all parts or only a specific part should run. Default behavior is all parts
        int part = -1;
        if(params.contains("--part")) {
            part = Integer.parseInt(params.getParameter("--part"));
        }

        PuzzleManager puzzleManager = new PuzzleManager(date.getYear(),
                all ? PuzzleManager.ALL_DAYS : date.getDayOfMonth(),
                part);
        puzzleManager.run();
    }

    private static LocalDate updateDate(LocalDate date, String year, String day) {
        int numYear = Integer.parseInt(year);
        int numDay = Integer.parseInt(day);
        return (date.withYear(numYear)).withDayOfMonth(numDay);
    }
}
