import java.util.Random;

/**
 * Represents the children of a family
 *
 * @author John Maxwell Gale Carnahan IV
 * @version 1.0
 */
public class Family {
    private int boys, girls;
    private Random rand = new Random();
    private static final double BOY_CHANCE = .5;
    private String orderString;

    /**
     * Constructor for the Family class. It creates a new family
     * with no children.
     */
    public Family() {
        boys = 0;
        girls = 0;
        orderString = "";
    }

    /**
     * Adds a child to the Family, the sex is determined randomly
     */
    public void newChild() {
        if (rand.nextDouble() > BOY_CHANCE) {
            boys++;
            orderString += "B";
        } else {
            girls++;
            orderString += "G";
        }
    }

    /**
     * Returns a string showing the order of births in the family
     *
     * @return String showing birth order, like "BBGBG"
     */
    public String toString() {
        return orderString;
    }

    /**
     * Returns the number of boys in the family
     *
     * @return int showing number of boys
     */
    public int numBoys() {
        return boys;
    }

    /**
     * Returns the number of girls in the family
     *
     * @return int showing number of girls
     */
    public int numGirls() {
        return girls;
    }
}