package com.baker.billy.years.year2025;

import com.baker.billy.core.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
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
        data = transpose(data);
        System.out.printf("The grand total using proper cephalopod numbers is %d\n", calculateProperCephaMath(data));
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
    
    private long calculateProperCephaMath(char[][] data) {
        long result = 0L;
        char operator = '!';
        
        long accumulator = 0L;
        for (int row = 0; row < data.length; row++) {
            char[] dataRow = data[row];
            char op = data[row][data[row].length - 1];
            if(op == '*' || op == '+') {
                operator = op;
                accumulator = op == '*' ? 1L : 0L;
                dataRow = Arrays.copyOf(dataRow, dataRow.length - 1);
            }
            String toConvert = String.valueOf(dataRow).trim();
            if(!toConvert.isEmpty()) {
                long val = Long.parseLong(toConvert);
                accumulator = operator == '*' ? accumulator * val : accumulator + val;
            } else {
                result += accumulator;
            }
        }
        result += accumulator;
        
        return result;
    }

    private char[][] transpose(char[][] in) {
        char[][] out = new char[in[0].length][in.length];

        for(int col = 0; col < in[0].length; col++) {
            for(int row = 0; row < in.length; row++) {
                out[col][row] = in[row][col];
            }
        }

        return out;
    }
}
