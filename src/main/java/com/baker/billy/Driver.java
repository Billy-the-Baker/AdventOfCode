package com.baker.billy;

import com.baker.billy.core.PuzzleManager;
import com.baker.billy.core.SimpleRequest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Map<String, String> params = parseArgs(args);

        // Determine the correct day and year to process. Default behavior is to run all available puzzles for the current year
        LocalDate date = LocalDate.now();
        boolean all = false;
        if(!params.containsKey("--all") && params.containsKey("--day") && params.containsKey("--year")) {
            try {
                int year = Integer.parseInt(params.get("--year"));
                int day = Integer.parseInt(params.get("--day"));
                date = date.withYear(year);
                date = date.withDayOfMonth(day);
            } catch(NumberFormatException e) {
                System.err.println("[EXCEPTION]: Driver.java : main() : Day or month value was non-numeric");
                System.out.printf("Day or month value was non-numeric. Defaulting to all days for the current year of %d\n", date.getYear());
            } catch(DateTimeException e) {
                System.err.println("[EXCEPTION]: Driver.java : main() : Date could not be adjusted with given data");
                System.out.printf("Date could not be adjusted with given data. Defaulting to all days for the current year of %d\n", date.getYear());
            }
        } else {
            all = true;
        }

        // Determine if all parts or only a specific part should run. Default behavior is all parts
        int part = 2;
        if(params.containsKey("--part")) {
            try {
                part = Integer.parseInt(params.get("--part"));
            } catch(NumberFormatException e) {
                System.err.println("[EXCEPTION]: Driver.java : main() : Part value was non-numeric");
                System.out.println("Part value was non-numeric. Defaulting to all parts");
            }
        }

        PuzzleManager puzzleManager = new PuzzleManager(date.getYear(),
                all ? -1 : date.getDayOfMonth(),
                part);
        puzzleManager.run();
    }

    /**
     * Parses the command line arguments sent into the program
     *
     * @param args the list of arguments
     * @return the arguments parsed out into key-value pairs
     */
    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> params = new HashMap<>();

        if(args == null || args.length == 0) {
            return params;
        }

        // More than one argument was provided
        if(args.length > 1) {
            for(int i = 0; i < args.length; i++) {
                String arg = args[i];

                if(!arg.startsWith("--")) {
                    // Bad syntax
                    continue;
                }

                if(FLAGS.contains(arg)) {
                    params.put(arg, "true");
                } else {
                    String lookAhead = args[i + 1];
                    if(!lookAhead.startsWith("--")) {
                        params.putIfAbsent(arg, lookAhead);
                    }
                }
            }
        } else {
            params.put(args[0], "true");
        }

        return params;
    }
}
