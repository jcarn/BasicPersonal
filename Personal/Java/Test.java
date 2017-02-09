import java.util.*;
public class Test {

    private static final int NUM_SIM = 10;
    private static final int NUM_WORDS = 10000;
    private ArrayList<String> sortable;
	public static void main(String[] args) {
        long startTime, totalTime;
        String randString;
        Random rand;
        int letterStart;
        startTime = System.nanoTime();
        sortable = new ArrayList<String>();
        for (int i = 0; i < NUM_SIM; i++) {
            for (int j = 0; j < NUM_WORDS; j++) {
                randString = Long.toHexString(Double.doubleToLongBits(Math.random()));
                sortable.add(randString);
            }
            Collections.sort(sortable);
            Random rand = new Random();
            randString = sortable.get(rand.nextInt(NUM_WORDS));
            letterStart = getFirstIndex(randString);
            sortable.clear();
        }
        totalTime += System.nanoTime - startTime;
        totalTime /= 1000000000;
        System.printf("This took: %d.4 seconds.",totalTime);
	}

    private static int getFirstIndex(String s) {
        char firstChar = s.charAt(0);
        return 1;
    }
}