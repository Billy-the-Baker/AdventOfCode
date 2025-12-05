package com.baker.billy.years.year2025;

import com.baker.billy.core.Puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Puzzle5 extends Puzzle {
    private Set<Range> fresh;

    /**
     * Creates a new puzzle
     *
     * @param year The year
     */
    public Puzzle5(int year) {
        super(year);
        fresh = new TreeSet<Range>((Range r1, Range r2) -> Long.compare(r2.end, r1.end));
    }

    @Override
    protected void task1() {
        List<Range> ranges = new ArrayList<>();
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
                boolean isFresh = false;
                for (Range r : ranges) {
                    if (test <= r.end && test >= r.start) {
                        fresh.add(r);
                        if (!isFresh) {
                            count++;
                            isFresh = true;
                        }
                    }
                }
            }
        }
        System.out.printf("There are %d fresh ingredients\n", count);
    }

    @Override
    protected void task2() {
        long total = 0L;
        for(Range r : fresh) {
            total += r.end - r.start + 1;
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
            if(initial.start <= r.end && r.start <= initial.end) {
                initial = new Range(Math.min(initial.start, r.start), Math.max(initial.end, r.end));
            } else {
                finalRanges.add(initial);
                initial = r;
            }
        }
        finalRanges.add(initial);
        return finalRanges;
    }

    private record Range(long start, long end) implements Comparable<Range> {
        @Override
        public int compareTo(Range o) {
            return Long.compare(end, o.end);
        }
    }
}
