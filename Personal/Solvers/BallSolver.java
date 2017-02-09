import java.util.Random;
public class BallSolver {
    public static void main(String[] args) {
        int finalShot = 100;
        double curProb,curMade;
        int numSim = 100000;
        int actualSim = numSim;
        Random rand = new Random();
        double probSum = 0;
        boolean isCounted = true;

        for (int i = 0; i < numSim; i++) {
            curMade = 1;
            curProb = .5;
            for (int curShot = 3; curShot < finalShot - 1; curShot++) {
                if (rand.nextDouble() < curProb) {
                    curProb = (double) ++curMade / curShot;
                    if (curShot == finalShot - 2) {
                        isCounted = true;
                    }
                } else {
                    curProb = (double) curMade / curShot;
                    if (curShot == finalShot - 2) {
                        isCounted = false;
                    }
                }
            }
            if (isCounted) {
                probSum += curProb;
            } else {
                actualSim--;
            }
        }
        System.out.println("The probability of making the last shot is: "
            + (probSum / actualSim));
    }
}