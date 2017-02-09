import java.util.*;

public class GeyserSolver {
    //sets the interval of each geyser in hours, then converts to seconds
    private static final int A_INTERVAL = 2*3600;
    private static final int B_INTERVAL = 4*3600;
    private static final int C_INTERVAL = 6*3600;
    private static final double NUM_SIM = 10000000;

    public static void main(String[] args) {
        //gives a start time, general initialization
        int aStart, bStart, cStart, arrivalTime;
        double aDivis, bDivis, cDivis;
        Random rand = new Random();
        int aCount = 0, bCount = 0, cCount = 0;
        int nextErupt;

        for (int i = 0; i < NUM_SIM; i++) {
            aStart = rand.nextInt(1000000);
            bStart = rand.nextInt(1000000);
            cStart = rand.nextInt(1000000);
            arrivalTime = rand.nextInt(100000000) + 1000000;

            aDivis = (arrivalTime - aStart) / (double)A_INTERVAL;
            bDivis = (arrivalTime - bStart) / (double)B_INTERVAL;
            cDivis = (arrivalTime - cStart) / (double)C_INTERVAL;

            nextErupt = findNextEruption(aDivis, bDivis, cDivis);
            switch (nextErupt) {
                case 0: aCount++;
                break;
                case 1: bCount++;
                break;
                case 2: cCount++;
                break;
                default:
            }
        }
        System.out.printf("A: %.4f B: %.4f C: %.4f",
            aCount / NUM_SIM * 100,
            bCount / NUM_SIM * 100,
            cCount / NUM_SIM * 100);
    }

    private static int findNextEruption(double aDivis, double bDivis, double cDivis) {
        double aDiff = (aDivis - Math.floor(aDivis)) * A_INTERVAL;
        double bDiff = (bDivis - Math.floor(bDivis)) * B_INTERVAL;
        double cDiff = (cDivis - Math.floor(cDivis)) * C_INTERVAL;

        if (aDiff < bDiff && aDiff < cDiff) {
            return 0;
        }else if (bDiff < cDiff) {
            return 1;
        }
        return 2;
    }
}