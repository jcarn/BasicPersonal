import java.util.*;

public class Test {
    public static void main(String[] args) {
        String s = "    Hello, I";
        s = s.replaceAll("\\s+","");
        String[] tempArr = s.split(",");
        editStringArr(tempArr);
        System.out.println("||" + tempArr[0] + "||"+tempArr[1] + "||");   
    }

    private static void editStringArr(String[] inArr) {
        String hello = inArr[1];
        hello = "WAYYOYO";
    }
}