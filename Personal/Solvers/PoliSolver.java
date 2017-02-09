import java.util.Random;
public class PoliSolver {
    public static void main(String[] args) {
        //initialize values
        int numSim = 10000;
        int numPolis = 5;
        int numVotes = 10;//sum of 1 to numPolis-1
        int[][] prefArr = getPrefs(numPolis);
        boolean[][] curVoteArr = getInitVoteArr(numPolis, numVotes);
        int curWinner = 0;
        int lastWinner = 1;
        int[] winArr = {0, 0, 0, 0, 0};

        for (int i=0; i < numSim; i++) {
            //find winner
            lastWinner = curWinner;
            curWinner = findFinalWinner(curVoteArr, numPolis);
            winArr[curWinner]++;
            //adjust
            for (int j = 0; j < numPolis; j++) {
                if (!isFavorable(j, curWinner, lastWinner, prefArr)) {
                    curVoteArr[j] = getNewVoteArr(numVotes);
                }
            }
        }
        System.out.println("A Wins:" + winArr[0]
            + "\nB Wins: " + winArr[1]
            + "\nC Wins: " + winArr[2]
            + "\nD Wins: " + winArr[3]
            + "\nE Wins: " + winArr[4]);
    }

//supply initial preferences
private static int[][] getPrefs(int numPolis) {
     int[][] retArr = {
        {0, 1, 2, 3, 4},
        {1, 0, 4, 3, 2},
        {2, 4, 0, 1, 3},
        {2, 1, 4, 0, 4},
        {4, 2, 3, 1, 0}
    };
    return retArr;
}

    //generate an initial voting array
    private static boolean[][] getInitVoteArr(int numPolis, int numVotes) {
        boolean[][] retArr = new boolean[numPolis][numVotes];
        for (int i = 0; i < numPolis; i++) {
            retArr[i] = getNewVoteArr(numVotes);
        }
        return retArr;
    }

    //A note on the format: AB0,AC1,AD2,AE3,BC4,BD5,BE6,CD7,CE8,DE9
    private static  boolean[] getNewVoteArr(int numVotes) {
        boolean[] retArr = new boolean[numVotes];
        Random rand = new Random();
        for (int i = 0; i < numVotes; i++) {
            retArr[i] = rand.nextBoolean();
        }
        return retArr;
    }

    //decides if the outcome was favorable to the politician
    private static boolean isFavorable(int curPoli, int curWinner,
        int lastWinner, int[][] prefArr) {
        if (prefArr[curPoli][curWinner] >= prefArr[curPoli][lastWinner] &&
            prefArr[curPoli][curWinner] > 2) {
            return false;
        }
        return true;
    }

    //simulates the entire election
    private static int findFinalWinner(boolean[][] curVoteArr, int numPolis) {
        MatchInfo curMatch = new MatchInfo(0, 0, 0, 1);//AB match starts
        while (curMatch.roundNum < numPolis - 1) {
            curMatch.incumbent = curMatch.roundNum + 1; //the incumbent will always be the roundNum + 1, round 0 is B, 1 is C, etc.
            curMatch.matchNum = getMatch(curMatch);
            curMatch.challenger = findMatchWinner(curMatch,curVoteArr);
            curMatch.roundNum++;   
        }
        return curMatch.challenger;
    }

    private static int findMatchWinner(MatchInfo curMatch, boolean[][] curVoteArr) {
        int challengerVote = 0;
        for (int i = 0; i < curVoteArr.length; i++) {
            if(curVoteArr[i][curMatch.matchNum]) {
                challengerVote++;
            }
        }
        if (challengerVote >=3) {
            return curMatch.challenger;
        }
        return curMatch.incumbent;
    }

    private static int getMatch(MatchInfo curMatch) {
        switch (curMatch.challenger) {
            case 0: switch (curMatch.incumbent) {
                case 1: return 0;
                case 2: return 1;
                case 3: return 2;
                case 4: return 3;
            }
            break;
            case 1: switch (curMatch.incumbent) {
                case 2: return 4;
                case 3: return 5;
                case 4: return 6;
            }
            case 2: switch (curMatch.incumbent) {
                case 3: return 7;
                case 4: return 8;
            }
            break;
            case 3: return 9;
            default: return 0;
            }
        return 0;
    }
}


//this allows for all necesarry information about a match to be passed between  methods
class MatchInfo {
    public int roundNum, matchNum, challenger, incumbent;
    public MatchInfo(int initRoundNum,int initMatchNum, int initChallenger, int initIncumbent) {
    }
}