/**
 * GameshowSolver simulates the riddle put forth in FivethirtyEight's 
 * "The Riddler" of 4.3.16. Found here: 
 * http://fivethirtyeight.com/features/can-you-win-this-hot-new-game-show/
 * It runs through a range of values and returns which value produces the most
 * favorable results, assuming the oppenent uses a simple strategy of trading
 * out if her number is less than half the max value.
 * 
 * @author John Maxwell Gale Carnahan IV
 * @version 0.9.0
 */
import java.util.Random;

public class GameshowSolver {
    private static final int NUM_SIM = 10000000;
    private static final double DELTA = 0.001;

    private static final double MIN_VAL = 0.0;
    private static final double MAX_VAL = 1.0;
    private static final double OPP_THRESHOLD = (MAX_VAL - MIN_VAL) / 2;

    /**
     * Performs the main loop of simlulations in addition to printing 
     * out the best number
     *
     * @param args empty - no command line input
     */
    public static void main(String[] args) {
        double curThreshold = .55;//MIN_VAL;
        double bestThreshold = curThreshold;
        int curMaxVictories = 0;
        int numVictories = 0;

        while (curThreshold < .61) {//MAX_VAL) {
            for (int i = 0; i < NUM_SIM; i++) {
                if (hostShow(curThreshold)) {
                    numVictories++;
                }
            }
            if (numVictories > curMaxVictories) {
                curMaxVictories = numVictories;
                bestThreshold = curThreshold;
            }
            numVictories = 0;
            curThreshold += DELTA;
        }
         double bestPercent = 100.0 * curMaxVictories / NUM_SIM;
         System.out.printf("The best threshold was: %.3f, which won %.3f%% of the time",
            bestThreshold, bestPercent);
    }

    /**
     * Hosts a single show, where we (the 1st contestant) get a new number
     * at the inputed threshold. The opponent uses the final constant value.
     * If the 1st contestant wins, then we return true. Otherwise, false.
     *
     * @param threshold the threshold below which we get a new number
     * @return boolean stating whether or not we won
     */
    private static boolean hostShow(double threshold) {
        Random rand = new Random();
        double ourNumber, oppNumber;
        //returns a double in the range of MIN_VAL to MAX_VAL
        ourNumber = (MAX_VAL - MIN_VAL) * rand.nextDouble() + MIN_VAL;
        oppNumber = (MAX_VAL - MIN_VAL) * rand.nextDouble() + MIN_VAL;

        if (ourNumber < threshold) {
            ourNumber = (MAX_VAL - MIN_VAL) * rand.nextDouble() + MIN_VAL;
        }
        if (oppNumber < OPP_THRESHOLD) {
            oppNumber = (MAX_VAL - MIN_VAL) * rand.nextDouble() + MIN_VAL;
        }

        if (ourNumber > oppNumber) {
            return true;
        } else {
            return false;
        }
    }

}