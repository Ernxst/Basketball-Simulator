package com.example.app.util;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class MathsUtil {
    /**
     * Return an integer, according to a gaussian function.
     *
     * @param peak         the maximum value.
     * @param value        input to gaussian function.
     * @param mean         mean.
     * @param stdDeviation standard deviation.
     * @return a random integer.
     */
    public static int gaussianInt(int peak, int value, int mean, double stdDeviation) {
        return (int) gaussianDouble(peak, value, mean, stdDeviation);
    }

    /**
     * Return an double, according to a gaussian function.
     *
     * @param peak         the maximum value.
     * @param value        input to gaussian function.
     * @param mean         mean.
     * @param stdDeviation standard deviation.
     * @return a random double.
     */
    public static double gaussianDouble(double peak, double value, double mean, double stdDeviation) {
        double numerator = Math.pow((value - mean), 2);
        double denominator = 2 * Math.pow(stdDeviation, 2);
        double exponent = -(numerator / denominator);
        return peak * Math.exp(exponent);
    }

    /**
     * Scale a value from the range ({@code oldMin}, {@code oldMax}) into the range ({@code newMin}, {@code newMax}).
     *
     * @param oldMax   the old maximum value.
     * @param oldMin   the old minimum value.
     * @param newMax   the new maximum value.
     * @param newMin   the new minimum value.
     * @param oldValue the value to scale.
     * @return the scaled value.
     */
    public static int scale(int oldMax, int oldMin, int newMax, int newMin, int oldValue) {
        int oldRange = (oldMax - oldMin);
        int newRange = (newMax - newMin);
        return (((oldValue - oldMin) * newRange) / oldRange) + newMin;
    }

    public static int[] cmToFeetAndInches(double cm) {
        int feet = (int) Math.floor(cm / 30.48);
        int inches = (int) Math.round((cm / 2.54) - (feet * 12));
        if (inches == 12) {
            inches = 0;
            feet++;
        }
        return new int[]{feet, inches};
    }

    /**
     * Return a random value according to a normal distribution.
     *
     * @param min          the minimum overall allowed.
     * @param max          the maximum overall allowed.
     * @param mean         the average overall.
     * @param stdDeviation the standard deviation of the normal distribution.
     * @return a random value according to a normal distribution, between {@code min} and {@code max}.
     */
    public static int randomNormalDistributionInRange(int min, int max, double mean, double stdDeviation) {
        int overall = (int) normalDistribution(mean, stdDeviation);
        return Math.max(Math.min(overall, max), min);
    }

    /**
     * Generate a random value according to a normal distribution.
     *
     * @param mean         the average of the distribution
     * @param stdDeviation the standard deviation of the distribution.
     * @return a random value.
     */
    public static double normalDistribution(double mean, double stdDeviation) {
        return ThreadLocalRandom.current().nextGaussian() * stdDeviation + mean;
    }

    /**
     * Return a random value according to a normal distribution.
     *
     * @param min          the minimum value allowed.
     * @param max          the maximum value allowed.
     * @param mean         the average value.
     * @param stdDeviation the standard deviation of the normal distribution.
     * @return a random value according to a normal distribution, between {@code min} and {@code max}.
     */
    public static double randomNormalDistributionInRange(double min, double max, double mean, double stdDeviation) {
        double value = normalDistribution(mean, stdDeviation);
        return Math.max(Math.min(value, max), min);
    }

    /**
     * Calculates the weighted average of a map.
     *
     * @param sample A map of values and weights.
     * @return the weighted average of the map.
     * @throws ArithmeticException if a divide by zero exception occurs.
     */
    public static double weightedAverage(Map<Double, Double> sample) throws ArithmeticException {
        double numerator = 0;
        double denominator = 0;
        for (Map.Entry<Double, Double> entry : sample.entrySet()) {
            numerator += entry.getKey() * entry.getValue();
            denominator += entry.getValue();
        }
        return numerator / denominator;
    }
}
