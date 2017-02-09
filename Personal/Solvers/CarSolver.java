import java.util.*;
public class CarSolver {
    final static int NUM_CARS = 10;
    final static int NUM_SIM = 10000;
    public static void main(String[] args) {
        ArrayList<Double> groupList;
        int groupSum = 0;
        //Fairly obvious sim loop to find a solid mean
        for (int i = 0; i < NUM_SIM; i++) {
            //clears the old list for a new one
            groupList = generateSpeedList();
            //calls the important method to sort the cars into groups
            groupList = findGroups(groupList);
            groupSum += groupList.size();
        }
        System.out.println("The average number of groups is: " +
            (double)groupSum/NUM_SIM);
    }
    /**
     *generates NUM_CARS number of cars with varying speeds
     *
     *@return ArrayList<Double>
     **/
    private static ArrayList<Double> generateSpeedList() {
        ArrayList<Double> groupList = new ArrayList<Double>(NUM_CARS);
        Random rand = new Random();
        for (int i = 0; i < NUM_CARS; i++) {
            groupList.add(i, rand.nextDouble());
        }
        return groupList;
    }

    /**
     *Assigns the lowest value in a group to the group in the list
     *Then sorts again recursively until the groups are stable stated
     *
     *@return ArrayList<Double>
     **/
    private static ArrayList<Double> findGroups(ArrayList<Double> groupList) { 
        if (isSorted(groupList)) {
            //do nothing, allow the recursion to fall out and return final List
            return groupList;
        }
        //iterate through group list and merge groups
        ArrayList<Double> localGroupList = new ArrayList<Double>();
        int curIndex = 0;
        localGroupList.add(groupList.get(0));
        for (int i = 0; i < groupList.size() - 1; i++) {
            if (groupList.get(i) > groupList.get(i+1)) {
                localGroupList.set(curIndex, groupList.get(i+1));
            } else {
                curIndex++;
                localGroupList.add(groupList.get(i+1));
            }
        }
        groupList = findGroups(localGroupList);
        return groupList;
    }

    /**
     *If the fastest cars are prefectly ordered in front of
     *the slower cars, returns true
     *
     *@return boolean
     **/
    private static boolean isSorted(ArrayList<Double> groupList) {
        for (int i = 0; i < groupList.size() - 1; i++) {
            if (groupList.get(i) > groupList.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
    //simple debugging print mechanism
    private static void printList(ArrayList<Double> groupList) {
        System.out.print("[ ");
        for (int i = 0; i < groupList.size(); i++) {
            System.out.printf(" %.3f, ",groupList.get(i));
        }
        System.out.print("]\n");
    }
}