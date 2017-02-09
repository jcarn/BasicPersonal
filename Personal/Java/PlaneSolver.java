import java.util.Random;
import java.util.stream.IntStream;
import java.util.Arrays;
public class PlaneSolver {
    private static final int NUM_PASSENGERS = 100;
    private static final int NUM_SIM = 100000;
    private static final int BOARD_TIME = 1;

    public static void main(String[] args) {
        int[] assignedArr;
        boolean[] seatArr;
        int randSeat, elapsedTime = 0, numSuccess = 0;
        Random rand = new Random();
        for (int s = 0; s < NUM_SIM; s++) {
            assignedArr = createAssignedArr();
            seatArr = new boolean[NUM_PASSENGERS];
            Arrays.fill(seatArr, false);

            seatArr[rand.nextInt(NUM_PASSENGERS)] = true;
            elapsedTime += BOARD_TIME;
            randSeat = rand.nextInt(NUM_PASSENGERS);
            for (int i = 1; i < NUM_PASSENGERS - 1; i++) {
                if (seatArr[assignedArr[i]]) {
                    elapsedTime += BOARD_TIME;
                    while (seatArr[randSeat]) {
                        randSeat = rand.nextInt(NUM_PASSENGERS);
                        elapsedTime += BOARD_TIME;
                    }
                    seatArr[randSeat] = true;
                } else {
                    elapsedTime += BOARD_TIME;
                    seatArr[assignedArr[i]] = true;
                }
            }
            if(!seatArr[assignedArr[NUM_PASSENGERS - 1]]) {
                numSuccess++;
            }
        }
        System.out.printf("You get your seat: %.2f%s of the time.",
            (double) numSuccess / NUM_SIM * 100, "%");
        System.out.printf("\n Average Elapsed Time: %.2f minutes",
            (double) elapsedTime / NUM_SIM);
    }

    /*
     * Creates an array of where eached passenger is assigned.
     *
     * @return int[] where each value represents the assigned seat
     */
    private static int[] createAssignedArr(){
        //creates array of sequential values
        int[] outArr = IntStream.range(0,NUM_PASSENGERS).toArray();
        //shuffle array
        int switchIndex, tempInt;
        int numShuffles = 1;
        Random rand = new Random();
        for (int s = 0; s < numShuffles; s++) {
            for (int i = 0; i < outArr.length; i++) {
                switchIndex = rand.nextInt(NUM_PASSENGERS);
                tempInt = outArr[i];
                outArr[i] = outArr[switchIndex];
                outArr[switchIndex] = tempInt;
            }
        }
        return outArr;
    }

}