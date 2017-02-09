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
    private static int numSim;
    private static final double DELTA = 0.001;

    private static final double MIN_VAL = 0.0;
    private static final double MAX_VAL = 1.0;

    private static final double MIN_THRESH = .5;
    private static final double MAX_THRESH = .8;
    private static double curThreshold, curOppThreshold;

    /**
     * Performs the main loop of simlulations in addition to printing 
     * out the best number
     *
     * @param args empty - no command line input
     */
    public static void main(String[] args) {
        try {
            numSim = Integer.parseInt(args[0]);
        } catch(Exception ex) {
            numSim = 100;
        }
            
        curThreshold = MIN_VAL;
        curOppThreshold = MIN_VAL;

        for (int i = 0; i < numSim/5; i++) {
            curThreshold = optimize(true);
            curOppThreshold = optimize(false);
        }

        System.out.printf("The best threshold is: %.3f", curThreshold);
    }

    private static double optimize(boolean notOpp) {
        double optThresh, staticThresh, bestThresh;
        optThresh = MIN_THRESH;
        if (notOpp) {
            staticThresh = curOppThreshold;
        } else {
            staticThresh = curThreshold;
        }
        bestThresh = optThresh;

        int numWins = 0;
        int maxWins = 0;
        int decTrack = 0;
        while (optThresh < MAX_THRESH) {
            for (int i = 0; i < numSim; i++) {
                if (hostShow(optThresh, staticThresh)) {
                    numWins++;
                }
            }

            if (numWins > maxWins) {
                maxWins = numWins;
                bestThresh = optThresh;
            }
            optThresh += DELTA;

            numWins = 0;
        }
        return bestThresh;
    }

    /**
     * Hosts a single show, where we (the 1st contestant) get a new number
     * at the inputed threshold for each contestent.
     * If the 1st (us) contestant wins, then we return true. Otherwise, false.
     *
     * @param threshold the threshold below which we get a new number
     * @return boolean stating whether or not we won
     */
    private static boolean hostShow(double threshold, double oppThreshold) {
        Random rand = new Random();
        double ourNumber, oppNumber;
        //returns a double in the range of MIN_VAL to MAX_VAL
        ourNumber = (MAX_VAL - MIN_VAL) * rand.nextDouble() + MIN_VAL;
        oppNumber = (MAX_VAL - MIN_VAL) * rand.nextDouble() + MIN_VAL;

        if (ourNumber < threshold) {
            ourNumber = (MAX_VAL - MIN_VAL) * rand.nextDouble() + MIN_VAL;
        }
        if (oppNumber < oppThreshold) {
            oppNumber = (MAX_VAL - MIN_VAL) * rand.nextDouble() + MIN_VAL;
        }

        if (ourNumber > oppNumber) {
            return true;
        } else {
            return false;
        }
    }

}