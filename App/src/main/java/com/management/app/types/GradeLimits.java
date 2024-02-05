package com.management.app.types;

import javafx.util.Pair;

/**
 * The GradeLimits class represents a grade range with associated note.
 * It uses a Pair to store the note and an IntStream representing the range of points.
 */
public class GradeLimits {
    private static class Limit {
        int min;
        int max;

        public Limit(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }
    }

    private final Pair<String, Limit> keyValuePair;

    /**
     * Constructor to initialize GradeLimits with a note and a range of points.
     *
     * @param note     The note associated with the grade range.
     * @param minPoint The minimum point in the range (inclusive).
     * @param maxPoint The maximum point in the range (inclusive).
     */
    public GradeLimits(String note, int minPoint, int maxPoint) {
        this.keyValuePair = new Pair<>(note, new Limit(minPoint, maxPoint));
    }

    /**
     * Get the note associated with the grade range.
     *
     * @return The note as a String.
     */
    public String getNote() {
        return keyValuePair.getKey();
    }

    /**
     * Get the minimum point in the range.
     *
     * @return The minimum point or -1 if the range is empty.
     */
    public int getMin() {
        return this.keyValuePair.getValue().getMin();
    }

    /**
     * Get the maximum point in the range.
     *
     * @return The maximum point or -1 if the range is empty.
     */
    public int getMax() {
        return this.keyValuePair.getValue().getMax();
    }
}
