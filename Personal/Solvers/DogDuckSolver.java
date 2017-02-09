import java.util.*;
public class DogDuckSolver() {
    private final int NUM_SIM = 10000;
    private final double RADIUS = 1;
    private final double DOG_OMEGA = Math.pi;
    private final double DUCK_V = RADIUS;
    private double duckVTheta;
    private PolarCoord duckCoord;
    private final double TICK_PER_SEC = 1000;

    public static void main(String[] args) {

    }

}

class PolarCoord() {
        public double rVal,theta;
        public PolarCoord(double r, double theta) {
            this.rVal = r;
            this.theta = theta;
    }
}
