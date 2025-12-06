package com.baker.billy.years.year2025;

import com.baker.billy.core.Puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Puzzle5 extends Puzzle {
    List<Range> ranges;

    /**
     * Creates a new puzzle
     *
     * @param year The year
     */
    public Puzzle5(int year) {
        super(year);
        ranges = new ArrayList<>();
    }

    @Override
    protected void task1() {
        boolean isRange = true;
        int count = 0;
        for(String str : input) {
            if("".equals(str)) {
                ranges = mergeOverlap(ranges);
                isRange = false;
                continue;
            }
            if(isRange) {
                String[] parts = str.split("-");
                Range range = new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
                addRangeToSortedList(ranges, range);
            } else {
                long test = Long.parseLong(str);
                for (Range r : ranges) {
                    if (r.inRange(test)) {
                        count++;
                        break;
                    }
                }
            }
        }
        System.out.printf("There are %d fresh ingredients\n", count);
    }

    @Override
    protected void task2() {
        long total = 0L;
        for(Range r : ranges) {
            total += r.length();
        }
        System.out.printf("There are %d total fresh ingredients\n", total);
    }

    private void addRangeToSortedList(List<Range> ranges, Range range) {
        int insertion = Collections.binarySearch(ranges, range, Comparator.reverseOrder());
        if (insertion < 0) {
            insertion = -insertion - 1;
        }
        ranges.add(insertion, range);
    }

    private List<Range> mergeOverlap(List<Range> ranges) {
        List<Range> finalRanges = new ArrayList<>();
        Range initial = ranges.getFirst();
        for(int i = 1; i < ranges.size(); i++) {
            Range r = ranges.get(i);
            if(initial.overlap(r)) {
                initial = initial.merge(r);
            } else {
                finalRanges.add(initial);
                initial = r;
            }
        }
        finalRanges.add(initial);
        return finalRanges;
    }

    private record Range(long start, long end) implements Comparable<Range> {
        public boolean inRange(long value) {
            return value <= end && value >= start;
        }

        public boolean overlap(Range r) {
            return start <= r.end && r.start <= end;
        }

        public Range merge(Range r) {
            return new Range(Math.min(start, r.start), Math.max(end, r.end));
        }

        public long length() {
            return end - start + 1;
        }

        @Override
        public int compareTo(Range o) {
            return Long.compare(end, o.end);
        }
    }
}
