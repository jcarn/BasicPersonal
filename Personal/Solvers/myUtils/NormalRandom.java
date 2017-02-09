package myUtils;

import java.util.Random;

/**
 * This class provides some more functionality and customability for nomralized
 * random values. If you only want normal values with mu's of 0.0 and simgas of
 * 1.0 then use the super method of nextGaussian()
 *
 * @author John Maxwell Gale Carnahan IV
 * @version 0.9.0
 */
public class NormalRandom extends Random {
    public NormalRandom() {
        super();
    }

    /**
     * Takes the given mu and sigma and returns a random double from
     * normal distribution with the given mu and sigma.
     *
     * @param mu where the distribution is centered
     * @param sigma Standard Deviation of the distribution
     * @return a random double from a normal distribution with the given mu and sigma
     */
    public double nextGaussian(double mu, double sigma) {
        //this generates a normal double from mu of 0.0 and sigma of 1.0
        double normalDouble = super.nextGaussian();

        //this is a simple transformation of a standard normal distribution
        //read more at http://davidmlane.com/hyperstat/A75494.html
        return normalDouble * sigma + mu;
    }

    /**
     * Takes in only a single value of mu or sigma and returns a normal
     * double based off that value, using the default for the one not given.
     * NOTE: mu defaults to 0.0, sigma defaults to 1.0
     *
     * @param muSigma value that is either mu, where the distribution
     * is centered, or sigma, the standard deviation of the distribution
     * @param isMu stating whether the first paramter is mu(true) or sigma(false)
     * @return a random double from a normal distribution with the given mu or sigma
     */
    public double nextGaussian(double muSigma, boolean isMu) {
        if (isMu) {
            return nextGaussian(muSigma, 1.0);
        } else {
            return nextGaussian(0.0, muSigma);
        }
    }

    /**
     * Gives a normalized random double using the default values of mu (.0.0)
     * and sigma (1.0). It is likely quicker to call the nextGaussian() method
     * from the super Random class instead of this one.
     *
     * @return a random double from a normal distribution
     */
    public double nextGaussian() {
        return nextGaussian(0.0, 1.0);
    }

}