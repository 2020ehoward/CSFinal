package Main;

import java.util.LinkedList;

/**
 * Created by evanphoward on 6/2/17.
 */

/*
This class is used as a static method to get the order of enemies
in a specified round and the delays between each enemy
In the list a -1 followed by a number represents a change in the delay
in the spawning of enemies
Otherwise, a number represents spawning in a enemy of that level
 */
public class Round {
    private static LinkedList<Integer> round = new LinkedList<>();
    //returns a "schedule" of a round, by the number of the round
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
            case 9:
                setDelay(25);
                addEnemy(3,30);
                break;
            case 10:
                setDelay(20);
                addEnemy(2,102);
                break;
            case 11:
                setDelay(30);
                addEnemy(4,2);
                setDelay(23);
                addEnemy(3,12);
                setDelay(20);
                addEnemy(2,10);
                setDelay(14);
                addEnemy(1,10);
                break;
            case 12:
                setDelay(25);
                addEnemy(3,10);
                setDelay(20);
                addEnemy(2,15);
                setDelay(30);
                addEnemy(4,5);
                break;
            case 13:
                for(int i=0;i<4;i++) {
                    setDelay(12);
                    addEnemy(1, 25);
                    setDelay(30);
                    addEnemy(3,1);
                    addEnemy(2,5);
                }
                addEnemy(2,3);
                break;
            case 14:
                setDelay(20);
                addEnemy(1,10);
                addEnemy(2,10);
                addEnemy(1,10);
                addEnemy(3,15);
                addEnemy(1,10);
                addEnemy(4,9);
                addEnemy(1,20);
                break;
            case 15:
                setDelay(15);
                addEnemy(4,20);
                break;
            case 16:
                setDelay(12);
                addEnemy(2,10);
                setDelay(30);
                addEnemy(3,5);
                setDelay(12);
                addEnemy(2,10);
                setDelay(30);
                addEnemy(4,5);
                break;
            case 17:
                setDelay(5);
                addEnemy(1,200);
                break;
            case 18:
                setDelay(20);
                addEnemy(4,10);
                addEnemy(3,20);
                addEnemy(4,10);
                addEnemy(3,20);
                setDelay(2);
                addEnemy(2,30);
                break;
            case 19:
                setDelay(30);
                addEnemy(4,50);
                setDelay(12);
                addEnemy(3,10);
                addEnemy(2,50);
                break;
            case 20:
                setDelay(20);
                for(int i=0;i<30;i++) {
                    addEnemy(1, 1);
                    addEnemy(2,1);
                    addEnemy(3,1);
                    addEnemy(4,1);
                }
                break;
            case 21:
                setDelay(15);
                for(int i=0;i<30;i++) {
                    addEnemy(4,1);
                    addEnemy(3,1);
                    addEnemy(2,1);
                    addEnemy(1,1);
                }
                break;
            case 22:
                setDelay(5);
                addEnemy(3,10);
                setDelay(10);
                addEnemy(4,5);
                setDelay(8);
                addEnemy(2,15);
                setDelay(20);
                addEnemy(4,30);
                break;
            case 23:
                setDelay(18);
                addEnemy(4,50);
                break;
            case 24:
                setDelay(20);
                addEnemy(4,10);
                addEnemy(3,10);
                addEnemy(2,10);
                addEnemy(1,10);
                setDelay(12);
                addEnemy(4,10);
                addEnemy(3,20);
                addEnemy(2,30);
                addEnemy(1,40);
                break;
            case 25:
                setDelay(12);
                addEnemy(4,75);
                break;
            default:
                round.add(-2);
        }
        return round;
    }

    //adds a specified amount of enemies at a specified level
    private static void addEnemy(int level, int num) {
        for(int i=0;i<num;i++)
            round.add(level);
    }

    //changes the delay to the specified number, in number of ticks
    private static void setDelay(int delay) {
        round.add(-1);
        round.add(delay);
    }
}
