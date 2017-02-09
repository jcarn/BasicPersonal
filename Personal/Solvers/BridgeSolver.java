import java.util.*;
import java.awt.Point;
public class BridgeSolver{
	private static double breakChance;
	private static Random rand;
	private static boolean[][] curMap;
	private static int height;
	private static int width;

	public static void main(String[] args){
		//initialize important values
		height = 2;
		width = height + 1;
		breakChance = .50;
		int maxN = 100;
		rand = new Random();
		int crossCount = 0;
		int numTrials = 1000;
		//perform trials and find % that can succeed
		Point finalPos;
		for(int j = 2;j<maxN+2;j++){
		height = j;
		width = height + 1;
		//create our map
		curMap = createMap(height,width);
        //printMap(curMap);
		for(int i = 0;i<numTrials; i++){
			finalPos = crossBridge(new Point(0,0),new Point(0,0));
			if(isFinished(finalPos)){
				crossCount++;
			}
			curMap = createMap(height,width);
		}
		double percent = 1.0 * crossCount / numTrials;
		System.out.printf("\nN: %d P: %.2f", j, percent);
		crossCount = 0; 
		}
		//System.out.printf("You may cross: %.5f of the time. \n",percent);
	}

	//creates a new "map" with broken bridges
	private static boolean[][] createMap(int height,int width){
		//initialize important objects
		int actualWidth = 2*width - 1;
		int actualHeight = 2*(height+2) - 1;
		curMap = new boolean[actualWidth][actualHeight];

		//creates the network of islands and possibly broken bridges
		for(int i = 0; i<actualWidth; i++){
			for(int j = 0; j<actualHeight;j++){
				if(i%2==0 && j%2==0){
					curMap[i][j] = true;
				}else if(i%2==0 || j%2==0){
					if(rand.nextDouble() > breakChance)
						curMap[i][j] = false;
					else
						curMap[i][j] = true;
				}else
					curMap[i][j] = false;
			}
		}

		//creates the shore
		for(int i = 0;i<actualWidth;i++){
			curMap[i][0] = true;
			curMap[i][actualHeight-1] = true;
		}

		return curMap; 
	}

	/*Takes arguments of the bridge array and the current row and column of the island. The shores are treated 
	as islands whose bridges never break.
	*/
	private static Point crossBridge(Point curPos,Point safePos){
		Point newPos = new Point(curPos.x,curPos.y);
		if(isFinished(curPos)){
			return curPos;
		}
		if(southBridge(curPos)){
			newPos.y+=2;
			curMap[newPos.x][newPos.y-1] = false;
			curPos = crossBridge(newPos,curPos);
            newPos = new Point(curPos.x,curPos.y);
		}
        if(eastBridge(curPos)){
			newPos.x+=2;
			curMap[newPos.x-1][newPos.y] = false;
			curPos = crossBridge(newPos,curPos);
            newPos = new Point(curPos.x,curPos.y);
		}
        if(westBridge(curPos)){
			newPos.x-=2;
			curMap[newPos.x+1][newPos.y] = false;
			curPos = crossBridge(newPos,curPos);
            newPos = new Point(curPos.x,curPos.y);
		}
        if(northBridge(curPos)){
			newPos.y-=2;
			curMap[newPos.x][newPos.y+1] = false;
			curPos = crossBridge(newPos,curPos);
            newPos = new Point(curPos.x,curPos.y);
		}
		if(isFinished(curPos))
			return curPos;
		return safePos;
	}

	private static boolean isFinished(Point curPos){
		if(curPos.y == 2 * (height + 2) - 2){
			return true;
		}
		return false;
	}

	private static boolean isStuck(Point curPos){
		if(eastBridge(curPos) ||
			southBridge(curPos) ||
			westBridge(curPos) ||
			northBridge(curPos))
			return false;
		return true;
	}

	private static boolean southBridge(Point curPos){
		if(curPos.y + 1 < 2*(height + 2) - 1)
			return curMap[curPos.x][curPos.y+1];
		else
			return false;
	}
	private static boolean eastBridge(Point curPos){
		if(curPos.x + 1 < 2*width - 1) {
			return curMap[curPos.x+1][curPos.y];
		} else
			return false;
	}
	private static boolean westBridge(Point curPos){
		if(curPos.x - 1 > 0) {
			return curMap[curPos.x-1][curPos.y];
		} else
			return false;
	}
	private static boolean northBridge(Point curPos){
		if(curPos.y- 1 > 0)
			return curMap[curPos.x][curPos.y-1];
		else
			return false;
	}

    private static void printMap(boolean[][] curMap) {
        String printStr = "";
        for (int i = 0; i < curMap[0].length; i++) {
            for (int j = 0; j < curMap.length; j++) {
                if (curMap[j][i]) {
                    printStr = printStr + "1";
                } else {
                    printStr = printStr + "0";
                }
            }
            System.out.println(printStr);
            printStr = "";
        }
    }

}
