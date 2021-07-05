package com.example.app.util;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class providing common functionality.
 */
public class Util {
    public static <T> Map<T, Integer> countFrequencies(List<@NotNull T> array) {
        Map<T, Integer> frequencies = new TreeMap<>();
        for (T element : array) {
            frequencies.merge(element, 1, Integer::sum);
        }
        return frequencies;
    }

    /**
     * Randomly choose an item from a list using generics.
     *
     * @param array the list to choose from.
     * @param <T>   any type.
     * @return a random element from the list.
     */
    public static <T> T randomChoice(List<@NotEmpty T> array) {
        int randomIndex = randomInt(0, array.size());
        return array.get(randomIndex);
    }

    /**
     * Randomly choose an item from an array using generics.
     *
     * @param array the array to choose from.
     * @param <T>   any type.
     * @return a random element from the array.
     */
    public static <T> T randomChoice(@NotEmpty T[] array) {
        int randomIndex = randomInt(0, array.length);
        return array[randomIndex];
    }

    /**
     * Return a random integer in range.
     *
     * @param min lower bound.
     * @param max upper bound.
     * @return a random integer.
     */
    public static int randomInt(int min, int max) {
        if (min == max)
            return min;
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * Return a random double in range.
     *
     * @param min lower bound.
     * @param max upper bound.
     * @return a random double.
     */
    public static double randomDouble(double min, double max) {
        if (min == max)
            return min;
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    /**
     * Generate a random date between two dates.
     *
     * @param start the lower bound for the date.
     * @param end   the upper bound for the date.
     * @return a random date within the bounds.
     */
    public static LocalDate randomDate(LocalDate start, LocalDate end) {
        long randomEpochDay = ThreadLocalRandom.current().nextLong(start.toEpochDay(), end.toEpochDay());
        return LocalDate.ofEpochDay(randomEpochDay);
    }

    /**
     * Calculate the number of years between two dates.
     *
     * @param start first date.
     * @param end   second date.
     * @return the number of years between {@code start} {@code end}
     */
    public static int yearsBetween(LocalDate start, LocalDate end) {
        int diff = start.getYear() - end.getYear();
        int startMonth = start.getMonth().getValue();
        int endMonth = end.getMonth().getValue();
        if (startMonth > endMonth || (startMonth == endMonth && start.getDayOfMonth() > end.getDayOfMonth())) {
            diff--;
        }
        return Math.abs(diff);
    }

    /**
     * Calculate the number of years between a date and today.
     *
     * @param date the lower bound for the date.
     * @return the number of years between {@code date} and today.
     */
    public static int yearsBetweenDateAndToday(LocalDate date) {
        return yearsBetween(date, LocalDate.now());
    }
}
