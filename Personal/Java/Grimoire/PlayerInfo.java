package Grimoire;

import java.util.*;
import java.io.*;

public class PlayerInfo {
    protected int healthPoints, gold, armorClass, level, profieciency, hitDice;
    //There are 9 levels of spells. This array hold the max slots, and slots left
    protected int[][] spellSlotArr = new int[9][2];
    //Character attributes, Str, Dex, Con, Int, Wis, and Cha
    protected HashMap<String, Integer> attrMap = new HashMap<String, Integer>();
    /*A list of 0s (no Prof.) and 1s (Prof.) and the associated
     *attribute. Attributes follow above order.
     *
     *Proficiencies in order:
     *1. Acrobatics - Dex
     *2. Animal Handling - Wis
     *3. Arcana - Int
     *4. Athletics - Str
     *5. Deception - Cha
     *6. History - Int
     *7. Insight - Wis
     *8. Intimidation - Cha
     *9. Investigation - Int
     *10. Medicine - Wis
     *11. Nature - Int
     *12. Perception - Wis
     *13. Performance - Cha
     *14. Persuasion - Cha
     *15. Religion - Int
     *16. Sleight of Hand - Dex
     *17. Stealth - Dex
     *18. Survival - Wis
     */
    protected HashMap<String, Integer> proficiencyMap = new HashMap<String, Integer>();
    protected String playerName;
    //Class, character name, background, race, alignment
    protected HashMap<String, String> basicInfoMap = new HashMap<String, String>();
    //Age, height, weight, eyes, skin, hair
    protected HashMap<String, String> charInfoMap = new HashMap<String, String>();
    //List of thins like languages or weapon Proficiencies
    protected ArrayList<String> otherProfList;
    //List of all equiptment
    protected ArrayList<String> treasureList;
    //list of features and traits
    protected ArrayList<String> featList;
    //given in feet
    protected double moveSpeed;

    private FileReader fileReader;
    private FileWriter fileWriter;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    protected ArrayList<String> fileList = new ArrayList<String>();

    protected final char INFO_DELIM = ':';
    protected final String LIST_FIELDS = "Spell Slots, Proficiencies, Misc. Proficiencies, Equipment, Feats";

    public PlayerInfo(String name) {
        this.playerName = name;
        readInfoFile(playerName);
    }

    /** Takes in the player's name and opens their file. Reads
     * through the file and initializes the various values based
     * on the information there.
     *
     *
     **/
    protected void readInfoFile(String fileName) {
        String filename = "Grimoire/GrimoireFiles/" + fileName + ".txt";
        String curLine = null;

        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            while ( (curLine = bufferedReader.readLine()) != null) {
                fileList.add(curLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        itemizePlayerList();
    }

    /*
     * Interprets the Stringed information from the file to initialize 
     * all of the values. 
     *
     */
    protected void itemizePlayerList() {
        String loopStr = null;
        String listStr = null;
        for (int i = 0; i < fileList.size(); i++) {
            loopStr = fileList.get(i);
            //If loopStr isn't in the list of lists, find where its value goes ELSE
            if ((loopStr.indexOf(INFO_DELIM) != -1) &&
                !LIST_FIELDS.contains(loopStr.substring(0,loopStr.indexOf(INFO_DELIM)))) {
                    findSingleValue(loopStr);
            } else {
                listStr = loopStr.substring(0, loopStr.indexOf(INFO_DELIM));;
                loopStr = "";
                while (++i < fileList.size()
                    && (fileList.get(i).indexOf(INFO_DELIM) != -1)) {
                    loopStr += fileList.get(i);
                }
                String[] tempArr = loopStr.split("\n");
                switch (listStr) {
                    case "Spell Slots": parseSpellText(tempArr);
                    break;
                    case "Proficiencies": parseProfText(tempArr);
                    break;
                    case "Misc. Proficiencies": otherProfList = new ArrayList(Arrays.asList(tempArr));
                    break;
                    case "Equipment": treasureList = new ArrayList(Arrays.asList(tempArr));
                    break;
                    case "Feats": featList = new ArrayList(Arrays.asList(tempArr));
                    break;
                }
            }
        }

    }

    /*
     * Takes the lines of the listed String array, reads them,
     * and stores the values in the SpellArr
     *
     * @param String[] with each line being N - X 
     */
    protected void parseSpellText(String[] spellLines) {
        int[] spellArr = new int[9];
        for(int i = 0; i < spellLines.length; i++) {
            spellArr[i] = 
                Integer.parseInt(spellLines[i].substring(spellLines.length - 1));
        }
    }

    /*
     * Reads the listed String array into values pertaining to 
     * proficienices. 
     * 
     * TODO: Make this not lazy as fuck. I am tired. This will work.
     * But you can do better than that. I know you've done better than that. 
     * You climbed the slopes of Mount Olympus and slayed Kurt Vonnegutt.
     * You ate the lotus flowers of Nightvale and left of your own volition.
     * You shielded Korabas over the Plains of Elysium.
     * You flew into the Chronoclastic Infundidibulum and exited with no temporal scattering 
     * You spent a night with the Faerie Queen and abused the laws of causality to escape.
     * You.... get the point. 
     * 
     * @param String of the form "Profession"
     */
     protected void parseProfText(String[] inArr) {
        String[] tempArr;
        String localString;
        for (String s : inArr) {
            localString = s;
            localString.replaceAll("\\s","");
            tempArr = localString.split(",");
            proficiencyMap.put(tempArr[0],proficiencyMod(tempArr[1]));
        }
    }

    /**
     * Calculates profiency modifier based off level
     * and attribute modifier. Input should be:
     * Strength
     * Dexterity
     * Constitution
     * Intelligence
     * Wisdom
     * Charisma
     *
     * @param attribute which attribute modifier to add
     * @return int giving total prof. modifier
     */
    private int proficiencyMod(String attribute) {
        return profBonus() + attrMod(attrMap.get(attribute));
    }

    /**
     * Finds the bonus to rolls based on level. As it
     * happens, the profiency bonus can be easily calculated
     * with the below equation.
     * 
     * @return int representing bonus to roll
     */
    protected int profBonus() {
        return (int)Math.floor((level / 4) + 2); 
    }

    /**
     * Calculate ability score modifier.
     *
     * @param attrScore attribute score to calcualte with
     * @return int representing modifier
     */ 
    protected int attrMod(int attrScore) {
        return (int)Math.floor((10 - attrScore) / 2);
    }

    

    /*
     * Takes a single-lined String, reads the type, and puts the value
     * into the required variable.
     *
     * @param String with the name and value that should be parsed
     */
    protected void findSingleValue(String singleStr) {
        switch(singleStr.substring(singleStr.indexOf(INFO_DELIM) + 1)) {
            case "Player Name": 
                playerName = parseInfoLine(singleStr);
            break;
            case "Character Name": 
                basicInfoArr[1] = parseInfoLine(singleStr);
            break;
            case "Class": 
                basicInfoArr[0] = parseInfoLine(singleStr);
            break;
            case "Level": 
                level = Integer.parseInt(parseInfoLine(singleStr));
            break;
            case "Strength":
                attributeArr[0] = Integer.parseInt(parseInfoLine(singleStr));
            break;
            case "Dexterity":
                attributeArr[1] = Integer.parseInt(parseInfoLine(singleStr));
            break;
            case "Constitution:":
                attributeArr[2] = Integer.parseInt(parseInfoLine(singleStr));
            break;
            case "Intelligence":
                attributeArr[3] = Integer.parseInt(parseInfoLine(singleStr));
            break;
            case "Wisdom":
                attributeArr[4] = Integer.parseInt(parseInfoLine(singleStr));
            break;
            case "Charisma":
                attributeArr[5] = Integer.parseInt(parseInfoLine(singleStr));
            break;
            case "Movespeed":
                moveSpeed = Double.parseDouble(parseInfoLine(singleStr));
            break;
            case "HP":
                healthPoints = Integer.parseInt(parseInfoLine(singleStr));
            break;
            case "AC":
                armorClass = Integer.parseInt(parseInfoLine(singleStr));
            break;
            case "Gold":
                gold = Integer.parseInt(parseInfoLine(singleStr));
            break;
        }
    }

    /**
     *Receives a string, a returns the information present after
     *the delimeter and a space
     *
     *@return String
     **/
    protected String parseInfoLine(String curLine) {
        int delimIndex = curLine.indexOf(INFO_DELIM);
        return curLine.substring(delimIndex + 2);
    }


    //Encapsulated return stuff from here down

    /**
     *@breif returns health
     *
     *@return int
     **/
    public int getHealth() {
        return healthPoints;
    }

    /*
     *@brief returns AC
     *
     *@return int
     */
    public int getAC() {
        return armorClass;
    }

    /*
     * @brief returns spell slots of a given level
     *
     * @return int
     */
    public int getSpellSlots(int slotLevel) {
        return spellSlotArr[slotLevel + 1];
    }

    public String getName() {
        return "The player's name is: " + playerName +
            "\nThe character's name is: " + basicInfoArr[2];
    }
}