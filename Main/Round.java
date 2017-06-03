package Main;

import java.util.LinkedList;

/**
 * Created by evanphoward on 6/2/17.
 */
public class Round {
    private static LinkedList<Integer> round = new LinkedList<>();
    public static LinkedList<Integer> getRound(int roundNum) {
        round.clear();
        switch(roundNum) {
            case 1: setDelay(20);
                addEnemy(1,20);
                break;
            case 2: setDelay(12);
                addEnemy(1,30);
                break;
            case 3: setDelay(12);
            addEnemy(1,10);
            setDelay(10);
            addEnemy(2,5);
            setDelay(8);
            addEnemy(1,10);
                break;
            case 4: setDelay(20);
            addEnemy(1,15);
            addEnemy(2,15);
            addEnemy(1,15);
            break;
            case 5: setDelay(25);
            addEnemy(1,5);
            addEnemy(2,25);
            break;
            case 6: setDelay(30);
            addEnemy(3,4);
            setDelay(20);
            addEnemy(1,15);
            addEnemy(2,15);
            break;
            case 7: setDelay(20);
            addEnemy(2,15);
            setDelay(35);
            addEnemy(3,5);
            setDelay(12);
            addEnemy(1,20);
            setDelay(20);
            addEnemy(2,10);
            break;
            case 8:
                setDelay(20);
                addEnemy(2,20);
                setDelay(30);
            addEnemy(3,2);
            setDelay(7);
            addEnemy(1,10);
            setDelay(35);
            addEnemy(3,12);
        }
        return round;
    }

    private static void addEnemy(int level, int num) {
        for(int i=0;i<num;i++)
            round.add(level);
    }
    private static void setDelay(int delay) {
        round.add(-1);
        round.add(delay);
    }
}
