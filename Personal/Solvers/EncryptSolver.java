/**
 * Decodes a phrase by randomly guessing phrases until one
 * produces results matching the English Dictionary
 *
 * @Author John Maxwell Gale Carnahan IV
 * @version 1.0
 */
import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;
import java.util.Arrays;

public class EncryptSolver {

    private static String encryptString;
    private static WordDictionary dict;
    private static HashMap<Character, Character> encryptMap;
    private static final double PERCENT_VALID = .5;
    private static long totalTime, genTime = 0, gibTime = 0, decryptTime = 0;

    public static void main(String[] args) {
        String userIn = "g";
        Scanner scan = new Scanner(System.in);
        while (userIn.equals("g")) {
            long startTime = System.nanoTime();
            dict = new WordDictionary(args[0]);
            encryptString =
                "A zsnw kmuuwkkxmddq kgdnwv lzw XanwLzajlqWayzl Javvdwj";
            encryptString = encryptString.toLowerCase();
            encryptMap = new HashMap<Character, Character>();
            genRandMap();
            int count = 0;
            while (isGibberish()) {
                genRandMap();
                count++;
                totalTime = System.nanoTime() - startTime;
                if((count % 100) == 0) {
                    //System.out.printf("Gen: %f.3 Gib: %f.3 Dec: %f.3 \n",
                     //   (double) genTime / totalTime, (double) gibTime / totalTime, 
                       // (double) decryptTime / totalTime);
                }
            }
            System.out.println(decrypt(encryptString));
            System.out.println("Makes sense?");
            userIn = scan.nextLine();
        }
    }

    /**
     * Random generates a mapping of letters to letters, and
     * sets the instanced encryptMap to this new map. It has to
     * work eventually?
     *
     */
    private static void genRandMap() {
        long startTime = System.nanoTime();
        final int numLetters = 26;
        Random rand = new Random();
        char[] alphaArr = new char[numLetters];
        char[] randArr = new char[numLetters];
        for (int i = 0; i < numLetters; i++) {
            alphaArr[i] = (char) (i + 'a');
        }
        randArr = Arrays.copyOf(alphaArr, alphaArr.length);
        int switchIndex;
        char curChar;
        for (int i = 0; i < numLetters; i++) {
            curChar = randArr[i];
            switchIndex = rand.nextInt(numLetters);
            randArr[i] = randArr[switchIndex];
            randArr[switchIndex] = curChar;
        }
        encryptMap.clear();
        for (int i = 0; i < numLetters; i++) {
            encryptMap.put(alphaArr[i], randArr[i]);
        }
        genTime += System.nanoTime() - startTime;
    }

    /**
     * Checks each decrypted word to see if
     * the number of English words exceeds the number of
     * gibberish/name words.
     *
     * @return boolean stating whether the String needs human input
     */
    private static boolean isGibberish() {
        int numEngWords;
        String[] wordArr = encryptString.split("\\s");
        numEngWords = 0;
        for (String s : wordArr) {
            long startTime = System.nanoTime();
            if (dict.hasWord(decrypt(s))) {
                numEngWords++;
            }
            gibTime += System.nanoTime() - startTime;
        }
        double wordRatio = (double) numEngWords / wordArr.length;
        return wordRatio <= PERCENT_VALID;
    }

    /**
     * Uses the HashMap as a key to decrypt the
     * encrypted String
     *
     * @param puzStr String to be decrypted
     * @return String that is decrypted
     */
    private static String decrypt(String puzStr) {
        long startTime = System.nanoTime();
        char[] strArr = puzStr.toCharArray();
        for (int i = 0; i < strArr.length; i++) {
            //System.out.print(c);
            strArr[i] = encryptMap.get(strArr[i]);
        }
        decryptTime += System.nanoTime() - startTime;
        return new String(strArr);
    }


}