import java.util.*;
import java.io.*;

public class WordDictionary extends ArrayList<String>{

    private String dictFileName;
    private HashMap<Character, Integer> indexValues = new HashMap<Character, Integer>();
    private static final int NUM_LETTERS = 26;
    
    /**
     * Constructor for a WordDictionary, an advanced arraylist for a dictionary.
     *
     * @param fN name of file containing dictionary words.
     */
    public WordDictionary(String fN) {
        this.dictFileName = fN;
        try{
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new FileReader(dictFileName));
            String curLine;
            while ( (curLine = bufferedReader.readLine()) != null) {
                add(curLine);
            }
        } catch(Exception ex){
        }

        findIndexVals();
    }

    /**
     * Effeciently finds if the dictionary possesses a word.
     *
     * @param w The String to be found
     * @return boolean stating if the WordDictionary has the word
     */
    public boolean hasWord(String w) {
        String word = w.toLowerCase();
        int startIndex = indexValues.get(word.charAt(0));
        int endIndex;
        if(word.charAt(0) != 'z'){
            endIndex = indexValues.get((char) (word.charAt(0) + 1));
        } else {
            endIndex = size();
        }
        for (int i = startIndex; i < endIndex; i++) {
            if (get(i).equals(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the first index of every letter.
     *
     * TODO: Implement binary search
     */
    private void findIndexVals() {
        int index = 0;
        for (int i = 0; i < NUM_LETTERS; i++) {
            for(int j = 0; j < size(); j++) {
                if(get(j).charAt(0) == (char)('a' + i)) {
                    index = j;
                    break;
                }
            }
            indexValues.put((char)('a' + i), index);
        }
    }

    /**
     * Returns the first index of the given character. Essentially
     * just a getter for HashMap<Character, Integer> indexValues
     *
     * @param inChar character to find first index of
     * @return int representing first index of the character
     */
    public int firstIndex(char inChar) {
        return indexValues.get(inChar);
    }

}