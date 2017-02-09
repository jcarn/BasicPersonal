import java.util.Scanner;

/**
 * Simulates a number of familes and prints information about
 * the genders of their children. (average,max to reach 1 of each
 * gender. total number of kids born)
 * @author John Gale Maxwell Carnahan IV
 * @version 0.9.1
 */
public class FamilySimulator {
    private static int totalBoys, totalGirls, maxKids;

    /**
     * Standard driver method. Loops while the user wants to keep
     * simulating families.
     *
     * @param args empty
     */
    public static void main(String[] args) {
        int numFams;
        Scanner scanFam, scanRun;
        double average;

        scanFam = new Scanner(System.in);
        scanRun = new Scanner(System.in);

        boolean run = true;
        while (run) {
            //get number of scan
            System.out.println("ady to Simulate. "
                + "Enter the number of families:");
            numFams = scanFam.nextInt();

            //loop for n times, and print accordingly
            totalBoys = 0;
            totalGirls = 0;
            maxKids = 0;
            simFams(numFams);

            //print average, and max, and total
            average =  (double) (totalBoys + totalGirls) / numFams;
            System.out.printf("The average number of children was %.2f "
                + "and the maximum was %d. \n", average, maxKids);
            System.out.printf("A total of %d boys and %d girls were born.\n",
                totalBoys, totalGirls);
            System.out.println("Would you like to run again? (Y/N)");
            run = scanRun.nextLine().equals("Y");
        }
    }

    /**
     * Simulates @param number of families
     *
     * @param numSim number of families to simulate
     */
    private static void simFams(int numSim) {
        Family fam;
        for (int i = 0; i < numSim; i++) {
            fam = new Family();
            while (fam.numBoys() < 1 || fam.numGirls() < 1) {
                fam.newChild();
            }

            totalBoys += fam.numBoys();
            totalGirls += fam.numGirls();
            if ((fam.numBoys() + fam.numGirls()) > maxKids) {
                maxKids = fam.numBoys() + fam.numGirls();
            }
            System.out.println("" + (i + 1) + " - " + fam);
        }
    }
}