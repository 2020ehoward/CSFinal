package Towers;

import Main.*;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by evanphoward on 5/31/17.
 */
public class FreezeTower extends Tower {
    //this tower does not fire bullets, so it doesn't need a bullet list
    private LinkedList<Enemy> enemies;
    private int cooldown;

    public FreezeTower(Gameboard g, int x, int y) {
        //bullet is null, speed, range, and texture are passed to make the right tower
        super(g,null,new ImageIcon("Textures/Towers/Tower3.png"),x,y,100,2* Gameboard.SQUARESIZE);
        this.cooldown=0;
    }

    public void tick() {
        //enemies is updated from the gameboard
        enemies = getParentBoard().getEnemies();

        if(cooldown==0) {
            //if an enemy is in range
            if(getClosestEnemy(enemies)!=-1) {
                double dist;
                for(Enemy e : enemies) {
                    //go through every enemy on the screen, if it is in range freeze it for 50 ticks
                    dist = Math.sqrt(Math.pow(getX() - (e.getX()+(e.getTexture().getIconWidth()/2)), 2) + Math.pow(getY() - (e.getY()+(e.getTexture().getIconHeight()/2)), 2));
                    if(dist<getRange()) {
                        e.freeze(50);
                        //reset cooldown
                        cooldown=getSpeed();
                    }
                }
            }
        }
        //if it hasn't reached 0, decrease it by one
        if(cooldown>0)
            cooldown--;
    }

    //reset cooldown to 0, allowing it to freeze as soon as enemies reach it
    public void endRound() {
        cooldown=0;
    }
}
