package com.baker.billy.years.year2025;

import com.baker.billy.core.Puzzle;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

public class Puzzle9 extends Puzzle {
    private List<Point> points;
    private Polygon polygon;
    private long maxContained;
    
    /**
     * Creates a new puzzle
     *
     * @param year The year
     */
    public Puzzle9(int year) {
        super(year);
        points = new ArrayList<>();
        polygon = new Polygon();
        maxContained = Long.MIN_VALUE;
    }

    @Override
    protected void task1() {
        for(String str : input) {
            String[] pts = str.split(",");
            Point p = new Point(
                    Integer.parseInt(pts[0]),
                    Integer.parseInt(pts[1])
            );
            points.add(p);
            polygon.addPoint(p.x, p.y);
        }
        
        long max = Long.MIN_VALUE;
        for(int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            for(int j = i + 1; j < points.size(); j++) {
                Point p2 = points.get(j);
                long area = (long)(Math.abs(p1.x - p2.x) + 1) * (Math.abs(p1.y - p2.y) + 1);
                
                if(area > max) {
                    max = area;
                }
            }
        }
        System.out.printf("The maximum area you can attain is %d\n", max);
    }

    @Override
    protected void task2() {
        System.out.printf("The maximum area you can attain is %d\n", maxContained);
    }
}
