package Towers;

import Main.*;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by evanphoward on 5/31/17.
 */
public class FreezeTower extends Tower {
    private LinkedList<Enemy> enemies;
    private int cooldown;

    public FreezeTower(Gameboard g, int x, int y) {
        super(g,null,new ImageIcon("Textures/Towers/Tower3.png"),x,y,100,2* Gameboard.SQUARESIZE);
        this.cooldown=0;
    }

    public void tick() {
        enemies = getParentBoard().getEnemies();

        if(cooldown==0) {
            if(getClosestEnemy(enemies)!=-1) {
                double dist;
                for(Enemy e : enemies) {
                    dist = Math.sqrt(Math.pow(getX() - (e.getX()+(e.getTexture().getIconWidth()/2)), 2) + Math.pow(getY() - (e.getY()+(e.getTexture().getIconHeight()/2)), 2));
                    if(dist<getRange()) {
                        e.freeze(50);
                        cooldown=getSpeed();
                    }
                }
            }
        }
        if(cooldown>0)
            cooldown--;
    }

    public void endRound() {
        cooldown=0;
    }
}
