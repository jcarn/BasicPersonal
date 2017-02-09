import myUtils.*;

public class FreethrowSolver {

    private static int numSim;
    public static final double DELTA = .0001;
    public static final double THROW_AVERAGE = .75;
    public static final double HOOP_RADIUS = 1.0;
    public static final double MU = 0.0;
    public static final double EPSILON = .0001;

    public static void main(String[] args) {
        try {
            numSim = Integer.parseInt(args[0]);
        } catch (Exception ex) {
            numSim = 10000;
        }

        //find a sigma such that the the 2D variant freethrows score 75%
        NormalRandom rand = new NormalRandom();
        double sigma = .6000;
        double hitPercent = 0;
        int numScores = 0;
        // Uncomment to have the the computer find your sigma for you
        // simga = 0.0
        // while (Math.abs(THROW_AVERAGE - (double)numScores / numSim) > EPSILON){
        //     numScores = 0;
        //     for (int i = 0; i < numSim; i++) {
        //         if (scores(rand.nextGaussian(MU, sigma), rand.nextGaussian(MU, sigma))) {
        //             numScores++;
        //         }
        //     }
        //     sigma += DELTA;
        // }

        System.out.printf("Sigma: %.4f \n", sigma);
        numScores = 0;
        for (int i = 0; i < numSim; i++) {
            if (scores(0.0, rand.nextGaussian(MU, sigma))) {
                numScores++;
            }
        }

        System.out.printf("You score %.4f%% of the time!",
            (float)numScores * 100/ numSim);
    }

    private static boolean scores(double x, double y) {
        //System.out.printf("X: %.3f Y: %.3f", x,y);
        return (x * x + y * y) <= HOOP_RADIUS * HOOP_RADIUS;
    }
}