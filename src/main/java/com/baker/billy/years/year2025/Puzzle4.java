package com.baker.billy.years.year2025;

import com.baker.billy.core.Puzzle;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class Puzzle4 extends Puzzle {
    private final Map<Point, Set<Point>> graph = new HashMap<>();

    /**
     * Creates a new puzzle
     *
     * @param year The year
     */
    public Puzzle4(int year) {
        super(year);
        constructGraph();
    }

    @Override
    protected void task1() {
        long total = graph.keySet().stream()
                .filter(k -> graph.get(k).size() < 4)
                .toList()
                .size();

        System.out.printf("There are %d rolls that can be moved\n", total);
    }

    @Override
    protected void task2() {
        int total = 0;
        List<Point> toRemove;
        do {
            toRemove = graph.keySet().stream()
                    .filter(k -> graph.get(k).size() < 4)
                    .toList();
            toRemove.forEach(k -> {
                graph.get(k).forEach(k2 -> graph.get(k2).remove(k));
                graph.remove(k);
            });
            total += toRemove.size();
        } while(!toRemove.isEmpty());

        System.out.printf("There are %d rolls that can be removed\n", total);
    }

    private void constructGraph() {
        int y = 0;
        for(String str : input) {
            int x = 0;
            for(char c : str.toCharArray()) {
                if(c == '@') {
                    Point p = new Point(x, y);
                    graph.put(p, new HashSet<>());
                    for(int dx = x - 1; dx < x + 2; dx++) {
                        for(int dy = y - 1; dy < y + 2; dy++) {
                            Point key = new Point(dx, dy);
                            if(graph.containsKey(key)) {
                                double distSq = Math.pow((key.x - x), 2) + Math.pow((key.y - y), 2);
                                if (distSq <= 2 && distSq != 0) {
                                    graph.get(key).add(p);
                                    graph.get(p).add(key);
                                }
                            }
                        }
                    }
                }
                x++;
            }
            y++;
        }
    }
}
