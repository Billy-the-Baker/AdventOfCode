package com.baker.billy.years.year2025;

import com.baker.billy.core.Puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle6 extends Puzzle {
    Map<Integer, List<String>> cephaNumbers;
    char[][] data;

    /**
     * Creates a new puzzle
     *
     * @param year The year
     */
    public Puzzle6(int year) {
        super(year);
        cephaNumbers = new HashMap<>();
        data = new char[input.length][];
    }

    @Override
    protected void task1() {
        String pattern = "\\d+|[*+]";
        for(int i = 0; i < input.length; i++) {
            data[i] = input[i].toCharArray();
        }

        for(String str : input) {
            List<String> values = getMatches(str, pattern);
            for(int i = 0; i < values.size(); i++) {
                if(!cephaNumbers.containsKey(i)) {
                    cephaNumbers.put(i, new ArrayList<>());
                }
                cephaNumbers.get(i).add(values.get(i));
            }
        }
        long result = 0L;

        result += cephaNumbers.values().stream()
                .mapToLong(this::calculateCephaMath)
                .sum();

        System.out.printf("The grand total is %d\n", result);
    }

    @Override
    protected void task2() {
        System.out.printf("The grand total using proper cephalopod numbers is %d\n", properCephalopodMath(data));
    }

    private List<String> getMatches(String input, String regex) {
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while(matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    private long calculateCephaMath(List<String> problem) {
        long result = Long.parseLong(problem.getFirst());

        char operator = problem.getLast().charAt(0);
        for(int i = 1; i < problem.size() - 1; i++) {
            if(operator == '+') {
                result += Long.parseLong(problem.get(i));
            } else {
                result *= Long.parseLong(problem.get(i));
            }
        }

        return result;
    }
    
    private long properCephalopodMath(char[][] data) {
        long result = 0L;
        
        StringBuilder buffer = new StringBuilder();
        long accumulator = 0L;
        char operator = '+';
        
        for(int col = 0; col < data[0].length; col++) {
            for(int row = 0; row < data.length; row++) {
                char c = data[row][col];
                if(c == '*' || c == '+') {
                    operator = c;
                    accumulator = operator == '*' ? 1L : 0L;
                } else {
                    if(c != ' ') {
                        buffer.append(c);
                    }
                }
            }
            if(buffer.isEmpty()) {
                result += accumulator;
                accumulator = operator == '*' ? 1L : 0L;
            } else {
                long val = Long.parseLong(buffer.toString());
                accumulator = operator == '*' ? accumulator * val : accumulator + val;
                buffer.setLength(0);
            }
        }
        result += accumulator;
        
        return result;
    }
}
