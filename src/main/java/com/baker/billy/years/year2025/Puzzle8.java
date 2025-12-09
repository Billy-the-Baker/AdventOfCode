package com.baker.billy.years.year2025;

import com.baker.billy.core.Puzzle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puzzle8 extends Puzzle {
    private List<Point3> points;
    private List<Point3> edges;
    private Map<Long, Long> union;
    private Map<Long, Long> circuits;
    private int iterations = 1000;
    
    /**
     * Creates a new puzzle
     *
     * @param year The year
     */
    public Puzzle8(int year) {
        super(year);
        points = new ArrayList<>();
        edges = new ArrayList<>();
        union = new HashMap<>();
        circuits = new HashMap<>();
    }

    @Override
    protected void task1() {
        for(String str : input) {
            String[] pts = str.split(",");
            Point3 p = new Point3(
                    Long.parseLong(pts[0]),
                    Long.parseLong(pts[1]),
                    Long.parseLong(pts[2])
            );
            points.add(p);
        }
        for(int i = 0; i < points.size(); i++) {
            for(int j = 0; j < points.size(); j++) {
                if(i > j) {
                    Point3 p1 = points.get(i);
                    Point3 p2 = points.get(j);
                    long dist = p1.distanceSq(p2);
                    edges.add(new Point3(dist, i, j));
                }
            }
        }
        
        for(long l = 0; l < points.size(); l++) {
            union.put(l, l);
        }
        
        edges.sort((p1, p2) -> Long.compare(p1.x, p2.x));
        
        for(int i = 0; i < iterations; i++) {
            var p = edges.get(i);
            mix(p.y, p.z);
        }
        
        for(int i = 0; i < points.size(); i++) {
            long find = find(i);
            long put = circuits.getOrDefault(find, 0L) + 1;
            circuits.put(find, put);
        }
        List<Long> sizes = circuits.values().stream().sorted(Comparator.reverseOrder()).toList();
        System.out.printf("The product of the three largest circuits is %d\n", (sizes.get(0) * sizes.get(1) * sizes.get(2)));
    }

    @Override
    protected void task2() {
        for(long l = 0; l < points.size(); l++) {
            union.put(l, l);
        }
        int connections = 0;
        for(int i = 0; i < edges.size(); i++) {
            var p = edges.get(i);
            if(find(p.y) != find(p.z)) {
                connections++;
                if(connections == points.size() - 1) {
                    System.out.printf("The last two junction boxes provide a value of %d\n", points.get((int)p.y).x * points.get((int)p.z).x);
                    return;
                }
                mix(p.y, p.z);
            }
        }
    }
    
    private long find(long l) {
        if(l == union.get(l)) {
            return l;
        }
        long result = find(union.get(l));
        union.put(l, result);
        return result;
    }
    
    private void mix(long x, long y) {
        union.put(find(x), find(y));
    }
    
    private class Point3 {
        long x;
        long y;
        long z;

        public Point3(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public long distanceSq(Point3 other) {
            return (long) (Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2) + Math.pow(other.z - z, 2));
        }
    }
}
