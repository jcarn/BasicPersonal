/**
 * Simulates the problem found here: 
 * http://fivethirtyeight.com/features/how-long-will-your-smartphone-distract-you-from-family-dinner/
 *
 * @author John Maxwell Gale Carnahan IV
 * @version 0.9.0.
 */

import java.util.Random;
import java.util.Scanner;

public class PhoneSolver {
    private static int numSim, maxTime;
    private static Random rand;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Number of Simulations: ");
        numSim = scan.nextInt();
        System.out.println("Maximum Time: ");
        maxTime = scan.nextInt();

        rand = new Random();
        long runTime = System.currentTimeMillis();
        int totalTime;
        totalTime = 0;
        for (int i = 0; i < numSim; i++) {
            totalTime += simulateDay();
        }

        double averageTime;
        averageTime = (double) totalTime / numSim;
        System.out.printf("Average Time: %.3f \n", averageTime);

        double runDuration; 
        runDuration = (double)(System.currentTimeMillis() - runTime) / 1000;
        System.out.printf("Runtime: %.4f", runDuration);
    }

    private static int simulateDay() {
        Random rand = new Random();
        int myTime, sisTime, curTime;
        myTime = rand.nextInt(maxTime) + 1;
        sisTime = rand.nextInt(maxTime) + 1;
        curTime = Math.max(myTime, sisTime);
        while (myTime != sisTime) {
            if (myTime < curTime) {
                myTime += rand.nextInt(maxTime) + 1;
            } else {
                sisTime += rand.nextInt(maxTime) + 1;
            }
            curTime = Math.max(myTime, sisTime);
        }
        return myTime;
    } 
}