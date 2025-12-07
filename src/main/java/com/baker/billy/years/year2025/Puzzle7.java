package com.baker.billy.years.year2025;

import com.baker.billy.core.Puzzle;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Puzzle7 extends Puzzle {
    Map<Point, Long> paths;
    char[][] diagram;
    
    /**
     * Creates a new puzzle
     *
     * @param year The year
     */
    public Puzzle7(int year) {
        super(year);
        paths = new HashMap<>();
        diagram = createDiagram();
    }

    @Override
    protected void task1() {
        long total = 0L;
        
        int currentRow = 0;
        Stack<Point> trail = new Stack<>();
        Point current = new Point(diagram[0].length / 2, currentRow);
        trail.push(current);
        
        while(!trail.isEmpty()) {
            current = trail.pop();
            currentRow = current.y;
            
            while(currentRow < diagram.length) {
                char c = diagram[currentRow][current.x];
                if(c == '|') {
                    break;
                }
                if(c == '.' || c == 'S') {
                    diagram[currentRow][current.x] = '|';
                }
                if(c == '^') {
                    int newPath = 0;
                    int newColumn = current.x - 1;
                    if(inBounds(diagram[currentRow].length, newColumn)) {
                        if(diagram[currentRow][newColumn] != '|') {
                            newPath |= 1;
                            trail.push(new Point(newColumn, currentRow));
                        }
                    }
                    newColumn = current.x + 1;
                    if(inBounds(diagram[currentRow].length, newColumn)) {
                        if(diagram[currentRow][newColumn] != '|') {
                            newPath |= 1;
                            trail.push(new Point(newColumn, currentRow));
                        }
                    }
                    total += newPath;
                    break;
                }
                currentRow++;
            }
        }
        
        System.out.printf("The beam is split %d times\n", total);
    }

    @Override
    protected void task2() {
        for(int row = diagram.length - 1; row >= 0; row--) {
            for(int col = 0; col < diagram[row].length; col++) {
                if(diagram[row][col] == '^') {
                    // Find the path on either side to another ^ or the bottom and store the score
                    // Bottom is score 1 and ^ is whatever the score for that coordinate is
                    int leftCol = col - 1;
                    int rightCol = col + 1;
                    long pathsToBottom = 0;
                    if(inBounds(diagram[row].length, leftCol)) {
                        pathsToBottom += travel(diagram, leftCol, row);
                    }
                    if(inBounds(diagram[row].length, rightCol)) {
                        pathsToBottom += travel(diagram, rightCol, row);
                    }
                    Point p = new Point(col, row);
                    paths.put(p, pathsToBottom);
                }
            }
        }
        
        long timeLines = paths.values().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(-1);
        
        if(timeLines > -1) {
            System.out.printf("There are %d different timelines a tachyon could end up on\n", timeLines);
        } else {
            System.out.println("Could not pull the number of timelines out of the paths");
        }

        //System.out.printf("There are %d different timelines a tachyon could end up on\n",
        //        trickleDown(diagram, 0, diagram[0].length / 2));
    }
    
    private long trickleDown(char[][] arr, int row, int col) {
        long[] line = new long[arr[row].length];
        line[col] = 1L;
        int minCol = col;
        int maxCol = col;
        int max = maxCol;
        for(row = 1; row < arr.length; row++) {
            for(col = minCol; col < maxCol + 1; col++) {
                if(arr[row][col] == '^') {
                    if(inBounds(line.length, col - 1)) {
                        minCol = Math.min(minCol, col - 1);
                        line[col - 1] += line[col];
                    }
                    if(inBounds(line.length, col + 1)) {
                        max = Math.max(maxCol, col + 1);
                        line[col + 1] += line[col];
                    }
                    line[col] = 0;
                }
            }
            maxCol = max;
        }
        return Arrays.stream(line).sum();
    }
    
    private long travel(char[][] arr, int col, int row) {
        while(row < arr.length) {
            if(arr[row][col] == '^') {
                Point p = new Point(col, row);
                return paths.get(p);
            }
            row++;
        }
        return 1;
    }
    
    private boolean inBounds(int length, int x) {
        return x >= 0 && x < length;
    }
    
    private char[][] createDiagram() {
        char[][] diagram = new char[input.length][];
        for(int row = 0; row < input.length; row++) {
            diagram[row] = input[row].toCharArray();
        }
        return diagram;
    }
}
