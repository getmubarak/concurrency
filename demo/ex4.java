
/*
The variance is used in statistics to measure how far a set of numbers is spread out. It can be calculated by averaging the squared difference from the mean of the set of numbers. For example, given the numbers 40, 30, 50 and 80 representing the ages of a population, we can calculate the variance by:
calculating the mean: (40 + 30 + 50 + 80) / 4 = 50
taking the square difference from the mean of the set of numbers: (40-50)2 + (30-50)2 + (50-50)2 + (80-50)2 = 1400
finally averaging it: 1400/4 = 350
*/

import java.util.Random;
import java.util.stream.DoubleStream;


public class Main {

    private static final Random rand = new Random();
    private static final int MIN = 1;
    private static final int MAX = 140;
    private static final int POPULATION_SIZE = 30_000_000;
    public static final int NUMBER_OF_RUNS = 20;

    public static void main(String... args) {
    	// generate a population with different ages
        double[] population = DoubleStream.generate(Main::randInt).limit(POPULATION_SIZE).toArray();

        long start = System.currentTimeMillis();
        double variance= varianceImperative(population);
        System.out.println("variance : " + variance);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
      }

    public static int randInt() {
        return rand.nextInt((MAX - MIN) + 1) + MIN;
    }

    public static double varianceImperative(double[] population){
        double average = 0.0;
        for(double p: population){
            average += p;
        }
        average /= population.length;

        double variance = 0.0;
        for(double p: population){
            variance += (p - average) * (p - average);
        }
        return variance / population.length;
    }

  
}
