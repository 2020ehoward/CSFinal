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
            case 1: round.add(20);
                for(int i=0;i<20;i++)
                round.add(1);
                break;
            case 2: round.add(12);
                for(int i=0;i<30;i++)
                    round.add(1);
                break;
            case 3: round.add(12);
            for(int i=0;i<10;i++)
                round.add(1);
            round.add(10);
            for(int i=0;i<5;i++)
                round.add(2);
            round.add(8);
            for(int i=0;i<10;i++)
                round.add(1);
            break;
        }
        return round;
    }
}
